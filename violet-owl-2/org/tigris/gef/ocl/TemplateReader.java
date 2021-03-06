// Copyright (c) 1996-99 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.tigris.gef.ocl;

import java.util.*;
import java.util.StringTokenizer;
import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

public class TemplateReader extends DefaultHandler {

    private final static TemplateReader INSTANCE = new TemplateReader();

    private static final Log LOG = LogFactory.getLog(TemplateReader.class);

    private Hashtable _templates; /* Class -> Vector of TemplateRecord */
    private Vector _macros;

    private TemplateRecord _currentTemplate = null;
    private MacroRecord _currentMacro = null;

    private String filename;

    protected TemplateReader() {
    }

    public static TemplateReader getInstance() {
        return INSTANCE;
    }

    public Hashtable read(String filename) throws ExpansionException {
        this.filename = filename;
        InputStream in = null;
        try {
            try {
                in = TemplateReader.class.getResourceAsStream(filename);
            } catch (Exception ex) {
                String relativePath = filename;
                if (relativePath.startsWith("/")) {
                    relativePath = relativePath.substring(1);
                }
                in = new FileInputStream(relativePath);
            }

            _templates = new Hashtable();
            _macros = new Vector();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            SAXParser pc = factory.newSAXParser();
            InputSource source = new InputSource(in);
            source.setSystemId(new java.net.URL("file", null, filename)
                    .toString());
            pc.parse(source, this);
        } catch (Exception ex) {
            throw new ExpansionException(ex);
        }
        return _templates;
    }

    public void setDocumentLocator(Locator locator) {
    }

    public void startDocument() {
        _currentTemplate = null;
        _currentMacro = null;
    }

    public void endDocument() {
        _currentTemplate = null;
        _currentMacro = null;
    }

    public void ignorableWhitespace(char[] ch, int start, int length) {
    }

    public void processingInstruction(String target, String data) {
    }

    public void startElement(String uri, String localname, String elementName,
            Attributes atts) throws SAXException {
        if (elementName.equals("template")) {
            String guard = atts.getValue("guard");
            String className = atts.getValue("class");
            LOG.debug("Start template guard=\"" + guard + "\" class=\""
                    + className + "\"");
            java.lang.Class classObj = null;
            Object objToStack = null;
            try {
                classObj = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new SAXException("Can't find the class " + className
                        + " refered to in the file " + filename, e);
            }

            _currentTemplate = new TemplateRecord(classObj, guard, null);
            _currentMacro = null;

        } else if (elementName.equals("macro")) {
            String name = atts.getValue("name");
            _currentMacro = new MacroRecord(name, null);
            _currentTemplate = null;
        } else {
            _currentMacro = null;
            _currentTemplate = null;
            if (!elementName.equals("TemplateSet")) {
                throw new SAXException("unknown tag: " + elementName);
            }
        }
    }

    public void characters(char[] ch, int start, int length) {
        if (_currentMacro != null) {
            _currentMacro.characters(ch, start, length);
        } else {
            if (_currentTemplate != null) {
                _currentTemplate.characters(ch, start, length);
            }
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localname, String elementName)
            throws SAXException {
        if (_currentTemplate != null && elementName.equals("template")) {
            String body = _currentTemplate.getBody().trim();
            body = expandMacros(body);
            _currentTemplate.setBody(body);
            Class classObj = _currentTemplate.getKey();
            Vector existing = (Vector) _templates.get(classObj);
            if (existing == null) {
                existing = new Vector();
            }
            existing.addElement(_currentTemplate);
            _templates.put(classObj, existing);
            _currentTemplate = null;
        } else if (_currentMacro != null && elementName.equals("macro")) {
            String body = _currentMacro.getBody().trim();
            body = expandMacros(body);
            _currentMacro.setBody(body);
            boolean inserted = false;
            int newNameLength = _currentMacro.getName().length();
            int size = _macros.size();
            for (int i = 0; i < size && !inserted; i++) {
                String n = ((MacroRecord) _macros.elementAt(i)).name;
                if (n.length() < newNameLength) {
                    _macros.insertElementAt(_currentMacro, i);
                    inserted = true;
                }
            }
            if (!inserted) {
                _macros.addElement(_currentMacro);
            }
            _currentMacro = null;
        }
    }

    public String expandMacros(String body) {
        StringBuffer resultBuffer = new StringBuffer(body.length() * 2);
        StringTokenizer st = new StringTokenizer(body, "\n\r");
        while (st.hasMoreElements()) {
            String line = st.nextToken();
            String expanded = expandMacrosOnOneLine(line);
            resultBuffer.append(expanded);
            resultBuffer.append("\n");
        }
        return resultBuffer.toString();
    }

    /** each line can have at most one macro */
    public String expandMacrosOnOneLine(String body) {
        int numMacros = _macros.size();
        for (int i = 0; i < numMacros; i++) {
            String k = ((MacroRecord) _macros.elementAt(i)).name;
            int findIndex = body.indexOf(k);
            if (findIndex != -1) {
                String mac = ((MacroRecord) _macros.elementAt(i)).body;
                StringBuffer resultBuffer;
                String prefix = body.substring(0, findIndex);
                String suffix = body.substring(findIndex + k.length());
                resultBuffer = new StringBuffer(mac.length()
                        + (prefix.length() + suffix.length()) * 10);
                StringTokenizer st = new StringTokenizer(mac, "\n\r");
                while (st.hasMoreElements()) {
                    resultBuffer.append(prefix);
                    resultBuffer.append(st.nextToken());
                    resultBuffer.append(suffix);
                    if (st.hasMoreElements())
                        resultBuffer.append("\n");
                }
                return resultBuffer.toString();
            }
        }
        return body;
    }
} /* end class TemplateReader */

class MacroRecord {
    String name;
    String body;
    private StringBuffer _buf = null;

    MacroRecord(String n, String b) {
        name = n;
        body = b;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        if (_buf != null) {
            body = _buf.toString();
        }
        return body;
    }

    public void setBody(String b) {
        body = b;
        _buf = null;
    }

    public void characters(char[] ch, int start, int length) {
        if (_buf == null) {
            _buf = new StringBuffer();
            if (body != null) {
                _buf.append(body);
            }
        }
        _buf.append(ch, start, length);
    }
} /* end class MacroRecord */

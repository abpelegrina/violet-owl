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

// File: Converter.java
// Classes: Converter
// Original Author: Toby.Baier@gmx.net
// $Id: Converter.java 1153 2008-11-30 16:14:45Z bobtarling $

package org.tigris.gef.util;

import java.util.Enumeration;

/**
 * This Class is a utility to convert java.util.*-classes to java.util.*-classes
 */

public class Converter {

    public static java.util.Hashtable convert(java.util.Hashtable oldOne) {
        if (oldOne == null)
            return null;
        java.util.Hashtable newOne = new java.util.Hashtable();
        Enumeration oldKeys = oldOne.keys();
        while (oldKeys.hasMoreElements()) {
            Object o = oldKeys.nextElement();
            newOne.put(o, oldOne.get(o));
        }

        return newOne;
    }

    public static java.util.Vector convert(java.util.Vector oldOne) {
        if (oldOne == null)
            return null;
        java.util.Vector newOne = new java.util.Vector();
        for (int i = 0; i < oldOne.size(); i++) {
            newOne.addElement(oldOne.elementAt(i));
        }
        return newOne;
    }

    public static java.util.Vector convertCollection(java.util.Collection oldCol) {
        if (oldCol == null)
            return null;

        java.util.Vector newVec = new java.util.Vector();
        java.util.Iterator iter = oldCol.iterator();
        while (iter.hasNext()) {
            newVec.addElement(iter.next());
        }
        return newVec;
    }
}
/* end class Converter */

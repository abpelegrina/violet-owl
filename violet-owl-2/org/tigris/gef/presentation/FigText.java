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

// File: FigText.java
// Classes: FigText
// Original Author: ics125 spring 1996
// $Id: FigText.java 1153 2008-11-30 16:14:45Z bobtarling $

package org.tigris.gef.presentation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tigris.gef.persistence.export.FontUtility;
import org.tigris.gef.properties.PropCategoryManager;
import org.tigris.gef.undo.Memento;
import org.tigris.gef.undo.UndoManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.StringTokenizer;

import javax.swing.JLabel;

/**
 * This class handles painting and editing text Fig's in a LayerDiagram.
 */

public class FigText extends Fig implements KeyListener, MouseListener {

    private static final long serialVersionUID = 4659312817576456477L;

    public static final int IGNORE = 0;
    public static final int INSERT = 1;
    public static final int END_EDITING = 2;

    private int returnAction = IGNORE;
    private int tabAction = IGNORE;

    /** Constants to specify text justification. */
    public static final int JUSTIFY_LEFT = 0;
    public static final int JUSTIFY_RIGHT = 1;
    public static final int JUSTIFY_CENTER = 2;

    /** Minimum size of a FigText object. */
    public static final int MIN_TEXT_WIDTH = 30;

    /**
     * The internal representation of a return character.
     */
    private static final char HARD_RETURN = '\n';
    /**
     * The internal representation of a return due to word wrap.
     */
    private static final char SOFT_RETURN = '\r';

    /** Font info. */
    private Font _font = new Font("TimesRoman", Font.PLAIN, 10);
    private transient FontMetrics _fm;
    private int _lineHeight;

    /** Color of the actual text characters. */
    private Color _textColor = Color.black;

    /**
     * Color to be drawn behind the actual text characters. Note that this will
     * be a smaller area than the bounding box which is filled with FillColor.
     */
    private Color textFillColor = Color.white;

    /**
     * True if the area behind individual characters is to be filled with
     * TextColor.
     */
    private boolean _textFilled = false;

    /** True if the text should be editable. False for read-only. */
    private boolean editable = true;

    private Class _textEditorClass = FigTextEditor.class;

    /** True if the text should be underlined. needs-more-work. */
    private boolean _underline = false;

    /**
     * True if word wrap is to take place when editing multiline text. False by
     * default (for backwards compatability)
     */
    private boolean wordWrap = false;

    /** Extra spacing between lines. Default is 0 pixels. */
    private int _lineSpacing = 0;

    /** Internal margins between the text and the edge of the rectangle. */
    private int _topMargin = 0;
    private int _botMargin = 0;
    private int _leftMargin = 0;
    private int _rightMargin = 0;

    /** True if the FigText can only grow in size, never shrink. */
    private boolean _expandOnly = false;

    private boolean _editMode = false;

    /** Text justification can be JUSTIFY_LEFT, JUSTIFY_RIGHT, or JUSTIFY_CENTER. */
    private int _justification = JUSTIFY_LEFT;

    /**
     * The current string to display. This is in an encoded format and so should
     * never be directly available to the client code. Client code can use the
     * accessor methods to view and amend this value with no knowledge of the
     * encoding.
     */
    private String _curText;

    private String lineSeparator;

    private TextEditor textEditor;

    private static TextEditor activeTextEditor;

    // //////////////////////////////////////////////////////////////
    // static initializer

    private static final Log LOG = LogFactory.getLog(FigText.class);

    /**
     * This puts the text properties on the "Text" and "Style" pages of the
     * org.tigris.gef.ui.TabPropFrame.
     */
    static {
        PropCategoryManager.categorizeProperty("Text", "font");
        PropCategoryManager.categorizeProperty("Text", "underline");
        PropCategoryManager.categorizeProperty("Text", "expandOnly");
        PropCategoryManager.categorizeProperty("Text", "lineSpacing");
        PropCategoryManager.categorizeProperty("Text", "topMargin");
        PropCategoryManager.categorizeProperty("Text", "botMargin");
        PropCategoryManager.categorizeProperty("Text", "leftMargin");
        PropCategoryManager.categorizeProperty("Text", "rightMargin");
        PropCategoryManager.categorizeProperty("Text", "text");
        PropCategoryManager.categorizeProperty("Style", "justification");
        PropCategoryManager.categorizeProperty("Style", "textFilled");
        PropCategoryManager.categorizeProperty("Style", "textFillColor");
        PropCategoryManager.categorizeProperty("Style", "textColor");
    }

    // //////////////////////////////////////////////////////////////
    // constructors

    /**
     * Construct a new FigText with the given position, size, color, string,
     * font, and font size. Text string is initially empty and centered.
     */
    public FigText(int x, int y, int w, int h, Color textColor,
            String familyName, int fontSize, boolean expandOnly) {
        super(x, y, w, h);
        _x = x;
        _y = y;
        _w = w;
        _h = h;
        _textColor = textColor;
        _font = new Font(familyName, Font.PLAIN, fontSize);
        _justification = JUSTIFY_CENTER;
        _curText = "";
        _expandOnly = expandOnly;
        lineSeparator = System.getProperty("line.separator");
    }

    public FigText(int x, int y, int w, int h, Color textColor,
            String familyName, int fontSize) {
        this(x, y, w, h, textColor, familyName, fontSize, false);
    }

    public FigText(int x, int y, int w, int h, Color textColor, Font font) {
        this(x, y, w, h, textColor, font.getName(), font.getSize());
    }

    /** Construct a new FigText with the given position and size */
    public FigText(int x, int y, int w, int h) {
        super(x, y, w, h);
        _x = x;
        _y = y;
        _w = w;
        _h = h;
        _justification = JUSTIFY_CENTER;
        _curText = "";
        _expandOnly = false;
        lineSeparator = System.getProperty("line.separator");
    }

    /** Construct a new FigText with the given position, size, and attributes. */
    public FigText(int x, int y, int w, int h, boolean expandOnly) {
        super(x, y, w, h);
        _x = x;
        _y = y;
        _w = w;
        _h = h;
        _justification = JUSTIFY_CENTER;
        _curText = "";
        _expandOnly = expandOnly;
        lineSeparator = System.getProperty("line.separator");
    }

    // //////////////////////////////////////////////////////////////
    // accessors

    /**
     * Reply a string that indicates how the text is justified: Left, Center, or
     * Right.
     */
    public String getJustificationByName() {
        if (_justification == JUSTIFY_LEFT)
            return "Left";
        else if (_justification == JUSTIFY_CENTER)
            return "Center";
        else if (_justification == JUSTIFY_RIGHT)
            return "Right";
        LOG.error("internal error, unknown text alignment");
        return "Unknown";
    }

    /**
     * Set the text justification given one of these strings: Left, Center, or
     * Right.
     */
    public void setJustificationByName(String justifyString) {
        if (justifyString.equals("Left"))
            _justification = JUSTIFY_LEFT;
        else if (justifyString.equals("Center"))
            _justification = JUSTIFY_CENTER;
        else if (justifyString.equals("Right"))
            _justification = JUSTIFY_RIGHT;
        _fm = null;
    }

    // //////////////////////////////////////////////////////////////
    // accessors and modifiers

    /**
     * Get the font metrics.
     */
    protected FontMetrics getFontMetrics() {
        return _fm;
    }

    public Color getTextColor() {
        return _textColor;
    }

    public void setTextColor(Color c) {
        firePropChange("textColor", _textColor, c);
        _textColor = c;
    }

    public Color getTextFillColor() {
        return textFillColor;
    }

    public void setTextFillColor(Color c) {
        firePropChange("textFillColor", textFillColor, c);
        textFillColor = c;
    }

    public boolean getTextFilled() {
        return _textFilled;
    }

    public void setTextFilled(boolean b) {
        firePropChange("textFilled", _textFilled, b);
        _textFilled = b;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean e) {
        firePropChange("editable", editable, e);
        editable = e;
    }

    public boolean getUnderline() {
        return _underline;
    }

    public void setUnderline(boolean b) {
        firePropChange("underline", _underline, b);
        _underline = b;
    }

    public int getJustification() {
        return _justification;
    }

    public void setJustification(int align) {
        firePropChange("justification", getJustification(), align);
        _justification = align;
    }

    public int getLineSpacing() {
        return _lineSpacing;
    }

    public void setLineSpacing(int s) {
        firePropChange("lineSpacing", _lineSpacing, s);
        _lineSpacing = s;
        calcBounds();
    }

    public int getTopMargin() {
        return _topMargin;
    }

    public void setTopMargin(int m) {
        firePropChange("topMargin", _topMargin, m);
        _topMargin = m;
        calcBounds();
    }

    public int getBotMargin() {
        return _botMargin;
    }

    public void setBotMargin(int m) {
        firePropChange("botMargin", _botMargin, m);
        _botMargin = m;
        calcBounds();
    }

    public int getLeftMargin() {
        return _leftMargin;
    }

    public void setLeftMargin(int m) {
        firePropChange("leftMargin", _leftMargin, m);
        _leftMargin = m;
        calcBounds();
    }

    public int getRightMargin() {
        return _rightMargin;
    }

    public void setRightMargin(int m) {
        firePropChange("rightMargin", _rightMargin, m);
        _rightMargin = m;
        calcBounds();
    }

    public boolean getExpandOnly() {
        return _expandOnly;
    }

    public void setExpandOnly(boolean b) {
        firePropChange("expandOnly", _expandOnly, b);
        _expandOnly = b;
    }

    public Font getFont() {
        return _font;
    }

    public void setFont(Font f) {
        firePropChange("font", _font, f);
        _font = f;
        _fm = null;
        calcBounds();
    }

    /**
     * USED BY PGML.tee
     */
    public String getFontFamily() {
        return _font.getFamily();
    }

    /**
     * USED BY PGML.tee
     */
    public void setFontFamily(String familyName) {
        Font f = new Font(familyName, _font.getStyle(), _font.getSize());
        setFont(f);
    }

    /**
     * USED BY PGML.tee
     */
    public int getFontSize() {
        return _font.getSize();
    }

    /**
     * USED BY PGML.tee
     */
    public void setFontSize(int size) {
        Font f = new Font(_font.getFamily(), _font.getStyle(), size);
        setFont(f);
    }

    public boolean getItalic() {
        return _font.isItalic();
    }

    public void setItalic(boolean b) {
        int style = (getBold() ? Font.BOLD : 0) + (b ? Font.ITALIC : 0);
        Font f = new Font(_font.getFamily(), style, _font.getSize());
        setFont(f);
    }

    public boolean getBold() {
        return _font.isBold();
    }

    public void setBold(boolean b) {
        int style = (b ? Font.BOLD : 0) + (getItalic() ? Font.ITALIC : 0);
        setFont(new Font(_font.getFamily(), style, _font.getSize()));
    }

    /**
     * @deprecated see setReturnAction and setLineSeparator
     * @param b
     */
    public void setMultiLine(boolean b) {
        if (b) {
            returnAction = INSERT;
        } else {
            returnAction = END_EDITING;
        }
    }

    /**
     * Configure the characters interpret as line separators
     * 
     * @param lineSeparator
     *                the characters to treat as a return press. This can be
     *                System.getProperty("lineSeparator") or some specific
     *                string if the application requires this value to work
     *                across different platforms. The only valid values are
     *                "\r\n", "\r" or "\n".
     */
    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    /**
     * @deprecated use getReturnAction()
     * @return true if multiple lines of text can be entered.
     */
    public boolean getMultiLine() {
        return returnAction == INSERT;
    }

    public boolean isWordWrap() {
        return wordWrap;
    }

    /**
     * Specifies what action the control should take on return press.
     * 
     * @param action
     *                values are IGNORE, INSERT or END_EDITING
     */
    public void setReturnAction(int action) {
        returnAction = action;
    }

    /**
     * Specifies what action the control should take on tab press.
     * 
     * @param action
     *                values are IGNORE, INSERT or END_EDITING
     */
    public void setTabAction(int action) {
        tabAction = action;
    }

    /**
     * Discover what action the control will take on tab press.
     * 
     * @return IGNORE, INSERT or END_EDITING
     */
    public int getTabAction() {
        return tabAction;
    }

    /**
     * Discover what action the control will take on return press.
     * 
     * @return IGNORE, INSERT or END_EDITING
     */
    public int getReturnAction() {
        return returnAction;
    }

    /**
     * Allow tab presses to be inserted into text during edit
     * 
     * @param b
     *                true if return presses are insertable
     * @deprecated use setTabAction(...)
     */
    public void setAllowsTab(boolean b) {
        if (b) {
            tabAction = INSERT;
        } else {
            tabAction = END_EDITING;
        }
    }

    /**
     * Turn on/off word wrapping of text.
     * 
     * @param b
     *                true if word wrap is to take place
     */
    public void setWordWrap(boolean b) {
        wordWrap = b;
    }

    /**
     * @deprecated use getTabAction()
     */
    public boolean getAllowsTab() {
        return tabAction == INSERT;
    }

    /**
     * Remove the last char from the current string line and return the new
     * string. Called whenever the user hits the backspace key. Needs-More-Work:
     * Very slow. This will eventually be replaced by full text editing... if
     * there are any volunteers to do that...
     */
    public String deleteLastCharFromString(String s) {
        int len = Math.max(s.length() - 1, 0);
        char[] chars = s.toCharArray();
        return new String(chars, 0, len);
    }

    /**
     * Delete the last char from the current string. Called whenever the user
     * hits the backspace key
     */
    public void deleteLastChar() {
        _curText = deleteLastCharFromString(_curText);
        calcBounds();
    }

    /** Append a character to the current String . */
    public void append(char c) {
        setText(_curText + c);
    }

    /** Append the given String to the current String. */
    public void append(String s) {
        setText(_curText + s);
    }

    /**
     * Set the give string to be the current string of this fig. Update the
     * current font and font metrics first.
     * 
     * @param str
     *                String to be set at this object.
     * @param graphics
     *                Graphics context for the operation.
     */
    public void setText(String str, Graphics graphics) {
        if (graphics != null) {
            _fm = graphics.getFontMetrics(_font);
        }

        setText(str);
    }

    /**
     * Sets the given string to the current string of this fig.
     * 
     * @param s
     */
    public void setText(String s) {
        String newText = encode(s, lineSeparator);
        if (textEditor != null && !_curText.equals(newText)) {
            textEditor.cancelEditing();
        }
        _curText = newText;
        calcBounds();
        _editMode = false;
    }

    /**
     * Set the give string to be the current string of this fig. Update the
     * current font and font metrics first.
     * 
     * @param str
     *                String to be set at this object.
     * @param graphics
     *                Graphics context for the operation.
     */
    void setTextFriend(String str, Graphics graphics) {
        if (graphics != null) {
            _fm = graphics.getFontMetrics(_font);
        }

        setTextFriend(str);
    }

    /**
     * Sets the given string to the current string of this fig.
     * 
     * @param s
     */
    void setTextFriend(String s) {
        if (UndoManager.getInstance().isGenerateMementos()
                && getOwner(this) == null) {
            Memento memento = new Memento() {
                String oldText = _curText;

                public void undo() {
                    _curText = oldText;
                    redraw();
                }

                public void redo() {
                    undo();
                }

                public void dispose() {
                }
            };
            UndoManager.getInstance().addMemento(memento);
        }
        _curText = encode(s, System.getProperty("line.separator"));
        calcBounds();
        _editMode = false;
    }

    /**
     * Determine the owner of the given Fig by recursing up through groups until
     * an owner is found
     */
    private Object getOwner(Fig fig) {
        Object owner = fig.getOwner();
        if (owner != null) {
            return owner;
        }
        Fig figGroup = fig.getGroup();
        if (figGroup == null) {
            return null;
        } else {
            return getOwner(figGroup);
        }
    }

    /**
     * Get the String held by this FigText. Multi-line text is represented by
     * newline characters embedded in the String. USED BY PGML.tee
     */
    public String getText() {
        String text = decode(_curText, lineSeparator);
        return text;
    }

    /**
     * Get the String held by this FigText. Multi-line text is represented by
     * newline characters embedded in the String.
     */
    public String getTextFriend() {
        return decode(_curText, System.getProperty("line.separator"));
    }

    /**
     * @deprecated in 0.11.1 it appears that the editor must always be
     *             FigTextEditor
     */
    public Class getTextEditorClass() {
        return _textEditorClass;
    }

    /**
     * @deprecated in 0.11.1 it appears that the editor must always be
     *             FigTextEditor
     */
    public void setTextEditorClass(Class editorClass) {
        _textEditorClass = editorClass;
    }

    /**
     * Paint the FigText. Distingusih between linewidth=1 and >1 If <linewidth>
     * is equal 1, then paint a single rectangle Otherwise paint <linewidth>
     * nested rectangles, whereas every rectangle is painted of 4 connecting
     * lines.
     */
    public void paint(Graphics g) {
        if (!(isVisible())) {
            return;
        }

        int chunkX = _x + _leftMargin;
        int chunkY = _y + _topMargin;
        StringTokenizer lines;

        int lineWidth = getLineWidth();

        if (getFilled()) {
            g.setColor(getFillColor());
            g.fillRect(_x, _y, _w, _h);
        }
        if (lineWidth > 0) {
            g.setColor(getLineColor());
            // test linewidth
            if (lineWidth == 1) {
                // paint single rectangle
                g.drawRect(_x, _y, _w - lineWidth, _h - lineWidth);
            } else {
                // paint <linewidth rectangles
                for (int i = 0; i < lineWidth; i++) {
                    // a rectangle is painted as four connecting lines
                    g.drawLine(_x + i, _y + i, _x + _w - i, _y + i);
                    g.drawLine(_x + _w - i, _y + i, _x + _w - i, _y + _h - i);
                    g.drawLine(_x + _w - i, _y + _h - i, _x + i, _y + _h - i);
                    g.drawLine(_x + i, _y + _h - i, _x + i, _y + i);
                }
            }
        }
        if (_font != null) {
            g.setFont(_font);
        }

        _fm = g.getFontMetrics(_font);
        int chunkH = _fm.getHeight() + _lineSpacing;
        chunkY = _y + _topMargin + chunkH;

        // TODO: StringTokenizer is not very efficient and we are possibly
        // iterating through the same infomation twice.
        // Can we improve this?
        if (_textFilled) {
            g.setColor(textFillColor);
            lines = new StringTokenizer(_curText, "" + HARD_RETURN
                    + SOFT_RETURN, true);
            while (lines.hasMoreTokens()
                    && chunkY <= getHeight() + getY() + _topMargin - _botMargin) {
                String curLine = lines.nextToken();
                int chunkW = _fm.stringWidth(curLine);
                switch (_justification) {
                case JUSTIFY_LEFT:
                    break;
                case JUSTIFY_CENTER:
                    chunkX = _x + (_w - chunkW) / 2;
                    break;
                case JUSTIFY_RIGHT:
                    chunkX = _x + _w - chunkW - _rightMargin;
                    break;
                }
                if (curLine.charAt(0) == HARD_RETURN
                        || curLine.charAt(0) == SOFT_RETURN)
                    chunkY += chunkH;
                else
                    g.fillRect(chunkX, chunkY - chunkH, chunkW, chunkH);
            }
        }

        g.setColor(_textColor);
        chunkX = _x + _leftMargin;
        chunkY = _y + _topMargin + chunkH;
        lines = new StringTokenizer(_curText, "" + HARD_RETURN + SOFT_RETURN,
                true);
        while (lines.hasMoreTokens()
                && chunkY <= getHeight() + getY() + _topMargin - _botMargin) {
            String curLine = lines.nextToken();
            int chunkW = _fm.stringWidth(curLine);
            switch (_justification) {
            case JUSTIFY_LEFT:
                break;
            case JUSTIFY_CENTER:
                chunkX = _x + (_w - chunkW) / 2;
                break;
            case JUSTIFY_RIGHT:
                chunkX = _x + _w - chunkW;
                break;
            }
            if (isHardReturn(curLine) || isSoftReturn(curLine)) {
                chunkY += chunkH;
            } else {
                if (_underline) {
                    g.drawLine(chunkX, chunkY + 1, chunkX + chunkW, chunkY + 1);
                }
                drawString(g, curLine, chunkX, chunkY);
            }
        }
    }

    public void appendSvg(StringBuffer sb) {
        sb.append("<text id ='").append(getId()).append("' x='").append(getX())
                .append("' y='").append(getY()).append(
                        "' transform='translate(").append(getFontSize())
                .append(',').append(getFontSize()).append(")'");
        appendSvgStyle(sb);
        sb.append(">").append(getText()).append("</text>");
    }

    protected void appendSvgStyle(StringBuffer sb) {
        sb.append(" style='fill:rgb(").append(getFillColor().getRed()).append(
                ",").append(getFillColor().getGreen()).append(",").append(
                getFillColor().getBlue()).append(");").append("stroke-width:")
                .append(getLineWidth()).append(";").append("stroke:rgb(")
                .append(getLineColor().getRed()).append(",").append(
                        getLineColor().getGreen()).append(",").append(
                        getLineColor().getBlue()).append(");'").append("font:")
                .append(getFontFamily()).append("; font-size:").append(
                        getFontSize()).append("'");
    }

    /**
     * Draws the given string starting at the given position. The position
     * indicates the baseline of the text. This method enables subclasses of
     * FigText to either change the displayed text or the starting position.
     * 
     * @param graphics
     *                Graphic context for drawing the string.
     * @param curLine
     *                The current text to be drawn.
     * @param xPos
     *                X-Coordinate of the starting point.
     * @param yPos
     *                Y-Coordinate of the starting point.
     */
    protected void drawString(Graphics graphics, String curLine, int xPos,
            int yPos) {
        graphics.drawString(curLine, xPos, yPos);
    }

    /**
     * Mouse clicks are handled differently that the default Fig behavior so
     * that it is easier to select text that is not filled. Needs-More-Work:
     * should actually check the individual text rectangles.
     */
    public boolean hit(Rectangle r) {
        int cornersHit = countCornersContained(r.x, r.y, r.width, r.height);
        return cornersHit > 0;
    }

    public int getMinimumHeight() {
        if (_fm != null)
            return _fm.getHeight();
        if (_font != null)
            return _font.getSize();
        return 0;
    }

    public int getTextBounds(Graphics graphics) {
        if (_font != null) {
            FontMetrics fontMetrics = graphics.getFontMetrics();
            return fontMetrics.stringWidth(getText());
        } else
            return 0;
    }

    public Dimension getMinimumSize() {
        Dimension d = new Dimension(0, 0);
        stuffMinimumSize(d);
        return d;
    }

    public void stuffMinimumSize(Dimension d) {
        if (_font == null) {
            return;
        }
        int overallW = 0;
        int numLines = 1;
        StringTokenizer lines = new StringTokenizer(_curText, "" + HARD_RETURN
                + SOFT_RETURN, true);
        while (lines.hasMoreTokens()) {
            String curLine = lines.nextToken();
            int chunkW = _fm.stringWidth(curLine);
            if (curLine.charAt(0) == HARD_RETURN
                    || curLine.charAt(0) == SOFT_RETURN) {
                numLines++;
            } else {
                overallW = Math.max(chunkW, overallW);
            }
        }
        // _lineHeight = _fm.getHeight();
        // int maxDescent = _fm.getMaxDescent();
        if (_fm == null) {
            _lineHeight = _font.getSize();
        } else {
            _lineHeight = _fm.getHeight();
        }
        int maxDescent = 0;
        int overallH = (_lineHeight + _lineSpacing) * numLines + _topMargin
                + _botMargin + maxDescent;
        overallH = Math.max(overallH, getMinimumHeight());
        overallW = Math.max(MIN_TEXT_WIDTH, overallW + _leftMargin
                + _rightMargin);
        d.width = overallW;
        d.height = overallH;
        // System.out.println("FigText.minimumSize: " + getText() + " = " +
        // overallW + " / " + overallH);
    }

    // //////////////////////////////////////////////////////////////
    // event handlers: KeyListener implemtation

    /**
     * When the user presses a key when a FigText is selected, that key should
     * be added to the current string and we start editing.
     */
    public void keyTyped(KeyEvent ke) {
        // This code must be in keyTyped rather than keyPressed.
        // If in keyPressed some platforms will automatically add the pressed
        // key to the editor when it opens others do not.
        // Using keyTyped it is not automatically added and we do so ourselves
        // if it is not some control character.
        if (isStartEditingKey(ke) && editable) {
            ke.consume();
            TextEditor te = startTextEditor(ke);
            if (!Character.isISOControl(ke.getKeyChar())) {
                te.setText(te.getText() + ke.getKeyChar());
            }
        }
    }

    public void keyPressed(KeyEvent ke) {
    }

    public void keyReleased(KeyEvent ke) {
    }

    protected boolean isStartEditingKey(KeyEvent ke) {
        return (!Character.isISOControl(ke.getKeyChar()));
    }

    // //////////////////////////////////////////////////////////////
    // event handlers: KeyListener implemtation

    public void mouseClicked(MouseEvent me) {
        if (me.isConsumed())
            return;
        if (me.getClickCount() >= 2 && editable) {
            startTextEditor(me);
            me.consume();
        }
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public TextEditor startTextEditor(InputEvent ie) {
        textEditor = new FigTextEditor(this, ie);
        activeTextEditor = textEditor;
        _editMode = true;
        return textEditor;
    }

    public static TextEditor getActiveTextEditor() {
        return activeTextEditor;
    }

    public void endTextEditor() {
        textEditor.endEditing();
    }

    public void cancelTextEditor() {
        textEditor.cancelEditing();
    }

    // //////////////////////////////////////////////////////////////
    // internal utility functions

    /**
     * Compute the overall width and height of the FigText object based on the
     * font, font size, and current text. Needs-More-Work: Right now text
     * objects can get larger when you type more, but they do not get smaller
     * when you backspace.
     */
    public void calcBounds() {
        if (_font == null) {
            return;
        }
        if (_fm == null) {
            _fm = new JLabel().getFontMetrics(_font);
        }

        _lineHeight = _fm.getHeight();
        int maxDescent = _fm.getMaxDescent();

        int overallW = 0;
        int numLines = 1;
        StringTokenizer lines = new StringTokenizer(_curText, "" + HARD_RETURN
                + SOFT_RETURN, true);
        while (lines.hasMoreTokens()) {
            String curLine = lines.nextToken();
            int chunkW = _fm.stringWidth(curLine);
            if (isHardReturn(curLine) || isSoftReturn(curLine))
                numLines++;
            else
                overallW = Math.max(chunkW, overallW);
        }
        int overallH = (_lineHeight + _lineSpacing) * numLines + _topMargin
                + _botMargin + maxDescent;
        overallW = Math.max(MIN_TEXT_WIDTH, overallW + _leftMargin
                + _rightMargin);
        if (_editMode) {
            switch (_justification) {
            case JUSTIFY_LEFT:
                break;

            case JUSTIFY_CENTER:
                if (_w < overallW)
                    _x -= (overallW - _w) / 2;
                break;

            case JUSTIFY_RIGHT:
                if (_w < overallW)
                    _x -= (overallW - _w);
                break;
            }
        }
        _w = _expandOnly ? Math.max(_w, overallW) : overallW;
        _h = _expandOnly ? Math.max(_h, overallH) : overallH;
    }

    /**
     * Creates an internal representation of the text from that provided by
     * setText(String). This involves converting the platform specific line
     * separator to always be a single '\n' character. It also involves
     * inserting '\r' characters where ever word wrap occurs. The client code
     * should never see this internal representation.
     * 
     * @param the
     *                text containing platform specific line ends an no word
     *                wrap.
     * @return the text containing GEF specific line ends and word wrap.
     */
    private String encode(String text, String lineTerminator) {

        StringBuffer buffer = new StringBuffer(text.length());

        int pos = 0;
        int lastPos = 0;
        while ((pos = text.indexOf(lineTerminator, lastPos)) >= 0) {
            buffer.append(text.substring(lastPos, pos)).append(HARD_RETURN);
            lastPos = pos + lineTerminator.length();
        }
        buffer.append(text.substring(lastPos));

        if (wordWrap) {
            return wordWrap(buffer.toString());
        } else {
            return buffer.toString();
        }
    }

    /**
     * Position '\r' as soft return points to indicate word wrap positions.
     * 
     * @param text
     *                the string to wrap
     * @return the text with soft returns wrapping in the correct position.
     */
    private String wordWrap(String text) {

        if (!wordWrap) {
            // TODO Make this an assert when we are JDK1.4.
            throw new IllegalArgumentException(
                    "Attempted to wordwrap while wordwrap off");
        }

        FontMetrics fm = FontUtility.getFontMetrics(_font);
        int tokenWidth;

        StringBuffer buffer = new StringBuffer(text.length());

        int x = 0;

        StringTokenizer st = new StringTokenizer(text, " " + HARD_RETURN
                + SOFT_RETURN, true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            tokenWidth = fm.stringWidth(token);

            if (isSoftReturn(token)) {
                // If we're recalculating a string that already been wrapped
                // then ignore
                // the old wrap points.
            } else if (isHardReturn(token)) {
                buffer.append(HARD_RETURN);
                x = 0;
            } else if (isSpace(token)) {
                buffer.append(token);
                x += tokenWidth;
            } else {
                if (x > 0 && x + tokenWidth > getWidth()) {
                    buffer.append(SOFT_RETURN);
                    x = 0;
                }
                x = appendToken(buffer, token, tokenWidth, fm, x);
            }
        }

        return buffer.toString();
    }

    private int appendToken(StringBuffer sb, String token, int tokenWidth,
            FontMetrics fm, int x) {
        if (x + tokenWidth <= getWidth()) {
            sb.append(token);
            x += tokenWidth;
        } else {
            String part = "";
            int lastPartWidth;
            int partWidth = 0;
            for (int i = 0; i < token.length(); ++i) {
                part += token.charAt(i);
                lastPartWidth = partWidth;
                partWidth = fm.stringWidth(part);
                if (partWidth > getWidth()) {
                    String nextPart = "";
                    int nextPartWidth = 0;
                    if (part.length() > 1) {
                        nextPart = part.substring(part.length() - 1);
                        nextPartWidth = fm.stringWidth(nextPart);
                        part = part.substring(0, part.length() - 1);
                        partWidth = lastPartWidth;
                    }
                    sb.append(part);
                    part = nextPart;
                    partWidth = nextPartWidth;
                    if (i < token.length() - 1) {
                        sb.append(SOFT_RETURN);
                        x = 0;
                    } else {
                        x += partWidth;
                    }
                }
            }
            if (partWidth > 0) {
                sb.append(part);
                x += partWidth;
            }
        }
        return x;
    }

    /**
     * Creates an internal representation of the text from that provided by
     * setText(String). This involves converting the platform specific line
     * separator to always be a single '\n' character. It also involves
     * inserting '\r' characters where ever word wrap occurs. The client code
     * should never see this internal representation.
     * 
     * @param the
     *                text containing platform specific line ends an no word
     *                wrap.
     * @return the text containing GEF specific line ends and word wrap.
     */
    private String decode(String text, String lineSeparator) {

        StringBuffer buffer = new StringBuffer(text.length());

        StringTokenizer st = new StringTokenizer(text, "" + HARD_RETURN
                + SOFT_RETURN, true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            if (isSoftReturn(token)) {
                // Ignore wrap points.
            } else if (isHardReturn(token)) {
                buffer.append(lineSeparator);
            } else {
                buffer.append(token);
            }
        }

        return buffer.toString();
    }

    private boolean isSoftReturn(String text) {
        return (text.length() == 1 && text.charAt(0) == SOFT_RETURN);
    }

    private boolean isHardReturn(String text) {
        return (text.length() == 1 && text.charAt(0) == HARD_RETURN);
    }

    private boolean isSpace(String text) {
        return (text.length() == 1 && text.charAt(0) == ' ');
    }

    protected void setBoundsImpl(int x, int y, int w, int h) {
        if (_w != w && wordWrap) {
            super.setBoundsImpl(x, y, w, h);
            _curText = wordWrap(_curText);
        } else {
            super.setBoundsImpl(x, y, w, h);
        }
    }

} /* end class FigText */

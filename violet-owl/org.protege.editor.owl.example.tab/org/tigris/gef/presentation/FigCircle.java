// $Id: FigCircle.java 1218 2009-01-12 22:16:24Z bobtarling $
// Copyright (c) 1996-2009 The Regents of the University of California. All
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

package org.tigris.gef.presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/**
 * Primitive Fig for displaying circles and ovals.
 * @author ics125
 */
public class FigCircle extends Fig {

    /**
     * 
     */
    private static final long serialVersionUID = 7376986113799307733L;
    /**
     * Used as a percentage tolerance for making it easier for the user to
     * select a hollow circle with the mouse. Needs-More-Work: This is bad
     * design that needs to be changed. Should use just GRIP_FACTOR.
     */
    public static final double CIRCLE_ADJUST_RADIUS = 0.1;
    protected boolean _isDashed = false;

    // //////////////////////////////////////////////////////////////
    // constructors

    /**
     * Construct a new FigCircle with the given position, size, and attributes.
     */
    public FigCircle(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /**
     * Construct a new FigCircle with the given position, size, line color, and
     * fill color
     */
    public FigCircle(int x, int y, int w, int h, Color lColor, Color fColor) {
        super(x, y, w, h, lColor, fColor);
    }

    /** Construct a new FigCircle w/ the given position and size. */
    public FigCircle(int x, int y, int w, int h, boolean resizable) {
        super(x, y, w, h);
        this.resizable = resizable;
    }

    /**
     * Construct a new FigCircle w/ the given position, size, line color, and
     * fill color.
     */
    public FigCircle(int x, int y, int w, int h, boolean resizable,
            Color lColor, Color fColor) {
        super(x, y, w, h, lColor, fColor);
        this.resizable = resizable;
    }

    // //////////////////////////////////////////////////////////////
    // display methods

    /** Draw this FigCircle. */
    public void paint(Graphics g) {

        final boolean dashed = getDashed();
        final int lineWidth = getLineWidth();

        if (dashed && (g instanceof Graphics2D)) {
            Graphics2D g2d = (Graphics2D) g;
            Stroke oldStroke = g2d.getStroke();
            float[] dash = { 10.0f, 10.0f };
            Stroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 1.0f, dash, 0.0f);
            g2d.setStroke(stroke);
            if (_filled && _fillColor != null) {
                g2d.setColor(_fillColor);
                g2d.fillOval(_x, _y, _w, _h);
            }

            if (lineWidth > 0 && _lineColor != null) {
                g2d.setColor(_lineColor);
                g2d.drawOval(_x, _y, _w - lineWidth, _h - lineWidth);
            }

            g2d.setStroke(oldStroke);
        } else if (_filled && _fillColor != null) {
            if (lineWidth > 0 && _lineColor != null) {
                g.setColor(_lineColor);
                g.fillOval(_x, _y, _w, _h);
            }

            if (!_fillColor.equals(_lineColor)) {
                g.setColor(_fillColor);
                g.fillOval(_x + lineWidth, _y + lineWidth, _w
                        - (lineWidth * 2), _h - (lineWidth * 2));
            }
        } else if (lineWidth > 0 && _lineColor != null) {
            g.setColor(_lineColor);
            g.drawOval(_x, _y, _w, _h);
        }
    }

    public void appendSvg(StringBuffer sb) {
        sb.append("<ellipse id='").append(getId()).append("'");
        appendSvgStyle(sb);
        sb.append("cx='").append(getCenter().x).append("'").append("cy='")
                .append(getCenter().y).append("'").append("rx='").append(
                        getWidth() / 2).append("'").append("ry='").append(
                        getHeight() / 2).append("' />");
    }

    /** Reply true if the given coordinates are inside the circle. */
    public boolean contains(int x, int y) {
        if (!super.contains(x, y)) {
            return false;
        }

        double dx = (double) (_x + _w / 2 - x) * 2 / _w;
        double dy = (double) (_y + _h / 2 - y) * 2 / _h;
        double distSquared = dx * dx + dy * dy;
        return distSquared <= 1.01;
    }

    /** Calculate border point of elipse */
    public Point connectionPoint(Point anotherPt) {
        double rx = _w / 2;
        double ry = _h / 2;
        double dx = anotherPt.x - _x;
        double dy = anotherPt.y - _y;
        double dd = ry * ry * dx * dx + rx * rx * dy * dy;
        double mu = rx * ry / Math.sqrt(dd);
        Point res = new Point((int) (mu * dx + _x + rx),
                (int) (mu * dy + _y + ry));
        // System.out.println("connectionPoint(p) returns
        // "+res.x+','+res.y+')');
        return res;
    }
} /* end class FigCircle */
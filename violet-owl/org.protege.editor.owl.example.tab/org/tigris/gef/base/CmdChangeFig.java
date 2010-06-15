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

// File: CmdReorder.java
// Classes: CmdReorder
// Original Author: jrobbins@ics.uci.edu
// $Id: CmdReorder.java 1153 2008-11-30 16:14:45Z bobtarling $

package org.tigris.gef.base;

import org.ugr.violet.presentation.OWLFigure;

/**
 * Cmd to change the back-to-front ordering of Fig's.
 * @see LayerDiagram#reorder
 */

public class CmdChangeFig extends Cmd {

    private static final long serialVersionUID = 1315160037668280033L;

    public static final int RECTANGLE = 1;
    public static final int CIRCLE = 2;
    public static final int DIAMOND = 3;
    public static final int RRECT = 4;

    public static CmdChangeFig toRectangle = new CmdChangeFig(RECTANGLE);
    public static CmdChangeFig toCircle = new CmdChangeFig(CIRCLE);
    public static CmdChangeFig toDiamond = new CmdChangeFig(DIAMOND);
    public static CmdChangeFig toRRect = new CmdChangeFig(RRECT);

    // //////////////////////////////////////////////////////////////
    // instance variables
    private int function;

    // //////////////////////////////////////////////////////////////
    // constructor

    /**
     * Construct a new CmdReorder with the given reordering constrant (see
     * above)
     */
    public CmdChangeFig(int f) {
        super(wordFor(f));
        function = f;
    }

    protected static String wordFor(int f) {
        switch (f) {
        case DIAMOND:
            return "Diamond";
        case RECTANGLE:
            return "Rectangle";
        case RRECT:
            return "RRect";
        case CIRCLE:
            return "Circle";
        }
        return "";
    }

    // //////////////////////////////////////////////////////////////
    // Cmd API

    public void doIt() {
        Editor ce = Globals.curEditor();
        LayerManager lm = ce.getLayerManager();
        SelectionManager sm = ce.getSelectionManager();
        
        for (Object o: sm.getSelectedFigs()){
        	((OWLFigure) o).changeFig(function);
        }
        
        sm.reorder(function, lm.getActiveLayer());
        sm.endTrans();
        // ce.repairDamage();
    }

    public void undoIt() {
        System.out.println("Cannot undo CmdReorder, yet");
    }
} /* end class CmdReorder */

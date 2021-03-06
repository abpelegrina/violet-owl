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




// File: NodeWall.java
// Classes: NodeWall
// Original Author: ics125b spring 1996
// $Id: NodeWall.java,v 1.2 2003/12/27 18:22:26 bobtarling Exp $

package org.tigris.gef.demo;

import java.awt.*;
import java.io.*;
import java.util.*;

import org.tigris.gef.base.*;
import org.tigris.gef.presentation.*;
import org.tigris.gef.graph.presentation.*;

/** An example subclass of NetNode for use in the EquipmentApplet
 *  demo. This class resents a wall outlet that can power computers
 *  and printers. 
 *
 * @see EquipmentApplet */
public class NodeWall extends NetNode implements Serializable {

  ////////////////////////////////////////////////////////////////
  // instance variables

  PortPower powerPort1, powerPort2;

  public String getId() {
    return toString();
  }
  
   /** Initialize a new NodeWall. */
  public void initialize(Hashtable args) {
    addPort(powerPort1 = new PortPower(this, PortPower.SOCKET));
    addPort(powerPort2 = new PortPower(this, PortPower.SOCKET));
   }

  public FigNode makePresentation(Layer lay) {
    Fig obj1 = new FigRect(0, 0, 200, 10, Color.black, Color.white);
    Fig obj2 = new FigRect( 3, 3, 14, 14, Color.black, Color.blue);
    Fig obj3 = new FigRect( 25, 3, 14, 14, Color.black, Color.blue);
    Collection temp_list = new Vector();
    temp_list.add(obj1);
    temp_list.add(obj2);
    temp_list.add(obj3);
    FigNode fn = new FigNode(this, temp_list);
    fn.bindPort(powerPort1, obj2);
    fn.bindPort(powerPort2, obj3);
    return fn;
  }
} /* end class NodeWall */

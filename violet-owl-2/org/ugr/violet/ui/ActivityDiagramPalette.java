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

// File: SamplePalette.java
// Classes: SamplePalette
// Original Author: jrobbins@ics.uci.edu
// $Id: SamplePalette.java,v 1.6 2005/05/11 11:43:31 bobtarling Exp $

package org.ugr.violet.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.tigris.gef.base.AlignAction;
import org.tigris.gef.base.CmdAlign;
import org.tigris.gef.base.CmdCreateNode;
import org.tigris.gef.base.CmdSetMode;
import org.tigris.gef.base.ModeBroom;
import org.tigris.gef.base.ModeSelect;
import org.tigris.gef.base.PrintAction;
import org.tigris.gef.base.SaveSVGAction;
import org.tigris.gef.graph.presentation.PaletteFig;
import org.ugr.violet.actions.CmdCreateActivityStep;
import org.ugr.violet.actions.CmdCreateNodeRelation;
import org.ugr.violet.actions.ModeCreateComplement;
import org.ugr.violet.actions.ModeCreateDisjoint;
import org.ugr.violet.actions.ModeCreateDomain;
import org.ugr.violet.actions.ModeCreateEquivalent;
import org.ugr.violet.actions.ModeCreateFollowedBy;
import org.ugr.violet.actions.ModeCreateObjectProperty;
import org.ugr.violet.actions.ModeCreateRange;
import org.ugr.violet.actions.ModeCreateSub;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.activity.NodeActivity;
import org.ugr.violet.graph.nodes.activity.NodeActivityStep;
import org.ugr.violet.graph.nodes.activity.NodeFirstStep;
import org.ugr.violet.graph.nodes.activity.NodeFork;
import org.ugr.violet.graph.nodes.activity.NodeJoin;
import org.ugr.violet.graph.nodes.activity.NodeLastStep;

/** A class to define a custom palette for use in some demos.
 *
 * @see org.tigris.gef.demo.FlexibleApplet
 * @see org.tigris.gef.demo.BasicApplication
 */

public class ActivityDiagramPalette extends OntologyPalette {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3626620406810549582L;

	/** Construct a new palette of example nodes for the Example application */
    public ActivityDiagramPalette() {

    }

    /** Define a button to make for the Example application */
    @SuppressWarnings("deprecation")
	public void defineButtons() {
    	add(new CmdSetMode(ModeSelect.class, "Select"));
        add(new CmdSetMode(ModeBroom.class, "Broom"));
       
        this.addSeparator();
        add(new CmdAlign(AlignAction.ALIGN_TOPS));
        add(new CmdAlign(AlignAction.ALIGN_LEFTS));
       
        
		this.addSeparator();
		
		
		add(new CmdCreateNode(NodeActivityStep.class, "Activity"));
		add(new CmdCreateNode(NodeFirstStep.class, "Initial"));
		add(new CmdCreateNode(NodeLastStep.class, "FinalState"));
		this.addSeparator();
		add(new CmdCreateNode(NodeFork.class, "Fork"));
		add(new CmdCreateNode(NodeJoin.class, "Join"));
		this.addSeparator();
		add(new CmdCreateNode(NodeActivityStep.class, "Decision"));
		this.addSeparator();
		add(new CmdCreateNode(NodeActivityStep.class, "Signal"));
		add(new CmdCreateNode(NodeActivityStep.class, "SignalReceiving"));
		
		//add(new CmdCreateNode(NodeClass.class, "ClaseOWL"));
		/*add(new CmdCreateNodeIndividual());
		add(new CmdCreateNodeDataProperty());
		add(new CmdCreateNodeObjectProperty());*/
		this.addSeparator();
		this.addSeparator();
		add(new CmdSetMode(ModeCreateFollowedBy.class, "ObjectProperty"));
		/*
		add(new CmdSetMode(ModeCreateObjectProperty.class, "ObjectProperty"));		
		add(new CmdSetMode(ModeCreateSub.class, "Sub"));		
		add(new CmdSetMode(ModeCreateDisjoint.class, "Disjoint"));		
		add(new CmdSetMode(ModeCreateEquivalent.class, "Equivalence"));
		add(new CmdSetMode(ModeCreateComplement.class, "Not"));
		*/
    }
} /* end class SamplePalette */

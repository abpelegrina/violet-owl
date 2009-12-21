package org.ugr.ontology.graph.nodes;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.graph.presentation.NetPort;

public class PortClass extends OntologyPort{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1282413599975091348L;

	public PortClass(NodeClass parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	public PortClass(NodeRestriction parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	protected Class defaultEdgeClass(NetPort otherPort) {
		try {
			if (otherPort.getClass() == this.getClass())
				return Class.forName("ugr.ontology.graph.edges.SuperEdge");
			else if (otherPort.getClass() == Class.forName("org.ugr.ontology.graph.nodes.PortClass"))
				return Class.forName("ugr.ontology.graph.edges.SuperEdge");
			else
				return null;
		} catch (java.lang.ClassNotFoundException ignore) {
			return null;
		}
	}
	
	/** Add the constraint that SamplePort's can only be connected to
	 * other ports of the same type. */
	public boolean canConnectTo(GraphModel gm, Object anotherPort) {
		boolean canConnect = true;
		if (this.getClass()== anotherPort.getClass()){
			canConnect = true;
		}
		return canConnect;
	}

}

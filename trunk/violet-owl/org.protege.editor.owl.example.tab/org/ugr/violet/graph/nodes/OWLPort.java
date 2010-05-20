package org.ugr.violet.graph.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.graph.presentation.NetPort;

public class OWLPort extends NetPort  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1920510914142825037L;

	public OWLPort(Object parent) {
		super(parent);
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
	
	public void removeEdges() {
		List<Object> aBorrar = new ArrayList<Object>();
		aBorrar.addAll(getEdges());
		
		for(Object o: aBorrar){
			this.removeEdge((NetEdge)o);
		}
	}
}

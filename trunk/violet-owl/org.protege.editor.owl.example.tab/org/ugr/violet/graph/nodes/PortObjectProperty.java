/**
 * 
 */
package org.ugr.violet.graph.nodes;

import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.presentation.NetPort;

/**
 * @author anab
 *
 */
public class PortObjectProperty extends OntologyPort {

	/**
	 * @param parent
	 */
	public PortObjectProperty(NodeObjectProperty parent) {
		super(parent);
	}
	
	@SuppressWarnings("unchecked")
	protected Class defaultEdgeClass(NetPort otherPort) {
		try {
			if (otherPort.getClass() == this.getClass())
				return Class.forName("org.ugr.ontology.graph.edges.RestrictionEdge");
			/*else if (otherPort.getClass() == Class.forName("org.tigris.gef.ontology.presentation.PortClass"))
				return Class.forName("org.tigris.getf.ontology.presentation.SuperEdge");*/
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 4084714306030765791L;

}

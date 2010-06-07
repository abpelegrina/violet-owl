/**
 * 
 */
package org.ugr.violet.graph.nodes.activity;

import org.semanticweb.owl.model.OWLIndividual;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.NodeIndividual;
import org.ugr.violet.graph.nodes.NodeIntersection;
import org.ugr.violet.graph.nodes.NodeObjectProperty;
import org.ugr.violet.graph.nodes.NodeRestriction;
import org.ugr.violet.graph.nodes.NodeUnion;
import org.ugr.violet.graph.nodes.OWLNode;
import org.ugr.violet.presentation.OntologyFig;

/**
 * @author anab
 *
 */
public abstract class NodeActivity extends OWLNode {

	
	protected NodeActivity(){
		super();
	}
	
	
	public OWLIndividual getStep(){
		return null;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3174032125618133809L;

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#asNodeClass()
	 */
	@Override
	public NodeClass asNodeClass() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#asNodeIndividual()
	 */
	@Override
	public NodeIndividual asNodeIndividual() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#asNodeIntersection()
	 */
	@Override
	public NodeIntersection asNodeIntersection() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#asNodeObjectProperty()
	 */
	@Override
	public NodeObjectProperty asNodeObjectProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#asNodeRestriction()
	 */
	@Override
	public NodeRestriction asNodeRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#asNodeUnion()
	 */
	@Override
	public NodeUnion asNodeUnion() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#getOntologyFig()
	 */
	@Override
	public OntologyFig getOntologyFig() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#isNodeClass()
	 */
	@Override
	public boolean isNodeClass() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#isNodeIndividual()
	 */
	@Override
	public boolean isNodeIndividual() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#isNodeIntersection()
	 */
	@Override
	public boolean isNodeIntersection() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#isNodeObjectProperty()
	 */
	@Override
	public boolean isNodeObjectProperty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#isNodeRestriction()
	 */
	@Override
	public boolean isNodeRestriction() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#isNodeUnion()
	 */
	@Override
	public boolean isNodeUnion() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.NetPrimitive#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isNodeActivityStep(){
		return false;
	}
	
	public NodeActivityStep asNodeActivityStep(){
		return null;
	}
	
	

}

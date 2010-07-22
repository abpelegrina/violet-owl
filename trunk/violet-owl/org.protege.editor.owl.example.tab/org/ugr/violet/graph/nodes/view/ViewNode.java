/**
 * 
 */
package org.ugr.violet.graph.nodes.view;

import java.util.Hashtable;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.Layer;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.NodeIndividual;
import org.ugr.violet.graph.nodes.NodeIntersection;
import org.ugr.violet.graph.nodes.NodeObjectProperty;
import org.ugr.violet.graph.nodes.NodeRestriction;
import org.ugr.violet.graph.nodes.NodeUnion;
import org.ugr.violet.graph.nodes.OWLNode;
import org.ugr.violet.graph.nodes.OWLPort;
import org.ugr.violet.presentation.OWLFigure;
import org.ugr.violet.presentation.view.FigView;
import org.ugr.violet.presentation.view.FigViewParameter;

/**
 * @author anab
 *
 */
public class ViewNode extends OWLNode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3927127313714515584L;
	
	
	protected OWLEntity entity = null;
	protected OWLEntity entity_aux = null;
	
	/**
	 * The figure that will represent the node. It has to be a 
	 */
	protected FigView figure = null;
	protected FigViewParameter params = null;
	
	/**
	 * 
	 */
	public ViewNode(FigViewParameter p){
		super();
		addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
		
		params = p;
	}
	
	
	public ViewNode(FigViewParameter p, OWLEntity e){
		this(p);
		entity = e;
	}
	
	public ViewNode(FigViewParameter p, OWLEntity e, OWLEntity aux){
		this(p,e);
		entity_aux = aux;
	}
	
	
	 public void initialize(Hashtable args) {
	        super.initialize(args);
	  }
	
	 public FigView makePresentation(Layer lay) {
	    	
	    	if (entity != null){
	    		
	    		String label = entity_aux == null ? entity.toString() : entity_aux.toString();
		    	
				figure = new FigView(entity, params, label);
				
				
		        figure.setOwner(this);
		    	figure.setBlinkPorts(true);
		        return figure;
	    	}
	    	else return null;
	    }


	public OWLEntity getOWLEntity(){
			return this.entity;
	}
	
	public String toString(){
		return entity.toString();
	}
	 
	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeClass()
	 */
	@Override
	public NodeClass asNodeClass() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeIndividual()
	 */
	@Override
	public NodeIndividual asNodeIndividual() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeIntersection()
	 */
	@Override
	public NodeIntersection asNodeIntersection() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeObjectProperty()
	 */
	@Override
	public NodeObjectProperty asNodeObjectProperty() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeRestriction()
	 */
	@Override
	public NodeRestriction asNodeRestriction() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeUnion()
	 */
	@Override
	public NodeUnion asNodeUnion() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#getOntologyFig()
	 */
	@Override
	public OWLFigure getOntologyFig() {
		return this.figure;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeClass()
	 */
	@Override
	public boolean isNodeClass() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeIndividual()
	 */
	@Override
	public boolean isNodeIndividual() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeIntersection()
	 */
	@Override
	public boolean isNodeIntersection() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeObjectProperty()
	 */
	@Override
	public boolean isNodeObjectProperty() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeRestriction()
	 */
	@Override
	public boolean isNodeRestriction() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeUnion()
	 */
	@Override
	public boolean isNodeUnion() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.NetPrimitive#getId()
	 */
	@Override
	public String getId() {
		return this.entity.toString();
	}

}

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
	protected OWLFigure figure = null;
	protected FigViewParameter params = null;
	
	/**
	 * 
	 */
	public ViewNode(FigViewParameter p){
		super();
		
		params = p;
		
		addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
	}
	
	
	public ViewNode(FigViewParameter p, OWLEntity e){
		super();
		
		entity = e;
		params = p;
		
		addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
		
	}
	
	public ViewNode(FigViewParameter p, OWLEntity e, OWLEntity aux){
		super();
		
		params = p;
		entity = e;
		entity_aux = aux;
		
		addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
	}
	
	
	 public void initialize(Hashtable args) {
	        super.initialize(args);
	  }
	
	 public OWLFigure makePresentation(Layer lay) {
	    	
	    	if (entity != null){
		    	
				figure = new FigView(entity, params, entity.toString());
				
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeIndividual()
	 */
	@Override
	public NodeIndividual asNodeIndividual() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeIntersection()
	 */
	@Override
	public NodeIntersection asNodeIntersection() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeObjectProperty()
	 */
	@Override
	public NodeObjectProperty asNodeObjectProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeRestriction()
	 */
	@Override
	public NodeRestriction asNodeRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#asNodeUnion()
	 */
	@Override
	public NodeUnion asNodeUnion() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#getOntologyFig()
	 */
	@Override
	public OWLFigure getOntologyFig() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeClass()
	 */
	@Override
	public boolean isNodeClass() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeObjectProperty()
	 */
	@Override
	public boolean isNodeObjectProperty() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeRestriction()
	 */
	@Override
	public boolean isNodeRestriction() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#isNodeUnion()
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

}

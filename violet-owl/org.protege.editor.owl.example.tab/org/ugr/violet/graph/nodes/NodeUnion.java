/**
 * 
 */
package org.ugr.violet.graph.nodes;

import java.awt.Point;
import java.util.Hashtable;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLObjectUnionOf;
import org.tigris.gef.base.Layer;
import org.ugr.violet.presentation.FigUnion;
import org.ugr.violet.presentation.OWLFigure;

/**
 * @author anab
 *
 */
public class NodeUnion extends OWLNode {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5064050975126988271L;
	private OWLDescription desc = null;
	private OWLEntity entidad = null;
	private OWLObjectUnionOf union = null;
	
	private FigUnion figura = null;
	
	public NodeUnion(OWLDescription c, OWLObjectUnionOf un){
		super();
		desc = c;
		union = un;
		addPort(east = new PortUnion(this));
        addPort(west = new PortUnion(this));
        addPort(north = new PortUnion(this));
        addPort(south = new PortUnion(this));
	}
	
	public NodeUnion(OWLClass c, OWLObjectUnionOf un){
		super();
		desc = c;
		union = un;
		addPort(east = new PortUnion(this));
        addPort(west = new PortUnion(this));
        addPort(north = new PortUnion(this));
        addPort(south = new PortUnion(this));
	}
	
	public NodeUnion(OWLEntity c, OWLObjectUnionOf un){
		super();
		entidad = c;
		union = un;
		addPort(east = new PortUnion(this));
        addPort(west = new PortUnion(this));
        addPort(north = new PortUnion(this));
        addPort(south = new PortUnion(this));
	}
	
	public OWLEntity getOWLEntity(){
		if (desc != null && !desc.isAnonymous())
			return desc.asOWLClass();
		else 
			return entidad;
	}
	
	/**
	 * 
	 */
	public FigUnion makePresentation(Layer lay) {
	    	
	    	figura = new FigUnion();
	    	
	        figura.setOwner(this);
	    	figura.setBlinkPorts(true);
	        return figura;
	}
	
	/**
	 * 
	 */
	public OWLObjectUnionOf getOWLObjectUnionOf(){
		return union;
	}
	
	/**
	 * 
	 * @return
	 */
	public OWLDescription getOWLDescription(){
		return desc;
	}

    /** Initialize a new SampleNode from the given default node and
     *  application specific model. <p>
     *
     *  Needs-More-Work: for now we construct the FigNode
     *  programatically, but eventually we will store it in a class
     *  variable and just refer to it, or copy it(?). That way the user
     *  can edit the FigNode(s) stored in the class variable and
     *  have those changes shown for all existing nodes, or for all
     *  future nodes. Maybe I should think about doing virtual copies?</p>
     */
    @SuppressWarnings("unchecked")
	public void initialize(Hashtable args) {
        super.initialize(args);
    }
	
    public void setLocation(Point p){
    	figura.setLocation(p);
    }
    
	@Override
	public NodeClass asNodeClass() {
		return null;
	}

	@Override
	public NodeIndividual asNodeIndividual() {
		return null;
	}

	@Override
	public NodeObjectProperty asNodeObjectProperty() {
		return null;
	}

	@Override
	public NodeRestriction asNodeRestriction() {
		return null;
	}

	@Override
	public OWLFigure getOntologyFig() {
		return figura;
	}

	@Override
	public boolean isNodeClass() {
		return false;
	}

	@Override
	public boolean isNodeIndividual() {
		return false;
	}

	@Override
	public boolean isNodeObjectProperty() {
		return false;
	}


	@Override
	public boolean isNodeRestriction() {
		return false;
	}


	@Override
	public String getId() {
		return super.toString();
	}

	@Override
	public NodeUnion asNodeUnion() {
		return this;
	}

	@Override
	public boolean isNodeUnion() {
		return true;
	}
	
	@Override
	public String toString() {
		
		return union.toString();
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.graph.nodes.OntologyNode#asNodeIntersection()
	 */
	@Override
	public NodeIntersection asNodeIntersection() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.graph.nodes.OntologyNode#isNodeIntersection()
	 */
	@Override
	public boolean isNodeIntersection() {
		// TODO Auto-generated method stub
		return false;
	}

}


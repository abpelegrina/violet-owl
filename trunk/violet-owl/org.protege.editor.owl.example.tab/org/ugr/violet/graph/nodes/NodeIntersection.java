/**
 * 
 */
package org.ugr.violet.graph.nodes;

import java.awt.Point;
import java.util.Hashtable;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLObjectIntersectionOf;
import org.tigris.gef.base.Layer;
import org.ugr.violet.presentation.FigIntersection;
import org.ugr.violet.presentation.OWLFigure;

/**
 * @author anab
 *
 */
public class NodeIntersection extends OWLNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5064050975126988271L;
	private OWLDescription desc = null;
	private OWLEntity entidad = null;
	private OWLObjectIntersectionOf interseccion = null;
	
	private FigIntersection figura = null;
	
	public NodeIntersection(OWLDescription c, OWLObjectIntersectionOf un){
		super();
		this.desc = c;
		this.interseccion = un;
		addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
	}
	
	public NodeIntersection(OWLEntity c, OWLObjectIntersectionOf un){
		super();
		this.entidad = c;
		this.interseccion = un;
		addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
	}
	
	/**
	 * 
	 */
	public FigIntersection makePresentation(Layer lay) {
	    	
	    	figura = new FigIntersection();	    	
	        figura.setOwner(this);
	    	figura.setBlinkPorts(true);
	        return figura;
	}
	
	public OWLEntity getOWLEntity(){
		if (desc != null && !desc.isAnonymous())
			return desc.asOWLClass();
		else
			return this.entidad;
	}
	
	/**
	 * 
	 */
	public OWLObjectIntersectionOf getOWLObjectIntersectionOf(){
		return interseccion;
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
		return null;
	}

	@Override
	public boolean isNodeUnion() {
		return false;
	}
	
	@Override
	public String toString() {
		
		return interseccion.toString();
	}

	@Override
	public NodeIntersection asNodeIntersection() {
		return this;
	}

	@Override
	public boolean isNodeIntersection() {
		return true;
	}

}

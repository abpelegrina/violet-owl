/**
 * 
 */
package org.ugr.violet.graph.nodes;

import java.awt.Point;
import java.util.Hashtable;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.tigris.gef.base.Layer;
import org.ugr.violet.presentation.FigComplement;
import org.ugr.violet.presentation.OWLFigure;

/**
 * @author anab
 *
 */
public class NodeComplement extends OWLNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5064050975126988271L;
	private OWLEntity entidad = null;
	private OWLObjectComplementOf union = null;
	
	private FigComplement figura = null;
	
	public NodeComplement(OWLEntity c, OWLObjectComplementOf un){
		super();
		entidad = c;
		union = un;
		addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
	}
	
	/**
	 * 
	 */
	public FigComplement makePresentation(Layer lay) {
	    	
	    	figura = new FigComplement();
	    	
	        figura.setOwner(this);
	    	figura.setBlinkPorts(true);
	        return figura;
	}
	
	/**
	 * 
	 */
	public OWLObjectComplementOf getOWLObjectComplementOf(){
		return union;
	}
	
	/**
	 * 
	 * @return
	 */
	public OWLClass getOWLClass(){
		if (entidad.isOWLClass()) return entidad.asOWLClass();
		else return null;
	}
	
	public OWLEntity getOWLEntity(){
		return entidad;
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
		
		return union.toString();
	}

	@Override
	public NodeIntersection asNodeIntersection() {
		return null;
	}

	@Override
	public boolean isNodeIntersection() {
		return false;
	}
	
	@Override
	public boolean isNodeComplement(){
		return true;
	}
	
	@Override
	public NodeComplement asNodeComplement(){
		return this;
	}

}

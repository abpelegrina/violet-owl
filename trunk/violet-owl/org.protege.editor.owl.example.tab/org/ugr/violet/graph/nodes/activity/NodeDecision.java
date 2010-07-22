/**
 * 
 */
package org.ugr.violet.graph.nodes.activity;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.protege.owl.examples.tab.VioletActivityEditor;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.GraphModel;
import org.ugr.violet.graph.ActivityGraphModel;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.NodeDataProperty;
import org.ugr.violet.graph.nodes.NodeIndividual;
import org.ugr.violet.graph.nodes.NodeIntersection;
import org.ugr.violet.graph.nodes.NodeObjectProperty;
import org.ugr.violet.graph.nodes.NodeRestriction;
import org.ugr.violet.graph.nodes.NodeUnion;
import org.ugr.violet.graph.nodes.OWLPort;
import org.ugr.violet.presentation.OWLFigure;
import org.ugr.violet.presentation.activity.FigDecision;

/**
 * @author anab
 *
 */
public class NodeDecision extends NodeActivityDiagram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7219027647508489833L;
	private static int cont = 0;
	
	
	private OWLIndividual step = null;
	private FigDecision figura = null;
	
	public  NodeDecision ( ){
    	super();
    	
    	step = VioletActivityEditor.manager.getOWLDataFactory().getOWLIndividual(URI.create(gm.activeOntology().getURI() + "#" +  gm.getTask() + "_decision_" + cont));
		cont++;
		OWLClass claseDecision = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(ActivityGraphModel.URIAmenities + "#Decision_Step"));
		
		OWLClassAssertionAxiom d = VioletActivityEditor.manager.getOWLDataFactory().getOWLClassAssertionAxiom (step, claseDecision);
		VioletActivityEditor.manager.applyChange(new AddAxiom( gm.activeOntology(), d));
		
		gm.addStepToSequence(step);
    	
    	addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
    }
	
	/**
     * Contructor. Creates a new node
     * @param unaPropiedadDeDatos OWL class represented by the new node
	 * @return 
     */
    public  NodeDecision (OWLIndividual paso){
    	super();
    	step = paso;
    	addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
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
    public void initialize(Hashtable args) {
        super.initialize(args);
    }
    
    /**
	 * @return the claseOWL
	 */
    @Override
	public OWLIndividual getStep() {
		return step;
	}
    
    /**
     * @return Id of the node
     */
    public String getId() {
        return step.toString();
    }
    
    /**
     * Gets the figure associated with the node
     * @return la figura
     */
    public OWLFigure getFigDataProperty(){
    	return figura;
    }

    /**
     * Draws the node in a layer
     * @param lay layer where we want to draw the node
     * @return the new figure
     */
    public OWLFigure makePresentation(Layer lay) {
    	
    	if (step != null){
	    	figura = new FigDecision (step);
	    	figura.setOwner(this);
	    	figura.setBlinkPorts(true);
	    	
	    	System.err.println("CREANDO LA FIGURA!!!!");
	    	
	    	return figura;
    	}
    	else return null;
    }


	/* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.NetNode#postDisconnect(org.tigris.gef.graph.GraphModel, java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void postDisconnect(GraphModel gm, Object anotherNode,
			Object myPort, Object otherPort) {
		// TODO Auto-generated method stub
		super.postDisconnect(gm, anotherNode, myPort, otherPort);
	}


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (step != null)
			return step.toString();
		else
			return "";
	}
	
	public Point getLocation() {
		return this.figura.getLocation();
	}

	public Dimension getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	public void setLocation(Point newLocation) {
		// TODO Auto-generated method stub
		this.figura.setLocation(newLocation);
	}

	@Override
	public OWLFigure getOntologyFig() {
		return figura;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "HELLO!!");	
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "HELLO!!");	
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "HELLO!!");	
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "HELLO!!");	
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "HELLO!!");	
	}

	@Override
	public boolean isNodeClass() {
		return true;
	}

	@Override
	public boolean isNodeIndividual() {
		return false;
	}

	@Override
	public boolean isNodeRestriction() {
		return false;
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
	public NodeRestriction asNodeRestriction() {
		return null;
	}

	@Override
	public NodeObjectProperty asNodeObjectProperty() {
		return null;
	}

	@Override
	public boolean isNodeObjectProperty() {
		return false;
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
	public NodeIntersection asNodeIntersection() {
		return null;
	}

	@Override
	public boolean isNodeIntersection() {
		return false;
	}
	
	public NodeDataProperty asDataProperty(){
		return null;
	}
	public boolean isDataProperty(){
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		// TODO Auto-generated method stub
		return step;
	}

}

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

import org.protege.owl.examples.tab.VioletEditor;
import org.protege.owl.examples.tab.VioletActivityEditor;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
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
import org.ugr.violet.presentation.activity.FigActivityStep;

/**
 * @author anab
 *
 */
public class NodeAction extends NodeActivityDiagram {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5882931174797119668L;


	public static final OWLClass activityStep = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(VioletActivityEditor.manager.getActiveOntology().getURI() + "#Activity_Step"));
	
	
	private OWLIndividual activity = null;
	private OWLIndividual activity_step = null;
	private OWLIndividual role = null;
	private FigActivityStep figura = null;
	
	
	
	/**
	 * Constructor sin parámentros, crea el nodo nuevo y crea tb la actividad, el paso y los enlaces asociados
	 */
	public NodeAction(){
		
		String nombreActividad = JOptionPane.showInputDialog("Activity Name: "); 
		
		activity = VioletActivityEditor.manager.getOWLDataFactory().getOWLIndividual(URI.create(gm.activeOntology().getURI() + "#" + nombreActividad));
    	OWLClass claseAccion = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create( ActivityGraphModel.URIAmenities + "#Action"));
    	
    	
    	OWLClassAssertionAxiom d = VioletActivityEditor.manager.getOWLDataFactory().getOWLClassAssertionAxiom (activity, claseAccion);
		VioletActivityEditor.manager.applyChange(new AddAxiom(gm.activeOntology(), d));
		
		// 1 Buscar el step asociado al la actividad/tarea
		
		activity_step = VioletActivityEditor.manager.getOWLDataFactory().getOWLIndividual(URI.create(gm.activeOntology().getURI() + "#" + nombreActividad + "_step"));
	    OWLClass claseStep = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(ActivityGraphModel.URIAmenities + "#Action_Step"));
	    	
	    	
	    d = VioletActivityEditor.manager.getOWLDataFactory().getOWLClassAssertionAxiom (activity_step, claseStep);
		VioletActivityEditor.manager.applyChange(new AddAxiom(gm.activeOntology(), d));
	
		// asociamos la tarea y el step mediante la propiedad de objetos "performs". Si ya existía la relación no pasa nada
		OWLObjectProperty performs = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ActivityGraphModel.URIAmenities + "#performs"));
		OWLObjectPropertyAssertionAxiom ax = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(activity_step, performs, activity);
		VioletActivityEditor.manager.applyChange(new AddAxiom(gm.activeOntology(), ax));
		
		// 2 Buscamos el rol asociado con la tarea (si existe)
		// TODO buscar el rol asociado con la tarea 
	
		
		// 3 Añadir la actividad como parte de la tarea modelada
		OWLIndividual secuencia = gm.getSequence();
		// Asociamos la secuencia a los steps
		OWLObjectProperty hasPart = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ActivityGraphModel.URIAmenities + "#has_part"));
		OWLObjectProperty partOf = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ActivityGraphModel.URIAmenities + "#part_of"));
		
		// step es parte de secuencia
		OWLObjectPropertyAssertionAxiom e = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(activity_step, partOf, secuencia);
		VioletActivityEditor.manager.applyChange(new AddAxiom(gm.activeOntology(), e));
		
		// secuencia tiene como parte a step
		e = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(secuencia, hasPart, activity_step);
		VioletActivityEditor.manager.applyChange(new AddAxiom(gm.activeOntology(), e));
		
		addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
	}


	/**
     * Contructor. Creates a new node
     * @param unaPropiedadDeDatos OWL class represented by the new node
     */
    public NodeAction (OWLIndividual act, OWLIndividual step){
    	super();
    	this.activity = act;
    	this.activity_step = step;
    	addPort(east = new OWLPort(this));
        addPort(west = new OWLPort(this));
        addPort(north = new OWLPort(this));
        addPort(south = new OWLPort(this));
    }
    
    /**
     * Contructor. Creates a new node
     * @param unaPropiedadDeDatos OWL class represented by the new node
     */
    public NodeAction (OWLIndividual act, OWLIndividual step, OWLIndividual rol){
    	super();
    	this.activity = act;
    	this.activity_step = step;
    	role = rol;
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
     * @return Id of the node
     */
    public String getId() {
        return activity_step.toString();
    }
    
    /**
     * Gets the figure associated with the node
     * @return la figura
     */
    public FigActivityStep getFigDataProperty(){
    	return figura;
    }

    /**
     * Draws the node in a layer
     * @param lay layer where we want to draw the node
     * @return the new figure
     */
    public FigActivityStep makePresentation(Layer lay) {
    	
    	if (activity != null){
	    	figura = new FigActivityStep(activity, activity_step, role);	    	
	        figura.setOwner(this);
	    	figura.setBlinkPorts(true);
	        return figura;
    	}
    	else return null;
    }

	/**
	 * @return the claseOWL
	 */
    @Override
	public OWLIndividual getStep() {
		return activity_step;
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
	
	public OWLEntity getOWLEntity(){
		return this.activity_step;
	}


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (activity_step != null)
			return activity_step.toString();
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
	
	public boolean isNodeActivityStep(){
		return true;
	}
	
	public NodeAction asNodeActivityStep(){
		return this;
	}

}

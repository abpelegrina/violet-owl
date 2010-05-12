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

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.GraphModel;
import org.ugr.violet.graph.OntologyActivityGraphModel;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.NodeDataProperty;
import org.ugr.violet.graph.nodes.NodeIndividual;
import org.ugr.violet.graph.nodes.NodeIntersection;
import org.ugr.violet.graph.nodes.NodeObjectProperty;
import org.ugr.violet.graph.nodes.NodeRestriction;
import org.ugr.violet.graph.nodes.NodeUnion;
import org.ugr.violet.graph.nodes.OntologyPort;
import org.ugr.violet.presentation.OntologyFig;
import org.ugr.violet.presentation.activity.FigActivityStep;

/**
 * @author anab
 *
 */
public class NodeActivityStep extends NodeActivity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5882931174797119668L;


	public static final OWLClass activityStep = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#Activity_Step"));
	
	
	private OWLIndividual activity = null;
	private OWLIndividual activity_step = null;
	private FigActivityStep figura = null;

	
	/**
	 * Constructor sin parámentros, crea el nodo nuevo y crea tb la actividad, el paso y los enlaces asociados
	 */
	public NodeActivityStep(){
		
		String nombreActividad = JOptionPane.showInputDialog("Activity Name: "); 
		
		activity = ExampleViewComponent.manager.getOWLDataFactory().getOWLIndividual(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + nombreActividad));
    	OWLClass claseAccion = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#Action"));
    	
    	
    	OWLClassAssertionAxiom d = ExampleViewComponent.manager.getOWLDataFactory().getOWLClassAssertionAxiom (activity, claseAccion);
		ExampleViewComponent.manager.applyChange(new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), d));
		
		OWLOntology activa = ExampleViewComponent.manager.getActiveOntology();
		
		NodeActivityStep nodeInd;
		
		// 1 Buscar el step asociado al la actividad/tarea
		
		activity_step = ExampleViewComponent.manager.getOWLDataFactory().getOWLIndividual(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + nombreActividad + "_step"));
	    OWLClass claseStep = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#Action_Step"));
	    	
	    	
	    d = ExampleViewComponent.manager.getOWLDataFactory().getOWLClassAssertionAxiom (activity_step, claseStep);
		ExampleViewComponent.manager.applyChange(new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), d));
	
		// asociamos la tarea y el step mediante la propiedad de objetos "performs". Si ya existía la relación no pasa nada
		OWLObjectProperty performs = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#performs"));
		OWLObjectPropertyAssertionAxiom ax = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(activity_step, performs, activity);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, ax));
		
		// 2 Buscamos el rol asociado con la tarea (si existe)
		// TODO buscar el rol asociado con la tarea 
	
		
		// 3 Añadir la actividad como parte de la tarea modelada
		OWLIndividual tareaPrincipal = ExampleViewComponent.lienzoActual.getTarea();
		// Asociamos la secuencia a los steps
		OWLObjectProperty hasPart = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#has_part"));
		OWLObjectProperty partOf = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#part_of"));
		
		OWLObjectPropertyAssertionAxiom e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(activity, partOf, tareaPrincipal);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(tareaPrincipal, hasPart, activity);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		// 4 Agregar los steps como parte de la secuencia del diagrama
		OWLIndividual secuencia = ExampleViewComponent.lienzoActual.getSecuencia();
		
		// step es parte de secuencia
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(activity_step, partOf, secuencia);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		// secuencia tiene como parte a step
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(secuencia, hasPart, activity_step);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		addPort(east = new OntologyPort(this));
        addPort(west = new OntologyPort(this));
        addPort(north = new OntologyPort(this));
        addPort(south = new OntologyPort(this));
	}


	/**
     * Contructor. Creates a new node
     * @param unaPropiedadDeDatos OWL class represented by the new node
     */
    public NodeActivityStep (OWLIndividual act, OWLIndividual step){
    	super();
    	this.activity = act;
    	this.activity_step = step;
    	addPort(east = new OntologyPort(this));
        addPort(west = new OntologyPort(this));
        addPort(north = new OntologyPort(this));
        addPort(south = new OntologyPort(this));
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
        return activity.toString();
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
	    	figura = new FigActivityStep(activity, activity_step);	    	
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
		// TODO Auto-generated method stub
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
	public OntologyFig getOntologyFig() {
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

}

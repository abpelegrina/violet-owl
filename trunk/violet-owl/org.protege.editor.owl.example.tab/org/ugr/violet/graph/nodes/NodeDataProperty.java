package org.ugr.violet.graph.nodes;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.net.URI;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.GraphModel;
import org.ugr.violet.layout.LayoutedNode;
import org.protege.owl.examples.tab.ExampleViewComponent;
import org.ugr.violet.presentation.FigDataProperty;
import org.ugr.violet.presentation.OWLFigure;

public class NodeDataProperty extends OWLNode implements Serializable, LayoutedNode, MouseListener {
	/**
	 * serial version of the class NetNode
	 */
	private static final long serialVersionUID = -3380856158583786547L;
	
	/**
	 * OWL class represented by the node
	 */
    private OWLDataProperty propiedadDeDatos = null;
    
    /**
     * Figure to draw
     */
    private FigDataProperty figura = null;
    
    /**
     * Constructor
     */
    public NodeDataProperty (){
    	super();
    	
    	//Creamos la nueva propiedad de datos
    	OWLDataFactory  f = ExampleViewComponent.manager.getOWLDataFactory();
    	
    	String nombreNueva = JOptionPane.showInputDialog("New data proprerty name, please:");
    	
    	if (nombreNueva != null && nombreNueva != ""){
	        propiedadDeDatos = f.getOWLDataProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + nombreNueva));
	        OWLAxiom axiom = f.getOWLDeclarationAxiom(propiedadDeDatos);
	        
	        AddAxiom addAxiom = new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), axiom);
	        
	        ExampleViewComponent.manager.applyChange(addAxiom);
	        
	    	addPort(east = new OWLPort(this));
	        addPort(west = new OWLPort(this));
	        addPort(north = new OWLPort(this));
	        addPort(south = new OWLPort(this));
    	}
    }
    
    /**
     * Contructor. Creates a new node
     * @param unaPropiedadDeDatos OWL class represented by the new node
     */
    public NodeDataProperty (OWLDataProperty unaPropiedadDeDatos){
    	super();
    	propiedadDeDatos = unaPropiedadDeDatos;
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
        return propiedadDeDatos.toString();
    }
    
    /**
     * Gets the figure associated with the node
     * @return la figura
     */
    public FigDataProperty getFigDataProperty(){
    	return figura;
    }

    /**
     * Draws the node in a layer
     * @param lay layer where we want to draw the node
     * @return the new figure
     */
    public FigDataProperty makePresentation(Layer lay) {
    	
    	if (propiedadDeDatos != null){
	    	figura = new FigDataProperty(propiedadDeDatos);	    	
	        figura.setOwner(this);
	    	figura.setBlinkPorts(true);
	        return figura;
    	}
    	else return null;
    }

	/**
	 * @return the claseOWL
	 */
	public OWLDataProperty getOWLDataProperty() {
		return propiedadDeDatos;
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
		if (propiedadDeDatos != null)
			return propiedadDeDatos.toString();
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
		return this;
	}
	public boolean isDataProperty(){
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OWLNode#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		return propiedadDeDatos;
	}
}

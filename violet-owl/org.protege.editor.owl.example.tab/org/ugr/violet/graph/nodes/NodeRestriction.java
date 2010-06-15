package org.ugr.violet.graph.nodes;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.tigris.gef.base.Layer;
import org.ugr.violet.base.Restriction;
import org.ugr.violet.layout.LayoutedNode;
import org.ugr.violet.presentation.FigRestriction;
import org.ugr.violet.presentation.OWLFigure;

public class NodeRestriction extends OWLNode implements Serializable, LayoutedNode {

	/**
	 * serial version of the class NetNode
	 */
	private static final long serialVersionUID = -3380856158583786547L;

	/**
	 * OWL property represented by the node
	 */
	private OWLObjectProperty property = null;


	/**
	 * OWL class where the restrictions described by the node are applied
	 */
	private OWLEntity entidad = null;

	/**
	 * 
	 */
	private Vector<Restriction> restrictions = null;

	/**
	 * Figure to draw
	 */
	private FigRestriction figura = null;


	/**
	 * Contructor. Creates a new node
	 * @param unaClase OWL class represented by the new node
	 */
	public NodeRestriction (OWLEntity c, OWLObjectProperty prop, Vector<Restriction> r){
		super();
		property = prop;
		restrictions = r;
		entidad = c;
		addPort(east = new PortClass(this));
		addPort(west = new PortClass(this));
		addPort(north = new PortClass(this));
		addPort(south = new PortClass(this));
	}

	/**
	 * Contructor. Creates a new node
	 * @param unaClase OWL class represented by the new node
	 */
	public NodeRestriction (OWLEntity c, OWLObjectProperty prop, Restriction r){
		super();
		property = prop;
		restrictions = new Vector<Restriction>();
		restrictions.add(r);
		entidad = c;
		addPort(east = new PortClass(this));
		addPort(west = new PortClass(this));
		addPort(north = new PortClass(this));
		addPort(south = new PortClass(this));
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

	}

	public boolean addRestrictions(Vector<Restriction> r){
		restrictions.addAll(r);

		figura.actualizaFigura();

		return true;
	}

	public boolean addRestrictions(Restriction r){
		restrictions.add(r);

		figura.actualizaFigura();

		return true;
	}

	public boolean removeRestrictions(Vector<Restriction> r){
		restrictions.removeAll(r);
		// si ya no quedan restricciones borramos la figura del modelo
		if (restrictions.size() == 0){
			figura.removeFromDiagram();
			return false;
		}
		else {
			figura.actualizaFigura();
			return true;
		}
	}

	/**
	 * @param r
	 */
	public boolean removeRestrictions(Restriction r) {
		restrictions.remove(r);
		// si ya no quedan restricciones borramos la figura del modelo
		if (restrictions.size() == 0){
			figura.removeFromDiagram();
			return false;
		}
		else {
			figura.actualizaFigura();
			return true;
		}
	}


	/**
	 * @return the property
	 */
	public OWLObjectProperty getProperty() {
		return property;
	}

	/**
	 * @return Id of the node
	 */
	public String getId() {
		return property.toString();
	}


	/**
	 * @return the restrictions
	 */
	public Vector<Restriction> getRestrictions() {
		return restrictions;
	}

	/**
	 * Gets the figure associated with the node
	 * @return la figura
	 */
	public FigRestriction getRestrictionFig(){
		return figura;
	}

	/**
	 * @return the clase
	 */
	public OWLClass getClase() {
		if (!entidad.isOWLClass()) 
			return null; 
		return
		entidad.asOWLClass();
	}

	public OWLDataProperty getOWLDataProperty(){
		if (!entidad.isOWLDataProperty())
			return null;
		else
			return entidad.asOWLDataProperty();
	}


	/**
	 * Draws the node in a layer
	 * @param lay layer where we want to draw the node
	 * @return the new figure
	 */
	public FigRestriction makePresentation(Layer lay) {
		if (property != null) {
			figura = new FigRestriction(property, restrictions);

			figura.setOwner(this);
			figura.setBlinkPorts(true);
			return figura;
			}
		else 
			return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (property != null)
			return property.toString();
		else
			return "";
	}

	/** Sample event handler: prints a message to the console. */
	public void mouseEntered(MouseEvent e) {}


	/** Sample event handler: prints a message to the console. */
	public void mouseExited(MouseEvent e) {}

	/** Sample event handler: prints a message to the console. */
	public void mouseReleased(MouseEvent e) {}

	/** Sample event handler: prints a message to the console. */
	public void mousePressed(MouseEvent e) {}

	/** Sample event handler: prints a message to the console. */
	public void mouseClicked(MouseEvent e) {
	}

	/** Sample event handler: prints a message to the console. */
	public void mouseDragged(MouseEvent e) {    }

	/** Sample event handler: prints a message to the console. */
	public void mouseMoved(MouseEvent e) {}

	/** Sample event handler: prints a message to the console. */
	public void keyTyped(KeyEvent e) {}

	/** Sample event handler: prints a message to the console. */
	public void keyReleased(KeyEvent e) {}

	/** Sample event handler: prints a message to the console. */
	public void keyPressed(KeyEvent e) {}

	public Point getLocation() {
		return null;
	}

	public OWLEntity getOWLEntity(){
		return entidad;
	}

	public Dimension getSize() {

		return null;
	}

	public void setLocation(Point newLocation) {

		this.figura.setLocation(newLocation);
	}

	public OWLClass getOWLClass(){
		if (!entidad.isOWLClass()) 
			return null; 
		return
		entidad.asOWLClass();
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
	public boolean isNodeRestriction() {
		return true;
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
		return this;
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

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#asNodeIntersection()
	 */
	@Override
	public NodeIntersection asNodeIntersection() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.nodes.OntologyNode#isNodeIntersection()
	 */
	@Override
	public boolean isNodeIntersection() {
		// TODO Auto-generated method stub
		return false;
	}
}

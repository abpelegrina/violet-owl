/**
 * 
 */
package org.ugr.violet.graph.nodes;

import java.awt.Point;
import java.net.URI;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.GraphModel;
import org.protege.owl.examples.tab.ExampleViewComponent;
import org.ugr.violet.presentation.FigObjectProperty;
import org.ugr.violet.presentation.OntologyFig;

/**
 * @author anab
 *
 */
public class NodeObjectProperty extends OntologyNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1741746760532032579L;
	
	private OWLObjectProperty propiedad = null;
	
	/**
	 * @return the propiedad
	 */
	public OWLObjectProperty getPropiedad() {
		return propiedad;
	}

	private FigObjectProperty fig = null;
	
	public NodeObjectProperty(OWLObjectProperty pr){
		super();
		
		propiedad = pr;
		
		addPort(east = new PortObjectProperty(this));
        addPort(west = new PortObjectProperty(this));
        addPort(north = new PortObjectProperty(this));
        addPort(south = new PortObjectProperty(this));
	}
	
	
	public NodeObjectProperty(){
		super();
		
    	String nombreNueva = JOptionPane.showInputDialog("New object proprty name, please:");
    	
    	if (nombreNueva != null && nombreNueva != ""){
    	

        	//Creamos la nueva clase
        	OWLDataFactory  f = ExampleViewComponent.manager.getOWLDataFactory();
        	        	
	        propiedad = f.getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + nombreNueva));
	        OWLDeclarationAxiom axiom = f.getOWLDeclarationAxiom(propiedad);
	        
	        AddAxiom addAxiom = new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), axiom);
	        ExampleViewComponent.manager.applyChange(addAxiom);
	        
	    	addPort(east = new PortObjectProperty(this));
	        addPort(west = new PortObjectProperty(this));
	        addPort(north = new PortObjectProperty(this));
	        addPort(south = new PortObjectProperty(this));
	        return;
    	}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void initialize(Hashtable args) {
        super.initialize(args);
    }
	
	@Override
	public OntologyFig makePresentation(Layer l){

		if (propiedad!=null){
		
    	fig = new FigObjectProperty(propiedad);
    	
        fig.setOwner(this);
    	fig.setBlinkPorts(true);
        return fig;
        }
		else return null;
	}
	
	public FigObjectProperty getFigObjectProperty(){
		return fig;
	}
	
    /* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.NetNode#postConnect(org.tigris.gef.graph.GraphModel, java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void postConnect(GraphModel gm, Object anotherNode, Object myPort,
			Object otherPort) {
	}

	/* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.NetNode#postDisconnect(org.tigris.gef.graph.GraphModel, java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void postDisconnect(GraphModel gm, Object anotherNode,
			Object myPort, Object otherPort) {
		super.postDisconnect(gm, anotherNode, myPort, otherPort);
	}


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (propiedad != null)
			return propiedad.toString();
		else
			return "";
	}
	
	public Point getLocation() {
		return this.fig.getLocation();
	}
	
	public void setLocation(Point newLocation) {
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
	public OntologyFig getOntologyFig() {
		return fig;
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
		return false;
	}

	@Override
	public String getId() {
		return toString();
	}

	@Override
	public NodeObjectProperty asNodeObjectProperty() {
		return this;
	}

	@Override
	public boolean isNodeObjectProperty() {
		return true;
	}

	@Override
	public NodeUnion asNodeUnion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNodeUnion() {
		// TODO Auto-generated method stub
		return false;
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

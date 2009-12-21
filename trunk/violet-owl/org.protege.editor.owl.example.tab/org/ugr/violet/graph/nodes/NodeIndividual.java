package org.ugr.ontology.graph.nodes;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;
import java.net.URI;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLIndividual;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.GraphModel;
import org.ugr.ontology.layout.LayoutedNode;
import org.ugr.ontology.presentation.FigIndividual;
import org.ugr.ontology.presentation.OntologyFig;


public class NodeIndividual extends OntologyNode implements Serializable, LayoutedNode {
	/**
	 * serial version of the class NetNode
	 */
	private static final long serialVersionUID = -3380856158583786547L;
	
	/**
	 * OWL class represented by the node
	 */
    private OWLIndividual individuo = null;
    
    /**
     * Figure to draw
     */
    private FigIndividual figura = null;
    
    /**
     * Constructor
     */
    public NodeIndividual (){
    	super();
    	
    	String nombreNueva = JOptionPane.showInputDialog("New individual name, please:");
    	
    	if (nombreNueva != null && nombreNueva != ""){
    	

        	//Creamos la nueva clase
        	OWLDataFactory  f = ExampleViewComponent.manager.getOWLDataFactory();
        	        	
	        individuo = f.getOWLIndividual(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + nombreNueva));
	        OWLDeclarationAxiom axiom = f.getOWLDeclarationAxiom(individuo);
	        
	        AddAxiom addAxiom = new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), axiom);
	        ExampleViewComponent.manager.applyChange(addAxiom);
	        
	    	addPort(east = new PortIndividual(this));
	        addPort(west = new PortIndividual(this));
	        addPort(north = new PortIndividual(this));
	        addPort(south = new PortIndividual(this));
	        return;
    	}
    }
    
    /**
     * Actualiza la figura de acuerdo con los datos actuales de la ontologia
     */
    public void updateFigure(){
    	if (figura != null){
    		figura.update();
    	}
    }

    
    /**
     * Contructor. Creates a new node
     * @param unaClase OWL class represented by the new node
     */
    public NodeIndividual (OWLIndividual anIndividual){
    	super();
    	individuo = anIndividual;
    	addPort(east = new PortIndividual(this));
        addPort(west = new PortIndividual(this));
        addPort(north = new PortIndividual(this));
        addPort(south = new PortIndividual(this));
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
    
    /**
     * @return Id of the node
     */
    public String getId() {
        return toString();
    }
    
    /**
     * Gets the figure associated with the node
     * @return la figura
     */
    public FigIndividual getFigIndividual(){
    	return figura;
    }

    /**
     * Draws the node in a layer
     * @param lay layer where we want to draw the node
     * @return the new figure
     */
    public FigIndividual makePresentation(Layer lay) {
    	
    	if (individuo != null){
    	
	    	figura = new FigIndividual(individuo);
	    	
	        figura.setOwner(this);
	    	figura.setBlinkPorts(true);
	        return figura;
    	}
    	else 
    		return null;
    } 
  

	/**
	 * @return the claseOWL
	 */
	public OWLIndividual getOWLIndividual() {
		return individuo;
	}


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (individuo != null)
			return individuo.toString();
		else
			return "";
	}
	
	public Point getLocation() {
		return this.figura.getLocation();
	}

	public Dimension getSize() {
		return null;
	}
	
	public void setLocation(Point newLocation) {
		this.figura.setLocation(newLocation);
	}

	@Override
	public OntologyFig getOntologyFig() {
		return figura;
	}

	/*************************************************************************************/
	
	@Override
	public boolean isNodeClass() {
		return false;
	}

	@Override
	public boolean isNodeIndividual() {
		return true;
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
		return this;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNodeUnion() {
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

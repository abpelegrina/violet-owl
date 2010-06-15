/**
 * 
 */
package org.ugr.violet.graph.nodes;

import java.net.URI;

import org.semanticweb.owl.model.OWLClass;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.base.Layer;
import org.protege.owl.examples.tab.ExampleViewComponent;
import org.ugr.violet.presentation.FigNAryRelation;
import org.ugr.violet.presentation.OWLFigure;

/**
 * @author anab
 *
 */
public class NodeNAryRelation extends NodeClass {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3865216979601064706L;
	public static final OWLClass claseBase = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#Reified_Relation"));
	private FigNAryRelation figura = null;


	/**
	 * @param claseOWL
	 */
	public NodeNAryRelation(OWLClass claseOWL) {
		// TODO Auto-generated constructor stub
		super(claseOWL);
	}

	
	@Override
	public FigNAryRelation makePresentation(Layer lay) {
    	
    	if (claseOWL != null){
	    	figura = new FigNAryRelation(claseOWL);	    	
	        figura.setOwner(this);
	    	figura.setBlinkPorts(true);
	    	figura.changeFig(CmdChangeFig.DIAMOND);
	        return figura;
    	}
    	else return null;
    }
	
	@Override
	public OWLFigure getOntologyFig() {
		// TODO Auto-generated method stub
		return figura;
	}


	/* (non-Javadoc)
	 * @see org.ugr.ontology.graph.nodes.NodeClass#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}


	public boolean isClassRelataion(){
		return true;
	}
	
	public NodeNAryRelation asNodeClassRelation(){
		return this;
	}
}

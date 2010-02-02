/**
 * 
 */
package org.protege.owl.examples.tab;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.semanticweb.owl.model.OWLIndividual;
import org.tigris.gef.graph.GraphModel;
import org.ugr.violet.graph.presentation.JOntologyActivityGraph;
import org.ugr.violet.graph.presentation.JOntologyGraph;
import org.ugr.violet.ui.OntologyPalette;

/**
 * @author anab
 *
 */
public class JTabPlugin extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2207313600224574469L;

	public static JOntologyActivityGraph lienzo = null;
	
	
	public JTabPlugin (){
		
		super();
		
		
		OntologyPalette barraDeHerramientas = new OntologyPalette();
        lienzo = new JOntologyActivityGraph(ExampleViewComponent.manager.getActiveOntology(), barraDeHerramientas);        

        OntologyPalette barraDeHerramientas2 = new OntologyPalette();
        JOntologyGraph l2 = new JOntologyGraph(ExampleViewComponent.manager.getActiveOntology(), barraDeHerramientas2);
        
        this.addTab("Basic View", l2);
        this.addTab("Activitiy View", lienzo);
	}
	
	public OWLIndividual getTarea(){
		return lienzo.getTarea();
	}

	public OWLIndividual getSecuencia(){
		return lienzo.getSecuencia();
	}
	
	public GraphModel getCurrentGraphModel(){
		return lienzo.getGraphModel();
	}
}

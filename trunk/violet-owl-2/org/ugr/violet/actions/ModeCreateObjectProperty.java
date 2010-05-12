/**
 * 
 */
package org.ugr.violet.actions;

import java.net.URI;

import javax.swing.JOptionPane;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.tigris.gef.presentation.Fig;
import org.ugr.violet.graph.OntologyGraphModel;
import org.ugr.violet.graph.edges.DomainRangeEdge;
import org.ugr.violet.graph.nodes.NodeNAryRelation;
import org.ugr.violet.presentation.FigClass;
import org.ugr.violet.presentation.FigNAryRelation;

/**
 * @author anab
 *
 */
public class ModeCreateObjectProperty extends ModeCreateAxiom {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5891216899390033425L;


	/* (non-Javadoc)
	 * @see org.ugr.violet.actions.ModeCreateAxiom#accionReleaseMouse(org.ugr.violet.graph.OntologyGraphModel)
	 */
	@Override
	public void accionReleaseMouse(OntologyGraphModel ogm) {
		
		if (FigNAryRelation.class.isInstance(sourceFigNode) && FigNAryRelation.class.isInstance(destFigNode)){
		
		}
		else if (!sourceFigNode.toString().equals(destFigNode.toString())) {
			String nombreNueva;
    		OWLClass sourceClass = sourceFigNode.getOWLEntity().asOWLClass();
    		OWLClass destClass = destFigNode.getOWLEntity().asOWLClass();
    		
    		// comprobamos si el destino o el origen son subclases de la clase N-Aria
    		if (NodeNAryRelation.claseBase.getSubClasses(ExampleViewComponent.manager.getActiveOntology()).contains(sourceClass)){
    			nombreNueva = JOptionPane.showInputDialog("New role name, please:");
    			
    			System.err.println("La clase origen es una propiedad de objetos");
    		}
    		else if (NodeNAryRelation.claseBase.getSubClasses(ExampleViewComponent.manager.getActiveOntology()).contains(destClass)){
    			nombreNueva = JOptionPane.showInputDialog("New role name, please:");
    			System.err.println("La clase destino es una propiedad de objetos");
    		}
    		else {
    			nombreNueva = JOptionPane.showInputDialog("New object property name, please:");
    		} 
        	
        	if (nombreNueva != null && nombreNueva != ""){
        	
            	//Creamos la nueva clase
            	OWLDataFactory  factory = ExampleViewComponent.manager.getOWLDataFactory();
            	        	
            	OWLObjectProperty propiedad = factory.getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + nombreNueva));
    	        OWLDeclarationAxiom axiom = factory.getOWLDeclarationAxiom(propiedad);
    	        
    	        AddAxiom addAxiom = new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), axiom);
    	        ExampleViewComponent.manager.applyChange(addAxiom);
    	        
    	        ogm.addRangeAxiom(propiedad, destClass);
    	        ogm.addDomainAxiom(propiedad, sourceClass);
    	        
    	        DomainRangeEdge arista = new DomainRangeEdge(sourceClass,destClass,propiedad);
    	        ogm.addConnection(sourceFigNode, destFigNode, arista);
        	}
    	}
		done();
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionDestino(org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionDestino(Fig f) {
		// TODO Auto-generated method stub
		return (FigClass.class.isInstance(f) || FigNAryRelation.class.isInstance(f));
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionOrigen(org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionOrigen(Fig f) {
		// TODO Auto-generated method stub
		return FigClass.class.isInstance(f) || FigNAryRelation.class.isInstance(f);
	}

}

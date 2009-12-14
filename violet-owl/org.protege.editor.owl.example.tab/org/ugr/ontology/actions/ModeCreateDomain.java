/**
 * 
 */
package org.ugr.ontology.actions;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.presentation.Fig;
import org.ugr.ontology.graph.OntologyGraphModel;
import org.ugr.ontology.presentation.FigClass;
import org.ugr.ontology.presentation.FigObjectProperty;

/**
 * @author anab
 *
 */
public class ModeCreateDomain extends ModeCreateAxiom {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5891216899390033425L;


	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#accionReleaseMouse(org.ugr.ontology.graph.OntologyGraphModel)
	 */
	@Override
	public void accionReleaseMouse(OntologyGraphModel modelo) {
		if (!destFigNode.toString().equals(sourceFigNode.toString())) {
        	
    		OWLEntity destino = destFigNode.getOWLEntity();
    		OWLEntity origen = sourceFigNode.getOWLEntity();
    		if (origen.isOWLObjectProperty() && destino.isOWLClass())
    			modelo.addDomainAxiom(origen.asOWLObjectProperty(), destino.asOWLClass());
    	}
		done();
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionDestino(java.lang.Class, org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionDestino(Fig f) {
		return FigClass.class.isInstance(f);
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionOrigen(java.lang.Class, org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionOrigen(Fig f) {
		return FigObjectProperty.class.isInstance(f);
	}

}

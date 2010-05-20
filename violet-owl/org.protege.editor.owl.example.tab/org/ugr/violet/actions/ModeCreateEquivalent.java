/**
 * 
 */
package org.ugr.violet.actions;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.presentation.Fig;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.presentation.OntologyFig;

/**
 * @author anab
 *
 */
public class ModeCreateEquivalent extends ModeCreateAxiom {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5891216899390033425L;

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#accionReleaseMouse(org.ugr.ontology.graph.OntologyGraphModel)
	 */
	@Override
	public void accionReleaseMouse(OWLGraphModel ogm) {
		if (!destFigNode.toString().equals(sourceFigNode.toString())) {
        	
    		OWLEntity e1 = destFigNode.getOWLEntity();
    		OWLEntity e2 = sourceFigNode.getOWLEntity();
    		
    		if(e1.isOWLClass() && e2.isOWLClass())
    			ogm.addEquivalentClassAxiom(e1.asOWLClass(), e2.asOWLClass());
    		else if (e1.isOWLObjectProperty() && e2.isOWLObjectProperty())
    			ogm.addEquivalentObjectPropertyAxiom(e1.asOWLObjectProperty(), e2.asOWLObjectProperty());
    		else if (e1.isOWLIndividual() && e2.isOWLIndividual())
    			ogm.addSameAsIndividualAxiom(e1.asOWLIndividual(), e2.asOWLIndividual());
    		else if (e1.isOWLDataProperty() && e2.isOWLDataProperty())
    			ogm.addEquivalentDataPropertyAxiom(e1.asOWLDataProperty(), e2.asOWLDataProperty());
    	}
		done();
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionDestino(org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionDestino(Fig f) {
		return OntologyFig.class.isInstance(f);
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionOrigen(org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionOrigen(Fig f) {
		return OntologyFig.class.isInstance(f);
	}

}

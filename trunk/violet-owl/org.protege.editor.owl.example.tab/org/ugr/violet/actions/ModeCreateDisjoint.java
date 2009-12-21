/**
 * 
 */
package org.ugr.ontology.actions;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.presentation.Fig;
import org.ugr.ontology.graph.OntologyGraphModel;
import org.ugr.ontology.presentation.OntologyFig;

/**
 * @author anab
 *
 */
public class ModeCreateDisjoint extends ModeCreateAxiom {

	private static final long serialVersionUID = -5891216899390033425L;

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#accionReleaseMouse(org.ugr.ontology.graph.OntologyGraphModel)
	 */
	@Override
	public void accionReleaseMouse(OntologyGraphModel ogm) {
		if (!destFigNode.toString().equals(sourceFigNode.toString())) {
			OWLEntity e1 = destFigNode.getOWLEntity();
			OWLEntity e2 = sourceFigNode.getOWLEntity();
			
			if(e1.isOWLClass() && e2.isOWLClass())
				ogm.addDisjointClassAxiom(e1.asOWLClass(), e2.asOWLClass());
			else if (e1.isOWLObjectProperty() && e2.isOWLObjectProperty())
				ogm.addDisjointObjectPropertyAxiom(e1.asOWLObjectProperty(), e2.asOWLObjectProperty());
			else if (e1.isOWLIndividual() && e2.isOWLIndividual())
				ogm.addDifferentIndividualAxiom(e1.asOWLIndividual(), e2.asOWLIndividual());
			else if (e1.isOWLDataProperty() && e2.isOWLDataProperty())
				ogm.addDisjointDataPropertyAxiom(e1.asOWLDataProperty(), e2.asOWLDataProperty());
		}
		done();
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionDestino(java.lang.Class, org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionDestino(Fig f) {
		return OntologyFig.class.isInstance(f);
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionOrigen(java.lang.Class, org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionOrigen(Fig f) {
		// TODO Auto-generated method stub
		return OntologyFig.class.isInstance(f);
	}

}

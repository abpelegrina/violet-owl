/**
 * 
 */
package org.ugr.violet.actions;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.presentation.Fig;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.presentation.OWLFigure;

/**
 * @author anab
 *
 */
public class ModeCreateSub extends ModeCreateAxiom {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5891216899390033425L;

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#accionReleaseMouse()
	 */
	@Override
	public void accionReleaseMouse(OWLGraphModel ogm) {
		
    	if (!destFigNode.toString().equals(sourceFigNode.toString())) {
        	
    		System.out.println("Coordenadas del destino " +  destFigNode.getLocation());
    		System.out.println("Coordenadas del origen " +  sourceFigNode.getLocation());
    		
    		OWLEntity e1 = destFigNode.getOWLEntity();
    		OWLEntity e2 = sourceFigNode.getOWLEntity();
    		
    		if(e1.isOWLClass() && e2.isOWLClass())
    			ogm.addSubClassAxiom(e2.asOWLClass(), e1.asOWLClass());
    		else if (e1.isOWLObjectProperty() && e2.isOWLObjectProperty())
    			ogm.addSubObjectPropertyAxiom(e2.asOWLObjectProperty(), e1.asOWLObjectProperty());
    	}
    	done();		
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionDestino(java.lang.Class, org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionDestino(Fig f) {
		return OWLFigure.class.isInstance(f);
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.actions.ModeCreateAxiom#checkCondicionOrigen(java.lang.Class, org.tigris.gef.presentation.Fig)
	 */
	@Override
	public boolean checkCondicionOrigen(Fig f) {
		return OWLFigure.class.isInstance(f);
	}

}

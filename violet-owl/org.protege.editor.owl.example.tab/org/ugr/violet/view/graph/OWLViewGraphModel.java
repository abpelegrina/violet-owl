/**
 * 
 */
package org.ugr.violet.view.graph;

import java.awt.Point;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLOntology;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.view.ViewManager;

/**
 * @author anab
 *
 */
public class OWLViewGraphModel extends OWLGraphModel {
	
	protected ViewManager manager;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7202999365698620524L;

	/**
	 * @param ont
	 */
	public OWLViewGraphModel(OWLOntology ont) {
		super(ont);
		manager = new ViewManager("<<file_name>>");
		
		
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.OntologyGraphModel#addClass(java.lang.String, java.awt.Point)
	 */
	@Override
	public boolean addClass(String className, Point location) {
		
		className=className.trim();

		// nos aseguramos que la clase no esta ya en el diagrama
		if ( !isThisClassPresent(className) ) {

			// buscamos en la ontología la clase por el nombre
			OWLClass claseOWL = getOwlClassByName(className);
			
			if(claseOWL != null /*&& manager.addOWLClass(claseOWL)*/)
				return super.addClass(className, location);
			else
				return false;
		}
		return
			false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.OntologyGraphModel#addDataProperty(java.lang.String, java.awt.Point)
	 */
	@Override
	public boolean addDataProperty(String dataPropertyName, Point nodeLocation) {
		// TODO Auto-generated method stub
		//if ()
			return super.addDataProperty(dataPropertyName, nodeLocation);
		
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.OntologyGraphModel#addIndividual(java.lang.String, java.awt.Point)
	 */
	@Override
	public boolean addIndividual(String individualName, Point location) {
		
		individualName=individualName.trim();
		OWLIndividual ind = this.getOwlIndividualByName(individualName);

		if (ind != null) {
			//if (manager.addOWLIndividual(ind))
				return super.addIndividual(individualName, location);
			//else 
				//return false;
		}
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.OntologyGraphModel#addObjectProperty(java.lang.String, java.awt.Point)
	 */
	@Override
	public boolean addObjectProperty(String objectPropertyName, Point location) {
		
		objectPropertyName=objectPropertyName.trim();
		OWLObjectProperty propiedad = this.getOwlObjectPropertyByName(objectPropertyName);

		if (propiedad != null) {
			//if(manager.addOWLObjectProperty(propiedad))
				return super.addObjectProperty(objectPropertyName, location);
			//else
				//return false;
		}
		else 
			return false;
	}
	
	
}

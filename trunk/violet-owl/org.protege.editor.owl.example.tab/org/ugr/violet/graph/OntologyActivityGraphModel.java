/**
 * 
 */
package org.ugr.violet.graph;

import java.awt.Point;

import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.ugr.violet.graph.edges.RestrictionEdge;
import org.ugr.violet.graph.nodes.NodeIndividual;
import org.ugr.violet.graph.nodes.activity.NodeActivityStep;

/**
 * @author anab
 * 
 */
public class OntologyActivityGraphModel extends OntologyGraphModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937652960022403075L;

	/**
	 * @param ont
	 */
	public OntologyActivityGraphModel(OWLOntology ont) {
		super(ont);
	}
	
	@Override
	public boolean addIndividual(String  individualName, Point location){
		individualName=individualName.trim();
		OWLIndividual ind = this.getOwlIndividualByName(individualName);

		if (ind != null) {
			NodeActivityStep nodeInd = new NodeActivityStep(ind);
			this.addNode(nodeInd);
			nodeInd.getOntologyFig().setLocation(location);

			// recorremos todas las aserciones de propiedades de objetos asociadas al nuevo individuo
			//TODO recuperar solo las aserciones válidas para este diagrama
			/*
			for(OWLObjectPropertyAssertionAxiom ax : ontologia.getObjectPropertyAssertionAxioms(ind)){
				OWLIndividual objeto = ax.getObject();
				OWLIndividual sujeto = ax.getSubject();
				OWLObjectProperty propiedad = ax.getProperty().asOWLObjectProperty();

				RestrictionEdge r = new RestrictionEdge(sujeto, objeto);
				r.setLabel(propiedad.toString());

				this.addConnection(sujeto, objeto, r);
			}*/
			return true;
		}
		else 
			return false;
	}


}

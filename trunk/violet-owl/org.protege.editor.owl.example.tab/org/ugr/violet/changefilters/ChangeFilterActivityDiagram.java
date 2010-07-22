package org.ugr.violet.changefilters;

import java.net.URI;

import org.protege.owl.examples.tab.VioletActivityEditor;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.ugr.violet.graph.ActivityGraphModel;
import org.ugr.violet.graph.edges.OWLEdge;
import org.ugr.violet.graph.edges.activity.FollowedByEdge;

/**
 * Filtra los cambios producidos en la ontología y actualiza el diagrama en consecuencia
 * @author anab
 */
public class ChangeFilterActivityDiagram extends ChangeFilterDiagram {

	private static OWLClass Followed_by_Relation = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(VioletActivityEditor.manager.getActiveOntology().getURI() + "#Followed_by_Relation"));
	
	/**
	 * @param o
	 */
	public ChangeFilterActivityDiagram(ActivityGraphModel o) {
		super(o);
		
		
	}
	
	@Override
	public void visit(OWLClassAssertionAxiom axiom){
		if (axiom.getClassesInSignature().contains(Followed_by_Relation)){
			if (this.isRemove()){
				
				FollowedByEdge aBorrar = null;

				// borrar el enlace entre las clases disjuntas
				for (Object o : ogm.getEdges()){
					FollowedByEdge e = ((FollowedByEdge) o);

					if (e.toString().equals(axiom.getIndividual().toString())){
						aBorrar = e;
					}
				}

				if (aBorrar != null)
					ogm.removeEdge(aBorrar);
			}
			else if(this.isAdd()) {
				//((ActivityGraphModel)ogm).addFlow(axiom.getIndividual());
			}
		}
	}

	// implementado
	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {
		
		OWLDataFactory factory = VioletActivityEditor.manager
		.getOWLDataFactory();
		OWLOntology ont = VioletActivityEditor.manager.getActiveOntology();

		OWLIndividual objeto = axiom.getObject();
		OWLIndividual sujeto = axiom.getSubject();
		OWLObjectProperty property = axiom.getProperty().asOWLObjectProperty();
		
		String propertyName = axiom.getProperty().getNamedProperty().toString();
		
		// evaluates property
		OWLObjectProperty evaluates = factory.getOWLObjectProperty(URI
				.create(ActivityGraphModel.URIAmenities + "#evaluates"));
		OWLDeclarationAxiom axiom1 = factory.getOWLDeclarationAxiom(evaluates);

		AddAxiom addAxiom = new AddAxiom(ont, axiom1);
		VioletActivityEditor.manager.applyChange(addAxiom);
		
		
		if (this.isRemove()){
			if (property.compareTo(evaluates) == 0){

				OWLEdge edge = ogm.getEdge(sujeto.toString());
				
				if (edge != null) {
					FollowedByEdge fbe = (FollowedByEdge) edge;
					fbe.update(null);
				}
			}
		}
		else if (this.isAdd()){
			if (property.compareTo(evaluates) == 0){
				
				OWLEdge edge = ogm.getEdge(sujeto.toString());
				
				if (edge != null) {
					FollowedByEdge fbe = (FollowedByEdge) edge;
					fbe.update(sujeto);
				}
				
			}
		}
	}

}

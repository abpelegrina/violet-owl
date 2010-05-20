package org.ugr.violet.changefilters;

import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.edges.OWLEdge;
import org.ugr.violet.graph.edges.RestrictionEdge;
import org.ugr.violet.graph.edges.activity.FollowedByEdge;

/**
 * Filtra los cambios producidos en la ontología y actualiza el diagrama en consecuencia
 * @author anab
 */
public class ChangeFilterActivityDiagram extends ChangeFilterDiagram {

	/**
	 * @param o
	 */
	public ChangeFilterActivityDiagram(OWLGraphModel o) {
		super(o);
		
		
	}

	// implementado
	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {

		OWLIndividual objeto = axiom.getObject();
		OWLIndividual sujeto = axiom.getSubject();
		
		String propertyName = axiom.getProperty().getNamedProperty().toString();
		
		// comprobamos que sea una propieada de objeto válida para este tipo de diagrama
		/*if ( !propertyName.equals("receive") && !propertyName.equals("send") && !propertyName.equals("is_in_state") && !propertyName.equals("followed_by") ) {
			System.err.println("ERROR: ese tipo de propiedad no está permitido: " + propertyName);
			return;
		}*/
		
		
		if (this.isRemove()){
			RestrictionEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				RestrictionEdge e = ((OWLEdge) o).asRestrictionEdge();

				if (e != null && e.isSujeto(sujeto) && e.isObjeto(objeto)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd()){
			FollowedByEdge edge = new FollowedByEdge(objeto.asOWLIndividual(), sujeto
					.asOWLIndividual());
			
			if (ogm != null)
				ogm.addConnection(sujeto, objeto, edge);
		}
	}

}

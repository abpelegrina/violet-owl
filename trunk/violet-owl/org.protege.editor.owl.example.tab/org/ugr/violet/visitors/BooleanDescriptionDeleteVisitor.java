package org.ugr.violet.visitors;

import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.semanticweb.owl.model.OWLObjectIntersectionOf;
import org.semanticweb.owl.model.OWLObjectUnionOf;
import org.semanticweb.owl.util.OWLDescriptionVisitorAdapter;
import org.tigris.gef.graph.presentation.NetPort;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.nodes.OWLNode;

public class BooleanDescriptionDeleteVisitor extends
OWLDescriptionVisitorAdapter {

	private OWLGraphModel ogm = null;
	public BooleanDescriptionDeleteVisitor(OWLGraphModel m){
		ogm = m;
	}

	@Override
	public void visit(OWLObjectComplementOf desc) {
		super.visit(desc);

		System.err.println("\nVisitando axioma " + desc);

		// es una clase anonima => otros axiomas
		if (desc.getOperand().isAnonymous()){
			desc.getOperand().accept(this);
		}
	}

	@Override
	public void visit(OWLObjectIntersectionOf desc) {		
		super.visit(desc);

		System.err.println("\nVisitando axioma " + desc);


		// creamos el nodo interseccion
		OWLNode nodoInterseccion = ogm.findOntologyNode(desc);

		for (OWLDescription d : desc.getOperands())			
			if (d.isAnonymous())
				d.accept(this);

		if (nodoInterseccion != null) {
			ogm.removeNode(nodoInterseccion);
			
			for (Object o : nodoInterseccion.getPorts()){
				NetPort p = (NetPort) o;

				for (Object edge : p.getEdges())
					ogm.removeEdge(edge);
			}
		}
	}

	@Override
	public void visit(OWLObjectUnionOf desc) {
		super.visit(desc);

		System.err.println("\nVisitando axioma " + desc);

		// creamos el nodo interseccion
		OWLNode nodoUnion = ogm.findOntologyNode(desc);

		for (OWLDescription d : desc.getOperands())			
			if (d.isAnonymous())
				d.accept(this);

		if (nodoUnion != null) {
			ogm.removeNode(nodoUnion);

			for (Object o : nodoUnion.getPorts()){
				NetPort p = (NetPort) o;

				for (Object edge : p.getEdges())
					ogm.removeEdge(edge);
			}

		}
	}

}

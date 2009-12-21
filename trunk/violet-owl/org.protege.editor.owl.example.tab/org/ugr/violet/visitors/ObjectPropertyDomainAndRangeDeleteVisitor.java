/**
 * 
 */
package org.ugr.ontology.visitors;

import java.util.ArrayList;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLObjectAllRestriction;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.semanticweb.owl.model.OWLObjectExactCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectIntersectionOf;
import org.semanticweb.owl.model.OWLObjectMaxCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectMinCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectOneOf;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectSelfRestriction;
import org.semanticweb.owl.model.OWLObjectSomeRestriction;
import org.semanticweb.owl.model.OWLObjectUnionOf;
import org.semanticweb.owl.model.OWLObjectValueRestriction;
import org.semanticweb.owl.util.OWLDescriptionVisitorAdapter;
import org.tigris.gef.graph.presentation.NetPort;
import org.ugr.ontology.base.Restriction;
import org.ugr.ontology.graph.OntologyGraphModel;
import org.ugr.ontology.graph.edges.OntologyEdge;
import org.ugr.ontology.graph.edges.RangeEdge;
import org.ugr.ontology.graph.nodes.NodeComplement;
import org.ugr.ontology.graph.nodes.NodeIntersection;
import org.ugr.ontology.graph.nodes.NodeOneOf;
import org.ugr.ontology.graph.nodes.NodeRestriction;
import org.ugr.ontology.graph.nodes.NodeUnion;
import org.ugr.ontology.graph.nodes.OntologyNode;

/**
 * @author anab
 *
 */
public class ObjectPropertyDomainAndRangeDeleteVisitor extends OWLDescriptionVisitorAdapter {
	private OntologyGraphModel ogm = null;
	private OWLObjectProperty propiedad = null;

	public ObjectPropertyDomainAndRangeDeleteVisitor(OntologyGraphModel modelo, OWLObjectProperty prop){
		ogm = modelo;
		propiedad = prop;
	}

	@Override
	public void visit(OWLClass desc) {
		super.visit(desc);

		ArrayList<RangeEdge> aEliminar = new ArrayList<RangeEdge>();

		// buscar el enlace y eliminarlo
		for (Object o: ogm.getEdges()){
			RangeEdge re = ((OntologyEdge) o).asRangeEdge();
			if (re != null && re.checkLink(propiedad) && re.checkLink(desc))
				aEliminar.add(re);
		}

		for (RangeEdge e : aEliminar)
			ogm.removeEdge(e);
	}

	@Override
	public void visit(OWLObjectAllRestriction desc) {
		super.visit(desc);
		Object[] clases = desc.getClassesInSignature().toArray();
		Restriction r = new Restriction(Restriction.ALL_RESTRICTION, (OWLClass)clases[0]);

		this.updateRestriccion(desc.getProperty().asOWLObjectProperty(), r);

	}

	@Override
	public void visit(OWLObjectExactCardinalityRestriction desc) {
		super.visit(desc);
		Object[] clases = desc.getClassesInSignature().toArray();		
		Restriction r = new Restriction(Restriction.EXACT_RESTRICTION, (OWLClass)clases[0], desc.getCardinality());

		this.updateRestriccion(desc.getProperty().asOWLObjectProperty(), r);
	}

	@Override
	public void visit(OWLObjectMaxCardinalityRestriction desc) {
		super.visit(desc);
		Object[] clases = desc.getClassesInSignature().toArray();
		Restriction r = new Restriction(Restriction.MAX_RESTRICTION, (OWLClass)clases[0], desc.getCardinality());

		this.updateRestriccion(desc.getProperty().asOWLObjectProperty(), r);
	}

	@Override
	public void visit(OWLObjectMinCardinalityRestriction desc) {
		super.visit(desc);
		Object[] clases = desc.getClassesInSignature().toArray();
		Restriction r = new Restriction(Restriction.MIN_RESTRICTION, (OWLClass)clases[0], desc.getCardinality());

		this.updateRestriccion(desc.getProperty().asOWLObjectProperty(), r);
	}

	@Override
	public void visit(OWLObjectSomeRestriction desc) {
		super.visit(desc);
		Object[] clases = desc.getClassesInSignature().toArray();
		Restriction r = new Restriction(Restriction.SOME_RESTRICTION, (OWLClass)clases[0]);

		this.updateRestriccion(desc.getProperty().asOWLObjectProperty(), r);		
	}

	/**
	 * 
	 * @param desc
	 * @param r
	 */
	private void updateRestriccion(OWLObjectProperty desc, Restriction r){

		NodeRestriction restriccion = ogm.findNodeRestriction(desc, propiedad);

		if (restriccion != null && !restriccion.removeRestrictions(r))
			ogm.removeNode(restriccion);

	}

	/******************************* OPERACIONES DE CONJUNTO ********************************/

	@Override
	public void visit(OWLObjectComplementOf desc) {
		super.visit(desc);
		ArrayList<NodeComplement> aEliminar = new ArrayList<NodeComplement>();

		BooleanDescriptionDeleteVisitor v = new BooleanDescriptionDeleteVisitor(ogm);


		if (desc.getOperand().isAnonymous())
			desc.getOperand().accept(v);

		for (Object o : ogm.getNodes()){
			NodeComplement nodo = ((OntologyNode) o).asNodeComplement();
			if (nodo != null && nodo.getOWLEntity().equals(propiedad) && desc.equals(nodo.getOWLObjectComplementOf()))
					aEliminar.add(nodo);
		}

		for (NodeComplement nodo : aEliminar) {
			ogm.removeNode(nodo);

			for (Object o : nodo.getPorts()){
				NetPort p = (NetPort) o;

				for (Object edge : p.getEdges())
					ogm.removeEdge(edge);
			}
		}
	}

	@Override
	public void visit(OWLObjectIntersectionOf desc) {
		super.visit(desc);
		ArrayList<NodeIntersection> aEliminar = new ArrayList<NodeIntersection>();

		BooleanDescriptionDeleteVisitor v = new BooleanDescriptionDeleteVisitor(ogm);

		for (OWLDescription d : desc.getOperands())
			if (d.isAnonymous())
				d.accept(v);

		for (Object o : ogm.getNodes()){
			NodeIntersection nodo = ((OntologyNode) o).asNodeIntersection();
			if (nodo != null && nodo.getOWLEntity().equals(propiedad) && desc.equals(nodo.getOWLObjectIntersectionOf()))
					aEliminar.add(nodo);
		}

		for (NodeIntersection nodo : aEliminar) {
			ogm.removeNode(nodo);

			for (Object o : nodo.getPorts()){
				NetPort p = (NetPort) o;

				for (Object edge : p.getEdges())
					ogm.removeEdge(edge);
			}
		}
	}

	@Override
	public void visit(OWLObjectUnionOf desc) {
		super.visit(desc);
		ArrayList<NodeUnion> aEliminar = new ArrayList<NodeUnion>();

		BooleanDescriptionDeleteVisitor v = new BooleanDescriptionDeleteVisitor(ogm);

		for (OWLDescription d : desc.getOperands())
			if (d.isAnonymous())
				d.accept(v);

		for (Object o : ogm.getNodes()){
			NodeUnion nodo = ((OntologyNode) o).asNodeUnion();
			if (nodo != null && nodo.getOWLEntity().equals(propiedad) && desc.equals(nodo.getOWLObjectUnionOf()))
					aEliminar.add(nodo);
		}

		for (NodeUnion nodo : aEliminar) {
			ogm.removeNode(nodo);

			for (Object o : nodo.getPorts()){
				NetPort p = (NetPort) o;

				for (Object edge : p.getEdges())
					ogm.removeEdge(edge);
			}
		}
	}

	/****************************************** OTROS ***************************************/
	
	@Override
	public void visit(OWLObjectOneOf oneOf) {
		super.visit(oneOf);
		
		OntologyNode aEliminar = null;
		
		for (Object o : ogm.getNodes()){
			NodeOneOf nodo = ((OntologyNode) o).asNodeOneOf();
			if (nodo != null){
				if (nodo.getOWLEntity().equals(propiedad) && oneOf.equals(nodo.getOWLObjectComplementOf()))
					aEliminar = nodo; 
			}
		}
		
		if (aEliminar != null){
			ogm.removeNode(aEliminar);
			
			for (Object o : aEliminar.getPorts()){
				NetPort p = (NetPort) o;
				
				for (Object edge : p.getEdges())
					ogm.removeEdge(edge);
			}
		}
			
	}

	@Override
	public void visit(OWLObjectSelfRestriction desc) {
		// TODO Auto-generated method stub
		super.visit(desc);
	}

	@Override
	public void visit(OWLObjectValueRestriction desc) {
		// TODO Auto-generated method stub
		super.visit(desc);
	}
	
}

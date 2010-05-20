/**
 * 
 */
package org.ugr.violet.visitors;

import java.awt.Point;

import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.semanticweb.owl.model.OWLObjectIntersectionOf;
import org.semanticweb.owl.model.OWLObjectUnionOf;
import org.semanticweb.owl.util.OWLDescriptionVisitorAdapter;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.edges.ComplementEdge;
import org.ugr.violet.graph.edges.IntersectionEdge;
import org.ugr.violet.graph.edges.UnionEdge;
import org.ugr.violet.graph.nodes.NodeIntersection;
import org.ugr.violet.graph.nodes.NodeUnion;
import org.ugr.violet.graph.nodes.OWLNode;

/**
 * 
 * @author anab
 */
public class BooleanDescriptionVisitor extends OWLDescriptionVisitorAdapter {

	private OWLGraphModel ogm = null;
	private OWLNode nodoRaiz = null;
	private OWLNode nodoActual = null;

	private int verticalLevel = 2;
	private int horizontalLevel = 0;
	private final int horizontalGap = 200;
	private final int verticalGap = 60;
	private int x,y;

	private OWLDescription actual = null;

	public OWLNode getRootNode(){
		return nodoRaiz;
	}

	public BooleanDescriptionVisitor(OWLGraphModel m, OWLNode nodoRaiz){
		ogm = m;
		nodoActual = nodoRaiz;
		x = nodoRaiz.getOntologyFig().getLocation().x - 60;
		y = nodoRaiz.getOntologyFig().getLocation().y;
	}


	@Override
	public void visit(OWLObjectComplementOf desc) {
		super.visit(desc);

		System.err.println("\nVisitando axioma " + desc);
		System.err.println("Nodo Raiz: " + nodoRaiz);
		System.err.println("actual " + nodoActual);

		// es una clase anonima => otros axiomas
		if (desc.getOperand().isAnonymous()){
			verticalLevel++;
			desc.getOperand().accept(this);
			ComplementEdge arista = new ComplementEdge(desc.getOperand(), desc);
			ogm.addConnection(nodoActual, desc.getOperand(), arista);
			verticalLevel--;
		}
		// no es una clase anonima => agregamos al clase directamente
		else {
			// conectamos las clases con 
			OWLNode nodo = ogm.findOntologyNode(desc.getOperand());

			if (nodo == null && 
					ogm.addClass(desc.getOperand().toString(), new Point(x+horizontalLevel*horizontalGap,y+verticalLevel*verticalGap)))
				nodo = ogm.findOntologyNode(desc.getOperand());	

			else {
				ComplementEdge arista = new ComplementEdge(desc.getOperand(), desc);
				ogm.addConnection(nodoActual, desc.getOperand().asOWLClass(), arista);
			}
		}
		if (actual != null)
			actual = desc;
	}

	@Override
	public void visit(OWLObjectIntersectionOf desc) {		
		super.visit(desc);

		System.err.println("\nVisitando axioma " + desc);
		System.err.println("Nodo Raiz: " + nodoRaiz);
		System.err.println("actual " + actual);
		// creamos el nodo interseccion
		NodeIntersection nodoInterseccion = new NodeIntersection(actual, desc);
		ogm.addNode(nodoInterseccion);
		nodoInterseccion.getOntologyFig().setLocation(x+horizontalLevel*horizontalGap, y+verticalLevel*verticalGap);

		if (nodoRaiz == null){
			nodoRaiz = nodoInterseccion;
		}

		// incrementamos el nivel vertical
		verticalLevel++;

		for (OWLDescription d : desc.getOperands()){

			if (d.isAnonymous()){
				actual = desc;
				nodoActual = nodoInterseccion;
				d.accept(this);
				OWLNode nodo = ogm.findOntologyNode(d);

				IntersectionEdge arista = new IntersectionEdge(d, desc);
				ogm.addConnection(nodoInterseccion, nodo, arista);
			}
			else {
				// conectamos las clases con 
				OWLNode nodo = ogm.findOntologyNode(d);

				if (nodo == null && 
						ogm.addClass(d.toString(), new Point(x+horizontalLevel*horizontalGap,y+verticalLevel*verticalGap)))
					nodo = ogm.findOntologyNode(d);	
				else {
					IntersectionEdge arista = new IntersectionEdge(d.asOWLClass(), desc);
					ogm.addConnection(nodoInterseccion, d, arista);
				}
			}
			horizontalLevel++;
		}

		verticalLevel--;
		horizontalLevel = 0;
	}

	@Override
	public void visit(OWLObjectUnionOf desc) {
		super.visit(desc);

		System.err.println("\nVisitando axioma " + desc);
		System.err.println("Nodo Raiz: " + nodoRaiz);
		System.err.println("actual " + actual);

		// creamos el nodo interseccion
		NodeUnion nodoUnion = new NodeUnion(actual, desc);
		ogm.addNode(nodoUnion);
		nodoUnion.getOntologyFig().setLocation(x+horizontalLevel*horizontalGap, y+verticalLevel*verticalGap);

		if (nodoRaiz == null){
			nodoRaiz = nodoUnion;
		}

		verticalLevel++;
		for (OWLDescription d : desc.getOperands()){
			if (d.isAnonymous()){
				actual = desc;
				nodoActual = nodoUnion;
				d.accept(this);
				OWLNode nodo = ogm.findOntologyNode(d);
				UnionEdge arista = new UnionEdge(d, desc);
				ogm.addConnection(nodoUnion, nodo, arista);
			}
			else {
				// conectamos las clases con 
				OWLNode nodo = ogm.findOntologyNode(d);
				if (nodo == null && 
						ogm.addClass(d.toString(), new Point(x+horizontalLevel*horizontalGap,y+verticalLevel*verticalGap)))
					nodo = ogm.findOntologyNode(d);	

				else {
					UnionEdge arista = new UnionEdge(d.asOWLClass(), desc);
					ogm.addConnection(nodoUnion, d, arista);
				}

			}
			horizontalLevel++;
		}
		verticalLevel--;
		horizontalLevel = 0;
	}
}

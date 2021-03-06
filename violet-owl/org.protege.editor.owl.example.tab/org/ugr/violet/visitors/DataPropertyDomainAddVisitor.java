/**
 * 
 */
package org.ugr.violet.visitors;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.util.OWLDescriptionVisitorAdapter;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.edges.RangeEdge;


/**
 * @author anab
 *
 */
public class DataPropertyDomainAddVisitor extends OWLDescriptionVisitorAdapter {

	private OWLGraphModel ogm = null;
	private OWLDataProperty propiedad = null;
	public DataPropertyDomainAddVisitor(OWLGraphModel modelo, OWLDataProperty prop){
		ogm = modelo;
		propiedad = prop;
		ogm.getNodeDataProperty(prop);
	}

	@Override
	public void visit(OWLClass desc) {
		super.visit(desc);

		// agregar un enlace entre la clase y el nodo que representa la propiedad
		RangeEdge arista = new RangeEdge(propiedad, desc);
		arista.setLabel("Domain");
		ogm.addConnection(propiedad, desc, arista);

	}

	/***************************************************************************************/
	/******************************** OBJECT PROPERTIES ************************************/
	/***************************************************************************************/

	/********************************** RESTRICCIONES **************************************/
	/*
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

	
	private void updateRestriccion(OWLObjectProperty desc, Restriction r){

		NodeRestriction restriccion = ogm.findNodeRestriction(desc, propiedad);

		if (restriccion != null)
			restriccion.addRestrictions(r);
		else {
			restriccion = new NodeRestriction(propiedad, desc, r);
			Point restrictionLocation = new Point();
			ogm.addNode(restriccion);
			restrictionLocation.x = nodoProp.getOntologyFig().getLocation().x + nodoProp.getOntologyFig().getWidth() + 30;
			restrictionLocation.y = nodoProp.getOntologyFig().getLocation().y;
			restriccion.setLocation(restrictionLocation);
			
			RangeEdge arista = new RangeEdge(propiedad, null);
			arista.setLabel("Domain");
			ogm.addConnection(propiedad, desc, arista);
		}
	}
	
	

	/******************************* OPERACIONES DE CONJUNTO ********************************/
	/*
	@Override
	public void visit(OWLObjectComplementOf desc) {
		super.visit(desc);			

		if (nodoProp == null)
			return;

		// buscamos el nodo
		NodeComplement nodoUnion = new NodeComplement(propiedad, desc);
		ogm.addNode(nodoUnion);
		Point p = nodoProp.getOntologyFig().getLocation();
		//p.x += nodoProp.getOntologyFig().getWidth() + 20;
		p.y += nodoProp.getOntologyFig().getHeight() + 60;
		nodoUnion.setLocation(p);

		RangeEdge arista = new RangeEdge(propiedad, null);
		arista.setLabel("Domain");
		
		ogm.addConnection(nodoProp, nodoUnion, arista);

		OWLDescription d = desc.getOperand();
		p.y += nodoUnion.getOntologyFig().getHeight() + 20;
		if (d.isAnonymous()) {
			BooleanDescriptionVisitor v = new BooleanDescriptionVisitor(ogm, nodoUnion);				
			d.accept(v);
			OntologyNode c = v.getRootNode();

			ComplementEdge edge = new ComplementEdge(d, desc);
			ogm.addConnection(nodoUnion, c, edge);
		}
		else {
			// Añadir un enlace con cada clase...
			NodeClass c = (NodeClass) ogm.getNode(d.asOWLClass().toString());

			if (c == null && ogm.addClass(d.asOWLClass().toString(), p))
				c = (NodeClass) ogm.getNode(d.asOWLClass().toString());
			else {
				ComplementEdge edge = new ComplementEdge(d.asOWLClass(), desc);
				ogm.addConnection(nodoUnion, c, edge);
			}
		}
	}

	@Override
	public void visit(OWLObjectIntersectionOf desc) {
		super.visit(desc);
		if (nodoProp == null)
			return;

		// buscamos el nodo
		NodeIntersection nodoUnion = new NodeIntersection(propiedad, desc);
		ogm.addNode(nodoUnion);
		Point p = nodoProp.getOntologyFig().getLocation();
		p.y += nodoProp.getOntologyFig().getHeight() + 60;
		nodoUnion.setLocation(p);

		RangeEdge arista = new RangeEdge(propiedad, null);
		arista.setLabel("Domain");

		ogm.addConnection(nodoProp, nodoUnion, arista);

		p.y += nodoUnion.getOntologyFig().getHeight() + 20;
		p.x -= 100;
		OntologyNode c;
		for (OWLDescription d : desc.getOperands()){

			if (d.isAnonymous()) {
				BooleanDescriptionVisitor v = new BooleanDescriptionVisitor(ogm, nodoUnion);				
				d.accept(v);
				c = v.getRootNode();

				if (c != null){
					IntersectionEdge edge = new IntersectionEdge(d, desc);
					ogm.addConnection(nodoUnion, c, edge);
				}
			}
			else {
				// Añadir un enlace con cada clase...
				c = (NodeClass) ogm.getNode(d.asOWLClass().toString());

				if (c == null && ogm.addClass(d.asOWLClass().toString(), p))
					c = (NodeClass) ogm.getNode(d.asOWLClass().toString());
				else {
					IntersectionEdge edge = new IntersectionEdge(d.asOWLClass(), desc);
					ogm.addConnection(nodoUnion, c, edge);
				}

			}
			if (c != null)
				p.x += c.getOntologyFig().getWidth() + 20;
		}
	}

	@Override
	public void visit(OWLObjectUnionOf desc) {
		super.visit(desc);

		if (nodoProp == null)
			return;

		// buscamos el nodo
		NodeUnion nodoUnion = new NodeUnion(propiedad, desc);
		ogm.addNode(nodoUnion);
		Point p = nodoProp.getOntologyFig().getLocation();
		p.y += nodoProp.getOntologyFig().getHeight() + 60;
		nodoUnion.setLocation(p);

		RangeEdge arista = new RangeEdge(propiedad, null);
		arista.setLabel("Domain");

		ogm.addConnection(nodoProp, nodoUnion, arista);

		OntologyNode c;
		
		p.y += nodoUnion.getOntologyFig().getHeight() + 20;
		p.x -= 100;
		for (OWLDescription d : desc.getOperands()){
			
			if (d.isAnonymous()) {
				BooleanDescriptionVisitor v = new BooleanDescriptionVisitor(ogm, nodoUnion);				
				d.accept(v);
				c= v.getRootNode();

				if (c != null){
					UnionEdge edge = new UnionEdge(d, desc);
					ogm.addConnection(nodoUnion, c, edge);
				}
			}
			else {
				// Añadir un enlace con cada clase...
				c = (NodeClass) ogm.getNode(d.asOWLClass().toString());

				if (c == null && ogm.addClass(d.asOWLClass().toString(), p))
					c = (NodeClass) ogm.getNode(d.asOWLClass().toString());
				else {
					UnionEdge edge = new UnionEdge(d.asOWLClass(), desc);
					ogm.addConnection(nodoUnion, c, edge);
				}
			}
			
			if (c != null)
				p.x += c.getOntologyFig().getWidth() + 20;
		}
	}
	*/

	/************************************** OTROS ******************************************/
	/*
	@Override
	public void visit(OWLObjectOneOf oneOf) {
		super.visit(oneOf);
		NodeDataProperty n = (NodeDataProperty) ogm.getNode(propiedad.toString());					

		if (n == null)
			return;

		// buscamos el nodo
		NodeOneOf nodoUnion = new NodeOneOf(propiedad, oneOf);
		ogm.addNode(nodoUnion);
		Point p = n.getOntologyFig().getLocation();
		p.x += n.getOntologyFig().getWidth() + 20;
		p.y += n.getOntologyFig().getHeight() + 20;
		nodoUnion.setLocation(p);

		Set<OWLIndividual> individuos = oneOf.getIndividuals();
		boolean clasesEnDiagrama = false;

		for (OWLIndividual d : individuos){

			// Agrega un enlace con cada clase...
			NodeIndividual c = (NodeIndividual) ogm.getNode(d.toString());

			if (c != null){
				OneOfEdge arista = new OneOfEdge(d, oneOf);
				clasesEnDiagrama = true;
				ogm.addConnection(nodoUnion, c, arista);
			}
		}
		if (!clasesEnDiagrama) {
			nodoUnion.getOntologyFig().setVisible(false);
		}
		else {
			RangeEdge e = new RangeEdge(null, propiedad);
			ogm.addConnection(n, nodoUnion, e);
		}
	}

	@Override
	public void visit(OWLObjectSelfRestriction desc) {
		super.visit(desc);
	}

	@Override
	public void visit(OWLObjectValueRestriction desc) {
		super.visit(desc);
	}
	*/

	/***************************************************************************************/
	/********************************* DATA PROPERTIES *************************************/
	/***************************************************************************************/
	/*

	@Override
	public void visit(OWLDataAllRestriction desc) {
		super.visit(desc);

		System.err.println(desc);
	}

	@Override
	public void visit(OWLDataExactCardinalityRestriction desc) {
		super.visit(desc);
		System.err.println(desc);
	}

	@Override
	public void visit(OWLDataMaxCardinalityRestriction desc) {
		super.visit(desc);
		System.err.println(desc);
	}

	@Override
	public void visit(OWLDataMinCardinalityRestriction desc) {
		super.visit(desc);
		System.err.println(desc);
	}

	@Override
	public void visit(OWLDataSomeRestriction desc) {
		super.visit(desc);
		System.err.println(desc);
	}

	@Override
	public void visit(OWLDataValueRestriction desc) {
		super.visit(desc);
		System.err.println(desc);
	}
	
	*/

}

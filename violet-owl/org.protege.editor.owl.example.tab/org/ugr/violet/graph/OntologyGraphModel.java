package org.ugr.violet.graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.semanticweb.owl.model.*;
import org.tigris.gef.graph.presentation.DefaultGraphModel;
import org.tigris.gef.graph.presentation.NetNode;
import org.tigris.gef.graph.presentation.NetPort;
import org.ugr.violet.base.OntologyDiagram;
import org.ugr.violet.base.Restriction;
import org.ugr.violet.graph.edges.ComplementEdge;
import org.ugr.violet.graph.edges.DisjointEdge;
import org.ugr.violet.graph.edges.EquivalentEdge;
import org.ugr.violet.graph.edges.IntersectionEdge;
import org.ugr.violet.graph.edges.InverseOfEdge;
import org.ugr.violet.graph.edges.OneOfEdge;
import org.ugr.violet.graph.edges.OntologyEdge;
import org.ugr.violet.graph.edges.RangeEdge;
import org.ugr.violet.graph.edges.RestrictionEdge;
import org.ugr.violet.graph.edges.SuperEdge;
import org.ugr.violet.graph.edges.UnionEdge;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.NodeComplement;
import org.ugr.violet.graph.nodes.NodeDataProperty;
import org.ugr.violet.graph.nodes.NodeIndividual;
import org.ugr.violet.graph.nodes.NodeIntersection;
import org.ugr.violet.graph.nodes.NodeNAryRelation;
import org.ugr.violet.graph.nodes.NodeObjectProperty;
import org.ugr.violet.graph.nodes.NodeOneOf;
import org.ugr.violet.graph.nodes.NodeRestriction;
import org.ugr.violet.graph.nodes.NodeUnion;
import org.ugr.violet.graph.nodes.OntologyNode;
import org.ugr.violet.graph.nodes.OntologyPort;
import org.protege.owl.examples.tab.ExampleViewComponent;
import org.ugr.violet.visitors.BooleanDescriptionDeleteVisitor;
import org.ugr.violet.visitors.BooleanDescriptionVisitor;
import org.ugr.violet.visitors.ComplementsOfVisitor;
import org.ugr.violet.visitors.DataPropertyDomainAddVisitor;
import org.ugr.violet.visitors.SuperClassesVisitor;

/**
 * Clase que representa el modelo del grafo asociado a la ontolog�a
 * @author Ana B. Pelegrina
 */
public class OntologyGraphModel extends DefaultGraphModel {

	private static final long serialVersionUID = -6702060722699798339L;

	/**
	 * Ontología representada por el modelo
	 */
	protected OWLOntology ontologia = null;

	/**
	 * Diagrama asociado al modelo
	 */
	protected OntologyDiagram owner = null;


	/************************************ CONSTRUCTORES **************************************/
	/**
	 * Constructor
	 * @param ont ontología asociada al diagrama
	 */
	public OntologyGraphModel(OWLOntology ont) {
		ontologia = ont;
	}

	/********************************* GETTERS AND SETTERS ***********************************/

	/**
	 * @return the owner
	 */
	public OntologyDiagram getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(OntologyDiagram owner) {
		this.owner = owner;
	}

	/**
	 * Busca el nodo identificado con nombre
	 * @param nombre nombre del nodo
	 * @return el nodo
	 */
	public OntologyNode getNode(String nombre){

		OntologyNode nodo = null, aux = null;
		System.err.println(nombre);

		for (Object n : getNodes()){
			aux = (OntologyNode) n;
			
			System.err.println(n.toString());
			
			if (aux.toString().equals(nombre))
				nodo = aux;
		}

		return nodo;
	}

	/**
	 * 
	 * @return
	 */
	public OWLOntology getOntology(){
		return this.ontologia;
	}

	/**
	 * Busca un nodo individuo en el modelo
	 * @param nombre nombre dle indivudio
	 * @return el nodo (null si no se ha encontrado)
	 */
	public NodeIndividual getNodeIndividual(String nombre){
		NodeIndividual nodo = null, aux = null;

		for (Object n : getNodes()){
			if (n instanceof NodeIndividual) {
				aux = (NodeIndividual) n;
				if (aux.getId().equals(nombre))
					nodo = aux;
			}
		}

		return nodo;
	}

	/**
	 * Busca una clase dentro de la ontología
	 * @param nombre nombre de la clase
	 * @return la clase (null si no se ha encontrado)
	 */
	public OWLClass getOwlClassByName(String nombre){
		return ExampleViewComponent.manager.getOWLClass(nombre);
	}

	/**
	 * Busca un individuo en la ontología a partir del nombre
	 * @param name nombre del individuo
	 * @return el individuo correspondiente (null si no se ha encontrado)
	 */
	public OWLIndividual getOwlIndividualByName(String name){
		return ExampleViewComponent.manager.getOWLIndividual(name);
	}

	/**
	 * @param objectPropertyName
	 * @return
	 */
	private OWLObjectProperty getOwlObjectPropertyByName(String objectPropertyName) {

		return ExampleViewComponent.manager.getOWLObjectProperty(objectPropertyName);
	}

	/**
	 * @param dataPropertyName
	 * @return
	 */
	private OWLDataProperty getOwlDataPropertyByName(String dataPropertyName) {
		return ExampleViewComponent.manager.getOWLDataProperty(dataPropertyName);
	}

	/**
	 * Busca el nodo asociado a una determinada entidad OWL dentro del diagrama.
	 * NOTA: si la entidad es una propiedad de objetos se devuleve la 1� ocurrencia,
	 * pudiendo haber m�s ocurrencias en el diagrama.
	 * @param entidad entidad OWL a buscar
	 * @return el nodo asociado a al entidad
	 */
	public OntologyNode findEntityNode(OWLEntity entidad){
		OntologyNode nodo = null, aux = null;

		for (Object n : getNodes()){
			aux = (OntologyNode) n;
			if (aux.getId().equals(entidad.toString()))
				nodo = aux;
		}

		return nodo;
	}

	/**
	 * 
	 * @param p
	 * @param entidad
	 * @return
	 */
	public NodeRestriction findNodeRestriction(OWLObjectProperty p, OWLEntity entidad){
		NodeRestriction n = null;

		for (Object o : getNodes()){
			NodeRestriction aux = ((OntologyNode) o).asNodeRestriction();

			if (aux != null && aux.getOWLEntity().equals(entidad) && aux.getProperty().equals(p)){
				n = aux;
			}
		}
		return n;
	}

	/**
	 * Obtiene todos los nodos restriccion asociados a una determinada propiedad
	 * @param prop propiedad a buscar
	 * @return una lista con todos los nodos asociados
	 */
	public List<NodeRestriction> getRestrictionNodes(OWLObjectProperty prop){

		List<NodeRestriction> nodos = new ArrayList<NodeRestriction>();

		for (Object o : getNodes()){
			NodeRestriction n =((OntologyNode) o).asNodeRestriction();

			if (n != null && n.getProperty().toString().equals(prop.toString()))
				nodos.add(n);
		}

		return nodos;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public NodeObjectProperty getNodeObjectProperty(OWLObjectProperty p){
		NodeObjectProperty nodo = null;

		for (Object o : this.getNodes()){
			NodeObjectProperty aux = ((OntologyNode) o).asNodeObjectProperty();

			if (aux != null && aux.getPropiedad().equals(p)){
				nodo = aux;
			}
		}

		return nodo;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public NodeDataProperty getNodeDataProperty(OWLDataProperty p){
		NodeDataProperty nodo = null;

		for (Object o : this.getNodes()){
			NodeDataProperty aux = ((OntologyNode) o).asDataProperty();

			if (aux != null && aux.getOWLDataProperty().equals(p)){
				nodo = aux;
			}
		}

		return nodo;
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public OntologyNode findOntologyNode(OWLDescription d){
		OntologyNode nodo = null;

		for (Object o : this.getNodes()){
			if (o.toString().equals(d.toString()))
				nodo = (OntologyNode)o;
		}
		return nodo;
	}

	public NodeObjectProperty findNodeObjectProperty(OWLObjectProperty propiedad){
		for (Object o : getNodes()){
			NodeObjectProperty nodo = ((OntologyNode) o).asNodeObjectProperty();
			
			if (nodo != null && nodo.getPropiedad().equals(propiedad))
				return nodo;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param complemento
	 * @param entidad
	 * @return
	 */
	public NodeComplement findComplementNode(OWLObjectComplementOf complemento, OWLEntity entidad){
		NodeComplement n = null;

		for (Object o : getNodes()){
			NodeComplement aux = ((OntologyNode) o).asNodeComplement();

			if (aux != null && aux.getOWLObjectComplementOf().equals(complemento) && aux.getOWLEntity().equals(entidad)){
				n = aux;
			}
		}
		return n;		
	}

	/*************************************** CONSULTA *****************************************/
	/**
	 * Comprueba si una clase OWL se encuentra en el modelo
	 * @param className nombre de la clase
	 * @return true si exite y false en caso contrario
	 */
	public boolean isThisClassPresent(String className){
		if (getNode(className) == null)
			return false;
		else
			return true;
	}

	/********************************************************************************************/	
	/************************************ MANIPULACION GRAFO ************************************/
	/********************************************************************************************/

	/************************************* Seleccion ********************************************/
	/**
	 * Resalta una determinada entidad OWL dentro del diagrama
	 * @param entidad
	 */
	public void setSelection(OWLEntity entidad) {

		// reseteamos los posibles nodos resaltados
		for (Object o : this.getNodes()){
			NetNode n = (NetNode) o;
			if (n != null) n.setHighlight(false);
		}

		// si la entidad que nos han pasado se encuentra en el diagrama
		if (entidad != null) {

			if (entidad.isOWLObjectProperty()){
				for (NodeRestriction n : this.getRestrictionNodes(entidad.asOWLObjectProperty())){
					n.setHighlight(true);
				}

				NodeObjectProperty n = this.getNodeObjectProperty(entidad.asOWLObjectProperty());

				if (n!=null)
					n.setHighlight(true);
			}
			else {
				OntologyNode nodo = this.findEntityNode(entidad);

				if (nodo != null){
					nodo.setHighlight(true);
				}
			}
		}

	}

	/****************************************** Adds ********************************************/
	/**
	 * Agrega un nuevo individuo al modelo
	 * @param individual Name nombre del individuo
	 * @param location punto del lienzo donde se colocar� el nodo
	 * @return true si se ha agregado el individuo con �xito y false en caso contrario
	 */
	public boolean addIndividual(String individualName, Point location){
		individualName=individualName.trim();
		OWLIndividual ind = this.getOwlIndividualByName(individualName);

		if (ind != null) {
			NodeIndividual nodeInd = new NodeIndividual(ind);
			this.addNode(nodeInd);
			nodeInd.getOntologyFig().setLocation(location);

			this.updateObjectPropertyAsseritons(ind);

			this.updateEquivalentIndividuals(ind);

			this.updateDisjointLinks(ind);

			this.updateOnesOf(ind);

			// recorremos todas las asercinoes de propiedades de objetos asociadas al nuevo individuo
			for(OWLObjectPropertyAssertionAxiom ax : ontologia.getObjectPropertyAssertionAxioms(ind)){
				OWLIndividual objeto = ax.getObject();
				OWLIndividual sujeto = ax.getSubject();
				OWLObjectProperty propiedad = ax.getProperty().asOWLObjectProperty();

				RestrictionEdge r = new RestrictionEdge(sujeto, objeto);
				r.setLabel(propiedad.toString());

				this.addConnection(sujeto, objeto, r);
			}
			return true;
		}
		else 
			return false;
	}

	/**
	 * Agrega un nuevo individuo al modelo
	 * @param individual Name nombre del individuo
	 * @param location punto del lienzo donde se colocar� el nodo
	 * @return true si se ha agregado el individuo con �xito y false en caso contrario
	 */
	public boolean addObjectProperty(String objectPropertyName, Point location){
		objectPropertyName=objectPropertyName.trim();
		OWLObjectProperty propiedad = this.getOwlObjectPropertyByName(objectPropertyName);

		if (propiedad != null) {
			NodeObjectProperty node = new NodeObjectProperty(propiedad);
			this.addNode(node);
			node.getOntologyFig().setLocation(location);

			this.updateEquivalentObjectProperties(propiedad);

			this.updateDisjointLinks(propiedad);

			this.updateRangesAndDomains(propiedad);

			this.updateInverseObjectProperty(propiedad);


			this.updateSubProperties(propiedad);

			return true;
		}
		else 
			return false;
	}

	/**
	 * @param dataPropertyName
	 * @param nodeLocation
	 */
	public void addDataProperty(String dataPropertyName, Point nodeLocation) {
		// TODO Auto-generated method stub
		dataPropertyName=dataPropertyName.trim();
		OWLDataProperty propiedad = this.getOwlDataPropertyByName(dataPropertyName);

		if (propiedad != null){
			NodeDataProperty nodo = new NodeDataProperty(propiedad);
			this.addNode(nodo);
			nodo.getOntologyFig().setLocation(nodeLocation);

			DataPropertyDomainAddVisitor v= new DataPropertyDomainAddVisitor(this, propiedad);

			for (OWLDescription e : propiedad.getDomains(ontologia))
				e.accept(v);
		}	
	}

	/**
	 * Agrega un nodo clase al modelo. Agrega al modelo todos los nodos, restricciones, nodos restriccion, relaciones
	 * de herencia, etc. necesarios, de acuerdo con la ontología.
	 * @param className nombre de la clase
	 * @param location punto en el que colocar el nodo en el lienzo
	 * @return true si se ha agregado el nodo con exito y false en caso contrario
	 */
	public boolean addClass(String className, Point location){

		className=className.trim();
		boolean res = false;

		// nos aseguramos que la clase no esta ya en el diagrama
		if ( !isThisClassPresent(className) ) {

			// buscamos en la ontología la clase por el nombre
			OWLClass claseOWL = getOwlClassByName(className);

			// nos aseguramos que hayamos encontrado la clase
			if (claseOWL == null) {
				res = false;
			}
			else {
				NodeClass nodo = null;
				
				// creamos el nodo a agregar al modelo	
				
				// comprobamos si es una clase especial relacion
				if (NodeNAryRelation.claseBase.getSubClasses(ExampleViewComponent.manager.getActiveOntology()).contains(claseOWL)){
					nodo = new NodeNAryRelation(claseOWL);
				}
				else 			
					nodo = new NodeClass(claseOWL);
				
				addNode(nodo);
				nodo.getOntologyFig().setLocation(location);

				// Actualizamos la restricciones, por si existe alguna aplicada a esta clase.
				// Se hace antes de agregar las restricciones propias para evitar que se aplique sobre las restricciones
				// asociadas a la nueva clase.
				//this.updateRestrictionsLinks(nodo);

				// creamos los enlaces de herencia con las posibles sub clases de la nueva clase ya presentes en el diagrama
				this.updatesSubClasses(claseOWL);

				// añadimos las restricciones asociadas a la clase
				this.addRestrictions(nodo);	

				this.updateDisjointLinks(claseOWL);

				this.updateEquivalentClasses(claseOWL);

				this.updateRangesAndDomains(claseOWL);

				this.updateUnions(claseOWL);

				this.updateIntersection(claseOWL);

				this.updateComplements(claseOWL);

				res = true;
			}
		}
		else 
			res = false;

		return res;
	}

	/**
	 * Agrega al modelo las restricciones asociadas a un nuevo nodo representando a una clase OWL
	 * @param nodo nodo recién agregado
	 */
	private void addRestrictions(NodeClass nodo){
		// creamos el visitante de restricciones
		SuperClassesVisitor restrictionVisitor = new SuperClassesVisitor(Collections.singleton(ontologia));
		OWLClass claseOWL = nodo.getOWLClass(); // recuperamos la clase del nodo


		// visitamos todas las super clases (incluidas las restricciones, of course) de la nueva clase
		for(OWLSubClassAxiom ax : ontologia.getSubClassAxiomsForLHS(claseOWL)) {
			OWLDescription superCls = ax.getSuperClass();
			superCls.accept(restrictionVisitor);

			if (!superCls.isAnonymous() && isThisClassPresent(superCls.asOWLClass().toString()))
				addInheritanceLink( claseOWL, superCls.asOWLClass());
		}
		/*
		// recuperamos la tabla hash con todas las restricciones asociadas a la clase; utilizando como clave la propiedad 
		// de objeto sobre la que se aplica la restricci�n. Esto es por cada restricci�n tendremos un vector de pares <clase, tipo_restriccion>
		// Ver clases Restriccion y RestrictionVisitor en este mismo paquete
		Hashtable<OWLObjectProperty, Vector<Restriction>> a = restrictionVisitor.getRestricciones();

		// calculamos las coordenadas iniciales de las figuras asociadas 
		Point location = nodo.getLocation();
		int x = nodo.getOntologyFig().getWidth()+20 + location.x;
		int y = location.y;

		List<OWLClass> relacionadas = new ArrayList<OWLClass>();

		// para cada propieadad de objeto sobre la que existen restricciones para la nueva clase
		for(OWLObjectProperty key : a.keySet()){

			// agregamos un nodo que engloga a todas las restricciones asociadas a la propiedad
			NodeRestriction r = new NodeRestriction(claseOWL, key, a.get(key));
			addNode(r);
			r.getOntologyFig().setLocation(x, y);
			y += r.getOntologyFig().getHeight() + 20;

			// agregamos el enlace con la clase a la que se aplican las restricciones
			this.addInheritanceLink(claseOWL, key);

			// para cada clase incluida en la definición de las restricciones asociadas a la propiedad...
			for (Restriction rr:a.get(key)){

				// nos aseguramos que no se haya incluido ya le link
				if (!relacionadas.contains(rr.getClase())){
					// ... incluimos un enlace entre este y la nueva clase
					addRestrictionLink(claseOWL, rr.getClase(), key);
					relacionadas.add(rr.getClase());
				}
			}
			relacionadas.clear();
		}
		*/

		// Agregamos las uniones
		this.updateUnions(nodo.getOWLClass(), restrictionVisitor.getUnions());
		// Agregamos las intersecciones
		this.updateIntersections(nodo.getOWLClass(), restrictionVisitor.getIntersections());
		// Agregamso los complementos
		this.updateComplements(nodo.getOWLClass(), restrictionVisitor.getComplements());

		// Agregamos las enumeraciones
		this.updateOnesOf(nodo.getOWLClass(), restrictionVisitor.getOnesOf());


		// borramos el contenido del visitante
		restrictionVisitor.reset();
		
	}


	/***************************************** Updates ******************************************/

	/**
	 * Actualiza los enlaces entre clases con restricciones cuando se inserta una nueva clase en el diagrama
	 * @param nuevaClase nodo correspondiente a la nueva clase agregada al modelo
	 */
	@SuppressWarnings("unused")
	private void updateRestrictionsLinks(NodeClass nuevaClase){

		//1) Recorremos todos los nodos del modelo
		for (Object o: this.getNodes()){
			OntologyNode n = (OntologyNode) o;

			// si se trata de un nodo restriccion
			if (n.isNodeRestriction()){
				NodeRestriction r = (NodeRestriction) n;
				Vector<Restriction> vRes = r.getRestrictions();

				for(Restriction rr : vRes){
					// si la restriccion incluye a la nueva clase ...
					if (rr.getClase().toString().equals(nuevaClase.getOWLClass().toString())){
						addRestrictionLink(r.getClase(), nuevaClase.getOWLClass(), r.getProperty());
						break;
					}
				}
			}
		}		
	}

	/**
	 * Actualiza los enlaces para las subclases de una nueva clase agregada al modelo
	 * @param nuevaClase nueva clase agregada
	 */
	private void updatesSubClasses(OWLClass nuevaClase) {
		// recorrermos las subclases
		for(OWLSubClassAxiom ax : ontologia.getSubClassAxiomsForRHS(nuevaClase)) {
			OWLClass superCls = ax.getSubClass().asOWLClass();

			if (!superCls.isAnonymous() && isThisClassPresent(superCls.toString()))
				addInheritanceLink(superCls, nuevaClase);
		}
	}

	/**
	 * Actualiza las propiedades 
	 * @param ind
	 */
	protected void updateObjectPropertyAsseritons(OWLIndividual ind) {
		for (Object o: this.getNodes()){
			NodeIndividual n = ((OntologyNode) o).asNodeIndividual();

			// si se trata de un nodo restriccion
			if (n != null){
				for(OWLObjectPropertyAssertionAxiom ax : ontologia.getObjectPropertyAssertionAxioms(n.getOWLIndividual())){
					if (ax.getObject().toString().equals(ind.toString())){

						OWLIndividual objeto = ax.getObject();
						OWLIndividual sujeto = ax.getSubject();
						OWLObjectProperty propiedad = ax.getProperty().asOWLObjectProperty();

						RestrictionEdge r = new RestrictionEdge(sujeto, objeto);
						r.setLabel(propiedad.toString());

						this.addConnection(sujeto, objeto, r);
					}
				}
			}
		}
	}

	/**
	 * @param propiedad 
	 * 
	 */
	private void updateDisjointLinks(OWLObjectProperty propiedad) {

		for (OWLDisjointObjectPropertiesAxiom ax : ontologia.getDisjointObjectPropertiesAxiom(propiedad)){

			Object[] clases = ax.getObjectPropertiesInSignature().toArray();


			OWLObjectProperty c1 = (OWLObjectProperty) clases[0];
			OWLObjectProperty c2 = (OWLObjectProperty) clases[1];
			DisjointEdge eje = new DisjointEdge(c1, c2);
			eje.setLabel("Disjoint");

			if (c1 != null && c2 != null)
				this.addConnection(c1, c2, eje);
		}
	}

	/**
	 * @param claseOWL 
	 * 
	 */
	private void updateDisjointLinks(OWLClass claseOWL) {

		for (OWLDisjointClassesAxiom ax : ontologia.getDisjointClassesAxioms(claseOWL)){

			Object[] clases = ax.getClassesInSignature().toArray();


			OWLClass c1 = (OWLClass) clases[0];
			OWLClass c2 = (OWLClass) clases[1];

			DisjointEdge eje = new DisjointEdge(c1, c2);
			eje.setLabel("Disjoint");

			if (c1 != null && c2 != null)
				this.addConnection(c1, c2, eje);
		}
	}

	/**
	 * Actualiza las clases equivalentes
	 * @param claseOWL
	 */
	private void updateEquivalentClasses(OWLClass claseOWL) {
		for (OWLEquivalentClassesAxiom ax : ontologia.getEquivalentClassesAxioms(claseOWL)){

			Object[] clases = ax.getClassesInSignature().toArray();

			OWLClass c1 = (OWLClass) clases[0];
			OWLClass c2 = (OWLClass) clases[1];

			EquivalentEdge eje = new EquivalentEdge(c1, c2);

			if (c1 != null && c2 != null)
				this.addConnection(c1, c2, eje);
		}
	}

	/**
	 * Actualiza las clases equivalentes
	 * @param propiedad
	 */
	private void updateEquivalentObjectProperties(OWLObjectProperty propiedad) {
		for (OWLEquivalentObjectPropertiesAxiom ax : ontologia.getEquivalentObjectPropertiesAxioms(propiedad)){

			Object[] clases = ax.getObjectPropertiesInSignature().toArray();

			OWLObjectProperty c1 = (OWLObjectProperty) clases[0];
			OWLObjectProperty c2 = (OWLObjectProperty) clases[1];

			EquivalentEdge eje = new EquivalentEdge(c1, c2);

			if (c1 != null && c2 != null)
				this.addConnection(c1, c2, eje);
		}
	}

	/**
	 * @param propiedad
	 */
	private void updateRangesAndDomains(OWLObjectProperty propiedad) {
		for (OWLDescription d: propiedad.getRanges(ontologia)){
			for (OWLClass c : d.getClassesInSignature()){
				RangeEdge r = new RangeEdge(propiedad, c);
				r.setLabel("Range");
				this.addConnection(propiedad, c, r);
			}

		}

		for (OWLDescription d: propiedad.getDomains(ontologia)){
			for (OWLClass c : d.getClassesInSignature()){
				RangeEdge r = new RangeEdge(propiedad, c);
				r.setLabel("Domain");
				this.addConnection(propiedad, c, r);
			}
		}
	}

	/**
	 * @param propiedad
	 */
	private void updateInverseObjectProperty(OWLObjectProperty propiedad) {
		for (OWLObjectPropertyExpression d: propiedad.getInverses(ontologia)){

			InverseOfEdge r = new InverseOfEdge(propiedad, d.asOWLObjectProperty());
			r.setLabel("InverseOf");
			this.addConnection(propiedad, d.asOWLObjectProperty(), r);
		}
	}

	/**
	 * @param propiedad
	 */
	private void updateSubProperties(OWLObjectProperty propiedad) {
		for (OWLObjectPropertyExpression c:propiedad.getSuperProperties(ontologia)){
			this.addInheritanceLink(propiedad, c.asOWLObjectProperty());
		}

		for (OWLObjectPropertyExpression c:propiedad.getSubProperties(ontologia)){
			this.addInheritanceLink(c.asOWLObjectProperty(), propiedad);
		}

	}

	/**
	 * @param ind
	 */
	private void updateDisjointLinks(OWLIndividual ind) {
		for (OWLDifferentIndividualsAxiom ax : ontologia.getDifferentIndividualAxioms(ind)){

			Object[] individuos = ax.getIndividuals().toArray();

			OWLIndividual c1 = (OWLIndividual) individuos[0];
			OWLIndividual c2 = (OWLIndividual) individuos[1];

			DisjointEdge eje = new DisjointEdge(c1, c2);
			eje.setLabel("Different");

			if (c1 != null && c2 != null)
				this.addConnection(c1, c2, eje);
		}
	}

	/**
	 * @param ind
	 */
	private void updateEquivalentIndividuals(OWLIndividual ind) {

		for (OWLSameIndividualsAxiom ax : ontologia.getSameIndividualAxioms(ind)){
			Object[] individuos = ax.getIndividuals().toArray();

			OWLIndividual c1 = (OWLIndividual) individuos[0];
			OWLIndividual c2 = (OWLIndividual) individuos[1];

			EquivalentEdge eje = new EquivalentEdge(c1, c2);

			if (c1 != null && c2 != null)
				this.addConnection(c1, c2, eje);
		}
	}

	/**
	 * @param claseOWL
	 */
	private void updateRangesAndDomains(OWLClass claseOWL) {
		// recorremos los nodos propiedad de objeto
		for (Object o : this.getNodes()){
			NodeObjectProperty n = ((OntologyNode) o).asNodeObjectProperty(); 

			if ( n!= null){
				// comprobamos si esta en el rango
				if (n.getPropiedad().getRanges(ontologia).contains(claseOWL)){
					//creamos el enlace de rango
					RangeEdge r = new RangeEdge(n.getPropiedad(), claseOWL);
					r.setLabel("Range");
					this.addConnection(n.getPropiedad(), claseOWL, r);
				}
				if (n.getPropiedad().getDomains(ontologia).contains(claseOWL)){
					// creamos el enlace dominio
					//creamos el enlace de rango
					RangeEdge r = new RangeEdge(n.getPropiedad(), claseOWL);
					r.setLabel("Domain");
					this.addConnection(n.getPropiedad(), claseOWL, r);
				}
			}
		}
	}

	/**
	 * 
	 * @param clase
	 */
	public void updateUnions(OWLClass clase) {

		for (Object o : getNodes()){		
			NodeUnion nodoUnion = ((OntologyNode) o).asNodeUnion();

			if (nodoUnion != null && nodoUnion.getOWLObjectUnionOf().getOperands().contains(clase)){
				if (!nodoUnion.getOntologyFig().isVisible()){
					nodoUnion.getOntologyFig().setVisible(true);
					SuperEdge e = new SuperEdge(null, clase);
					this.addConnection(nodoUnion.getOWLDescription(), nodoUnion, e);
				}
				UnionEdge arista = new UnionEdge(clase, nodoUnion.getOWLObjectUnionOf());
				this.addConnection(nodoUnion, clase, arista);
			}
		}
	}

	/**
	 * @param clase 
	 * @param unions
	 */
	public void updateUnions(OWLDescription clase, Collection<OWLObjectUnionOf> unions) {

		
		System.err.println("Entrando en update unions");
		
		for (OWLObjectUnionOf union : unions){

			// buscamos el nodo de la clase
			NodeClass n = (NodeClass) this.getNode(clase.toString());					

			// si el nodo no esta en el diagrama salimos
			if (n == null)
				return;

			// creamos el nodo union
			NodeUnion nodoUnion = new NodeUnion(clase, union);
			this.addNode(nodoUnion);
			Point p = n.getOntologyFig().getLocation();
			p.y += n.getOntologyFig().getHeight() + 20;
			nodoUnion.setLocation(p);
			
			// unimos el nodo clase con el nodo union
			SuperEdge e = new SuperEdge(null, clase.asOWLClass());
			this.addConnection(n, nodoUnion, e);
			
			p.y += 50;
			p.x -= 100;

			// recorremos todos los operandos
			for (OWLDescription d : union.getOperands()){
				
				System.err.println("Recorriendo los operandos");
				
				OntologyNode c;
				// la clase es anonima
				if (d.isAnonymous()) {
					
					System.err.println("La clase es anonima");
					
					BooleanDescriptionVisitor v = new BooleanDescriptionVisitor(this, nodoUnion);				
					d.accept(v);
					c = v.getRootNode();

					if (c != null && c.getOntologyFig().isVisible()){
						UnionEdge arista = new UnionEdge(d, union);
						this.addConnection(nodoUnion, c, arista);
					}
				}
				else {

					System.err.println("La clase no es anonima");
					
					// Añadir un enlace con cada clase...
					c = (NodeClass) this.getNode(d.asOWLClass().toString());

					// si el nodo no existe, lo creamos
					if (c == null && addClass(d.asOWLClass().toString(), p))
						c = (NodeClass) this.getNode(d.asOWLClass().toString());

					else {
						UnionEdge arista = new UnionEdge(d.asOWLClass(), union);
						this.addConnection(nodoUnion, c, arista);
					}
				}

				if (c != null)
					p.x += 20 + c.getOntologyFig().getWidth();
			}	
		}
	}

	/**
	 * 
	 * @param clase
	 */
	public void updateIntersection(OWLClass clase) {

		for (Object o : getNodes()){		
			// buscamos el nodo
			NodeIntersection nodoInterseccion = ((OntologyNode) o).asNodeIntersection();

			if (nodoInterseccion != null) {
				// buscamos la clase en la signatura y agregamos el enlace si es necesario
				for (OWLDescription d: nodoInterseccion.getOWLObjectIntersectionOf().getOperands()){

					if (!d.isAnonymous()) {

						OWLClass c = d.asOWLClass();

						if (c != null && c.equals(clase)){
							if (!nodoInterseccion.getOntologyFig().isVisible()){
								nodoInterseccion.getOntologyFig().setVisible(true);
								SuperEdge e = new SuperEdge(null, clase);
								this.addConnection(nodoInterseccion.getOWLDescription(), nodoInterseccion, e);
							}
							IntersectionEdge arista = new IntersectionEdge(clase, nodoInterseccion.getOWLObjectIntersectionOf());
							this.addConnection(nodoInterseccion, this.getNode(clase.toString()), arista);
						}
					}
				}
			}
		}
	}

	/**
	 * @param clase 
	 * @param intersections
	 */
	public void updateIntersections(OWLEntity clase, Set<OWLObjectIntersectionOf> intersections) {
		for (OWLObjectIntersectionOf interseccion : intersections){

			// buscamos el nodo de la clase
			NodeClass n = (NodeClass) this.getNode(clase.toString());					

			// si el nodo no esta en el diagrama salimos
			if (n == null)
				return;

			// creamos el nodo union
			NodeIntersection nodoInterseccion = new NodeIntersection(clase, interseccion);
			this.addNode(nodoInterseccion);
			Point p = n.getOntologyFig().getLocation();
			p.y += n.getOntologyFig().getHeight() + 30;
			nodoInterseccion.setLocation(p);
			
			// unimos el nodo clase con el nodo union
			SuperEdge e = new SuperEdge(null, clase.asOWLClass());
			this.addConnection(n, nodoInterseccion, e);
			
			p.y +=  50;
			p.x -= 100;
			
			// recorremos todos los operandos
			for (OWLDescription d : interseccion.getOperands()){
				OntologyNode c;
				// la clase es anonima
				if (d.isAnonymous()) {
					
					BooleanDescriptionVisitor v = new BooleanDescriptionVisitor(this, nodoInterseccion);				
					d.accept(v);
					c = v.getRootNode();

					if (c != null && c.getOntologyFig().isVisible()){
						IntersectionEdge arista = new IntersectionEdge(d, interseccion);
						this.addConnection(nodoInterseccion, c, arista);
					}
				}
				else {

					// A�adir un enlace con cada clase...
					c = (NodeClass) this.getNode(d.asOWLClass().toString());

					// si el nodo no existe, lo creamos
					if (c == null && addClass(d.asOWLClass().toString(), p))
						c = (NodeClass) this.getNode(d.asOWLClass().toString());
					else {
						IntersectionEdge arista = new IntersectionEdge(d.asOWLClass(), interseccion);
						this.addConnection(nodoInterseccion, c, arista);
					}
				}

				if (c != null)
					p.x += 20 + c.getOntologyFig().getWidth();
			}	
		}
	}

	/**
	 * @param clase 
	 * @param complements
	 */
	public void updateComplements(OWLClass clase, Set<OWLObjectComplementOf> complements) {
		if (complements.size() > 0){
			NodeClass n = (NodeClass) this.getNode(clase.toString());

			if (n != null) {
				for (OWLObjectComplementOf complemento : complements){
					OntologyNode c;

					if (complemento.getOperand().isAnonymous()) {
						BooleanDescriptionVisitor v = new BooleanDescriptionVisitor(this, n);				
						complemento.getOperand().accept(v);						
						c = v.getRootNode();
					}
					else
						c = (OntologyNode) this.getNode(complemento.getOperand().asOWLClass().toString());

					if (c != null){
						ComplementEdge arista = new ComplementEdge(complemento.getOperand(), complemento);
						this.addConnection(n, c, arista);
					}
				}
			}
		}
	}

	/**
	 * @param claseOWL
	 */
	private void updateComplements(OWLClass claseOWL) {

		ComplementsOfVisitor v = new ComplementsOfVisitor();

		for (Object o : getNodes()){		
			// buscamos el nodo
			NodeClass nodoClase = ((OntologyNode) o).asNodeClass();

			if (nodoClase != null) {
				// recuperamos las clases complemento de la clase


				OWLClass c = nodoClase.getOWLClass();

				for (OWLDescription d : c.getSuperClasses(ontologia)){
					d.accept(v);
				}

				if (v.getClases().contains(claseOWL)){
					ComplementEdge arista = new ComplementEdge(c, (OWLObjectComplementOf)claseOWL.getComplementNNF());
					this.addConnection(c, claseOWL, arista);
				}
			}
		}
	}

	/**
	 * @param class1
	 * @param oneOfs
	 */
	public void updateOnesOf(OWLClass clase, Set<OWLObjectOneOf> oneOfs) {
		if (oneOfs.size() > 0){

			for (OWLObjectOneOf oneOf : oneOfs){

				NodeClass n = (NodeClass) this.getNode(clase.toString());					

				if (n == null)
					return;

				// buscamos el nodo
				NodeOneOf nodoUnion = new NodeOneOf(clase, oneOf);
				this.addNode(nodoUnion);
				Point p = n.getOntologyFig().getLocation();
				p.x += n.getOntologyFig().getWidth() + 20;
				p.y += n.getOntologyFig().getHeight() + 20;
				nodoUnion.setLocation(p);

				Set<OWLIndividual> individuos = oneOf.getIndividuals();
				boolean clasesEnDiagrama = false;

				for (OWLIndividual d : individuos){

					// Agrega un enlace con cada clase...
					NodeIndividual c = (NodeIndividual) this.getNode(d.toString());


					if (c != null){
						OneOfEdge arista = new OneOfEdge(d, oneOf);
						clasesEnDiagrama = true;
						this.addConnection(nodoUnion, c, arista);
					}
				}
				if (!clasesEnDiagrama) {
					nodoUnion.getOntologyFig().setVisible(false);
				}
				else {
					SuperEdge e = new SuperEdge(null, clase);
					this.addConnection(n, nodoUnion, e);
				}
			}
		}
	}

	/**
	 * 
	 * @param individuo
	 */
	public void updateOnesOf(OWLIndividual individuo) {

		for (Object o : getNodes()){		
			// buscamos el nodo
			NodeOneOf nodoOneOf = ((OntologyNode) o).asNodeOneOf();

			if (nodoOneOf != null) {
				// buscamos la clase en la signatura y agregamos el enlace si es necesario
				for (OWLIndividual d: nodoOneOf.getOWLObjectComplementOf().getIndividuals()){


					if (d.equals(individuo)){
						if (!nodoOneOf.getOntologyFig().isVisible()){
							nodoOneOf.getOntologyFig().setVisible(true);
							SuperEdge e = new SuperEdge(null, individuo);
							this.addConnection(nodoOneOf.getOWLClass(), nodoOneOf, e);
						}
						OneOfEdge arista = new OneOfEdge(individuo, nodoOneOf.getOWLObjectComplementOf());
						this.addConnection(nodoOneOf, individuo, arista);
					}
				}
			}
		}
	}

	/**
	 * Actualiza el nodo asociado a un individuo
	 * @param individual
	 */
	public void updateAssertionClass(OWLIndividual individual) {
		// buscar los nodos clase y agregarles una clase padre
		for (Object o : this.getNodes()){
			NodeIndividual ind = ((OntologyNode) o).asNodeIndividual();

			if (ind != null){
				ind.updateFigure();				
				break;
			}
		}
	}

	/**
	 * @param restricciones
	 */
	public void updateRestrictions(OWLClass clase,
			Hashtable<OWLObjectProperty, Vector<Restriction>> restricciones) {

		boolean encontrado = false;

		for (OWLObjectProperty propiedad : restricciones.keySet()){
			if (restricciones.size() > 0){
				encontrado = false;
				// recorremos todos los nodos restriccion
				for (Object o : this.getNodes()){
					NodeRestriction nRes = ((OntologyNode) o).asNodeRestriction();			

					if (nRes != null){

						// si la clase y la propiedad coinciden...
						if (nRes.getClase().equals(clase) && nRes.getProperty().equals(propiedad)){

							encontrado = true;

							// ... agregamos la restriccion al nodo
							nRes.addRestrictions(restricciones.get(propiedad));
						}
					}
				}

				// si no hemos encontrado el nodo => crear uno nuevo
				if (!encontrado){
					// agregamos un nodo que engloga a todas las restricciones asociadas a la propiedad
					NodeRestriction r = new NodeRestriction(clase, propiedad, restricciones.get(propiedad));
					addNode(r);
					int x = this.getNode(clase.toString()).getOntologyFig().getLocation().x + 75;
					int y = this.getNode(clase.toString()).getOntologyFig().getLocation().y;
					List<OWLClass> relacionadas = new ArrayList<OWLClass>();
					r.getOntologyFig().setLocation(x, y);

					// agregamos el enlace con la clase a la que se aplican las restricciones
					this.addInheritanceLink(clase, propiedad);

					// para cada clase incluida en la definici�n de las restricciones asociadas a la propiedad...
					for (Restriction rr:restricciones.get(propiedad)){

						// nos aseguramos que no se haya incluido ya le link
						if (!relacionadas.contains(rr.getClase())){
							// ... incluimos un enlace entre este y la nueva clase
							addRestrictionLink(clase, rr.getClase(), propiedad);
							relacionadas.add(rr.getClase());
						}
					}

					relacionadas.clear();
				}
			}
		}
	}

	/**************************************** Deletes ********************************************/
	/**
	 * Elimina el nodo asociado a una clase OWL del modelo
	 * @param clase clase a eliminar
	 */
	public void deleteClass(OWLClass clase){
		//1- buscar la clase
		OntologyNode nodo = this.getNode(clase.toString());
		this.removeNode(nodo);
	}

	/**
	 * Elimina el nodo asociado a un individuo del modelo
	 * @param i individuo a eliminar
	 */
	public void deleteIndividual(OWLIndividual i){
		OntologyNode nodo = this.getNode(i.toString());
		this.removeNode(nodo);
	}

	/**
	 * Elimina todas las referencias a una determinada propiedad de datos en el diagrama
	 * @param d la propiedad de objetos
	 */
	public void deleteDataProperty(OWLDataProperty d){
		OntologyNode nodo = this.getNode(d.toString());
		this.removeNode(nodo);
	}

	/**
	 * Elimina todas las referencias a una propiedad de objetos en el diagrama
	 * @param d propiedad de objetos a eliminar
	 */
	@SuppressWarnings("unchecked")
	public void deleteObjectProperty(OWLObjectProperty d){

		List<Object> nodosAEliminar = new ArrayList<Object>();
		List<Object> aristasAEliminar = new ArrayList<Object>();

		// 1 - buscar todos los nodos restricci�n que hagan referencia a esa propiedad y borrarlos
		for (Object o : getNodes()){
			NodeRestriction nr = ((OntologyNode) o).asNodeRestriction();
			if (nr != null  && nr.getProperty().toString().equals(d.toString())){
				nodosAEliminar.add(nr);
				aristasAEliminar.addAll(nr.north.getEdges());
				aristasAEliminar.addAll(nr.south.getEdges());
				aristasAEliminar.addAll(nr.east.getEdges());
				aristasAEliminar.addAll(nr.west.getEdges());
			}
		}

		for (Object o:nodosAEliminar){
			this.removeNode(o);
		}
		nodosAEliminar.clear();

		// 2 - buscar todos los edge que hagan referencia a la propiedad y borrarlos
		for (Object o: this.getEdges()){
			RestrictionEdge re = ((OntologyEdge) o).asRestrictionEdge();
			if (re != null && re.getId().equals(d.toString()))
				aristasAEliminar.add(re);
		}

		for (Object o:aristasAEliminar)
			this.removeEdge(o);

		aristasAEliminar.clear();
	}

	/**
	 * Elimina un enlace de herencia entre dos entidades OWL
	 * @param sup super-entidad
	 * @param sub sub-entidad
	 */
	public void deleteInheritanceLink(OWLEntity sup, OWLEntity sub){
		List<Object> nodosAEliminar = new ArrayList<Object>();

		for (Object o: this.getEdges()){
			SuperEdge se = ((OntologyEdge) o).asSuperEdge();

			if (se != null && se.getSub().toString().equals(sub.toString()) && se.getSup().toString().equals(sup.toString()))
				nodosAEliminar.add(se);
		}

		for (Object o:nodosAEliminar)
			this.removeEdge(o);
	}

	/**
	 * @param clase 
	 * @param unions
	 */
	public void deleteUnions(OWLClass clase, Set<OWLObjectUnionOf> unions) {
		if (unions.size() > 0){

			ArrayList<NodeUnion> aEliminar = new ArrayList<NodeUnion>();

			for (OWLObjectUnionOf union : unions){

				BooleanDescriptionDeleteVisitor v = new BooleanDescriptionDeleteVisitor(this);

				for (OWLDescription d : union.getOperands())
					if (d.isAnonymous())
						d.accept(v);

				for (Object o : getNodes()){
					NodeUnion nodo = ((OntologyNode) o).asNodeUnion();
					if (nodo != null){
						if (nodo.getOWLDescription().equals(clase) && union.equals(nodo.getOWLObjectUnionOf()))
							aEliminar.add(nodo);
					}
				}
			}




			for (NodeUnion nodo : aEliminar) {
				this.removeNode(nodo);

				for (Object o : nodo.getPorts()){
					NetPort p = (NetPort) o;

					for (Object edge : p.getEdges())
						this.removeEdge(edge);
				}
			}
		}	
	}

	/**
	 * @param clase 
	 * @param intersections
	 */
	public void deleteIntersections(OWLClass clase, Set<OWLObjectIntersectionOf> intersections) {
		if (intersections.size() > 0){
			ArrayList<NodeIntersection> aEliminar = new ArrayList<NodeIntersection>();

			for (OWLObjectIntersectionOf interseccion : intersections){
				BooleanDescriptionDeleteVisitor v = new BooleanDescriptionDeleteVisitor(this);

				for (OWLDescription d : interseccion.getOperands())
					if (d.isAnonymous())
						d.accept(v);

				for (Object o : getNodes()){
					NodeIntersection nodo = ((OntologyNode) o).asNodeIntersection();
					if (nodo != null){
						// TODO resolver error al eliminar
						
						if (nodo.getOWLDescription().equals(clase) && interseccion.equals(nodo.getOWLObjectIntersectionOf()))
							aEliminar.add(nodo);
					}
				}
			}

			for (NodeIntersection nodo : aEliminar) {
				this.removeNode(nodo);

				for (Object o : nodo.getPorts()){
					NetPort p = (NetPort) o;

					for (Object edge : p.getEdges())
						this.removeEdge(edge);
				}
			}
		}
	}

	/**
	 * @param clase 
	 * @param complements
	 */
	public void deleteComplements(OWLClass clase, Set<OWLObjectComplementOf> complements) {

		System.err.println("Entra en el metodo");

		if (complements.size() > 0){

			ArrayList<ComplementEdge> aEliminar = new ArrayList<ComplementEdge>();
			for (OWLObjectComplementOf comp : complements){

				System.err.println("Buscando la arista para " + comp);

				if (comp.getOperand().isAnonymous()){
					BooleanDescriptionDeleteVisitor v = new BooleanDescriptionDeleteVisitor(this);
					comp.accept(v);
				}

				for (Object o : this.getEdges()){

					//System.err.println("Comparando con la arista " + o);

					ComplementEdge ce = ((OntologyEdge) o).asComplementEdge();

					if (ce != null){

						System.err.println("Comparando con la arista " + o);

						if (!ce.checkLink(comp.getOperand().asOWLClass()))
							System.err.println("Falla la comparaci�n con la clase ");

						if (!ce.checkLink(comp))
							System.err.println("Falla la comparaci�n con el complemento");

						if (ce.checkLink(comp.getOperand().asOWLClass()) && ce.checkLink(comp)){
							aEliminar.add(ce);
							System.err.println("agregada arista para eliminarla " + o);
						}
					}
				}
			}

			System.err.println("Aristas a eliminar " + aEliminar.size());

			for (ComplementEdge ce : aEliminar){
				this.removeEdge(ce);
				System.err.println("Eliminando " + ce);
			}
		}
	}

	/**
	 * @param clase 
	 * @param restricciones
	 */
	public void deleteRestrictions(
			OWLClass clase, Hashtable<OWLObjectProperty, Vector<Restriction>> restricciones) {
		if (restricciones.size() > 0){

			// recorremos todas las propiedades modificadas
			for (OWLObjectProperty propiedad : restricciones.keySet()){

				// si hay al menos una restriccion
				if (restricciones.size() > 0){

					ArrayList<NodeRestriction> aEliminar = new ArrayList<NodeRestriction>();

					// recorremos todos los nodos restriccion
					for (Object o : this.getNodes()){
						NodeRestriction nRes = ((OntologyNode) o).asNodeRestriction();			

						if (nRes != null){

							// si la clase y la propiedad coinciden...
							if (nRes.getClase().equals(clase) && nRes.getProperty().equals(propiedad)){								
								// ... eliminamos la restriccion al nodo
								if (!nRes.removeRestrictions(restricciones.get(propiedad)))
									aEliminar.add(nRes);

							}
						}
					}

					// elimamos las restricciones convenientes
					for (NodeRestriction r : aEliminar)
						this.removeNode(r);
				}
			}
		}
	}	

	/**
	 * @param clase 
	 * @param unions
	 */
	public void deleteOnesOf(OWLClass clase, Set<OWLObjectOneOf> unions) {
		if (unions.size() > 0){

			ArrayList<NodeOneOf> aEliminar = new ArrayList<NodeOneOf>();

			for (OWLObjectOneOf union : unions){
				for (Object o : getNodes()){
					NodeOneOf nodo = ((OntologyNode) o).asNodeOneOf();
					if (nodo != null){
						if (nodo.getOWLClass().equals(clase) && union.equals(nodo.getOWLObjectComplementOf()))
							aEliminar.add(nodo);
					}
				}
			}

			for (NodeOneOf nodo : aEliminar) {
				this.removeNode(nodo);

				for (Object o : nodo.getPorts()){
					NetPort p = (NetPort) o;

					for (Object edge : p.getEdges())
						this.removeEdge(edge);
				}
			}
		}	
	}


	/****************************************** Auxs ********************************************/

	/**
	 * Agrega una conexión de herencia directa entre dos clases
	 * @param sup superclase 
	 * @param sub subclase
	 */
	public void addInheritanceLink(OWLEntity sub, OWLEntity sup){
		SuperEdge eg = new SuperEdge(sup, sub);
		addConnection(sub, sup, eg);
	}

	/**
	 * conecta dos clases relacionadas por una restricción
	 * @param sub clase restringida
	 * @param sup clase que restringe
	 * @param prop propiedad sobre la que existe la restricción
	 */
	private void addRestrictionLink(OWLClass sub, OWLClass sup, OWLObjectProperty prop){
		RestrictionEdge eg = new RestrictionEdge(sub, sup);
		eg.setLabel(prop.toString());
		addConnection(sub, sup, eg);
	}

	/**
	 * Agrega una conexion entre dos nodos
	 * @param nodoOrigen
	 * @param nodoDestino
	 * @param eg
	 */
	public void addConection(OntologyNode nodoOrigen, OntologyNode nodoDestino, OntologyEdge eg){
		int xOrigen, yOrigen, xDestino, yDestino;
		int altoOrigen, anchoOrigen;
		
		System.err.println("Intentando agregar enlace en modelo ");
		
		// 2) calculamos los puertos mas cercanos y agregamos el enlace al modelo
		if (nodoDestino != null && nodoOrigen != null) {
			
			System.err.println("Los nodos no son nulos ");

			OntologyPort puertoDestino = nodoDestino.west;
			OntologyPort puertoOrigen = nodoOrigen.east;

			//a) recuperamos las coordenadas de los nodos y sus dimensiones
			xDestino = nodoDestino.getOntologyFig().getLocation().x;
			yDestino = nodoDestino.getOntologyFig().getLocation().y;
			xOrigen = nodoOrigen.getOntologyFig().getLocation().x;
			yOrigen = nodoOrigen.getOntologyFig().getLocation().y;	

			altoOrigen = nodoOrigen.getOntologyFig().getHeight();
			anchoOrigen = nodoOrigen.getOntologyFig().getWidth();

			//b) calculamos los puertos que minimizan las distancias
			if (nodoDestino.equals(nodoOrigen)){
				puertoOrigen = nodoOrigen.west;
				puertoDestino = nodoDestino.east;
			}
			else if ( (yOrigen + altoOrigen) > yDestino){
				if ( xDestino < anchoOrigen ) {
					puertoOrigen = nodoOrigen.west;
					puertoDestino = nodoDestino.east;
				}
				else if (xDestino > (xOrigen + anchoOrigen)) {
					puertoOrigen = nodoOrigen.east;
					puertoDestino = nodoDestino.west;
				}
				else {
					puertoDestino = nodoDestino.south;
					puertoOrigen = nodoOrigen.north;
				}
			}
			// el nodo origen est� al sur del destino
			else if ( yOrigen + altoOrigen < yDestino){				
				if (xDestino < xOrigen + anchoOrigen) {
					puertoOrigen = nodoOrigen.west;
					puertoDestino = nodoDestino.east;
				}
				else if (xDestino > (xOrigen + anchoOrigen)){
					puertoOrigen = nodoOrigen.east;
					puertoDestino = nodoDestino.west;
				}
				else {
					puertoOrigen = nodoOrigen.south;
					puertoDestino = nodoDestino.north;
				}
			}
			// los nodos est�n a la misma altura
			else if (xDestino < xOrigen + anchoOrigen) {
				puertoDestino = nodoDestino.east;
				puertoOrigen = nodoOrigen.west;

			}
			else if (xDestino > (xOrigen + anchoOrigen)) {
				puertoDestino = nodoDestino.west;
				puertoOrigen = nodoOrigen.east;
			}

			//c) asociamos los puertos
			eg.setSourcePort(puertoOrigen);
			eg.setDestPort(puertoDestino);
			puertoOrigen.addEdge(eg);
			puertoDestino.addEdge(eg);

			//d) agregamos el enlace al modelo	
			this.addEdge(eg);

			//e) asociamos los puertos para las figuras. De estas manera los enlaces se moveran
			//   con las cajas. 
			//   NOTA: este agregacion hay que hacerla aki y nunca antes de los pasos previos 
			eg.getFigEdge().setSourceFigNode(nodoOrigen.getOntologyFig());
			eg.getFigEdge().setSourcePortFig(nodoOrigen.getOntologyFig());
			eg.getFigEdge().setDestFigNode(nodoDestino.getOntologyFig());
			eg.getFigEdge().setDestPortFig(nodoDestino.getOntologyFig());
			
			//
			
			if (nodoDestino.equals(nodoOrigen)){
				Point[] puntos = eg.getFigEdge().getPoints();
				Point[] nuevos = new Point[6];
				int margin = 18;

				nuevos[0] = puntos[0];
				nuevos[1] = new Point(xDestino-margin, yDestino+altoOrigen/2);
				nuevos[2] = new Point(xDestino-margin, yDestino-margin);
				nuevos[3] = new Point(xDestino+anchoOrigen+margin, yDestino-margin);
				nuevos[4] = new Point(xDestino+anchoOrigen+margin, yDestino+altoOrigen/2);
				nuevos[5] = puntos[puntos.length-1];

				eg.getFigEdge().setPoints(nuevos);
			}
			else 
				eg.getFigEdge().computeRoute();
		}
		else 
			System.err.println("Alguno de los nodos es nulo!!!!!!!!!!!!");
	}
	
	/**
	 * Agrega una conexión entre dos nodos
	 * @param entidadOrigen nodo origen
	 * @param entidadDestino nodo destino
	 * @param eg arista con la que se quiere unir los nodos
	 */
	public void addConnection(Object entidadOrigen, Object entidadDestino, OntologyEdge eg) {		
		if (entidadOrigen == null || entidadDestino == null || eg == null) return;
		
		System.err.println("Los objetos no son nulos!!!!!!!!!!!!");

		// 1) recuperamos los nodos asociados a las clases c1 y c2
		OntologyNode nodoDestino, nodoOrigen;
		nodoOrigen = getNode(entidadOrigen.toString());
		nodoDestino = getNode(entidadDestino.toString());

		this.addConection(nodoOrigen, nodoDestino, eg);
	}



	/***********************************************************************************************/
	/**************************************** SOBRECARGADO *****************************************/
	/***********************************************************************************************/
	/* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.DefaultGraphModel#canConnect(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean canConnect(Object srcPort, Object destPort) {
		if (srcPort.getClass().equals(destPort.getClass()) )
			return true;
		else
			return false;
	}

	@Override
	public void removeNode(Object node) {		
		if (node == null) return;	
		super.removeNode(node);

		// si acabamos de eliminar un nodo clase tenemos que eliminar todos los nodos restriccion
		// asociados		
		NodeClass nc = ((OntologyNode) node).asNodeClass();
		if (nc != null) {

			// lista con los nodos a eliminar. No se eliminan los nodos directamente para evitar
			// accesos concurrentes al vector de nodos del modelo
			List<NodeRestriction> aEliminar = new ArrayList<NodeRestriction>();

			// recorremos los nodo y nos quedamos con los nodos restriccion asociados a la clase a eliminar
			for (Object o : getNodes()){
				NodeRestriction nr = ((OntologyNode) o).asNodeRestriction();

				// si el nodo es una restricci�n
				if (nr != null){
					if ( nr.getOWLClass().toString().equals(nc.getOWLClass().toString()) ){
						aEliminar.add(nr);
					}
				}
			}

			// eliminamos del modelo los nodos restriccion
			for (NodeRestriction nr : aEliminar){
				this.removeNode(nr);
			}
		}


	}


	/***************************************************************************************/
	/******************************* MANIPULACIÓN ONTOLOGÍA ********************************/
	/***************************************************************************************/

	/**
	 * Agrega a la ontologia un axioma de clases disjuntas
	 * @param claseOWL
	 * @param claseOWL2
	 */
	public void addDisjointClassAxiom(OWLClass claseOWL, OWLClass claseOWL2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();
		OWLDisjointClassesAxiom a = fact.getOWLDisjointClassesAxiom(claseOWL, claseOWL2);		
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * Agrega a la ontologia un axioma de propiedades de objetos disjuntas
	 * @param p1
	 * @param asOWLObjectProperty2
	 */
	public void addDisjointObjectPropertyAxiom(
			OWLObjectProperty p1,
			OWLObjectProperty p2) {

		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLDisjointObjectPropertiesAxiom a = fact.getOWLDisjointObjectPropertiesAxiom(p1,p2);		
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * Agrega a la ontologia un axioma de individuos diferentes
	 * @param ind1
	 * @param ind2
	 */
	public void addDifferentIndividualAxiom(OWLIndividual ind1, OWLIndividual ind2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLDifferentIndividualsAxiom a = fact.getOWLDifferentIndividualsAxiom(ind1, ind2);		
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * Agrega a la ontologia un axioma de clases disjuntas
	 * @param dp1
	 * @param dp2
	 */
	public void addDisjointDataPropertyAxiom(OWLDataProperty dp1, OWLDataProperty dp2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();
		OWLDisjointDataPropertiesAxiom a = fact.getOWLDisjointDataPropertiesAxiom(dp1, dp2);		
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * Agrega en la ontologia un axioma de equivalencia de clases
	 * @param c1
	 * @param c2
	 */
	public void addEquivalentClassAxiom(OWLClass c1, OWLClass c2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLEquivalentClassesAxiom a = fact.getOWLEquivalentClassesAxiom(c1, c2);		
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * Agrega en la ontologia un axioma de propiedades de objetos equivalentes
	 * @param asOWLObjectProperty
	 * @param asOWLObjectProperty2
	 */
	public void addEquivalentObjectPropertyAxiom(OWLObjectProperty p1, OWLObjectProperty p2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLEquivalentObjectPropertiesAxiom a = fact.getOWLEquivalentObjectPropertiesAxiom(p1,p2);		
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * Agrega en la ontologia un axioma de propiedades de objetos equivalentes
	 * @param asOWLObjectProperty
	 * @param asOWLObjectProperty2
	 */
	public void addEquivalentDataPropertyAxiom(OWLDataProperty p1, OWLDataProperty p2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLEquivalentDataPropertiesAxiom a = fact.getOWLEquivalentDataPropertiesAxiom(p1,p2);		
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * Inserta en la ontologia un axioma de individuos equivalentes
	 * @param asOWLIndividual
	 * @param asOWLIndividual2
	 */
	public void addSameAsIndividualAxiom(OWLIndividual ind1, OWLIndividual ind2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		Set<OWLIndividual> iguales = new HashSet<OWLIndividual>();
		iguales.add(ind1);
		iguales.add(ind2);
		OWLSameIndividualsAxiom a = fact.getOWLSameIndividualsAxiom(iguales);		
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * @param asOWLObjectProperty
	 * @param asOWLClass
	 */
	public void addRangeAxiom(OWLObjectProperty prop, OWLClass clase) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLObjectPropertyRangeAxiom a = fact.getOWLObjectPropertyRangeAxiom(prop, clase);
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * @param asOWLObjectProperty
	 * @param asOWLClass
	 */
	public void addDomainAxiom(OWLObjectProperty prop, OWLClass clase) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLObjectPropertyDomainAxiom a = fact.getOWLObjectPropertyDomainAxiom(prop, clase);
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * @param asOWLClass
	 * @param asOWLClass2
	 */
	public void addSubClassAxiom(OWLClass c1, OWLClass c2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLSubClassAxiom a = fact.getOWLSubClassAxiom(c1, c2);
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	/**
	 * @param asOWLObjectProperty
	 * @param asOWLObjectProperty2
	 */
	public void addSubObjectPropertyAxiom(OWLObjectProperty p1, OWLObjectProperty p2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();		
		OWLObjectSubPropertyAxiom a = fact.getOWLSubObjectPropertyAxiom(p1, p2);
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	public void addOneOfAxiom(Set<OWLIndividual> individuos, OWLClass clase) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();

		OWLObjectOneOf oneOf = fact.getOWLObjectOneOf(individuos);

		OWLSubClassAxiom a = fact.getOWLSubClassAxiom(clase, oneOf);
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	public void addUnionOfAxiom(Set<OWLDescription> clases, OWLClass clase) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();

		OWLObjectUnionOf oneOf = fact.getOWLObjectUnionOf(clases);

		OWLSubClassAxiom a = fact.getOWLSubClassAxiom(clase, oneOf);
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	public void addIntersectionOfAxiom(Set<OWLDescription> clases, OWLClass clase) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();

		OWLObjectIntersectionOf oneOf = fact.getOWLObjectIntersectionOf(clases);

		OWLSubClassAxiom a = fact.getOWLSubClassAxiom(clase, oneOf);
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}

	public void addComplementOfAxiom(OWLClass c1, OWLClass c2) {
		OWLDataFactory fact = ExampleViewComponent.manager.getOWLDataFactory();

		OWLObjectComplementOf oneOf = fact.getOWLObjectComplementOf(c1);

		OWLSubClassAxiom a = fact.getOWLSubClassAxiom(c2, oneOf);
		AddAxiom addAxiomChange = new AddAxiom(ontologia, a);         
		ExampleViewComponent.manager.applyChange(addAxiomChange);
	}




}

package org.ugr.violet.changefilters;

import java.util.Collections;

import org.semanticweb.owl.model.OWLAntiSymmetricObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLAxiomAnnotationAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owl.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owl.model.OWLDataRange;
import org.semanticweb.owl.model.OWLDataSubPropertyAxiom;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owl.model.OWLDisjointClassesAxiom;
import org.semanticweb.owl.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owl.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLDisjointUnionAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLEntityAnnotationAxiom;
import org.semanticweb.owl.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owl.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owl.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owl.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLImportsDeclaration;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owl.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyChainSubPropertyAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owl.model.OWLObjectSubPropertyAxiom;
import org.semanticweb.owl.model.OWLOntologyAnnotationAxiom;
import org.semanticweb.owl.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLSameIndividualsAxiom;
import org.semanticweb.owl.model.OWLSubClassAxiom;
import org.semanticweb.owl.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owl.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owl.model.SWRLRule;
import org.semanticweb.owl.util.OWLOntologyChangeFilter;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.edges.DisjointEdge;
import org.ugr.violet.graph.edges.EquivalentEdge;
import org.ugr.violet.graph.edges.InverseOfEdge;
import org.ugr.violet.graph.edges.OWLEdge;
import org.ugr.violet.graph.edges.RangeEdge;
import org.ugr.violet.graph.edges.RestrictionEdge;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.NodeObjectProperty;
import org.ugr.violet.visitors.DataPropertyDomainAddVisitor;
import org.ugr.violet.visitors.DataPropertyDomainDeleteVisitor;
import org.ugr.violet.visitors.ObjectPropertyDomainAndRangeAddVisitor;
import org.ugr.violet.visitors.ObjectPropertyDomainAndRangeDeleteVisitor;
import org.ugr.violet.visitors.SuperClassesVisitor;

/**
 * Filtra los cambios producidos en la ontología y actualiza el diagrama en consecuencia
 * @author anab
 */
public class ChangeFilterDiagram extends OWLOntologyChangeFilter {

	protected OWLGraphModel ogm = null;

	/**
	 * Crea el filtro de cambios para aplicarlos al diagrama
	 * @param o
	 */
	public ChangeFilterDiagram(OWLGraphModel o){
		super();
		ogm = o;
	}

	// implementada
	@Override
	public void visit(OWLDisjointClassesAxiom axiom) {

		Object[] clases = axiom.getClassesInSignature().toArray();


		OWLClass c1 = (OWLClass) clases[0];
		OWLClass c2 = (OWLClass) clases[1];

		if (this.isRemove()){

			DisjointEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				DisjointEdge e = ((OWLEdge) o).asDisjointEdge();

				if (e != null && e.checkLink(c1) && e.checkLink(c2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd()){

			DisjointEdge eje = new DisjointEdge(c1, c2);
			eje.setLabel("Disjoint");

			if (c1 != null && c2 != null)
				ogm.addConnection(c1, c2, eje);
		}
	}

	// implementada
	@Override
	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		Object[] propiedades = axiom.getObjectPropertiesInSignature().toArray();		

		OWLObjectProperty p1 = (OWLObjectProperty) propiedades[0];
		OWLObjectProperty p2 = (OWLObjectProperty) propiedades[1];

		if (this.isRemove()){

			DisjointEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				DisjointEdge e = ((OWLEdge) o).asDisjointEdge();

				if (e != null && e.checkLink(p1) && e.checkLink(p2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd()){

			DisjointEdge eje = new DisjointEdge(p1, p2);
			eje.setLabel("Disjoint");

			if (p1 != null && p2 != null)
				ogm.addConnection(p1, p2, eje);
		}
	}

	// implementada
	@Override
	public void visit(OWLEquivalentClassesAxiom axiom) {
		Object[] clases = axiom.getClassesInSignature().toArray();

		OWLClass c1 = (OWLClass) clases[0];
		OWLClass c2 = (OWLClass) clases[1];

		if (this.isRemove()){
			EquivalentEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				EquivalentEdge e = ((OWLEdge) o).asEquivalentEdge();

				if (e != null && e.checkLink(c1) && e.checkLink(c2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd() && c1 != null && c2 != null){
			EquivalentEdge eje = new EquivalentEdge(c1, c2);
			ogm.addConnection(c1, c2, eje);
		}
	}

	// implementada
	@Override
	public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
		Object[] propiedades = axiom.getObjectPropertiesInSignature().toArray();		

		OWLObjectProperty p1 = (OWLObjectProperty) propiedades[0];
		OWLObjectProperty p2 = (OWLObjectProperty) propiedades[1];

		if (this.isRemove()){
			EquivalentEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				EquivalentEdge e = ((OWLEdge) o).asEquivalentEdge();

				if (e != null && e.checkLink(p1) && e.checkLink(p2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd() && p1 != null && p2 != null){
			EquivalentEdge eje = new EquivalentEdge(p1, p2);
			ogm.addConnection(p1, p2, eje);
		}
	}

	@Override
	public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {

	}

	@Override
	public void visit(OWLAxiomAnnotationAxiom axiom) {

	}

	// implementado
	@Override
	public void visit(OWLClassAssertionAxiom axiom) {
		ogm.updateAssertionClass(axiom.getIndividual());
	}

	@Override
	public void visit(OWLDataPropertyAssertionAxiom axiom) {

	}

	// implementado
	@Override
	public void visit(OWLDataPropertyDomainAxiom axiom) {

		if (this.isAdd()) {
			DataPropertyDomainAddVisitor v = new DataPropertyDomainAddVisitor(ogm, axiom.getProperty().asOWLDataProperty());
			axiom.getDomain().accept(v);
		}
		else if (this.isRemove()){
			DataPropertyDomainDeleteVisitor v = new DataPropertyDomainDeleteVisitor(ogm, axiom.getProperty().asOWLDataProperty());
			axiom.getDomain().accept(v);
		}
	}

	@Override
	public void visit(OWLDataPropertyRangeAxiom axiom) {
		OWLDataProperty propiedad = axiom.getProperty().asOWLDataProperty();
		OWLDataRange rango = axiom.getRange();

		if (rango.isDataType()){

		}

	}

	// implementado
	@Override
	public void visit(OWLDataSubPropertyAxiom axiom) {
		if (this.isRemove()){
			ogm.deleteInheritanceLink(axiom.getSubProperty().asOWLDataProperty(), axiom.getSuperProperty().asOWLDataProperty());
		}
		else if (this.isAdd()){
			ogm.addInheritanceLink(axiom.getSuperProperty().asOWLDataProperty(), axiom.getSubProperty().asOWLDataProperty());
		}
	}

	// implementado
	@Override
	public void visit(OWLDifferentIndividualsAxiom axiom) {

		Object[] individuos = axiom.getIndividuals().toArray();

		OWLIndividual c1 = (OWLIndividual) individuos[0];
		OWLIndividual c2 = (OWLIndividual) individuos[1];

		if (this.isRemove()){
			// eliminar la conexi�n
			DisjointEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				DisjointEdge e = ((OWLEdge) o).asDisjointEdge();

				if (e != null && e.checkLink(c1) && e.checkLink(c2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd()){
			DisjointEdge eje = new DisjointEdge(c1, c2);
			eje.setLabel("Different");

			if (c1 != null && c2 != null)
				ogm.addConnection(c1, c2, eje);
		}
	}

	// implementado 
	@Override
	public void visit(OWLDisjointDataPropertiesAxiom axiom) {
		Object[] propiedades = axiom.getDataPropertiesInSignature().toArray();		

		if (propiedades.length < 2) return;

		OWLDataProperty p1 = (OWLDataProperty) propiedades[0];
		OWLDataProperty p2 = (OWLDataProperty) propiedades[1];

		if (this.isRemove()){

			DisjointEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				DisjointEdge e = ((OWLEdge) o).asDisjointEdge();

				if (e != null && e.checkLink(p1) && e.checkLink(p2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd()){

			DisjointEdge eje = new DisjointEdge(p1, p2);
			eje.setLabel("Disjoint");

			if (p1 != null && p2 != null)
				ogm.addConnection(p1, p2, eje);
		}
	}

	@Override
	public void visit(OWLDisjointUnionAxiom axiom) {

		System.out.println("Datos " + axiom.getOWLClass() + " datos: " + axiom.getDescriptions());

		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(OWLEntityAnnotationAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	// implementado
	@Override
	public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
		Object[] clases = axiom.getDataPropertiesInSignature().toArray();

		OWLDataProperty c1 = (OWLDataProperty) clases[0];
		OWLDataProperty c2 = (OWLDataProperty) clases[1];

		if (this.isRemove()){
			EquivalentEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				EquivalentEdge e = ((OWLEdge) o).asEquivalentEdge();

				if (e != null && e.checkLink(c1) && e.checkLink(c2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd() && c1 != null && c2 != null){
			EquivalentEdge eje = new EquivalentEdge(c1, c2);
			ogm.addConnection(c1, c2, eje);
		}
	}

	@Override
	public void visit(OWLFunctionalDataPropertyAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(OWLImportsDeclaration axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {

	}

	// implementada
	@Override
	public void visit(OWLInverseObjectPropertiesAxiom axiom) {		

		OWLObjectProperty p1 = axiom.getFirstProperty().asOWLObjectProperty();
		OWLObjectProperty p2 = axiom.getSecondProperty().asOWLObjectProperty();

		if (this.isRemove()){

			InverseOfEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				InverseOfEdge e = ((OWLEdge) o).asInverseOfEdge();

				if (e != null && e.checkLink(p1) && e.checkLink(p2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);

		}
		else if (this.isAdd()){
			InverseOfEdge r = new InverseOfEdge(p1, p2);
			r.setLabel("InverseOf");
			ogm.addConnection(p1, p2, r);
		}
	}

	@Override
	public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	// implementado
	@Override
	public void visit(OWLObjectPropertyAssertionAxiom axiom) {

		OWLIndividual objeto = axiom.getObject();
		OWLIndividual sujeto = axiom.getSubject();
		OWLObjectProperty propiedad = axiom.getProperty().asOWLObjectProperty();

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
			RestrictionEdge r = new RestrictionEdge(sujeto, objeto);
			r.setLabel(propiedad.toString());

			ogm.addConnection(sujeto, objeto, r);
		}
	}

	@Override
	public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	// implementada
	@Override
	public void visit(OWLObjectPropertyDomainAxiom axiom) {
		OWLObjectProperty propiedad = axiom.getProperty().asOWLObjectProperty();
		OWLDescription dominio = axiom.getDomain();

		if (this.isRemove()){

			if (!dominio.isAnonymous()){

				RangeEdge aBorrar = null;

				// borrar el enlace entre las clases disjuntas
				for (Object o : ogm.getEdges()){
					RangeEdge e = ((OWLEdge) o).asRangeEdge();

					if (e != null && e.checkLink(dominio.asOWLClass()) && e.checkLink(propiedad)){
						aBorrar = e;
					}
				}

				if (aBorrar != null)
					ogm.removeEdge(aBorrar);
			}
			else {
				ObjectPropertyDomainAndRangeDeleteVisitor v =
					new ObjectPropertyDomainAndRangeDeleteVisitor(ogm, propiedad); 
				dominio.accept(v);
			}
		}
		else if (this.isAdd()){
			if (dominio.isAnonymous()) {
				ObjectPropertyDomainAndRangeAddVisitor v =
					new ObjectPropertyDomainAndRangeAddVisitor(ogm, propiedad, false); 
				dominio.accept(v);
			}
			else {
				RangeEdge r = new RangeEdge(propiedad, dominio.asOWLClass());

				NodeObjectProperty nodoProp = ogm.getNodeObjectProperty(propiedad);
				NodeClass clase = (NodeClass) ogm.getNode(dominio.asOWLClass().toString());

				r.setLabel("Domain");
				ogm.addConnection(nodoProp, clase, r);
			}
		}
	}

	// implementada
	@Override
	public void visit(OWLObjectPropertyRangeAxiom axiom) {
		OWLObjectProperty propiedad = axiom.getProperty().asOWLObjectProperty();
		OWLDescription rango = axiom.getRange();

		if (this.isRemove()){

			if (rango.isAnonymous()){
				ObjectPropertyDomainAndRangeDeleteVisitor v =
					new ObjectPropertyDomainAndRangeDeleteVisitor(ogm, propiedad); 
				rango.accept(v);
			}
			else {
				RangeEdge aBorrar = null;

				// borrar el enlace entre las clases disjuntas
				for (Object o : ogm.getEdges()){
					RangeEdge e = ((OWLEdge) o).asRangeEdge();

					if (e != null && e.checkLink(rango.asOWLClass()) && e.checkLink(propiedad)){
						aBorrar = e;
					}
				}

				if (aBorrar != null)
					ogm.removeEdge(aBorrar);
			}
		}
		else if (this.isAdd()){
			if (rango.isAnonymous()){
				ObjectPropertyDomainAndRangeAddVisitor v =
					new ObjectPropertyDomainAndRangeAddVisitor(ogm, propiedad, true); 
				rango.accept(v);
			}
			else {
				RangeEdge r = new RangeEdge(propiedad, rango.asOWLClass());
				r.setLabel("Range");

				NodeObjectProperty nodoProp = ogm.getNodeObjectProperty(propiedad);
				NodeClass clase = (NodeClass) ogm.getNode(rango.asOWLClass().toString());

				ogm.addConnection(nodoProp, clase, r);
			}
		}
	}

	// implementado
	@Override
	public void visit(OWLObjectSubPropertyAxiom axiom) {
		if (this.isRemove()){
			ogm.deleteInheritanceLink(axiom.getSuperProperty().asOWLObjectProperty(), axiom.getSubProperty().asOWLObjectProperty());
		}
		else if (this.isAdd()){
			ogm.addInheritanceLink(axiom.getSubProperty().asOWLObjectProperty(), axiom.getSuperProperty().asOWLObjectProperty());
		}
	}

	@Override
	public void visit(OWLOntologyAnnotationAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	// implementada
	@Override
	public void visit(OWLSameIndividualsAxiom axiom) {
		Object[] individuos = axiom.getIndividuals().toArray();

		OWLIndividual c1 = (OWLIndividual) individuos[0];
		OWLIndividual c2 = (OWLIndividual) individuos[1];

		if (this.isRemove()){
			// eliminar la conexi�n
			EquivalentEdge aBorrar = null;

			// borrar el enlace entre las clases disjuntas
			for (Object o : ogm.getEdges()){
				EquivalentEdge e = ((OWLEdge) o).asEquivalentEdge();

				if (e != null && e.checkLink(c1) && e.checkLink(c2)){
					aBorrar = e;
				}
			}

			if (aBorrar != null)
				ogm.removeEdge(aBorrar);
		}
		else if (this.isAdd()){
			EquivalentEdge eje = new EquivalentEdge(c1, c2);

			if (c1 != null && c2 != null)
				ogm.addConnection(c1, c2, eje);
		}
	}

	@Override
	public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	@Override
	public void visit(SWRLRule rule) {
		if (this.isRemove()){

		}
		else if (this.isAdd()){

		}
	}

	/**
	 * Filtra los cambios realizados en la ontología quedándose con aquellos a los axiomas
	 * de declaración de entidades
	 */
	// implementado
	@Override
	public void visit(OWLDeclarationAxiom axiom) {
		
		
		

		// si se ha eliminado alguna entidad hay que asegurarse que no quede "ni rastro" de ella en el diagrama
		if (this.isRemove()){
			
			System.err.println("Borrando una clase");

			// recuperamos la entidad
			OWLEntity entidad = axiom.getEntity();

			if (entidad.isOWLClass()){  // borrar la clase del diagrama si es que existe
				ogm.deleteClass(entidad.asOWLClass());
			}
			else if (entidad.isOWLIndividual()){
				// eliminar el individuo del diagrama
				ogm.deleteIndividual(entidad.asOWLIndividual());
			}
			else if (entidad.isOWLDataProperty()){
				// eliminar la propiedad de datos del diagrama
				ogm.deleteDataProperty(entidad.asOWLDataProperty());
			}
			// NOTA: no es necesario comprobar para propiedades de objetos puesto que en este caso 
			//       no se elimina ningun axioma de declaracion
		}
	}

	// implementado
	@Override
	public void visit(OWLSubClassAxiom axiom) {

		System.err.println("Entrando en Axioma SubClass");
		
		// si se ha eliminado alguna entidad hay que asegurarse que no quede "ni rastro" de ella en el diagrama
		if (this.isRemove()){

			// la superclase es anonima => restriccion
			if (axiom.getSuperClass().isAnonymous()){

				// utilizamos el visitante de restricciones
				SuperClassesVisitor restrictionVisitor = new SuperClassesVisitor(Collections.singleton(ogm.getOntology()));
				axiom.getSuperClass().accept(restrictionVisitor);

				ogm.deleteUnions(axiom.getSubClass().asOWLClass(), restrictionVisitor.getUnions());
				ogm.deleteIntersections(axiom.getSubClass().asOWLClass(), restrictionVisitor.getIntersections());
				ogm.deleteComplements(axiom.getSubClass().asOWLClass(), restrictionVisitor.getComplements());
				ogm.deleteRestrictions(axiom.getSubClass().asOWLClass(), restrictionVisitor.getRestricciones());
				ogm.deleteOnesOf(axiom.getSubClass().asOWLClass(), restrictionVisitor.getOnesOf());
			}
			// la superclase es conocida => eliminar el enlace entre las clases
			else {
				// buscar las clases involucradas y eliminar los enlaces
				ogm.deleteInheritanceLink(axiom.getSuperClass().asOWLClass(), axiom.getSubClass().asOWLClass());
			}
		}
		else if (this.isAdd()){
			// la superclase es anonima => restriccion
			if (axiom.getSuperClass().isAnonymous()){

				System.err.println("Super clase es anonima");
				
				// utilizamos el visitante de restricciones
				SuperClassesVisitor restrictionVisitor = new SuperClassesVisitor(Collections.singleton(ogm.getOntology()));
				axiom.getSuperClass().accept(restrictionVisitor);

				ogm.updateUnions(axiom.getSubClass().asOWLClass(), restrictionVisitor.getUnions());
				ogm.updateIntersections(axiom.getSubClass().asOWLClass(), restrictionVisitor.getIntersections());
				ogm.updateComplements(axiom.getSubClass().asOWLClass(), restrictionVisitor.getComplements());
				ogm.updateRestrictions(axiom.getSubClass().asOWLClass(), restrictionVisitor.getRestricciones());
				ogm.updateOnesOf(axiom.getSubClass().asOWLClass(), restrictionVisitor.getOnesOf());


			}
			// la superclase es conocida => herencia directa entre clases
			else {
				
				System.err.println("Super clase no es anonima. Añadiendo enlace...");
				// agregar el nuevo enlace
				ogm.addInheritanceLink(axiom.getSubClass().asOWLClass(), axiom.getSuperClass().asOWLClass());
			}
		}
	}
}

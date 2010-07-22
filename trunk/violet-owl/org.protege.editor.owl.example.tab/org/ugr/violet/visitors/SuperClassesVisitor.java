package org.ugr.violet.visitors;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataSomeRestriction;
import org.semanticweb.owl.model.OWLObjectAllRestriction;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.semanticweb.owl.model.OWLObjectExactCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectIntersectionOf;
import org.semanticweb.owl.model.OWLObjectMaxCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectMinCardinalityRestriction;
import org.semanticweb.owl.model.OWLObjectOneOf;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectSomeRestriction;
import org.semanticweb.owl.model.OWLObjectUnionOf;
import org.semanticweb.owl.model.OWLObjectValueRestriction;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.util.OWLDescriptionVisitorAdapter;
import org.ugr.violet.base.Restriction;

/**
 * Visita una ontologia recuperando todas las restricciones asociadas a una clase OWL
 * @author anab
 */
public class SuperClassesVisitor extends OWLDescriptionVisitorAdapter {

	/** ????? */
	private boolean processInherited = true;

	/** Clases que ya han sido recorridas */
	private Set<OWLClass> processedClasses;


	/** Tabla hash en la que se almacenar n las restricciones encontradas */
	private Hashtable<OWLObjectProperty, Vector<Restriction>> restrictions;
	
	/** Conjunto de uniones */
	private Set<OWLObjectUnionOf> uniones = null;
	
	/** Conjunto de Intersecciones */
	private Set<OWLObjectIntersectionOf> intersecciones = null;
	
	/** Complementos */
	private Set<OWLObjectComplementOf> complementos = null;

	/** Complementos */
	private Set<OWLObjectOneOf> enumeraciones = null;
	
	/**
	 * Crea un nuevo visitante
	 * @param onts conjunto de ontolog as que se visitar 
	 */
	public SuperClassesVisitor(Set<OWLOntology> onts) {
		processedClasses = new HashSet<OWLClass>();
		restrictions = new Hashtable<OWLObjectProperty, Vector<Restriction>>();
		
		uniones = new TreeSet<OWLObjectUnionOf>();
		intersecciones = new TreeSet<OWLObjectIntersectionOf>();
		complementos = new TreeSet<OWLObjectComplementOf>();
		
		enumeraciones = new TreeSet<OWLObjectOneOf>();
	}
	
	public Set<OWLObjectOneOf> getOnesOf(){
		return enumeraciones;
	}
	
	/**
	 * 
	 * @param processInherited
	 */
	public void setProcessInherited(boolean processInherited) {
		this.processInherited = processInherited;
	}
	
	/**
	 * @return the datos
	 */
	public Hashtable<OWLObjectProperty,Vector<Restriction>> getRestricciones() {
		return restrictions;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<OWLObjectIntersectionOf> getIntersections(){
		return this.intersecciones;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<OWLObjectUnionOf> getUnions(){
		return this.uniones;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<OWLObjectComplementOf> getComplements(){
		return this.complementos;
	}
	
	/**
	 * Borra todos los datos almacenados por el visitante
	 */
	public void reset() {
		processedClasses.clear();
		restrictions.clear();
	}

	/**
	 * Visita uan clase OWL
	 */
	public void visit(OWLClass desc) {
		if(processInherited && !processedClasses.contains(desc)) {
			processedClasses.add(desc);
		}
	}
	
	
	/**
	 * Visita una restriccion existencial, agrega la restriccion a la tabla de restricciones
	 * @param desc descriptor asociado a la restriccion
	 */
	public void visit(OWLObjectSomeRestriction desc) {
		// This method gets called when a description (OWLDescription) is an
		// existential (someValuesFrom) restriction and it asks us to visit it
		
		Vector<Restriction> v = restrictions.get(desc.getProperty().getNamedProperty());
		
		if (v == null) {
			v = new Vector<Restriction>();
			restrictions.put(desc.getProperty().getNamedProperty(), v);
		}
		
		for (OWLClass c: desc.getClassesInSignature())
			v.add(new Restriction(Restriction.SOME_RESTRICTION, c));
		
		System.err.println("Restrictin found!!!!");
	}

	
	/**
	 * Visita una restriccion universal, agrega la restriccion a la tabla de restricciones
	 * @param desc descriptor asociado a la restriccion
	 */
	public void visit(OWLObjectAllRestriction desc){
		
		Vector<Restriction> v = restrictions.get(desc.getProperty().getNamedProperty());
		
		if (v == null) {
			v = new Vector<Restriction>();
			restrictions.put(desc.getProperty().getNamedProperty(), v);
		}
		
		for (OWLClass c: desc.getClassesInSignature())
			v.add(new Restriction(Restriction.ALL_RESTRICTION, c));
		
		System.err.println("Restrictin found!!!!");
	}
	
	/**
	 * Visita una restriccion de cardinalidad maxima, agrega la restriccion a la tabla de restricciones
	 * @param desc descriptor asociado a la restriccion
	 */
	public void visit(OWLObjectMaxCardinalityRestriction desc){
		
		Vector<Restriction> v = restrictions.get(desc.getProperty().getNamedProperty());
		
		if (v == null) {
			v = new Vector<Restriction>();
			restrictions.put(desc.getProperty().getNamedProperty(), v);
		}
		
		for (OWLClass c: desc.getClassesInSignature())
			v.add(new Restriction(Restriction.MAX_RESTRICTION, c, desc.getCardinality()));
		
		System.err.println("Restrictin found!!!!");
	}
	
	/**
	 * Visita una restriccion de cardinalidad minima, agrega la restriccion a la tabla de restricciones
	 * @param desc descriptor asociado a la restriccion
	 */
	public void visit(OWLObjectMinCardinalityRestriction desc){
		
		Vector<Restriction> v = restrictions.get(desc.getProperty().getNamedProperty());
		
		if (v == null) {
			v = new Vector<Restriction>();
			restrictions.put(desc.getProperty().getNamedProperty(), v);
		}

		for (OWLClass c: desc.getClassesInSignature())
			v.add(new Restriction(Restriction.MIN_RESTRICTION, c, desc.getCardinality()));
		
		System.err.println("Restrictin found!!!!");
	}
	
	/**
	 * Visita una restriccion de cardinalidad exacta, agrega la restriccion a la tabla de restricciones
	 * @param desc descriptor asociado a la restriccion
	 */
	public void visit(OWLObjectExactCardinalityRestriction desc){
		
		Vector<Restriction> v = restrictions.get(desc.getProperty().getNamedProperty());
		
		if (v == null) {
			v = new Vector<Restriction>();
			restrictions.put(desc.getProperty().getNamedProperty(), v);
		}

		for (OWLClass c: desc.getClassesInSignature())
			v.add(new Restriction(Restriction.EXACT_RESTRICTION, c, desc.getCardinality()));
		
		System.err.println("Restrictin found!!!!");
	}
	
	/**
	 * Visita una restriccion de valor, agrega la restriccion a la tabla de restricciones
	 * @param desc descriptor asociado a la restriccion
	 */
	public void visit(OWLObjectValueRestriction desc){
		
		Vector<Restriction> v = restrictions.get(desc.getProperty().getNamedProperty());
		
		if (v == null) {
			v = new Vector<Restriction>();
			restrictions.put(desc.getProperty().getNamedProperty(), v);
		}

		for (OWLClass c: desc.getClassesInSignature())
			v.add(new Restriction(Restriction.VALUE_RESTRICTION, c));
		
		System.err.println("Restrictin found!!!!");
	}
	
	public void visit(OWLDataSomeRestriction desc){
		
	}
	
	public void visit(OWLObjectUnionOf desc){
		uniones.add(desc);
	}
	
	public void visit(OWLObjectIntersectionOf desc){
		intersecciones.add(desc);
	}
	
	public void visit(OWLObjectComplementOf desc){
		complementos.add(desc);
	}
	
	@Override
	public void visit(OWLObjectOneOf desc) {
		this.enumeraciones.add(desc);
	}
}
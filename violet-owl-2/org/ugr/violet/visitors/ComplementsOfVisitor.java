/**
 * 
 */
package org.ugr.violet.visitors;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLObject;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.semanticweb.owl.model.OWLObjectIntersectionOf;
import org.semanticweb.owl.util.OWLDescriptionVisitorAdapter;

/**
 * @author anab
 *
 */
public class ComplementsOfVisitor extends OWLDescriptionVisitorAdapter {
	
	Set<OWLClass> processedClasses = new HashSet<OWLClass>();
	Set<OWLClass> clases = new HashSet<OWLClass>();
	private boolean processInherited = true;
	
	public void visit(OWLDescription o){
		System.err.println(o.getClass());
	}
	
	/**
	 * Visita uan clase OWL
	 */
	public void visit(OWLClass desc) {
		if(processInherited && !processedClasses.contains(desc)) {
			processedClasses.add(desc);
		}
	}
	
	public void visit(OWLObjectComplementOf c){
		if (!c.getOperand().isAnonymous())
			clases.add(c.getOperand().asOWLClass());
	}
	
	public Set<OWLClass> getClases(){
		return clases;
	}
	
	public void clear(){
		processedClasses.clear();
		clases.clear();
	}
		
	/*
	public void visit(OWLObjectComplementOf desc){
		complementos.add(desc.getOperand().asOWLClass());
	}*/

}

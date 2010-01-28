/**
 * 
 */
package org.ugr.violet.graph;

import java.awt.Point;
import java.net.URI;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.tigris.gef.graph.presentation.NetNode;
import org.ugr.violet.graph.edges.RestrictionEdge;
import org.ugr.violet.graph.nodes.NodeIndividual;
import org.ugr.violet.graph.nodes.OntologyNode;
import org.ugr.violet.graph.nodes.activity.NodeActivityStep;
import org.ugr.violet.graph.nodes.activity.NodeDecision;
import org.ugr.violet.graph.nodes.activity.NodeFork;
import org.ugr.violet.graph.nodes.activity.NodeMerge;

/**
 * @author anab
 * 
 */
public class OntologyActivityGraphModel extends OntologyGraphModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937652960022403075L;

	/**
	 * @param ont
	 */
	public OntologyActivityGraphModel(OWLOntology ont) {
		super(ont);
	}
	
	@Override
	public boolean addIndividual(String  taskName, Point location){
		taskName=taskName.trim();
		OWLIndividual ind = this.getOwlIndividualByName(taskName);
		OntologyNode nodo = null;
		OWLOntology activa = ExampleViewComponent.manager.getActiveOntology();
		
		if (ind != null) {
			
			// clasificar tipo de individuo
			
			OWLClass c = (OWLClass) ind.getTypes(activa).toArray()[0];
			
			if (c.toString().equals("Action")  || c.toString().equals("Task")){
				nodo = this.addActionStep(ind);
				System.err.println("ES una acción");
			}
			else if (c.toString().equals("Fork_Step")){
				nodo = this.addForkStep(ind);
			}
			else if (c.toString().equals("Join_Step")){
				nodo = this.addForkStep(ind);
			}
			else if (c.toString().equals("Decision_Step")){
				nodo = this.addDecisionStep(ind);
			}
			else if (c.toString().equals("Merge_Step")){
				nodo = this.addMergeStep(ind);
			}
			else if (true){
				System.err.println("NO es una acción");
			}
			
			
			System.err.flush();
			
			if (nodo != null) {
				
				System.err.println("El nodo se ha creado con exito");
				this.addNode(nodo);
				nodo.getOntologyFig().setLocation(location);
			}
			else
				System.err.println("El nodo NO se ha creado con exito");
			
			return true;
		}
		else 
			return false;
	}

	
	/**
	 * @param ind
	 * @return
	 */
	private OntologyNode addMergeStep(OWLIndividual ind) {
		NodeMerge nodo = new NodeMerge(ind);
		return nodo;
	}

	/**
	 * @param ind
	 * @return
	 */
	private OntologyNode addDecisionStep(OWLIndividual ind) {
		NodeDecision nodo = new NodeDecision(ind);
		return nodo;
	}

	/**
	 * @param ind
	 */
	private OntologyNode addForkStep(OWLIndividual ind) {
		NodeFork nodo = new NodeFork(ind);
		return nodo;
	}

	private OntologyNode addActionStep(OWLIndividual task){
		OWLIndividual step = null;
		OWLIndividual role = null;
		OWLOntology activa = ExampleViewComponent.manager.getActiveOntology();
		
		NodeActivityStep nodeInd;
		
		// 1 Buscar el step asociado al la actividad/tarea
		OWLObjectProperty performs = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#performs"));
		for(OWLObjectPropertyAssertionAxiom ax : ontologia.getObjectPropertyAssertionAxioms(task)){
			OWLIndividual objeto = ax.getObject();
			OWLIndividual sujeto = ax.getSubject();
			OWLObjectProperty propiedad = ax.getProperty().asOWLObjectProperty();

			if (propiedad.toString().equals(performs.toString()) && sujeto.toString().equals(task.toString())){
				step = objeto;
			}
		}
		
		// Si no existe el step asociado lo creamos y lo asociamos con la tarea
		if (step == null) {
			step = ExampleViewComponent.manager.getOWLDataFactory().getOWLIndividual(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + task + "_step"));
	    	OWLClass claseStep = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#Action_Step"));
	    	
	    	
	    	OWLClassAssertionAxiom d = ExampleViewComponent.manager.getOWLDataFactory().getOWLClassAssertionAxiom (step, claseStep);
			ExampleViewComponent.manager.applyChange(new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), d));
		}
	
		// asociamos la tarea y el step mediante la propiedad de objetos "performs". Si ya existía la relación no pasa nada
		OWLObjectPropertyAssertionAxiom ax = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(step, performs, task);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, ax));
		
		// 2 Buscamos el rol asociado con la tarea (si existe)
		// TODO buscar el rol asociado con la tarea 
		
		
		// 3 Añadir la actividad como parte de la tarea modelada
		OWLIndividual tareaPrincipal = ExampleViewComponent.lienzo.getTarea();
		// Asociamos la secuencia a los steps
		OWLObjectProperty hasPart = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#has_part"));
		OWLObjectProperty partOf = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#part_of"));
		
		OWLObjectPropertyAssertionAxiom e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(task, partOf, tareaPrincipal);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(tareaPrincipal, hasPart, task);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		// 4 Agregar los steps como parte de la secuencia del diagrama
		OWLIndividual secuencia = ExampleViewComponent.lienzo.getSecuencia();
		
		// step es parte de secuencia
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(step, partOf, secuencia);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		// secuencia tiene como parte a step
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(secuencia, hasPart, step);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		// 5 Cear el nodo y agregarlo al diagrama
		nodeInd = new NodeActivityStep(task, step);
			
		return nodeInd;

		// recorremos todas las aserciones de propiedades de objetos asociadas al nuevo individuo
		//TODO recuperar solo las aserciones válidas para este diagrama => relaciones entre 
		/*
		for(OWLObjectPropertyAssertionAxiom ax : ontologia.getObjectPropertyAssertionAxioms(ind)){
			OWLIndividual objeto = ax.getObject();
			OWLIndividual sujeto = ax.getSubject();
			OWLObjectProperty propiedad = ax.getProperty().asOWLObjectProperty();

			RestrictionEdge r = new RestrictionEdge(sujeto, objeto);
			r.setLabel(propiedad.toString());

			this.addConnection(sujeto, objeto, r);
		}*/
	}

}

/**
 * 
 */
package org.ugr.violet.graph;

import java.awt.Point;
import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLConstant;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDataPropertyExpression;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLObjectPropertyExpression;
import org.semanticweb.owl.model.OWLOntology;
import org.ugr.violet.graph.edges.OWLEdge;
import org.ugr.violet.graph.edges.activity.FollowedByEdge;
import org.ugr.violet.graph.nodes.OWLNode;
import org.ugr.violet.graph.nodes.activity.NodeActivityDiagram;
import org.ugr.violet.graph.nodes.activity.NodeAction;
import org.ugr.violet.graph.nodes.activity.NodeDecision;
import org.ugr.violet.graph.nodes.activity.NodeFirstStep;
import org.ugr.violet.graph.nodes.activity.NodeFork;
import org.ugr.violet.graph.nodes.activity.NodeLastStep;
import org.ugr.violet.graph.nodes.activity.NodeMerge;

/**
 * @author anab
 * 
 */
public class ActivityGraphModel extends OWLGraphModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5937652960022403075L;

	public static String URIAmenities = "http://dl.dropbox.com/u/1475213/amenities.owl";

	private OWLClass action;
	private OWLClass activity;
	private OWLClass informationObject;
	private OWLClass event;
	private OWLClass role;

	private OWLIndividual task = null;
	private OWLIndividual sequence = null;

	/**
	 * @return the task
	 */
	public OWLIndividual getTask() {
		return task;
	}

	/**
	 * @return the sequence
	 */
	public OWLIndividual getSequence() {
		return sequence;
	}

	// ************************* Constructor ***************************
	/**
	 * @param ont
	 */
	public ActivityGraphModel(OWLIndividual Task) {
		super();

		// TODO import AMENITIES if it isn't imported

		OWLDataFactory fact = manager.getOWLDataFactory();

		action = fact.getOWLClass(URI.create(URIAmenities + "#Action"));
		activity = fact.getOWLClass(URI.create(URIAmenities + "#Activity"));
		informationObject = fact.getOWLClass(URI.create(URIAmenities + "#Information_Object"));
		event = fact.getOWLClass(URI.create(URIAmenities + "#Event"));
		role = fact.getOWLClass(URI.create(URIAmenities + "#Role"));

		if (task != null)
			task = Task;
		else
			createNewTask("Prueba_task");
	}

	// ****************************************************************

	private void createNewTask(String taskName) {
		
		OWLDataFactory fact = manager.getOWLDataFactory();
		
		// 1. Creamos un nuevo individuo de la clase tarea:
		task = fact.getOWLIndividual(URI.create(activeOntology().getURI() + "#" + taskName));
		OWLClass ClaseTarea = fact.getOWLClass(URI.create(URIAmenities + "#Task"));

		// Creamos la relación de jerarquía
		OWLClassAssertionAxiom d = fact.getOWLClassAssertionAxiom(task, ClaseTarea);

		// agregamos el axioma a la ontología
		AddAxiom addAx3 = new AddAxiom(activeOntology(), d);

		// aplicamos los cambios
		manager.applyChange(addAx3);

		// 2. Creamos una nueva secuencia y se la asociamos
		sequence = fact.getOWLIndividual(URI.create(activeOntology().getURI() + "#" + taskName + "_Sequence"));
		OWLClass ClaseSecuencia = fact.getOWLClass(URI.create(URIAmenities + "#Sequence"));

		// Creamos la relación de jerarquía
		d = fact.getOWLClassAssertionAxiom(sequence, ClaseSecuencia);

		// agregamos el axioma a la ontología
		addAx3 = new AddAxiom(activeOntology(), d);

		// aplicamos los cambios
		manager.applyChange(addAx3);

		// Asociamos la secuencia y la tarea
		OWLObjectProperty descritaEn = fact.getOWLObjectProperty(URI.create(URIAmenities + "#described_in"));
		OWLObjectPropertyAssertionAxiom e = fact.getOWLObjectPropertyAssertionAxiom(task, descritaEn, sequence);

		// aplicamos los cambios
		manager.applyChange(new AddAxiom(activeOntology(), e));

	}

	// ************************ AD nodes *******************************

	public void addStepToSequence(OWLIndividual step) {

		OWLOntology activa = activeOntology();
		OWLDataFactory fact = manager.getOWLDataFactory();

		OWLObjectProperty hasPart = fact.getOWLObjectProperty(	URI.create(ActivityGraphModel.URIAmenities + "#has_part"));
		OWLObjectProperty partOf = fact.getOWLObjectProperty(URI.create(ActivityGraphModel.URIAmenities + "#part_of"));

		// step es parte de secuencia
		OWLObjectPropertyAssertionAxiom e = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(step, partOf, sequence);
		manager.applyChange(new AddAxiom(activa, e));

		// secuencia tiene como parte a step
		e = manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(sequence, hasPart, step);
		manager.applyChange(new AddAxiom(activa, e));
	}

	@Override
	public boolean addIndividual(String indName, Point location) {
		indName = indName.trim();
		OWLIndividual ind = this.getOwlIndividualByName(indName);
		OWLNode nodo = null;
		OWLOntology activa = manager.getActiveOntology();

		if (ind != null) {

			// clasificar tipo de individuo

			boolean isFlow = false;
			OWLClass type = null;

			for (OWLDescription c : ind.getTypes(activa)) {

				System.err.println("Type : " + c);

				if (!c.isAnonymous()) {
					type = c.asOWLClass();

					if (type.compareTo(action) == 0) {
						nodo = this.addActionStep(ind);
					} else if (type.compareTo(activity) == 0) {
						nodo = this.addActionStep(ind);
					} else if (type.compareTo(event) == 0) {
						nodo = this.addEventStep(ind);
					} else if (type.compareTo(informationObject) == 0) {
						nodo = this.addInformationObjectStep(ind);
					} else
						System.err.println("No matching type found!!!");
				}
			}

			System.err.flush();

			if (nodo != null && !isFlow) {
				this.addNode(nodo);
				nodo.getOntologyFig().setLocation(location);
				updateStep(((NodeActivityDiagram) nodo).getStep(), location);
			}

			return true;
		} else
			return false;
	}

	/**
	 * @param ind
	 * @return
	 */
	private OWLNode addInformationObjectStep(OWLIndividual ind) {
		// TODO Auto-generated method stub

		// 1. Create the information object node

		// 2. Fetch all the possible relations between the information object
		// and steps

		return null;
	}

	/**
	 * @param ind
	 * @return
	 */
	private OWLNode addEventStep(OWLIndividual ind) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param ind
	 * @return
	 */
	private OWLNode addMergeStep(OWLIndividual ind, Point location) {
		NodeMerge nodo = new NodeMerge(ind);
		this.addNode(nodo);
		nodo.getOntologyFig().setLocation(location.x + 30, location.y+60);
		return nodo;
	}

	/**
	 * @param ind
	 * @return
	 */
	private OWLNode addDecisionStep(OWLIndividual ind, Point location) {
		NodeDecision nodo = new NodeDecision(ind);
		this.addNode(nodo);
		nodo.getOntologyFig().setLocation(location.x + 30, location.y+60);
		return nodo;
	}

	/**
	 * @param ind
	 */
	private OWLNode addForkStep(OWLIndividual ind, Point location) {
		NodeFork nodo = new NodeFork(ind);
		this.addNode(nodo);
		nodo.getOntologyFig().setLocation(location.x + 30, location.y+60);
		return nodo;
	}

	/**
	 * @param action
	 * @return
	 */
	public OWLNode addActionStep(OWLIndividual action) {
		OWLIndividual step = null;
		OWLIndividual executor = null;

		OWLOntology activa = activeOntology();
		OWLDataFactory fact = manager.getOWLDataFactory();

		NodeAction nodeInd;

		// 1 Buscar el step asociado al la actividad/tarea
		OWLObjectProperty performs = fact.getOWLObjectProperty(URI.create(ActivityGraphModel.URIAmenities + "#performs"));

		for (OWLOntology ont : ontologies())
			for (OWLObjectPropertyAssertionAxiom ax : ont
					.getObjectPropertyAssertionAxioms(action)) {
				OWLIndividual objeto = ax.getObject();
				OWLIndividual sujeto = ax.getSubject();
				OWLObjectProperty propiedad = ax.getProperty()
						.asOWLObjectProperty();

				if (propiedad.toString().equals(performs.toString())
						&& sujeto.toString().equals(action.toString())) {
					step = objeto;
				}
			}

		// Si no existe el step asociado lo creamos y lo asociamos con la tarea
		if (step == null) {
			step = fact.getOWLIndividual(URI.create(manager.getActiveOntology().getURI() + "#" + action + "_step"));
			OWLClass claseStep = fact.getOWLClass(URI.create(ActivityGraphModel.URIAmenities + "#Action_Step"));

			OWLClassAssertionAxiom d = fact.getOWLClassAssertionAxiom(step, claseStep);
			manager.applyChange(new AddAxiom(manager.getActiveOntology(), d));

		}

		// 2 Buscamos el rol asociado con la tarea (si existe)
		OWLObjectProperty does = fact.getOWLObjectProperty(URI.create(URIAmenities + "#do"));

		System.err.println("Instancias de role = " 	+ role.getIndividuals(manager.getActiveOntology()).size());

		// for all the instances of Role
		for (OWLIndividual ind : role.getIndividuals(manager
				.getActiveOntology()))

			// for all the ontologies
			for (OWLOntology ont : ontologies()) {

				// fetch the object propery values for ind
				Map<OWLObjectPropertyExpression, Set<OWLIndividual>> x = ind
						.getObjectPropertyValues(ont);

				// for all the object property values
				for (OWLObjectPropertyExpression exp : x.keySet()) {

					// if the property is do
					if (exp.asOWLObjectProperty().compareTo(does) == 0)

						// for al the values
						for (OWLIndividual i : x.get(exp))

							// if the value is the action ...
							if (i.compareTo(action) == 0)
								executor = ind; // ind is the excutor of action!
				}
			}

		// 3 Añadir la actividad como parte de la tarea modelada
		OWLObjectProperty hasPart = fact.getOWLObjectProperty(URI.create(manager.getActiveOntology().getURI() + "#has_part"));
		OWLObjectPropertyAssertionAxiom e = fact.getOWLObjectPropertyAssertionAxiom(sequence, hasPart, step);
		manager.applyChange(new AddAxiom(activa, e));

		// 4 recorremos todas las aserciones de propiedades de objetos asociadas
		// al nuevo individuo
		// TODO recuperar solo las aserciones válidas para este diagrama =>
		// relaciones entre
		/*
		 * for(OWLObjectPropertyAssertionAxiom ax :
		 * ontologia.getObjectPropertyAssertionAxioms(ind)){ OWLIndividual
		 * objeto = ax.getObject(); OWLIndividual sujeto = ax.getSubject();
		 * OWLObjectProperty propiedad = ax.getProperty().asOWLObjectProperty();
		 * 
		 * RestrictionEdge r = new RestrictionEdge(sujeto, objeto);
		 * r.setLabel(propiedad.toString());
		 * 
		 * this.addConnection(sujeto, objeto, r); }
		 */
		
		
		// 5 Cear el nodo y agregarlo al diagrama
		nodeInd = new NodeAction(action, step, executor);

		return nodeInd;
	}

	/**
	 * 
	 */
	protected void updateStep(OWLIndividual source, Point location) {

		if (source == null)
			return;

		OWLDataFactory fact = manager.getOWLDataFactory();
		
		OWLObjectProperty followed_by = fact.getOWLObjectProperty(URI.create(URIAmenities + "#followed_by"));
		
		System.err.println("searching for followed_by relations...");
		for (OWLOntology ont : ontologies()) {
			
			

			// fetch the object propery values for the action
			Map<OWLObjectPropertyExpression, Set<OWLIndividual>> x = source.getObjectPropertyValues(ont);

			// for all the object property values
			for (OWLObjectPropertyExpression exp : x.keySet()) {
				
				System.err.println("object property " +  exp);
				
				// if the property is followed_by
				if (exp.asOWLObjectProperty().compareTo(followed_by) == 0)
					// for al the values
					for (OWLIndividual followed_by_relation : x.get(exp))
						this.addFlow(source, followed_by_relation, location);
			}
		}

	}

	// ************************** AD Edges *****************************
	/**
	 * @param followed_by_relation
	 * @return
	 */
	public OWLEdge addFlow(OWLIndividual sourceStep, OWLIndividual followed_by_relation, Point location) {
	
		System.err.println("Adding flow to the step");
		
		OWLDataFactory fact = manager.getOWLDataFactory();
		
		// 1. Recuperar la relación following step.
		OWLObjectProperty following_step = fact.getOWLObjectProperty(URI
				.create(URIAmenities + "#following_step"));
		
		// 2. Recuperar la guarda de la relación
		OWLObjectProperty evaluates = fact.getOWLObjectProperty(URI.create(URIAmenities + "#evaluates"));
		OWLDataProperty has_expression = fact.getOWLDataProperty(URI.create(URIAmenities + "#has_expression"));
		OWLIndividual guard = null;
		String guardLabel = "";
		
		for(OWLIndividual guarda : this.getValuesForObjectPropertyInAllOntologies(followed_by_relation, evaluates)){
			
			guard = guarda;
			for (OWLConstant c : this.getValuesForDataPropertyInAllOntologies(guard, has_expression)){
				
				System.err.println("Encontrada expresion para guarda: " + c);
				guardLabel = c.toString();
				break;
			}
			break;
		}
	
		
		OWLClass ios = fact.getOWLClass(URI.create(URIAmenities + "#Information_Object_Step"));
		OWLClass decisionStep = fact.getOWLClass(URI.create(URIAmenities + "#Decision_Step"));
		OWLClass mergeStep = fact.getOWLClass(URI.create(URIAmenities + "#Merge_Step"));
		OWLClass firstStep = fact.getOWLClass(URI.create(URIAmenities + "#First_Step"));
		OWLClass finalStep = fact.getOWLClass(URI.create(URIAmenities + "#Final_Step"));
		OWLClass forkStep = fact.getOWLClass(URI.create(URIAmenities + "#Fork_Step"));
		OWLClass joinStep= fact.getOWLClass(URI.create(URIAmenities + "#Join_Step"));
		
		OWLClass type;
		
		for (OWLIndividual targetStep : this.getValuesForObjectPropertyInAllOntologies(followed_by_relation, following_step)){
			
			if (this.getNode(targetStep.toString()) == null) {
				for (OWLDescription d : targetStep.getTypes(ontologies()))
					if (!d.isAnonymous()) {
						type = d.asOWLClass();
						
						if (type.compareTo(ios) == 0){
							
						}
						else if (type.compareTo(decisionStep) == 0){
							this.addDecisionStep(targetStep, location);
						}
						else if (type.compareTo(mergeStep) == 0){
							this.addMergeStep(targetStep, location);
						}
						else if (type.compareTo(firstStep) == 0){
							this.addFirstStep(targetStep, location);
						}
						else if (type.compareTo(finalStep) == 0){
							this.addFinalStep(targetStep, location);
						}
						else if (type.compareTo(forkStep) == 0){
							this.addForkStep(targetStep, location);
						}
						else if (type.compareTo(joinStep) == 0){
							this.addForkStep(targetStep, location);
						}
					}
				
			}
			
			OWLEdge edge = new FollowedByEdge(sourceStep, targetStep, followed_by_relation, guardLabel);
			this.addConnection(sourceStep, targetStep, edge);
		}
		
		
		
		
		return null;

	}

	/**
	 * @param related
	 */
	private void addFinalStep(OWLIndividual related, Point location) {
		NodeLastStep n = new NodeLastStep(related);
		this.addNode(n);
		n.getOntologyFig().setLocation(location.x+20, location.y+60);
		
	}
	
	/**
	 * @param related
	 */
	private void addFirstStep(OWLIndividual related, Point location) {
		NodeFirstStep n = new NodeFirstStep(related);
		this.addNode(n);
		n.getOntologyFig().setLocation(location.x+20, location.y+60);
	}


	// ************* In ADs only individuals are allowed!!! ***************

	
	public boolean addClass(String className, Point location) {
		return false;
	}

	public boolean addDataProperty(String dataPropertyName, Point nodeLocation) {
		return false;
	}

	public boolean addObjectProperty(String objectPropertyNAme,
			Point nodeLocation) {
		return false;
	}

	// ******************************* AUX *********************************
	
	private Set<OWLIndividual> getValuesForObjectPropertyInAllOntologies(OWLIndividual ind, OWLObjectProperty p){
		
		Set<OWLIndividual> related = new HashSet<OWLIndividual>();
		
		for (OWLOntology ont : ontologies()) {

			// fetch the object propery values for the action
			Map<OWLObjectPropertyExpression, Set<OWLIndividual>> x = ind.getObjectPropertyValues(ont);

			// for all the object property values
			for (OWLObjectPropertyExpression exp : x.keySet())
				// if the property is followed_by
				if (exp.asOWLObjectProperty().compareTo(p) == 0)
					// for al the values
					related.addAll(x.get(exp));
		}
		
		return related;
	}
	
	private Set<OWLConstant> getValuesForDataPropertyInAllOntologies(OWLIndividual ind, OWLDataProperty p){
		
		Set<OWLConstant> related = new HashSet<OWLConstant>();
		
		for (OWLOntology ont : ontologies()) {
			

			// fetch the object propery values for the action
			Map<OWLDataPropertyExpression, Set<OWLConstant>> x =  ind.getDataPropertyValues(ont);

			// for all the object property values
			for (OWLDataPropertyExpression exp : x.keySet())
				// if the property is followed_by
				if (exp.asOWLDataProperty().compareTo(p) == 0)
					// for al the values
					related.addAll(x.get(exp));
		}
		
		return related;
	}
	
	private boolean checkType(OWLIndividual ind, OWLClass type){
		
		boolean typeFound = false;
		
		for (OWLDescription c : ind.getTypes(ontologies()))
			if (!c.isAnonymous()) {
				type = c.asOWLClass();

				if (type.compareTo(ind) == 0) 
					typeFound = true;
			}
		
		return typeFound;
	}
}

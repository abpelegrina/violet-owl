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
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.ugr.violet.graph.edges.OWLEdge;
import org.ugr.violet.graph.edges.activity.FollowedByEdge;
import org.ugr.violet.graph.nodes.OWLNode;
import org.ugr.violet.graph.nodes.activity.NodeActivity;
import org.ugr.violet.graph.nodes.activity.NodeActivityStep;
import org.ugr.violet.graph.nodes.activity.NodeDecision;
import org.ugr.violet.graph.nodes.activity.NodeFork;
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

	/**
	 * @param ont
	 */
	public ActivityGraphModel(OWLOntology ont) {
		super(ont);
	}

	public void addStepToSequence(OWLIndividual step) {

		OWLOntology activa = ExampleViewComponent.manager.getActiveOntology();

		OWLIndividual secuencia = ExampleViewComponent.lienzoActual
				.getSecuencia();
		OWLObjectProperty hasPart = ExampleViewComponent.manager
				.getOWLDataFactory().getOWLObjectProperty(
						URI.create(ExampleViewComponent.manager
								.getActiveOntology().getURI()
								+ "#has_part"));
		OWLObjectProperty partOf = ExampleViewComponent.manager
				.getOWLDataFactory().getOWLObjectProperty(
						URI.create(ExampleViewComponent.manager
								.getActiveOntology().getURI()
								+ "#part_of"));

		// step es parte de secuencia
		OWLObjectPropertyAssertionAxiom e = ExampleViewComponent.manager
				.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(step,
						partOf, secuencia);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));

		// secuencia tiene como parte a step
		e = ExampleViewComponent.manager.getOWLDataFactory()
				.getOWLObjectPropertyAssertionAxiom(secuencia, hasPart, step);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
	}

	@Override
	public boolean addIndividual(String indName, Point location) {
		indName = indName.trim();
		OWLIndividual ind = this.getOwlIndividualByName(indName);
		OWLNode nodo = null;
		OWLOntology activa = ExampleViewComponent.manager.getActiveOntology();

		if (ind != null) {

			// clasificar tipo de individuo

			boolean isFlow = false;

			OWLClass c = (OWLClass) ind.getTypes(activa).toArray()[0];

			if (c.toString().equals("Action") || c.toString().equals("Task")
					|| c.toString().equals("Activity")) {
				nodo = this.addActionStep(ind);
				
			} else if (c.toString().equals("Fork_Step")) {
				nodo = this.addForkStep(ind);
			} else if (c.toString().equals("Join_Step")) {
				nodo = this.addForkStep(ind);
			} else if (c.toString().equals("Decision_Step")) {
				nodo = this.addDecisionStep(ind);
			} else if (c.toString().equals("Merge_Step")) {
				nodo = this.addMergeStep(ind);
			} else if (c.toString().equals("Followed_by_Relation")) {
				this.addFlow(ind);
				isFlow = true;
			}

			System.err.flush();

			if (nodo != null && !isFlow) {
				this.addNode(nodo);
				nodo.getOntologyFig().setLocation(location);
				updateStep(((NodeActivity)nodo).getStep());
			}

			return true;
		} else
			return false;
	}

	/**
	 * @param ind
	 * @return
	 */
	public OWLEdge addFlow(OWLIndividual ind) {

		OWLEdge edge = null;
		OWLNode nodoOrigen, nodoDestino;
		OWLIndividual source = null, target = null, guard=null, aux;

		// find the source and the targe of the relation (a.k.a. found the
		// relations with followed_by and following_step)

		// 1. Create the object properties followed_by and following_step
		// followed_by object property
		OWLDataFactory factory = ExampleViewComponent.manager
				.getOWLDataFactory();
		OWLOntology ont = ExampleViewComponent.manager.getActiveOntology();

		OWLObjectProperty followed_by = factory.getOWLObjectProperty(URI
				.create(ont.getURI() + "#followed_by"));
		OWLDeclarationAxiom axiom = factory.getOWLDeclarationAxiom(followed_by);

		AddAxiom addAxiom = new AddAxiom(ont, axiom);
		ExampleViewComponent.manager.applyChange(addAxiom);

		// following_step object property
		OWLObjectProperty following_step = factory.getOWLObjectProperty(URI
				.create(ont.getURI() + "#following_step"));
		axiom = factory.getOWLDeclarationAxiom(following_step);

		addAxiom = new AddAxiom(ont, axiom);
		ExampleViewComponent.manager.applyChange(addAxiom);
		
		// evaluates property
		OWLObjectProperty evaluates = factory.getOWLObjectProperty(URI
				.create(ont.getURI() + "#evaluates"));
		axiom = factory.getOWLDeclarationAxiom(evaluates);

		addAxiom = new AddAxiom(ont, axiom);
		ExampleViewComponent.manager.applyChange(addAxiom);

		// 2. visit all of the Object Property Assertion axioms and select only
		// the ones that involve ind and the flow properties
		for (Object o : this.getNodes()) {
			NodeActivity nodo = (NodeActivity) o;

			System.out.println();

			if (nodo.isNodeActivityStep()) {

				System.err.println("nodo: " + nodo);

				aux = nodo.asNodeActivityStep().getStep();

				// comprobamos si existe un property assertion que lo relacione
				// que el individual
				for (OWLObjectPropertyAssertionAxiom ass : ont
						.getObjectPropertyAssertionAxioms(aux)) {

					// it is following_step property?
					if (ass.getProperty().compareTo(followed_by) == 0
							&& ass.getObject().compareTo(ind) == 0) {
						source = aux;
						System.err.println("encontrado origen: " + source);
					}
				}
			}
		}

		for (OWLObjectPropertyAssertionAxiom ass : ont
				.getObjectPropertyAssertionAxioms(ind)) {

			System.err.println("axioma: " + ass);

			// it is following_step property?
			if (ass.getProperty().compareTo(following_step) == 0) {
				target = ass.getObject();
				System.err.println("encontrado destino: " + target);
			}
			
			
			// it is evaluates property?
			if (ass.getProperty().compareTo(evaluates) == 0) {
				guard = ass.getObject();
				System.err.println("encontrado guarda: " + guard);
			}
			
			
		}

		// If we found both the followed_by and the following_step we create the
		// edge and add it to the diagram. Otherwise none happens.
		if (source != null && target != null) {

			// create the edge
			edge = new FollowedByEdge(source, target, ind, guard);
						
			// this method takes care about the presence (or absence) of the
			// nodes corresponding to the steps in the current diagram
			// and acts consecuently
			this.addConnection(source, target, edge);
		} else
			System.err.println("No se porque no entra: " + source + " "
					+ target);

		// we return the edge, just because.
		return edge;
	}

	/**
	 * @param ind
	 * @return
	 */
	private OWLNode addMergeStep(OWLIndividual ind) {
		NodeMerge nodo = new NodeMerge(ind);
		return nodo;
	}

	/**
	 * @param ind
	 * @return
	 */
	private OWLNode addDecisionStep(OWLIndividual ind) {
		NodeDecision nodo = new NodeDecision(ind);
		return nodo;
	}

	/**
	 * @param ind
	 */
	private OWLNode addForkStep(OWLIndividual ind) {
		NodeFork nodo = new NodeFork(ind);
		return nodo;
	}

	public OWLNode addActionStep(OWLIndividual task) {
		OWLIndividual step = null;
		OWLIndividual role = null;

		OWLOntology activa = ExampleViewComponent.manager.getActiveOntology();

		NodeActivityStep nodeInd;

		// 1 Buscar el step asociado al la actividad/tarea
		OWLObjectProperty performs = ExampleViewComponent.manager
				.getOWLDataFactory().getOWLObjectProperty(
						URI.create(ExampleViewComponent.manager
								.getActiveOntology().getURI()
								+ "#performs"));
		for (OWLObjectPropertyAssertionAxiom ax : ontologia
				.getObjectPropertyAssertionAxioms(task)) {
			OWLIndividual objeto = ax.getObject();
			OWLIndividual sujeto = ax.getSubject();
			OWLObjectProperty propiedad = ax.getProperty()
					.asOWLObjectProperty();

			if (propiedad.toString().equals(performs.toString())
					&& sujeto.toString().equals(task.toString())) {
				step = objeto;
			}
		}

		// Si no existe el step asociado lo creamos y lo asociamos con la tarea
		if (step == null) {
			step = ExampleViewComponent.manager.getOWLDataFactory()
					.getOWLIndividual(
							URI.create(ExampleViewComponent.manager
									.getActiveOntology().getURI()
									+ "#" + task + "_step"));
			OWLClass claseStep = ExampleViewComponent.manager
					.getOWLDataFactory().getOWLClass(
							URI.create(ExampleViewComponent.manager
									.getActiveOntology().getURI()
									+ "#Action_Step"));

			OWLClassAssertionAxiom d = ExampleViewComponent.manager
					.getOWLDataFactory().getOWLClassAssertionAxiom(step,
							claseStep);
			ExampleViewComponent.manager.applyChange(new AddAxiom(
					ExampleViewComponent.manager.getActiveOntology(), d));
		}

		// asociamos la tarea y el step mediante la propiedad de objetos
		// "performs". Si ya existía la relación no pasa nada
		OWLObjectPropertyAssertionAxiom ax = ExampleViewComponent.manager
				.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(step,
						performs, task);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, ax));

		// 2 Buscamos el rol asociado con la tarea (si existe)
		// TODO buscar el rol asociado con la tarea

		/*
		 * // 3 Añadir la actividad como parte de la tarea modelada
		 * OWLIndividual tareaPrincipal =
		 * ExampleViewComponent.lienzoActual.getTarea(); // Asociamos la
		 * secuencia a los steps OWLObjectProperty hasPart =
		 * ExampleViewComponent
		 * .manager.getOWLDataFactory().getOWLObjectProperty(
		 * URI.create(ExampleViewComponent.manager.getActiveOntology().getURI()
		 * + "#has_part")); OWLObjectProperty partOf =
		 * ExampleViewComponent.manager
		 * .getOWLDataFactory().getOWLObjectProperty(
		 * URI.create(ExampleViewComponent.manager.getActiveOntology().getURI()
		 * + "#part_of"));
		 * 
		 * OWLObjectPropertyAssertionAxiom e =
		 * ExampleViewComponent.manager.getOWLDataFactory
		 * ().getOWLObjectPropertyAssertionAxiom(task, partOf, tareaPrincipal);
		 * ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		 * 
		 * e =ExampleViewComponent.manager.getOWLDataFactory().
		 * getOWLObjectPropertyAssertionAxiom(tareaPrincipal, hasPart, task);
		 * ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		 * 
		 * // 4 Agregar los steps como parte de la secuencia del diagrama
		 * OWLIndividual secuencia =
		 * ExampleViewComponent.lienzoActual.getSecuencia();
		 * 
		 * // step es parte de secuencia e =
		 * ExampleViewComponent.manager.getOWLDataFactory
		 * ().getOWLObjectPropertyAssertionAxiom(step, partOf, secuencia);
		 * ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		 * 
		 * // secuencia tiene como parte a step e =
		 * ExampleViewComponent.manager.
		 * getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(secuencia,
		 * hasPart, step); ExampleViewComponent.manager.applyChange(new
		 * AddAxiom(activa, e));
		 */

		// 5 Cear el nodo y agregarlo al diagrama
		nodeInd = new NodeActivityStep(task, step);
		
		

		return nodeInd;

		// recorremos todas las aserciones de propiedades de objetos asociadas
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
	}

	/**
	 * 
	 */
	protected void updateStep(OWLIndividual source) {
		
		if (source == null) return;

		OWLDataFactory factory = ExampleViewComponent.manager
				.getOWLDataFactory();
		OWLOntology ont = ExampleViewComponent.manager.getActiveOntology();

		OWLObjectProperty followed_by = factory.getOWLObjectProperty(URI
				.create(ont.getURI() + "#followed_by"));
		OWLDeclarationAxiom axiom = factory.getOWLDeclarationAxiom(followed_by);
		AddAxiom addAxiom = new AddAxiom(ont, axiom);
		ExampleViewComponent.manager.applyChange(addAxiom);

		// comprobamos si existe un property assertion que lo relacione que el
		// individual
		for (OWLObjectPropertyAssertionAxiom ass : ont.getObjectPropertyAssertionAxioms(source)) {
			
			System.err.println("Recorriendo el axioma: " + ass);
			
			// it is followed_by property?
			if (ass.getProperty().compareTo(followed_by) == 0)
				this.addFlow(ass.getObject());
		}
	}

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

}

/**
 * 
 */
package org.ugr.violet.graph.presentation;


import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.JOptionPane;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.tigris.gef.base.DeleteFromModelAction;
import org.tigris.gef.event.GraphSelectionEvent;
import org.tigris.gef.event.GraphSelectionListener;
import org.tigris.gef.event.ModeChangeEvent;
import org.tigris.gef.event.ModeChangeListener;
import org.tigris.gef.graph.GraphEdgeRenderer;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.GraphNodeRenderer;
import org.tigris.gef.graph.presentation.JGraph;
import org.ugr.violet.base.OntologyActivityDiagram;
import org.ugr.violet.graph.OntologyActivityGraphModel;
import org.ugr.violet.graph.nodes.activity.NodeActivity;
import org.ugr.violet.graph.nodes.activity.NodeFirstStep;
import org.ugr.violet.graph.nodes.activity.NodeLastStep;
import org.ugr.violet.ui.OntologyPalette;

/**
 * Clase que representa el lienzo sobre el que se dibuja la representaci�n visual de la ontolog�a
 * @author anab	
 */

public class JOntologyActivityGraph extends JGraph implements ModeChangeListener, DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5721239684646257988L;
	
	/**
	 * Diagrama
	 */
	private OntologyActivityDiagram od = null;
	
	/**
	 * Modelo
	 */
	private OntologyActivityGraphModel ogm = null;
	
	private OWLOntology activa = null;	

	private OWLIndividual tarea = null;
	
	private OWLIndividual secuencia = null;
	
	/**
	 * @return the tarea
	 */
	public OWLIndividual getTarea() {
		return tarea;
	}

	/**
	 * @return the secuencia
	 */
	public OWLIndividual getSecuencia() {
		return secuencia;
	}
	
	/**
	 * constructor
	 * @param activa ontolog�a para la que se quiere contruir el modelo
	 * @param p paleta con los controles
	 */
	public JOntologyActivityGraph(OWLOntology ont, OntologyPalette p) {
		super();
		this.setBounds(10, 10, 300, 200);
		this.add(p, BorderLayout.NORTH);
		
		activa = ont;
		// creamos el diagrama asociado a la ontolog�a
		ogm = new OntologyActivityGraphModel(activa);
		od = new OntologyActivityDiagram(activa.getURI().toString(), ogm);
		this.setGraphModel( ogm );
		
		// al darle al supr se borrara el componente seleccionado
		this.bindKey(new DeleteFromModelAction("DeleteFromDiagram"), KeyEvent.VK_DELETE, 0);
		
		// Activamos el soporte para el drag'n drop
		DropTarget dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
		this.setDropTarget(dropTarget);

		
		/* Agregamos un listener para seleccionar en los distintos paneles del entorno Protege la entidad OWL 
		 * seleccionada en el diagrama*/
		this.addGraphSelectionListener(new GraphSelectionListener(){

			public void selectionChanged(GraphSelectionEvent gse) {
				
				for (Object o : gse.getSelections()){
					OWLEntity entidad = ExampleViewComponent.manager.getOWLEntity(o.toString());
					ExampleViewComponent.workspace.getOWLSelectionModel().setSelectedEntity(entidad);
				}
			}
		});
		
		/*Y a la inversa tb: agregamos un listener para que los cambios en la seleccion en Prot�g� 
		 * se refleje en el diagrama*/
		/*ExampleViewComponent.workspace.getOWLSelectionModel().addListener(new OWLSelectionModelListener(){

			public void selectionChanged() throws Exception {
				OWLEntity entidad = ExampleViewComponent.workspace.getOWLSelectionModel().getSelectedEntity();
				ogm.setSelection(entidad);
			}			
		});*/
		
		String taskName = JOptionPane.showInputDialog("Select the name of the new task"); 
		
		if (taskName != null) {
			this.createNewTask(taskName);
			this.createDiagramSkeleton(taskName);
		}
	}
	
	
	/**
	 * constructor
	 * @param activa ontolog�a para la que se quiere contruir el modelo
	 * @param p paleta con los controles
	 */
	public JOntologyActivityGraph(OWLOntology ont, OntologyPalette p, OWLIndividual task) {
		super();
		this.setBounds(10, 10, 300, 200);
		
		tarea = task;
		
		activa = ont;
		// creamos el diagrama asociado a la ontolog�a
		ogm = new OntologyActivityGraphModel(activa);
		od = new OntologyActivityDiagram(activa.getURI().toString(), ogm);
		this.setGraphModel( ogm );
		
		// al darle al supr se borrara el componente seleccionado
		this.bindKey(new DeleteFromModelAction("DeleteFromDiagram"), KeyEvent.VK_DELETE, 0);
		
		// Activamos el soporte para el drag'n drop
		DropTarget dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
		this.setDropTarget(dropTarget);

		
		/* Agregamos un listener para seleccionar en los distintos paneles del entorno Protege la entidad OWL 
		 * seleccionada en el diagrama*/
		this.addGraphSelectionListener(new GraphSelectionListener(){

			public void selectionChanged(GraphSelectionEvent gse) {
				
				for (Object o : gse.getSelections()){
					OWLEntity entidad = ExampleViewComponent.manager.getOWLEntity(o.toString());
					ExampleViewComponent.workspace.getOWLSelectionModel().setSelectedEntity(entidad);
				}
			}
		});
		
		/*Y a la inversa tb: agregamos un listener para que los cambios en la seleccion en Prot�g� 
		 * se refleje en el diagrama*/
		/*ExampleViewComponent.workspace.getOWLSelectionModel().addListener(new OWLSelectionModelListener(){

			public void selectionChanged() throws Exception {
				OWLEntity entidad = ExampleViewComponent.workspace.getOWLSelectionModel().getSelectedEntity();
				ogm.setSelection(entidad);
			}			
		});*/
		
		String taskName = JOptionPane.showInputDialog("Select the name of the new task"); 
		
		
			this.createNewTask(taskName);
			this.createDiagramSkeleton(tarea.toString());
	}
	
	private void createNewTask(String taskName){
		// 1. Creamos un nuevo individuo de la clase tarea:
		tarea = ExampleViewComponent.manager.getOWLDataFactory().getOWLIndividual(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + taskName));
		OWLClass ClaseTarea = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#Task"));
		
		// Creamos la relación de jerarquía
		OWLClassAssertionAxiom d = ExampleViewComponent.manager.getOWLDataFactory().getOWLClassAssertionAxiom (tarea, ClaseTarea);

		// agregamos el axioma a la ontología
		AddAxiom addAx3 = new AddAxiom(activa, d);

		// aplicamos los cambios
		ExampleViewComponent.manager.applyChange(addAx3);
	}
	
	
	private void createDiagramSkeleton(String taskName){
		
		
		// 2. Creamos una nueva secuencia y se la asociamos
		secuencia = ExampleViewComponent.manager.getOWLDataFactory().getOWLIndividual(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#"+ taskName + "_Sequence"));
		OWLClass ClaseSecuencia = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#Sequence"));
		
		// Creamos la relación de jerarquía
		OWLClassAssertionAxiom d = ExampleViewComponent.manager.getOWLDataFactory().getOWLClassAssertionAxiom (secuencia, ClaseSecuencia);

		// agregamos el axioma a la ontología
		AddAxiom addAx3 = new AddAxiom(activa, d);

		// aplicamos los cambios
		ExampleViewComponent.manager.applyChange(addAx3);
		
		// Asociamos la secuencia y la tarea
		OWLObjectProperty descritaEn = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#described_in"));
		OWLObjectPropertyAssertionAxiom e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(tarea, descritaEn, secuencia);

		// aplicamos los cambios
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		// 3. Creamos pasos de inicio y de fin y los conectamos para que veamos lo bonito que queda
		OWLIndividual inicio = ExampleViewComponent.manager.getOWLDataFactory().getOWLIndividual(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + taskName + "_start"));
		OWLClass ClaseInicio = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#First_Step"));
		
		OWLIndividual fin = ExampleViewComponent.manager.getOWLDataFactory().getOWLIndividual(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + taskName + "_end"));
		OWLClass ClaseFin = ExampleViewComponent.manager.getOWLDataFactory().getOWLClass(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#Final_Step"));
		
		d = ExampleViewComponent.manager.getOWLDataFactory().getOWLClassAssertionAxiom (inicio, ClaseInicio);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, d));
		d  = ExampleViewComponent.manager.getOWLDataFactory().getOWLClassAssertionAxiom (fin, ClaseFin);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, d));
		
		// Asociamos la secuencia a los steps
		OWLObjectProperty hasPart = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#has_part"));
		OWLObjectProperty partOf = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#part_of"));
		
		// inicio
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(inicio, partOf, secuencia);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(secuencia, hasPart, inicio);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		// fin
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(fin, partOf, secuencia);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(secuencia, hasPart, fin);
		ExampleViewComponent.manager.applyChange(new AddAxiom(activa, e));
		
		// Asociamos los pasos entre si???
		
		// 4. Pintamos el inicio y el fin => necesitamos las figuras y nodos en cuestión
		
		NodeActivity nodo = new NodeFirstStep(inicio);
		ogm.addNode(nodo);
		nodo.getOntologyFig().setLocation(new Point(100, 20));
		
		nodo = new NodeLastStep(fin);
		ogm.addNode(nodo);
		nodo.getOntologyFig().setLocation(new Point(100, 420));
	}
	
	public void modeChange(ModeChangeEvent mce) {
		
	}

	public void setGraphEdgeRenderer(GraphEdgeRenderer rend) {
		getEditor().setGraphEdgeRenderer(rend);
	}
	

	public void setGraphModel(GraphModel gm) {
		super.setGraphModel(gm);
	}

	public void setGraphNodeRenderer(GraphNodeRenderer rend) {
		getEditor().setGraphNodeRenderer(rend);
	}

	/**
	 * 
	 */
	public void dragEnter(DropTargetDragEvent dtde) {
	}

	public void dragExit(DropTargetEvent dte) {
	}

	public void dragOver(DropTargetDragEvent dtde) {
	}

	/**
	 * Controla la acci�n drop, agregando clases o individuos seg�n sea necesario
	 */
	public void drop(DropTargetDropEvent dtde) {
		
		dtde.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable t = dtde.getTransferable();
		
		// recuperamos la posici�n donde se ha soltado el objeto
		Point p = dtde.getLocation();
		
		// ajustamos la posicion al desplazamiento (scroll) del panel
		p.x += this.getViewPosition().x;
		p.y += this.getViewPosition().y;
		
		try {
			// recuperamos el objeto arrastrado como cadena
			String cadena = t.getTransferData(DataFlavor.stringFlavor).toString();
			
			// intentamos agregar el objeto como una clase
			if (!ogm.addClass(cadena, p))
				if (!ogm.addIndividual(cadena, p))
					if (!ogm.addObjectProperty(cadena, p))
						ogm.addDataProperty(cadena, p); // si falla lo intentamos como individuo
			dtde.dropComplete(true);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
	}
}

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

import org.protege.owl.examples.tab.VioletEditor;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.util.OWLOntologyChangeFilter;
import org.tigris.gef.base.DeleteFromModelAction;
import org.tigris.gef.event.GraphSelectionEvent;
import org.tigris.gef.event.GraphSelectionListener;
import org.tigris.gef.event.ModeChangeEvent;
import org.tigris.gef.event.ModeChangeListener;
import org.tigris.gef.graph.GraphEdgeRenderer;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.GraphNodeRenderer;
import org.tigris.gef.graph.presentation.JGraph;
import org.ugr.violet.base.ActivityDiagram;
import org.ugr.violet.changefilters.ChangeFilterActivityDiagram;
import org.ugr.violet.changefilters.ChangeFilterDiagram;
import org.ugr.violet.graph.ActivityGraphModel;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.nodes.activity.NodeActivityDiagram;
import org.ugr.violet.graph.nodes.activity.NodeFirstStep;
import org.ugr.violet.graph.nodes.activity.NodeLastStep;
import org.ugr.violet.ui.ActivityDiagramPalette;
import org.ugr.violet.ui.OWLPalette;

/**
 * Clase que representa el lienzo sobre el que se dibuja la representaci�n visual de la ontolog�a
 * @author anab	
 */

public class JOWLActivityGraph extends JOWLGraph implements ModeChangeListener, DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5721239684646257988L;

	private OWLIndividual tarea = null;
	
	private OWLIndividual secuencia = null;
	
	@Override
	public boolean isViewCanvas(){
		return true;
	}
	
	@Override
	public boolean isBaseCanvas(){
		return false;
	}
	
	/**
	 * constructor
	 * @param activa ontolog�a para la que se quiere contruir el modelo
	 * @param p paleta con los controles
	 */
	public JOWLActivityGraph(OWLOntology ont, OWLPalette p) {
		super(ont, p);
		
		// creamos el diagrama asociado a la ontolog�a
		ogm = new ActivityGraphModel(null);
		od = new ActivityDiagram(activa.getURI().toString(), ogm);
		this.setGraphModel( ogm );
		
		/*
		this.createNewTask("prueba");
		this.createDiagramSkeleton("prueba");
		*/
	}
	
	
	/**
	 * constructor
	 * @param activa ontolog�a para la que se quiere contruir el modelo
	 * @param p paleta con los controles
	 */
	public JOWLActivityGraph(OWLOntology ont, OWLPalette p, OWLIndividual task) {
		super(ont, p);
		
		
		tarea = task;
		
		// creamos el diagrama asociado a la ontolog�a
		ogm = new ActivityGraphModel(tarea);
		od = new ActivityDiagram(activa.getURI().toString(), ogm);
		this.setGraphModel( ogm );
		
		/*
		// creamos la nueva tarea
		this.createNewTask("prueba");
		this.createDiagramSkeleton(tarea.toString());
		*/
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
	
	@Override
	public OWLOntologyChangeFilter getChangeListener(){
		if (changeListener == null)
			changeListener = new ChangeFilterActivityDiagram((ActivityGraphModel) getGraphModel());
		
		return changeListener;
	}
	
	public boolean isOWLActivityGraph(){
		return true;
	}
}

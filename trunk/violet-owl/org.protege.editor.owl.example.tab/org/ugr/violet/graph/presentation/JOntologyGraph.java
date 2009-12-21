/**
 * 
 */
package org.ugr.violet.graph.presentation;


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

import org.protege.editor.owl.model.selection.OWLSelectionModelListener;
import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.OWLEntity;
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
import org.ugr.violet.base.OntologyDiagram;
import org.ugr.violet.graph.OntologyGraphModel;
import org.ugr.violet.ui.OntologyPalette;

/**
 * Clase que representa el lienzo sobre el que se dibuja la representaci�n visual de la ontolog�a
 * @author anab	
 */

public class JOntologyGraph extends JGraph implements ModeChangeListener, DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5721239684646257988L;
	
	/**
	 * Diagrama
	 */
	private OntologyDiagram od = null;
	
	/**
	 * Modelo
	 */
	private OntologyGraphModel ogm = null;
	
	/**
	 * constructor
	 * @param activa ontolog�a para la que se quiere contruir el modelo
	 * @param p paleta con los controles
	 */
	public JOntologyGraph(OWLOntology activa, OntologyPalette p) {
		super();
		this.setBounds(10, 10, 300, 200);
		
		// creamos el diagrama asociado a la ontolog�a
		ogm = new OntologyGraphModel(activa);
		od = new OntologyDiagram(activa.getURI().toString(), ogm);
		this.setGraphModel( od.getOntologyGraphModel() );
		
		// al darle al supr se borrara el componente seleccionado
		this.bindKey(new DeleteFromModelAction("DeleteFromDiagram"), KeyEvent.VK_DELETE, 0);
		DropTarget dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
		this.setDropTarget(dropTarget);
	

		
		
		/* Agregamos un listener para seleccionar en los distintos paneles del entorno Prot�g� la entidad OWL 
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

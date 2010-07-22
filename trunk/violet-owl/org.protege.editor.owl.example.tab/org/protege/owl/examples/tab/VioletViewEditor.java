/**
 * 
 */
package org.protege.owl.examples.tab;

import java.awt.BorderLayout;
import java.util.List;

import org.apache.log4j.Logger;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.OWLWorkspace;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntologyChange;
import org.semanticweb.owl.model.OWLOntologyChangeListener;
import org.ugr.violet.graph.presentation.JOWLGraph;
import org.ugr.violet.ui.OWLPalette;
import org.ugr.violet.view.graph.presentation.JOWLViewGraph;

/**
 * @author anab
 *
 */
public class VioletViewEditor extends VioletEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -111047107134045889L;
	private static final Logger log = Logger.getLogger(VioletEditor.class);
	
	
	public static OWLModelManager manager = null;
	public static OWLWorkspace workspace = null;
	private static JOWLGraph lienzoActual = null;
	OWLOntologyChangeListener oocl;

	@Override
	protected void disposeOWLView() {
		manager.removeOntologyChangeListener(oocl);
	}
	
	@Override
	protected void initialiseOWLView() throws Exception {

		// DON'T CHANGE THE LAYOUT. GEF DOESN'T WORK WITH OTHER LAYOUTS
		setLayout(new BorderLayout());
		inicializaRecursosGEF();

		manager = this.getOWLModelManager();
		workspace = this.getOWLWorkspace();

		OWLPalette barraDeHerramientas = new OWLPalette();
		JOWLViewGraph lienzoActividad = new JOWLViewGraph(barraDeHerramientas);
		
		lienzoActual = lienzoActividad;
		this.add(lienzoActividad);
		
		oocl = new OWLOntologyChangeListener() {

			public void ontologiesChanged(
					List<? extends OWLOntologyChange> cambio)
					throws OWLException {
				System.err.println("Cambios: " + cambio);

				lienzoActual.getChangeListener().processChanges(cambio);
			}
		};

		manager.addOntologyChangeListener(oocl);

		log.info("Example View Component initialized");
	}
	
	public static JOWLGraph getLienzoActual() {
		return lienzoActual;
	}

}

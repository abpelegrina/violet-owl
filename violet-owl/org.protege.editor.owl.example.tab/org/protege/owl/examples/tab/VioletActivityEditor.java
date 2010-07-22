package org.protege.owl.examples.tab;

import java.awt.BorderLayout;
import java.util.List;

import org.apache.log4j.Logger;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntologyChange;
import org.semanticweb.owl.model.OWLOntologyChangeListener;
import org.ugr.violet.graph.presentation.JOWLActivityGraph;
import org.ugr.violet.graph.presentation.JOWLGraph;
import org.ugr.violet.ui.ActivityDiagramPalette;

/**
 * 
 * @author anab
 */
public class VioletActivityEditor extends VioletEditor {
	private static final long serialVersionUID = -4515710047558710080L;

	private static final Logger log = Logger.getLogger(VioletEditor.class);

	private static JOWLGraph lienzoActual = null;

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

		//tabs = new JTabbedPane();

		
		ActivityDiagramPalette barraDeHerramientas = new ActivityDiagramPalette();
		JOWLActivityGraph lienzoActividad = new JOWLActivityGraph(barraDeHerramientas);
		
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

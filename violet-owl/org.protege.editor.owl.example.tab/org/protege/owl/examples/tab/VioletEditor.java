package org.protege.owl.examples.tab;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.OWLWorkspace;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntologyChange;
import org.semanticweb.owl.model.OWLOntologyChangeListener;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;
import org.ugr.violet.graph.presentation.JOWLGraph;
import org.ugr.violet.ui.OWLPalette;

/**
 * 
 * @author anab
 */
public class VioletEditor extends AbstractOWLViewComponent {
	private static final long serialVersionUID = -4515710047558710080L;

	private static final Logger log = Logger.getLogger(VioletEditor.class);

	public static OWLModelManager manager = null;
	public static OWLWorkspace workspace = null;
	private static JOWLGraph lienzoActual = null;
	OWLOntologyChangeListener oocl;

	@Override
	protected void disposeOWLView() {
		manager.removeOntologyChangeListener(oocl);
	}

	protected void inicializaRecursosGEF() {

		Localizer.addResource("GefBase",
				"org.tigris.gef.base.BaseResourceBundle");

		Localizer.addResource("GefPres",
				"org.tigris.gef.presentation.PresentationResourceBundle");

		Localizer.addLocale(Locale.getDefault());

		Localizer.switchCurrentLocale(Locale.getDefault());

		ResourceLoader.addResourceExtension("gif");

		ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
	}

	@Override
	protected void initialiseOWLView() throws Exception {

		// DON'T CHANGE THE LAYOUT. GEF DOESN'T WORK WITH OTHER LAYOUTS
		setLayout(new BorderLayout());
		inicializaRecursosGEF();

		manager = this.getOWLModelManager();
		workspace = this.getOWLWorkspace();
		
		OWLPalette barraDeHerramientas2 = new OWLPalette();
		JOWLGraph lienzoBasico = new JOWLGraph(barraDeHerramientas2);

		lienzoActual = lienzoBasico;
		this.add(lienzoBasico);
		

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

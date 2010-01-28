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
import org.semanticweb.owl.util.OWLOntologyChangeFilter;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;
import org.ugr.violet.changefilters.ChangeFilterActivityDiagram;
import org.ugr.violet.graph.OntologyGraphModel;
import org.ugr.violet.graph.presentation.JOntologyActivityGraph;
import org.ugr.violet.ui.OntologyPalette;

/**
 * 
 * @author anab
 */
public class ExampleViewComponent extends AbstractOWLViewComponent {
    private static final long serialVersionUID = -4515710047558710080L;
    
    private static final Logger log = Logger.getLogger(ExampleViewComponent.class);
    public static JOntologyActivityGraph lienzo = null;
    private OntologyPalette barraDeHerramientas;
    public static OWLModelManager manager = null;
    public static OWLWorkspace workspace = null;
    OWLOntologyChangeListener oocl;

    @Override
    protected void disposeOWLView() {
    	manager.removeOntologyChangeListener(oocl);
    }
    
    private void inicializaRecursosGEF(){
    	
    	Localizer.addResource("GefBase","org.tigris.gef.base.BaseResourceBundle");

        Localizer.addResource("GefPres","org.tigris.gef.presentation.PresentationResourceBundle");

        Localizer.addLocale(Locale.getDefault());

        Localizer.switchCurrentLocale(Locale.getDefault());

        ResourceLoader.addResourceExtension("gif");

        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
    }

    @Override
    protected void initialiseOWLView() throws Exception {
    	
    	// ¡OJO!: no cambiar el layout. Siempre a de ser BorderLayout o la carga inicial de las clases no funcionar�
        setLayout(new BorderLayout());
        inicializaRecursosGEF();
        barraDeHerramientas = new OntologyPalette();
        add(barraDeHerramientas, BorderLayout.NORTH);
        manager = this.getOWLModelManager();
        workspace = this.getOWLWorkspace();
        lienzo = new JOntologyActivityGraph(manager.getActiveOntology(), barraDeHerramientas);
        
        add(lienzo, BorderLayout.CENTER);
        oocl = new OWLOntologyChangeListener(){

			public void ontologiesChanged(List<? extends OWLOntologyChange> cambio) throws OWLException {
				
				System.err.println("Cambios: " + cambio);
				
				OWLOntologyChangeFilter filter = new ChangeFilterActivityDiagram((OntologyGraphModel) lienzo.getGraphModel());
				//Process the list of changes
				filter.processChanges(cambio);
				
			}
        };
        manager.addOntologyChangeListener(oocl);
        log.info("Example View Component initialized");
        
        
    }    

}

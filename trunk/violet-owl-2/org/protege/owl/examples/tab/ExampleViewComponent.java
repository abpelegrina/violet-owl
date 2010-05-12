package org.protege.owl.examples.tab;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
import org.ugr.violet.changefilters.ChangeFilterDiagram;
import org.ugr.violet.graph.OntologyGraphModel;
import org.ugr.violet.graph.presentation.JOntologyActivityGraph;
import org.ugr.violet.graph.presentation.JOntologyGraph;
import org.ugr.violet.ui.ActivityDiagramPalette;
import org.ugr.violet.ui.OntologyPalette;
import org.ugr.violet.view.ViewManager;
import org.ugr.violet.view.graph.presentation.JOntologyViewGraph;

/**
 * 
 * @author anab
 */
public class ExampleViewComponent extends AbstractOWLViewComponent {
    private static final long serialVersionUID = -4515710047558710080L;
    
    private static final Logger log = Logger.getLogger(ExampleViewComponent.class);
   
    
    public static OWLModelManager manager = null;
    public static OWLWorkspace workspace = null;
    private static List<JOntologyGraph> lienzos = null;
    public static JOntologyGraph lienzoActual = null;
    OWLOntologyChangeListener oocl;
    ChangeListener cl = null;
    JTabbedPane tabs;

    @Override
    protected void disposeOWLView() {
    	manager.removeOntologyChangeListener(oocl);
    	tabs.removeChangeListener(cl);
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
    	
    	// DON'T CHANGE THE LAYOUT. GET DOESN'T WORK WITH OTHER LAYOUTS
        setLayout(new BorderLayout());
        inicializaRecursosGEF();
        
        manager = this.getOWLModelManager();
        workspace = this.getOWLWorkspace();
        
        tabs = new JTabbedPane();
        
        lienzos = new ArrayList<JOntologyGraph>();
        
        ActivityDiagramPalette barraDeHerramientas = new ActivityDiagramPalette();
        JOntologyActivityGraph lienzoActividad = new JOntologyActivityGraph(ExampleViewComponent.manager.getActiveOntology(), barraDeHerramientas);
        OntologyPalette barraDeHerramientas2 = new OntologyPalette();
        JOntologyGraph lienzoBasico = new JOntologyViewGraph(ExampleViewComponent.manager.getActiveOntology(), barraDeHerramientas2);
        
        lienzos.add(lienzoBasico);
        lienzos.add(lienzoActividad);
        
        tabs.addTab("Basic Diagram", lienzoBasico);
        tabs.addTab("Activity Diagram", lienzoActividad);
               
        
        cl = new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JTabbedPane t = (JTabbedPane) e.getSource();
				int index = t.getSelectedIndex();
				
				if (index != -1){
					lienzoActual = (JOntologyGraph) t.getComponent(index);
					manager.removeOntologyChangeListener(oocl);
					
					if (lienzoActual.isBaseCanvas()){
						oocl = new OWLOntologyChangeListener(){

							public void ontologiesChanged(List<? extends OWLOntologyChange> cambio) throws OWLException {
								
								System.err.println("Cambios: " + cambio);
									
								OWLOntologyChangeFilter filter = new ChangeFilterDiagram((OntologyGraphModel) lienzoActual.getGraphModel());
								//Process the list of changes
								filter.processChanges(cambio);
							}
				        };
					}
					else if (lienzoActual.isViewCanvas()){
						oocl = new OWLOntologyChangeListener(){

							public void ontologiesChanged(List<? extends OWLOntologyChange> cambio) throws OWLException {
								
								System.err.println("Cambios: " + cambio);
								
								OWLOntologyChangeFilter filter = new ChangeFilterActivityDiagram((OntologyGraphModel) lienzoActual.getGraphModel());
								//Process the list of changes
								filter.processChanges(cambio);
								
							}
				        };
					}
					
					manager.addOntologyChangeListener(oocl);
				}				
			}
        };
        
        tabs.addChangeListener(cl);
        
        add(tabs);
        
        tabs.setSelectedIndex(1);
        lienzoActual = lienzoActividad;
        
        oocl = new OWLOntologyChangeListener(){

			public void ontologiesChanged(List<? extends OWLOntologyChange> cambio) throws OWLException {
				System.err.println("Cambios: " + cambio);
				OWLOntologyChangeFilter filter = new ChangeFilterActivityDiagram((OntologyGraphModel) lienzoActual.getGraphModel());
				//Process the list of changes
				filter.processChanges(cambio);
			}
        };
        
        manager.addOntologyChangeListener(oocl);
        
        log.info("Example View Component initialized");        
        //ViewManager vm = new ViewManager("");
        
    }    

}

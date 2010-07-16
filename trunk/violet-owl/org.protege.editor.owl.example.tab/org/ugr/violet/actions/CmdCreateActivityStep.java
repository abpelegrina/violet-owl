/**
 * 
 */
package org.ugr.violet.actions;

import java.net.URI;
import java.util.Random;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.protege.owl.examples.tab.VioletEditor;
import org.protege.owl.examples.tab.VioletViewEditor;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.tigris.gef.base.CmdCreateNode;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Mode;
import org.tigris.gef.base.ModePlace;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.GraphNodeHooks;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.graph.presentation.NetNode;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.NodeNAryRelation;
import org.ugr.violet.graph.nodes.activity.NodeAction;

/**
 * @author anab
 *
 */
public class CmdCreateActivityStep extends CmdCreateNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7782554219129224707L;
	 private static Log LOG = LogFactory.getLog(CmdCreateNode.class);
	 NetNode nodo = null;

	/**
	 * @param nodeClass
	 * @param name
	 */
	public CmdCreateActivityStep() {
		super(NodeAction.class, false, "ClaseOWL");
	}
	
	/**
     * Actually instantiate the NetNode and FigNode objects and set the global
     * next mode to ModePlace
     */
    public void doIt() {
    	OWLDataFactory  f = VioletViewEditor.manager.getOWLDataFactory();

    	String nombreActividad = JOptionPane.showInputDialog("New activity name, please:");
    	String nombreStep = nombreActividad+"_step";
    	
    	
    	if (nombreActividad != null && nombreActividad != ""){
	        OWLIndividual actividad = f.getOWLIndividual(URI.create(VioletViewEditor.manager.getActiveOntology().getURI() + "#" + nombreActividad));
	        OWLClass claseActividad = f.getOWLClass(URI.create(VioletViewEditor.manager.getActiveOntology().getURI() + "#Activity"));
	        OWLIndividual step = f.getOWLIndividual(URI.create(VioletViewEditor.manager.getActiveOntology().getURI() + "#" + nombreStep));
	        OWLClass claseStep = f.getOWLClass(URI.create(VioletViewEditor.manager.getActiveOntology().getURI() + "#Activity_Step"));
	        
	        OWLAxiom axiom = f.getOWLClassAssertionAxiom (actividad, claseActividad);
	        OWLAxiom axiom2 = f.getOWLClassAssertionAxiom (step, claseStep);
	        
	        AddAxiom addAxiom = new AddAxiom(VioletViewEditor.manager.getActiveOntology(), axiom);
	        
	        VioletViewEditor.manager.applyChange(addAxiom);
	        
	        addAxiom = new AddAxiom(VioletViewEditor.manager.getActiveOntology(), axiom2);
	        
	        VioletViewEditor.manager.applyChange(addAxiom);
	        
	        
	     // Asociamos la secuencia a los steps
			OWLObjectProperty performs = VioletViewEditor.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(VioletViewEditor.manager.getActiveOntology().getURI() + "#performs"));
			
			
			// inicio
			OWLObjectPropertyAssertionAxiom e = VioletViewEditor.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(step, performs, actividad);
			VioletViewEditor.manager.applyChange(new AddAxiom(VioletViewEditor.manager.getActiveOntology(), e));
	        
	        
	        nodo = new NodeAction(actividad, step);
	        
	        LOG.debug("New node created " + nodo);

	        if (nodo instanceof GraphNodeHooks) {
	            LOG.debug("Initializing GraphNodeHooks");
	            ((GraphNodeHooks) nodo).initialize(_args);
	        }
	        Editor ce = Globals.curEditor();
	        GraphModel gm = ce.getGraphModel();
	        if (!MutableGraphModel.class.isInstance(gm)) 
	            return;
	        setArg("graphModel", gm);

	        String instructions = null;
	        Object actionName = getValue(javax.swing.Action.NAME);
	        if (actionName != null) {
	            instructions = "Click to place " + actionName.toString();
	        }
	        Mode placeMode = new ModePlace(this, instructions);

	        Object shouldBeSticky = getArg("shouldBeSticky");
	        Globals.mode(placeMode, shouldBeSticky == Boolean.TRUE);
	        if (LOG.isDebugEnabled())
	            LOG.debug("Mode set to ModePlace with sticky mode "
	                    + shouldBeSticky);
    	}
       
    }
	
	@Override
	public Object makeNode() {
		return nodo;
    }

}

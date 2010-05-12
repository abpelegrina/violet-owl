/**
 * 
 */
package org.ugr.violet.actions;

import java.net.URI;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDataProperty;
import org.tigris.gef.base.CmdCreateNode;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Mode;
import org.tigris.gef.base.ModePlace;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.GraphNodeHooks;
import org.tigris.gef.graph.MutableGraphModel;
import org.ugr.violet.graph.nodes.NodeDataProperty;
import org.ugr.violet.graph.nodes.NodeObjectProperty;

/**
 * @author anab
 *
 */
public class CmdCreateNodeDataProperty extends CmdCreateNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7782554219129224707L;
	 private static Log LOG = LogFactory.getLog(CmdCreateNode.class);
	 private NodeDataProperty nodo = null;

	/**
	 * @param nodeClass
	 * @param name
	 */
	public CmdCreateNodeDataProperty() {
		super(NodeObjectProperty.class, false, "DataProperty");
	}
	
	public void doIt(){
		String nombreNueva = JOptionPane.showInputDialog("New data proprerty name, please:");
    	
    	if (nombreNueva != null && nombreNueva != ""){
	        OWLDataFactory f = ExampleViewComponent.manager.getOWLDataFactory();
			OWLDataProperty propiedadDeDatos = f.getOWLDataProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#" + nombreNueva));
	        OWLAxiom axiom = f.getOWLDeclarationAxiom(propiedadDeDatos);
	        
	        AddAxiom addAxiom = new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), axiom);
	        
	        ExampleViewComponent.manager.applyChange(addAxiom);
	        
	        nodo = new NodeDataProperty(propiedadDeDatos);
	        
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

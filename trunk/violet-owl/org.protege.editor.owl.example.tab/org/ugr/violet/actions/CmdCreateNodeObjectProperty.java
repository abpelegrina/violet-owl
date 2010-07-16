/**
 * 
 */
package org.ugr.violet.actions;

import java.net.URI;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.protege.owl.examples.tab.VioletEditor;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLDataFactory;
import org.semanticweb.owl.model.OWLDeclarationAxiom;
import org.semanticweb.owl.model.OWLObjectProperty;
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
public class CmdCreateNodeObjectProperty extends CmdCreateNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7782554219129224707L;
	 private static Log LOG = LogFactory.getLog(CmdCreateNode.class);
	 
	 private NodeObjectProperty nodo = null;

	/**
	 * @param nodeClass
	 * @param name
	 */
	public CmdCreateNodeObjectProperty() {
		super(NodeDataProperty.class, false, "ObjectProperty");
	}
	
	public void doIt(){
String nombreNueva = JOptionPane.showInputDialog("New object property name, please:");
    	
    	if (nombreNueva != null && nombreNueva != ""){    	

        	//Creamos la nueva clase
        	OWLDataFactory  f = VioletEditor.manager.getOWLDataFactory();
        	        	
	        OWLObjectProperty propiedad = f.getOWLObjectProperty(URI.create(VioletEditor.manager.getActiveOntology().getURI() + "#" + nombreNueva));
	        OWLDeclarationAxiom axiom = f.getOWLDeclarationAxiom(propiedad);
	        
	        AddAxiom addAxiom = new AddAxiom(VioletEditor.manager.getActiveOntology(), axiom);
	        VioletEditor.manager.applyChange(addAxiom);
	        
	        NodeObjectProperty nodo = new NodeObjectProperty(propiedad);
	        
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

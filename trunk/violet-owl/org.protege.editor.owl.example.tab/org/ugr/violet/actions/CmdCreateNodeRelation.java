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
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataFactory;
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

/**
 * @author anab
 *
 */
public class CmdCreateNodeRelation extends CmdCreateNode {

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
	public CmdCreateNodeRelation() {
		super(NodeClass.class, false, "Nary");
	}
	
	/**
     * Actually instantiate the NetNode and FigNode objects and set the global
     * next mode to ModePlace
     */
    public void doIt() {
    	OWLDataFactory  f = VioletEditor.manager.getOWLDataFactory();

    	//String nombreNueva = JOptionPane.showInputDialog("New relation name, please:");
    	Random aleatorio = new Random();
    	String nombreNueva = "Generated_Relation_" + aleatorio.nextInt();	
    	if (nombreNueva != null && nombreNueva != ""){
	        OWLClass claseOWL = f.getOWLClass(URI.create(VioletEditor.manager.getActiveOntology().getURI() + "#" + nombreNueva));
	        OWLAxiom axiom = f.getOWLSubClassAxiom(claseOWL, NodeNAryRelation.claseBase);
	        
	        AddAxiom addAxiom = new AddAxiom(VioletEditor.manager.getActiveOntology(), axiom);
	        
	        VioletEditor.manager.applyChange(addAxiom);
	        
	        nodo = new NodeNAryRelation(claseOWL);
	        
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

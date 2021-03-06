/**
 * 
 */
package org.ugr.violet.actions;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.net.URI;

import org.protege.owl.examples.tab.VioletEditor;
import org.protege.owl.examples.tab.VioletActivityEditor;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLClassAssertionAxiom;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.tigris.gef.base.ModeCreatePolyEdge;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigPoly;
import org.tigris.gef.undo.UndoManager;
import org.ugr.violet.graph.ActivityGraphModel;
import org.ugr.violet.graph.edges.activity.FollowedByEdge;
import org.ugr.violet.presentation.activity.FigActivityDiagram;

/**
 *
 *
 * Permite crear una relación followed by entre dos nodos de un diagrama de actividad
 * Comprueba que tanto el nodo de destino como el nodo origen sean de la clase adecuada
 * @author anab
 *
 */
public class ModeCreateFollowedBy extends ModeCreatePolyEdge {

	private FigActivityDiagram sourceFigNode = null;
	private FigActivityDiagram destFigNode = null;
	
	static private OWLObjectProperty followed_by = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ActivityGraphModel.URIAmenities + "#followed_by"));
	static private OWLObjectProperty following_step = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ActivityGraphModel.URIAmenities + "#following_step"));
	
	
	private OWLClass Followed_by_Relation = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(ActivityGraphModel.URIAmenities + "#Followed_by_Relation"));
	private OWLClass Control_Followed_by_Relation = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(ActivityGraphModel.URIAmenities + "#Control_Followed_by_Relation"));
	private OWLClass Followed_Followed_by_Relation = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(ActivityGraphModel.URIAmenities + "#Object_Followed_by_Relation"));
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5891216899390033425L;

	/* (non-Javadoc)
	 * @see org.tigris.gef.base.ModeCreatePolyEdge#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent me) {
		 if (me.isConsumed()) {
	            if (LOG.isDebugEnabled())
	                LOG
	                        .debug("MousePressed detected but rejected as already consumed");
	            return;
	        }

	        UndoManager.getInstance().addMementoLock(this);
	        int x = me.getX(), y = me.getY();
	        // Editor editor = Globals.curEditor();
	        Fig underMouse = editor.hit(x, y);
	        if (underMouse == null) {
	            // System.out.println("bighit");
	            underMouse = editor.hit(x - 16, y - 16, 32, 32);
	        }
	        if (underMouse == null && _npoints == 0) {
	            done();
	            me.consume();
	            
	            if (LOG.isDebugEnabled())
	                LOG
	                        .debug("MousePressed detected but nothing under mouse - consumed anyway");
	            return;
	        }
	        if (!( FigActivityDiagram.class.isInstance(underMouse) ) && _npoints == 0) {
	            done();
	            me.consume();
	            
	            if (LOG.isDebugEnabled())
	                LOG
	                        .debug("MousePressed detected but not on a FigNode - consumed anyway");
	            return;
	        }
	        if (sourceFigNode == null) { // _npoints == 0) {
	            sourceFigNode = (FigActivityDiagram) underMouse;
	            startPort = sourceFigNode.getPuertoNorte();
	            
	            System.out.println("Figura encontrada");
	        }
	        if (startPort == null) {
	            if (LOG.isDebugEnabled())
	                LOG
	                        .debug("MousePressed detected but not on a port - consumed anyway");
	            done();
	            me.consume();
	            return;
	        }
	        startPortFig = sourceFigNode.getPortFig(startPort);

	        if (_npoints == 0) {
	            createFig(me);
	        }
	        if (LOG.isDebugEnabled())
	            LOG
	                    .debug("MousePressed detected and processed by ancestor - consumed");
	        me.consume();
	}

	/* (non-Javadoc)
	 * @see org.tigris.gef.base.ModeCreatePolyEdge#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent me) {
		if (me.isConsumed()) {
            return;
        }
        if (sourceFigNode == null) {
            done();
            me.consume();
            return;
        }

        UndoManager.getInstance().startChain();

        int x = me.getX(), y = me.getY();
        Fig f = editor.hit(x, y);
        if (f == null) {
            f = editor.hit(x - 16, y - 16, 32, 32);
        }
        GraphModel graphModel = editor.getGraphModel();
        if (!(graphModel instanceof ActivityGraphModel)) {
            f = null;
        }

       

        if (FigActivityDiagram.class.isInstance(f)) {
        	destFigNode = (FigActivityDiagram) f;
        	if (!destFigNode.toString().equals(sourceFigNode.toString())) {
        		
        		
        		
        		// clasificar el tipo de enlace:
        		//1 - Si el nodo de inicio es Work_Unit_Ste, la relacion serán Followed_By_Relation
        		
        		ActivityGraphModel agm = (ActivityGraphModel) graphModel;
	        	
        		OWLEntity e1 = destFigNode.getOWLEntity();
        		OWLEntity e2 = sourceFigNode.getOWLEntity();
        		
        		if(e1.isOWLIndividual() && e2.isOWLIndividual()){
        			
        			// 1. We create the individual that reifies the flow relation
        			OWLClass flowClass = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(ActivityGraphModel.URIAmenities + "#Followed_by_Relation"));
        			OWLIndividual flow = VioletActivityEditor.manager.getOWLDataFactory().getOWLIndividual(URI.create(agm.activeOntology().getURI() + "#" + e2.toString() + "_followed_by"));
        			
        			// type assertion for flow
        			OWLClassAssertionAxiom axm = VioletActivityEditor.manager.getOWLDataFactory().getOWLClassAssertionAxiom(flow, flowClass);
        			AddAxiom ax = new AddAxiom(agm.activeOntology(), axm);
        			VioletActivityEditor.manager.applyChange(ax);
        			
        			// 2. We create the two relations between the steps: followed_by and following_step
        			
        			// followed_by
        			OWLObjectPropertyAssertionAxiom e = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(e2.asOWLIndividual(), followed_by, flow);
        			
        			// creamos el cambio y lo almacenamos en la ontología
        			ax = new AddAxiom(VioletActivityEditor.manager.getActiveOntology(), e);
        			VioletActivityEditor.manager.applyChange(ax);
        			
        			// following_step
        			e = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(flow, following_step, e1.asOWLIndividual());
        			
        			// creamos el cambio y lo almacenamos en la ontología
        			ax = new AddAxiom(VioletActivityEditor.manager.getActiveOntology(), e);
        			VioletActivityEditor.manager.applyChange(ax);
        			
        			// 3. Creamos la guarda
        			OWLClass GuardClass = VioletActivityEditor.manager.getOWLDataFactory().getOWLClass(URI.create(ActivityGraphModel.URIAmenities + "#Guard"));
        			OWLIndividual guard = VioletActivityEditor.manager.getOWLDataFactory().getOWLIndividual(URI.create(agm.activeOntology().getURI() + "#" + flow + "_guard"));
        			OWLObjectProperty evaluates = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ActivityGraphModel.URIAmenities + "#evaluates"));
        			OWLDataProperty has_expression = VioletActivityEditor.manager.getOWLDataFactory().getOWLDataProperty(URI.create(ActivityGraphModel.URIAmenities + "#has_expression"));
        			
        			// creamos el individuo guard
        			axm = VioletActivityEditor.manager.getOWLDataFactory().getOWLClassAssertionAxiom(guard, GuardClass);
        			ax = new AddAxiom(agm.activeOntology(), axm);
        			VioletActivityEditor.manager.applyChange(ax);
        			
        			// asociamos la relacion con la guarda
        			e = VioletActivityEditor.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(flow, evaluates, guard);
        			ax = new AddAxiom(VioletActivityEditor.manager.getActiveOntology(), e);
        			VioletActivityEditor.manager.applyChange(ax);
        			
        			// le añadimos valor a la guarda
        			OWLDataPropertyAssertionAxiom a = VioletActivityEditor.manager.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(guard, has_expression, "");
        			ax = new AddAxiom(agm.activeOntology(), a);
        			VioletActivityEditor.manager.applyChange(ax);
        			
        			// creamos el edge
        			FollowedByEdge edge = new FollowedByEdge(e2.asOWLIndividual(), e1.asOWLIndividual(), flow, null);
        			
        			if (graphModel != null)
        				agm.addConnection(e2.asOWLIndividual(), e1.asOWLIndividual(), edge);
        		}
        		else
        			System.err.println("Alguno no es individuo");
        			
        		done();
        	}
        	else {
        		System.err.println("Destino y origen coinciden");
        	}
            
        }
        else { 
        	System.err.println("El destino no es un nodo válido");
        	
        	
        	if (!nearLast(x, y)) {
            editor.damageAll();
            Point snapPt = new Point(x, y);
            editor.snap(snapPt);
            ((FigPoly) _newItem).addPoint(snapPt.x, snapPt.y);
            _npoints++;
            editor.damageAll();
        	}
        }
        _lastX = x;
        _lastY = y;
        UndoManager.getInstance().removeMementoLock(this);
        me.consume();
	}

}

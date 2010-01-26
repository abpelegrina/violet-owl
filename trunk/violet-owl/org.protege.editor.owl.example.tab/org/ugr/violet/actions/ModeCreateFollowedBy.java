/**
 * 
 */
package org.ugr.violet.actions;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.net.URI;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLObjectPropertyAssertionAxiom;
import org.tigris.gef.base.ModeCreatePolyEdge;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigPoly;
import org.tigris.gef.undo.UndoManager;
import org.ugr.violet.graph.OntologyActivityGraphModel;
import org.ugr.violet.graph.OntologyGraphModel;
import org.ugr.violet.graph.edges.activity.FollowedByEdge;
import org.ugr.violet.presentation.FigClass;
import org.ugr.violet.presentation.OntologyFig;
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
        if (!(graphModel instanceof OntologyActivityGraphModel)) {
            f = null;
        }

        OntologyGraphModel ogm = (OntologyGraphModel) graphModel;

        if (FigActivityDiagram.class.isInstance(f)) {
        	destFigNode = (FigActivityDiagram) f;
        	if (!destFigNode.toString().equals(sourceFigNode.toString())) {
	        	
        		OWLEntity e1 = destFigNode.getOWLEntity();
        		OWLEntity e2 = sourceFigNode.getOWLEntity();
        		
        		if(e1.isOWLIndividual() && e2.isOWLIndividual()){
        			
        			// creamos el axioma que relaciona los dos pasos o steps dentro del diagrama
        			OWLObjectProperty partOf = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectProperty(URI.create(ExampleViewComponent.manager.getActiveOntology().getURI() + "#followed_by"));
        			
        			// creamos el axioma para la nueva afirmación sobre una propiedad de objetos
        			OWLObjectPropertyAssertionAxiom e = ExampleViewComponent.manager.getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(e2.asOWLIndividual(), partOf, e1.asOWLIndividual());
        			
        			// creamos el cambio y lo almacenamos en la ontología
        			AddAxiom ax = new AddAxiom(ExampleViewComponent.manager.getActiveOntology(), e);
        			ExampleViewComponent.manager.applyChange(ax);
        		
        			//ogm.addEdge(edge);
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

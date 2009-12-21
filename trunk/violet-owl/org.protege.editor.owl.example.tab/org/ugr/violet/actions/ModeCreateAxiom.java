/**
 * 
 */
package org.ugr.violet.actions;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.ModeCreatePolyEdge;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigPoly;
import org.tigris.gef.undo.UndoManager;
import org.ugr.violet.graph.OntologyGraphModel;
import org.ugr.violet.presentation.OntologyFig;

/**
 * @author anab
 *
 */
public abstract class ModeCreateAxiom extends ModeCreatePolyEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7163880810951001691L;
	
	protected OntologyFig sourceFigNode = null;
	protected OntologyFig destFigNode = null;
	
	abstract public boolean checkCondicionOrigen(Fig f);
	abstract public boolean checkCondicionDestino(Fig f);
	abstract public void accionReleaseMouse(OntologyGraphModel modelo);
	
	
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
	        if (!checkCondicionOrigen(underMouse) && _npoints == 0) {
	            done();
	            me.consume();
	            
	            if (LOG.isDebugEnabled())
	                LOG
	                        .debug("MousePressed detected but not on a FigNode - consumed anyway");
	            return;
	        }
	        if (sourceFigNode == null) { // _npoints == 0) {
	            sourceFigNode = (OntologyFig) underMouse;
	            startPort = sourceFigNode.getPuertoNorte();
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
        if (!(graphModel instanceof OntologyGraphModel)) {
            f = null;
        }

        OntologyGraphModel ogm = (OntologyGraphModel) graphModel;

        if (checkCondicionDestino(f)) {
        	destFigNode = (OntologyFig) f;
        	accionReleaseMouse(ogm);
        	/*        	
        	if (!destFigNode.toString().equals(sourceFigNode.toString())) {
	        	
        		OWLEntity e1 = destFigNode.getOWLEntity();
        		OWLEntity e2 = sourceFigNode.getOWLEntity();
        		
        		if(e1.isOWLClass() && e2.isOWLClass())
        			ogm.addSubClassAxiom(e2.asOWLClass(), e1.asOWLClass());
        		else if (e1.isOWLObjectProperty() && e2.isOWLObjectProperty())
        			ogm.addSubObjectPropertyAxiom(e2.asOWLObjectProperty(), e1.asOWLObjectProperty());
	        	done();
        	}*/
            
        }
        else if (!nearLast(x, y)) {
            editor.damageAll();
            Point snapPt = new Point(x, y);
            editor.snap(snapPt);
            ((FigPoly) _newItem).addPoint(snapPt.x, snapPt.y);
            _npoints++;
            editor.damageAll();
        }
        _lastX = x;
        _lastY = y;
        UndoManager.getInstance().removeMementoLock(this);
        me.consume();
	}
	

}

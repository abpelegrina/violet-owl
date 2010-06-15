package org.ugr.violet.presentation;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigPoly;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;

public class FigComment extends OWLFigure implements MouseListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3665302970734330693L;
	private FigPoly urCorner;
	private int width = 80;
	private int height = 60;
	private FigPoly outlineFig;
	private int dogear = 10;
	private String texto = "";
	private FigText bodyTextFig;
	private boolean readyToEdit = true;
	 
	public FigComment(String text){
		super();
		texto  = text;
		this.initialize();
	}

	
	public FigRect getPuertoNorte(){
		return null;
	}
	
	private void initialize() {
        Color fg = super.getLineColor(); // Use super because not fully init'd
        Color fill = super.getFillColor();
        
        bodyTextFig = new FigText(2, 2, width - 2 - dogear,  height - 4);
        bodyTextFig.setText(texto);
        
        outlineFig = new FigPoly(fg, fill);
        outlineFig.addPoint(0, 0);
        outlineFig.addPoint(width - 1 - dogear, 0);
        outlineFig.addPoint(width - 1, dogear);
        outlineFig.addPoint(width - 1, height - 1);
        outlineFig.addPoint(0, height - 1);
        outlineFig.addPoint(0, 0);
        outlineFig.setFilled(true);
        outlineFig.setLineWidth(1);

        urCorner = new FigPoly(fg, fill);
        urCorner.addPoint(width - 1 - dogear, 0);
        urCorner.addPoint(width - 1, dogear);
        urCorner.addPoint(width - 1 - dogear, dogear);
        urCorner.addPoint(width - 1 - dogear, 0);
        urCorner.setFilled(true);
        Color col = outlineFig.getFillColor();
        urCorner.setFillColor(col.darker());
        urCorner.setLineWidth(1);

        addFig(outlineFig);
        addFig(urCorner);
        addFig(bodyTextFig);
        
        col = outlineFig.getFillColor();
        urCorner.setFillColor(col.darker());

        setBlinkPorts(false); //make port invisible unless mouse enters
        Rectangle r = getBounds();
        setBounds(r.x, r.y, r.width, r.height);
        updateEdges();
    }

	public void mouseClicked(MouseEvent me) {
        if (!readyToEdit) {
            readyToEdit = true;
        }
        if (me.isConsumed()) {
            return;
        }
        if (me.getClickCount() >= 2
	    && !(me.isPopupTrigger()
		 || me.getModifiers() == InputEvent.BUTTON3_MASK)) {
            if (getOwner() == null) {
                return;
            }
            Fig f = hitFig(new Rectangle(me.getX() - 2, me.getY() - 2, 4, 4));
            if (f instanceof MouseListener) {
                ((MouseListener) f).mouseClicked(me);
            }
        }
        me.consume();
    }


	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent ke) {
		if (Character.isISOControl(ke.getKeyChar())) {
            return;
        }
        if (!readyToEdit) {
            
                readyToEdit = true;
        }
        if (ke.isConsumed()) {
            return;
        }
        if (getOwner() == null) {
            return;
        }
        bodyTextFig.keyTyped(ke);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#agregaFiguras()
	 */
	@Override
	protected void makeFigure() {
		// TODO Auto-generated method stub
		
	}

}

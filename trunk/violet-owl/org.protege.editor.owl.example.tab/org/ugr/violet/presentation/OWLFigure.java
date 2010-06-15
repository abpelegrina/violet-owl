package org.ugr.violet.presentation;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JMenu;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigNode;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.util.Localizer;

public abstract class OWLFigure extends FigNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4716260604601113533L;
	protected FigRect puertoNorte, puertoSur, puertoEste, puertoOeste;
	protected Fig box;
	protected static final int tamPuerto = 2;
	protected int x,y,ancho,alto;
	protected Color colorRelleno;
	
	protected final static int defaultFunction = CmdChangeFig.RECTANGLE; 
	

	protected OWLFigure(){
		super();
		this.setShadowSize(4);
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract OWLEntity getOWLEntity();
	
	/**
	 * 
	 * @return
	 */
	public abstract FigRect getPuertoNorte();
	
	/**
	 * 
	 * @return
	 */
	public boolean isFigClass(){
		return false;
	}
	
	protected void colocaPuertos(){
		y = box.getY();
		x = box.getX();
		int desplazamientoX = x + box.getWidth()/2-tamPuerto/2;
		int desplazamientoY = y - tamPuerto/2;
		int desplazamiento2 =x+box.getWidth()-tamPuerto/2;
		
		puertoNorte = new FigRect(  desplazamientoX,   desplazamientoY,   tamPuerto, tamPuerto, Color.gray, Color.white);
		puertoSur =   new FigRect(  desplazamientoX,  -desplazamientoY-2, tamPuerto, tamPuerto, Color.gray, Color.white);
		puertoEste =  new FigRect(  desplazamiento2,  -tamPuerto/2,       tamPuerto, tamPuerto, Color.gray, Color.white);
		puertoOeste = new FigRect(  x-tamPuerto/2,    -tamPuerto/2,       tamPuerto, tamPuerto, Color.gray, Color.white);
		
		
		puertoNorte.setVisible(false);
		puertoSur.setVisible(false);
		puertoEste.setVisible(false);
		puertoOeste.setVisible(false);
		
		this.addFig(puertoNorte);
		this.addFig(puertoEste);
		this.addFig(puertoSur);
		this.addFig(puertoOeste);
	}
	
	/**
	 * 
	 * @return
	 */
	public FigClass asFigClass(){
		return null;
	}
	
	/**
	 * 
	 */
	protected abstract void makeFigure();
	
	/**
	 * @param function
	 */
	public void changeFig(int function) {
		Point p = this.getLocation();
		removeAll();
		
		if (function == CmdChangeFig.RECTANGLE)
			box = new FigRect(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.CIRCLE)
			box = new FigCircle(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.DIAMOND)
			box = new FigDiamond(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.RRECT)
			box = new FigRRect(x, y, ancho, alto, Color.black, colorRelleno);
		
		makeFigure();
		setLocation(p);
		this.setOwner(this.getOwner());
		redraw();
	}
	
	// genera el menu contextual que incluye la transformacion de las figuras
	@Override
	public Vector<Object> getPopUpActions(MouseEvent me) {
		Vector<Object> popUpActions = new Vector<Object>();

		
		popUpActions.addAll(super.getPopUpActions(me));
		popUpActions.addAll(this.getMenuPopUp());

		return popUpActions;
	}
	
	protected Vector<Object> getMenuPopUp(){
		Vector<Object> popUpActions = new Vector<Object>();
		
		JMenu changeFigure = new JMenu(Localizer.localize("PresentationGef",
		"ChangeFigure"));

		changeFigure.add(CmdChangeFig.toCircle);
		changeFigure.add(CmdChangeFig.toRectangle);
		changeFigure.add(CmdChangeFig.toDiamond);
		changeFigure.add(CmdChangeFig.toRRect);
		popUpActions.addElement(changeFigure);

		popUpActions.add(changeFigure);
		
		return popUpActions;
	}
}

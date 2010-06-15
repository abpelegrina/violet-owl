/**
 * 
 */
package org.ugr.violet.presentation;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JMenu;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.util.Localizer;
import org.ugr.violet.graph.nodes.NodeOneOf;

/**
 * @author anab
 *
 */
public class FigOneOf extends OWLFigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5520112571448548195L;
	private FigText nombreIndv; 
	
	public FigOneOf(){
		this(defaultFunction);
	}
	
	public FigOneOf(int function){
		super();
		colorRelleno = Color.getHSBColor(0.5f, 0.83f, 0.6f);
		if (function == CmdChangeFig.RECTANGLE)
			box = new FigRect(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.CIRCLE)
			box = new FigCircle(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.DIAMOND)
			box = new FigDiamond(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.RRECT)
			box = new FigRRect(x, y, ancho, alto, Color.black, colorRelleno);
		this.makeFigure();
	}
	
	/**
	 * 
	 */
	protected void makeFigure() {
		alto = 30;
		ancho = 80;
		
		x = -ancho / 2;
		y = -alto  / 2;
		
		System.err.println(y);
		
		// la caja
		box.setWidth(ancho);
		box.setHeight(alto);
		box.setX(x);
		box.setY(y);
		
		// el letrero
		nombreIndv = new FigText( x+10, y+5, 100, 50);
		nombreIndv.setLineWidth(0);
		
		nombreIndv.setJustification(FigText.JUSTIFY_CENTER);
		nombreIndv.setEditable(true);
		nombreIndv.setText("<<OneOf>>");
		nombreIndv.setFilled(false);
		nombreIndv.setTextColor(Color.white);
		box.setWidth(nombreIndv.getWidth() + 20);
		
		//agregamos las figuras
		this.addFig(box);
		super.colocaPuertos();
		this.addFig(nombreIndv);
	}

	@Override
	public void setOwner(Object own) {
		super.setOwner(own);
		NodeOneOf node = (NodeOneOf) own;
		bindPort(node.north, puertoNorte);
		bindPort(node.south, puertoSur);
		bindPort(node.east, puertoEste);
		bindPort(node.west, puertoOeste);
	}
	
	@Override
	public OWLEntity getOWLEntity() {
		return null;
	}

	@Override
	public FigRect getPuertoNorte() {
		return this.puertoNorte;
	}
	
	public void update(){
		this.removeAll();
		this.makeFigure();
	}
	
	@Override
	protected Vector<Object> getMenuPopUp(){
		Vector<Object> popUpActions = new Vector<Object>();		
		return popUpActions;
	}

}

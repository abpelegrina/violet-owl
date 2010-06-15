package org.ugr.violet.presentation;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JMenu;

import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.util.Localizer;
import org.ugr.violet.graph.nodes.NodeDataProperty;

/**
 * 
 * @author Ana B. Pelegrina
 */
public class FigDataProperty extends OWLFigure {

	private static final long serialVersionUID = 8151944926067219396L;
	private FigText dataPropertyName;
	private OWLDataProperty propiedad = null;

	public FigRect getPuertoNorte(){
		return puertoNorte;
	}

	
	public FigDataProperty(OWLDataProperty oc){
		this(oc, defaultFunction);
	}
	
	/**
	 * 
	 * @param oc
	 */
	public FigDataProperty(OWLDataProperty oc, int function) {
		super();
		propiedad = oc;
		colorRelleno = Color.getHSBColor(0.44f, .7545f, .6549f);
		if (function == CmdChangeFig.RECTANGLE)
			box = new FigRect(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.CIRCLE)
			box = new FigCircle(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.DIAMOND)
			box = new FigDiamond(x, y, ancho, alto, Color.black, colorRelleno);
		else if (function == CmdChangeFig.RRECT)
			box = new FigRRect(x, y, ancho, alto, Color.black, colorRelleno);
		makeFigure();
	}
	
	// agrega las sub-figuras a las figura
	protected void makeFigure(){
    	
    	alto = 30;
		
		
		if (propiedad == null)
			ancho = 80;
		else
			ancho = 8 * propiedad.toString().length() + 30;
		
		x = -ancho / 2;
		y = -alto  / 2;	
		
		// la caja
		box.setWidth(ancho);
		box.setHeight(alto);
		box.setX(x);
		box.setY(y);
		this.addFig(box);

		// el letrero
		dataPropertyName = new FigText( x+10, y+5, 100, 50);
		dataPropertyName.setLineWidth(0);
		dataPropertyName.setJustification(FigText.JUSTIFY_CENTER);
		dataPropertyName.setText(propiedad.toString());
		dataPropertyName.setFilled(false);
		dataPropertyName.setTextColor(Color.white);
	
		
		box.setSize(dataPropertyName.getWidth()+20, box.getHeight());
		
		super.colocaPuertos();
		box.setFillColor(colorRelleno);
		this.addFig(dataPropertyName);
	}

	public void setOwner(Object own) {
		super.setOwner(own);
		//if (!(own instanceof NodeClass)) return;
		NodeDataProperty node = (NodeDataProperty) own;
		bindPort(node.north, puertoNorte);
		bindPort(node.south, puertoSur);
		bindPort(node.east, puertoEste);
		bindPort(node.west, puertoOeste);
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setClassName (String name){

		dataPropertyName.setText(name);
	}

	@Override
	public OWLEntity getOWLEntity() {
		return propiedad;
	}

	@Override
	public String toString() {
		return propiedad.toString();
	}
	
	@Override
	protected Vector<Object> getMenuPopUp(){
		Vector<Object> popUpActions = new Vector<Object>();
		
		JMenu changeFigure = new JMenu(Localizer.localize("PresentationGef","ChangeFigure"));

		changeFigure.add(CmdChangeFig.toCircle);
		changeFigure.add(CmdChangeFig.toRRect);
		popUpActions.addElement(changeFigure);

		popUpActions.add(changeFigure);
		
		return popUpActions;
	}
}

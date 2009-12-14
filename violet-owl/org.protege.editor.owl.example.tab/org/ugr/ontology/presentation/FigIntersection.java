/**
 * 
 */
package org.ugr.ontology.presentation;

import java.awt.Color;
import java.util.Vector;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.ugr.ontology.graph.nodes.NodeIntersection;

/**
 * @author anab
 *
 */
public class FigIntersection extends OntologyFig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5520112571448548195L;
	private FigText simbolo;
	
	public FigIntersection(){
		this(CmdChangeFig.CIRCLE);
	}
	
	public FigIntersection(int function){
		super();
		colorRelleno = Color.getHSBColor(0.1204f, 0.7941f, 0.8000f); 
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
		alto = 20;
		ancho = 20;
		
		x = -ancho / 2;
		y = -alto  / 2;
		
		System.err.println(y);
		
		// la caja
		box.setWidth(ancho);
		box.setHeight(alto);
		box.setX(x);
		box.setY(y);
		
		// el letrero
		simbolo = new FigText(x+10, y-5, 1, 1);
		simbolo.setLineWidth(0);
		//simbolo.setJustification(FigText.JUSTIFY_CENTER);
		simbolo.setText("∩");
		simbolo.setFontSize(16);
		simbolo.setFilled(false);
		simbolo.setTextColor(Color.white);

		//box.setWidth(simbolo.getWidth());

		//agregamos las figuras		
		this.addFig(box);
		super.colocaPuertos();
		this.addFig(simbolo);
	}

	@Override
	public void setOwner(Object own) {
		super.setOwner(own);
		NodeIntersection node = (NodeIntersection) own;
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

/**
 * 
 */
package org.ugr.violet.presentation.activity;

import java.awt.Color;
import java.util.Vector;

import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigRect;
import org.ugr.violet.graph.nodes.activity.NodeFirstStep;

/**
 * @author anab
 *
 */
public class FigFirstStep extends FigActivityDiagram {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5520112571448548195L;
	private OWLIndividual step = null;
	
	public FigFirstStep(OWLIndividual paso){
		this(CmdChangeFig.CIRCLE, paso);
	}
	
	public FigFirstStep(int function, OWLIndividual paso){
		super();
		step=paso;
		colorRelleno = Color.BLACK; 
		box = new FigCircle(x, y, ancho, alto, Color.black, colorRelleno);
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
		

		//box.setWidth(simbolo.getWidth());

		//agregamos las figuras		
		this.addFig(box);
		super.colocaPuertos();
	}

	@Override
	public void setOwner(Object own) {
		super.setOwner(own);
		NodeFirstStep node = (NodeFirstStep) own;
		bindPort(node.north, puertoNorte);
		bindPort(node.south, puertoSur);
		bindPort(node.east, puertoEste);
		bindPort(node.west, puertoOeste);
	}
	
	@Override
	public OWLEntity getOWLEntity() {
		return step;
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
	
	@Override
	public String toString(){
		return step.toString();
	}
}

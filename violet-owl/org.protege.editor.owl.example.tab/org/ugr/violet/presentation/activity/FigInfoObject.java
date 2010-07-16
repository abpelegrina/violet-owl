/**
 * 
 */
package org.ugr.violet.presentation.activity;

import java.awt.Color;
import java.util.Vector;

import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.ugr.violet.graph.nodes.activity.NodeInfoIObject;

/**
 * @author anab
 *
 */
public class FigInfoObject extends FigActivityDiagram {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return step.toString();
	}

	private static final long serialVersionUID = 8151944926067219396L;
	private FigText className;
	private OWLIndividual informationObject = null;
	private OWLIndividual step = null;
	private OWLIndividual state = null;

	public FigRect getPuertoNorte(){
		return puertoNorte;
	}

	/**
	 * 
	 * @param oc
	 */
	public FigInfoObject(OWLIndividual actividad, OWLIndividual paso, OWLIndividual r) {
		this(actividad, paso, r, defaultFunction);
	}
	
	/**
	 * 
	 * @param oc
	 * @param function
	 */
	public FigInfoObject(OWLIndividual oc, OWLIndividual paso, OWLIndividual r, int function){

		informationObject = oc;
		step = paso;
		state = r;
		colorRelleno = Color.WHITE;
		box = new FigRRect(x, y, ancho, alto, Color.black, colorRelleno);
		makeFigure();
	}

	// agrega las sub-figuras a las figura
	protected void makeFigure(){

		alto = 30;
		ancho = 80;

		x = -ancho / 2;
		y = -alto  / 2;		
		// la caja

		box.setWidth(ancho);
		box.setHeight(alto);
		box.setX(x);
		box.setY(y);

		this.addFig(box);
		 
		box.setFillColor(Color.WHITE);
		// el letrero
		className = new FigText( x+10, y+5, 100, 50);
		className.setLineWidth(0);
		className.setJustification(FigText.JUSTIFY_CENTER);
		className.setFilled(false);
		String stateLabel = "";
		
		if (state != null){
			stateLabel = "\n["+ state +"]";
			box.setHeight(box.getHeight()+10);
		}
			
		className.setText( informationObject.toString() + stateLabel);
		className.setFillColor(colorRelleno);

		
		box.setSize(className.getWidth()+20, box.getHeight());

		super.colocaPuertos();
		this.addFig(className);
		box.setFillColor(colorRelleno);
		
	}

	public void setOwner(Object own) {
		super.setOwner(own);
		//if (!(own instanceof NodeClass)) return;
		NodeInfoIObject node = (NodeInfoIObject) own;
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

		className.setText(name);
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		return step;
	}
	
	@Override
	public boolean isFigActivityStep(){
		return true;
	}

	@Override 
	public FigActivityStep asFigActivityStep(){
		return null;
	}
	
	
	@Override
	protected Vector<Object> getMenuPopUp(){
		Vector<Object> popUpActions = new Vector<Object>();		
		return popUpActions;
	}
}

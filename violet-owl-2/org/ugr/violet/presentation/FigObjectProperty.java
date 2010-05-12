/**
 * 
 */
package org.ugr.violet.presentation;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JMenu;

import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.util.Localizer;
import org.ugr.violet.graph.nodes.NodeObjectProperty;

/**
 * @author anab
 *
 */
public class FigObjectProperty extends OntologyFig {

	OWLObjectProperty propiedad = null;
	private FigText nombrePropiedad;
	
	public FigObjectProperty(OWLObjectProperty propiedad){
		this(propiedad, defaultFunction);
	}
	
	/**
	 * @param propiedad
	 * @return 
	 */
	public FigObjectProperty(OWLObjectProperty propiedad, int function) {
		super();
		this.propiedad = propiedad;
		colorRelleno = Color.getHSBColor(0.5746f, 0.7037f, 0.6353f);
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
	
	public FigRect getPuertoNorte(){
		return puertoNorte;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4271699627051311175L;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return propiedad.toString();
	} 

	
	// agrega las sub-figuras a las figura
	protected void makeFigure(){
		
		alto = 30;
		ancho = 80;
		
		x = -ancho / 2;
		y = -alto  / 2;
		
		box.setWidth(ancho);
		box.setHeight(alto);
		box.setX(x);
		box.setY(y);
		
		// el letrero
		nombrePropiedad = new FigText( x+10, y+5, 100, 50);
		nombrePropiedad.setLineWidth(0);
		
		nombrePropiedad.setJustification(FigText.JUSTIFY_CENTER);
		nombrePropiedad.setEditable(true);
		nombrePropiedad.setText(this.propiedad.toString());
		nombrePropiedad.setFilled(false);
		nombrePropiedad.setTextColor(Color.white);
		box.setWidth(nombrePropiedad.getWidth() + 20);
		
		//agregamos las figuras
		this.addFig(box);
		super.colocaPuertos();
		this.addFig(nombrePropiedad);

	}

	public void setOwner(Object own) {
		super.setOwner(own);
		//if (!(own instanceof NodeClass)) return;
		NodeObjectProperty node = (NodeObjectProperty) own;
		bindPort(node.north, puertoNorte);
		bindPort(node.south, puertoSur);
		bindPort(node.east, puertoEste);
		bindPort(node.west, puertoOeste);
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setPropName (String name){

		nombrePropiedad.setText(name);
	}


	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		return propiedad;
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

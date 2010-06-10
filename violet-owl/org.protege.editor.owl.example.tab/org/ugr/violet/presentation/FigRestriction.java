package org.ugr.violet.presentation;

import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JMenu;

import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.util.Localizer;
import org.ugr.violet.base.Restriction;
import org.ugr.violet.graph.nodes.NodeRestriction;

/**
 * 
 * @author Ana B. Pelegrina
 */
public class FigRestriction extends OntologyFig {
	private static final long serialVersionUID = 8151944926067219396L;
	private FigLine separator;
	private FigText className;
	private OWLObjectProperty property = null;
	private Vector<Restriction> restricciones = null;

	public FigRestriction(OWLObjectProperty op, Vector<Restriction> restric){
		this(op, restric,defaultFunction);
	}
	
	/**
	 * 
	 * @param oc
	 */
	public FigRestriction(OWLObjectProperty op, Vector<Restriction> restric, int function) {
		super();
		property = op;
		restricciones = restric;
		colorRelleno = Color.getHSBColor(0.25f, 0.25f, 0.90f);
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return property.toString();
	}
	
	public FigRect getPuertoNorte(){
		return puertoNorte;
	}
	
	// agrega las sub-figuras a las figura
	// TODO: calcular de forma din�mica el tama�o de la caja, en funci�n de la longitud del
	// nombre de la clase
	protected void makeFigure(){
		
		alto = 30 * (restricciones.size() + 1);
		
		
		if (property == null)
			ancho = 80;
		else
			ancho = 8 * property.toString().length() + 30;
		
		x = -ancho / 2;
		y = -alto  / 2;
		
		// la caja
		box.setWidth(ancho);
		box.setHeight(alto);
		box.setX(x);
		box.setY(y);
		
		this.addFig(box);
		
		
		
		int i=35;
		int maxWidth = box.getWidth();
		FigText label;
		for (Restriction r:restricciones) {
			
			label = new FigText( x+10, y+5+i, 100, 50);
			label.setFilled(false);
			
			if (r.getRestrictionType() == Restriction.SOME_RESTRICTION)
				label.setText("someValuesFrom : " + r.getOWLClass().toString());
			else if (r.getRestrictionType() == Restriction.ALL_RESTRICTION)
				label.setText("onlyValuesFrom : " + r.getOWLClass().toString());
			else if (r.getRestrictionType() == Restriction.MAX_RESTRICTION)
				label.setText("MaxCardinality : " + r.getCardinality() + ", "  + r.getOWLClass().toString());
			else if (r.getRestrictionType() == Restriction.MIN_RESTRICTION)
				label.setText("MinCardinality : " + r.getCardinality() + ", " + r.getOWLClass().toString());
			else if (r.getRestrictionType() == Restriction.EXACT_RESTRICTION)
				label.setText("ExactCardinality : " + r.getCardinality() + ", " + r.getOWLClass().toString());
			else if (r.getRestrictionType() == Restriction.VALUE_RESTRICTION)
				label.setText("hasValue : " + r.getCardinality() + ", " + r.getOWLClass().toString());
			
			label.setLineWidth(0);
			label.setJustification(FigText.JUSTIFY_CENTER);
			label.setFillColor(colorRelleno);
			this.addFig(label);
			
			if (label.getWidth() > maxWidth )
				maxWidth = label.getWidth();
			
			i += 30;
		}
		
		className.setFilled(false);
		
		box.setSize(maxWidth+20, box.getHeight());
	
		separator = new FigLine(x,y+30, x+box.getWidth(), y+30, Color.black);
		this.addFig(separator);
		
		super.colocaPuertos();
		
		// el letrero
		className = new FigText( x+10, y+5, 100, 50);
		className.setLineWidth(0);
		className.setJustification(FigText.JUSTIFY_CENTER);
		className.setFillColor(colorRelleno);
		
		className.setText(property.toString());
		this.addFig(className);
	}

	public void setOwner(Object own) {
		super.setOwner(own);
		//if (!(own instanceof NodeClass)) return;
		NodeRestriction node = (NodeRestriction) own;
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

	/**
	 * Actualiza la figura
	 */
	public void actualizaFigura() {
		Point p = this.getLocation();
		this.removeAll();
		this.makeFigure();
		this.setLocation(p);
	}


	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		// TODO Auto-generated method stub
		return property;
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

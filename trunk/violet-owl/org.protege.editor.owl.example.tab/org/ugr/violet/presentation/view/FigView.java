/**
 * 
 */
package org.ugr.violet.presentation.view;

import java.awt.Color;
import java.util.Set;

import org.protege.owl.examples.tab.VioletEditor;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.ugr.violet.presentation.OWLFigure;

/**
 * @author anab
 *
 */
public class FigView extends OWLFigure {
	
	FigViewParameter params = null;
	OWLEntity entity = null;
	String label = null;
	
	public FigView(OWLEntity e){
		entity = e;
		
		this.makeFigure();
	}
	
	public FigView(OWLEntity e, FigViewParameter p){
		entity = e;
		params = p;
		
		
		this.makeFigure();
	}
	
	public FigView(OWLEntity e, FigViewParameter p, String l){
		entity = e;
		params = p;
		label = l;
		this.makeFigure();
	}
	

	/* (non-Javadoc)
	 * @see org.ugr.violet.presentation.OntologyFig#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		// TODO Auto-generated method stub
		return entity;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.presentation.OntologyFig#getPuertoNorte()
	 */
	@Override
	public FigRect getPuertoNorte() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.presentation.OntologyFig#makeFigure()
	 */
	@Override
	protected void makeFigure() {
		
		// Aplicamos los parámetros de la figura
		switch (params.shape){
		case FigViewParameter.RECTANGLE:
			box = new FigRect(x, y, ancho, alto, Color.black, colorRelleno);
			break;
		case FigViewParameter.RRECTANGLE:
			box = new FigRRect(x, y, ancho, alto, Color.black, colorRelleno);
			break;
		case FigViewParameter.DIAMOND:
			box = new FigDiamond(x, y, ancho, alto, Color.black, colorRelleno);
			break;
		case FigViewParameter.CIRCLE:
			box = new FigCircle(x, y, ancho, alto, Color.black, colorRelleno);
			break;
		}
		
		if (params.line){
			box.setLineWidth(params.linewidth);
			box.setLineColor(params.linecolor);
		}
		else box.setLineWidth(0);
		
		box.setFillColor(params.color);
		
		// el letrero
		FigText name = new FigText( x+10, y+5, 100, 50);
		name.setLineWidth(0);
		String label = ""; 
		
		label += label;
		name.setJustification(FigText.JUSTIFY_CENTER);
		name.setEditable(true);
		name.setText(label);
		name.setFilled(false);
		name.setTextColor(Color.white);
		box.setWidth(name.getWidth() + 20);
		
		

		//agregamos las figuras
		this.addFig(box);
		super.colocaPuertos();
		this.addFig(name);

	}

}

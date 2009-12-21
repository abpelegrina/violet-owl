package org.ugr.ontology.presentation;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JMenu;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.util.Localizer;
import org.ugr.ontology.graph.nodes.NodeClass;

/**
 * 
 * @author Ana B. Pelegrina
 */
public class FigClass extends OntologyFig {
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return claseOWL.toString();
	}

	private static final long serialVersionUID = 8151944926067219396L;
	private FigLine separator;
	private FigText className;
	private OWLClass claseOWL = null;

	public FigRect getPuertoNorte(){
		return puertoNorte;
	}

	/**
	 * 
	 * @param oc
	 */
	public FigClass(OWLClass oc) {
		this(oc, defaultFunction);
	}
	
	/**
	 * 
	 * @param oc
	 * @param function
	 */
	public FigClass(OWLClass oc, int function){
		super();
		claseOWL = oc;
		colorRelleno = Color.getHSBColor(0.1204f, 0.7941f, 0.8000f);
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

		Vector<OWLDataProperty> v = new Vector<OWLDataProperty>();

		for (OWLDataProperty odp : ExampleViewComponent.manager.getActiveOntology().getReferencedDataProperties()) {

			ExampleViewComponent.manager.getActiveOntology().getDataPropertyRangeAxiom(odp);

			if (odp.getDomains(ExampleViewComponent.manager.getActiveOntology()).contains(claseOWL)){
				v.add(odp);
			}
		}

		alto = 30 * (v.size() + 1);
		


		if (claseOWL == null)
			ancho = 80;
		else
			ancho = 8 * claseOWL.toString().length() + 30;

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
		for (OWLDataProperty r:v) {

			label = new FigText( x+10, y+5+i, 100, 50);			
			label.setText(r.getRanges(ExampleViewComponent.manager.getActiveOntology()) + " " + r);
			label.setFillColor(colorRelleno);
			label.setLineWidth(0);
			label.setJustification(FigText.JUSTIFY_CENTER);
			this.addFig(label);

			if (label.getWidth() > maxWidth )
				maxWidth = label.getWidth();			
			i += 30;
		}

		box.setFillColor(Color.WHITE);
		// el letrero
		className = new FigText( x+10, y+5, 100, 50);
		className.setLineWidth(0);
		className.setJustification(FigText.JUSTIFY_CENTER);
		className.setFilled(false);
		className.setText(claseOWL.toString());
		className.setFillColor(colorRelleno);

		if (v.size() > 0) {
			box.setSize(maxWidth+20, box.getHeight());
			separator = new FigLine(x,y+30, x+box.getWidth(), y+30, Color.black);
			this.addFig(separator);
		}
		else {
			box.setSize(className.getWidth()+20, box.getHeight());
		}

		super.colocaPuertos();
		this.addFig(className);
		box.setFillColor(colorRelleno);
		
	}

	public void setOwner(Object own) {
		super.setOwner(own);
		//if (!(own instanceof NodeClass)) return;
		NodeClass node = (NodeClass) own;
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
		return claseOWL;
	}

	@Override
	public boolean isFigClass(){
		return true;
	}

	@Override 
	public FigClass asFigClass(){
		return this;
	}
	
	@Override
	protected Vector<Object> getMenuPopUp(){
		Vector<Object> popUpActions = new Vector<Object>();
		
		JMenu changeFigure = new JMenu(Localizer.localize("PresentationGef","ChangeFigure"));

		changeFigure.add(CmdChangeFig.toRectangle);
		changeFigure.add(CmdChangeFig.toRRect);
		popUpActions.addElement(changeFigure);

		popUpActions.add(changeFigure);
		
		return popUpActions;
	}

}

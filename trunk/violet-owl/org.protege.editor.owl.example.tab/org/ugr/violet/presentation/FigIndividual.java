package org.ugr.violet.presentation;

import java.awt.Color;
import java.awt.Point;
import java.util.Set;
import java.util.Vector;

import javax.swing.JMenu;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.util.Localizer;
import org.ugr.violet.graph.nodes.NodeIndividual;

public class FigIndividual extends OntologyFig {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return individuo.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2815060650593788085L;
	private FigText nombreIndv;
	private OWLIndividual individuo = null;

	
	public FigIndividual(OWLIndividual oc){
		this(oc, defaultFunction);
	}
	
	/**
	 * 
	 * @param oc
	 */
	public FigIndividual(OWLIndividual oc, int function) {
		super();
		individuo = oc;
		alto = 30;
		ancho = 80;		
		x = -ancho / 2;
		y = -alto  / 2;
		
		colorRelleno = Color.getHSBColor(301.0169f/360, 0.7108f, 0.3255f); 
		
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
	
	public FigRect getPuertoNorte(){
		return puertoNorte;
	}
	
	// agrega las sub-figuras a las figura
	protected void makeFigure(){		
		// el letrero
		nombreIndv = new FigText( x+10, y+5, 100, 50);
		nombreIndv.setLineWidth(0);
		String label = ""; 
		Set<OWLDescription> clases = individuo.getTypes(ExampleViewComponent.manager.getActiveOntologies());
		for(OWLDescription d:clases) {
			label += " " + d.toString();
		}
		label += ": " + individuo.toString();
		nombreIndv.setJustification(FigText.JUSTIFY_CENTER);
		nombreIndv.setEditable(true);
		nombreIndv.setText(label);
		nombreIndv.setFilled(false);
		nombreIndv.setTextColor(Color.white);
		box.setWidth(nombreIndv.getWidth() + 20);

		//agregamos las figuras
		this.addFig(box);
		super.colocaPuertos();
		this.addFig(nombreIndv);

	}

	public void setOwner(Object own) {
		super.setOwner(own);
		//if (!(own instanceof NodeClass)) return;
		NodeIndividual node = (NodeIndividual) own;
		bindPort(node.north, puertoNorte);
		bindPort(node.south, puertoSur);
		bindPort(node.east, puertoEste);
		bindPort(node.west, puertoOeste);
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setIndivName (String name){

		nombreIndv.setText(name);
	}


	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		return individuo;
	}

	/**
	 * 
	 */
	public void update() {
		Point p = this.getLocation();
		this.removeAll();
		makeFigure();
		this.setLocation(p);
		this.redraw();
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

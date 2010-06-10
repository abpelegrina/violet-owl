/**
 * 
 */
package org.ugr.violet.presentation.activity;

import java.awt.Color;
import java.net.URI;
import java.util.Vector;

import javax.swing.JMenu;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLIndividual;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.util.Localizer;
import org.ugr.violet.graph.nodes.NodeClass;
import org.ugr.violet.graph.nodes.activity.NodeAction;
import org.protege.owl.examples.tab.ExampleViewComponent;
import org.ugr.violet.presentation.FigClass;

/**
 * @author anab
 *
 */
public class FigActivityStep extends FigActivityDiagram {
	
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
	private OWLIndividual actividad = null;
	private OWLIndividual step = null;
	private OWLIndividual role = null;

	public FigRect getPuertoNorte(){
		return puertoNorte;
	}

	/**
	 * 
	 * @param oc
	 */
	public FigActivityStep(OWLIndividual actividad, OWLIndividual paso, OWLIndividual r) {
		this(actividad, paso, r, defaultFunction);
	}
	
	/**
	 * 
	 * @param oc
	 * @param function
	 */
	public FigActivityStep(OWLIndividual oc, OWLIndividual paso, OWLIndividual r, int function){

		actividad = oc;
		step = paso;
		role = r;
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
		String roleLabel = "";
		
		if (role != null){
			roleLabel = "{"+ role +"}\n";
			box.setHeight(box.getHeight()+10);
		}
			
		className.setText(roleLabel + actividad.toString());
		className.setFillColor(colorRelleno);

		
		box.setSize(className.getWidth()+20, box.getHeight());

		super.colocaPuertos();
		this.addFig(className);
		box.setFillColor(colorRelleno);
		
	}

	public void setOwner(Object own) {
		super.setOwner(own);
		//if (!(own instanceof NodeClass)) return;
		NodeAction node = (NodeAction) own;
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
		return this;
	}
	
	
	@Override
	protected Vector<Object> getMenuPopUp(){
		Vector<Object> popUpActions = new Vector<Object>();		
		return popUpActions;
	}
}

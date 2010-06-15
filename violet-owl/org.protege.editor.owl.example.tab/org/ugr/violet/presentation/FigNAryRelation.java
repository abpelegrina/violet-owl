/**
 * 
 */
package org.ugr.violet.presentation;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JMenu;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.CmdChangeFig;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.util.Localizer;
import org.ugr.violet.graph.nodes.NodeClass;

/**
 * @author anab
 *
 */
public class FigNAryRelation extends OWLFigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2781483225342732142L;
	private OWLClass claseOWL = null;

	/**
	 * 
	 */
	public FigNAryRelation(OWLClass clase) {
		super();
		claseOWL = clase;
		colorRelleno = Color.getHSBColor(0.5746f, 0.7037f, 0.6353f);
		this.setResizable(false);
		box = new FigDiamond(x, y, ancho, alto, Color.black, colorRelleno);
		makeFigure();
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		// TODO Auto-generated method stub
		return claseOWL;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#getPuertoNorte()
	 */
	@Override
	public FigRect getPuertoNorte() {
		// TODO Auto-generated method stub
		return this.puertoNorte;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyFig#makeFigure()
	 */
	@Override
	protected void makeFigure() {
		alto = 15;
		ancho = 15;

		x = -ancho / 2;
		y = -alto  / 2;		
		// la caja

		box.setWidth(ancho);
		box.setHeight(alto);
		box.setX(x);
		box.setY(y);

		/*
		// el letrero
		className = new FigText( x+10, y+5, 1, 1);
		className.setLineWidth(0);
		className.setJustification(FigText.JUSTIFY_CENTER);
		className.setEnclosingFig(box);
		className.setFilled(false);
		className.setTextColor(Color.WHITE);
		className.setText(claseOWL.toString());
		
		box.setWidth(className.getWidth()+20);*/

		// los puertos de conexion
		this.addFig(box);
		super.colocaPuertos();
		//this.addFig(className);
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
	
	// genera el menu contextual que incluye la transformacion de las figuras
	@Override
	public Vector<Object> getMenuPopUp( ) {
		Vector<Object> popUpActions = new Vector<Object>();
		
		JMenu changeFigure = new JMenu(Localizer.localize("PresentationGef","ChangeFigure"));

		changeFigure.add(CmdChangeFig.toCircle);
		changeFigure.add(CmdChangeFig.toDiamond);
		popUpActions.addElement(changeFigure);

		popUpActions.add(changeFigure);
		
		return popUpActions;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {		
		return claseOWL.toString();
	}

}

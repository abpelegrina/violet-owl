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
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.ugr.violet.graph.nodes.NodeUnion;
import org.ugr.violet.graph.nodes.activity.NodeFork;
import org.ugr.violet.presentation.OWLFigure;

/**
 * @author anab
 *
 */
public class FigFork extends FigActivityDiagram {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5520112571448548195L;
	private OWLIndividual step;
	
	public FigFork(OWLIndividual step){
		super();
		 
		this.step = step;
		
		box = new FigRect(0, 0, 90, 12, Color.black, Color.BLACK);		
		this.makeFigure();
	}
	

	@Override
	public void setOwner(Object own) {
		super.setOwner(own);
		NodeFork node = (NodeFork) own;
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

	/* (non-Javadoc)
	 * @see org.ugr.violet.presentation.OntologyFig#makeFigure()
	 */
	@Override
	protected void makeFigure() {
		this.addFig(box);
		colocaPuertos();
	}

}

/**
 * 
 */
package org.ugr.violet.presentation.activity;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.presentation.FigRect;
import org.ugr.violet.presentation.OWLFigure;

/**
 * @author anab
 *
 */
public class FigActivityDiagram extends OWLFigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6382632212036915734L;

	/**
	 * 
	 */
	public FigActivityDiagram() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.presentation.OntologyFig#getOWLEntity()
	 */
	@Override
	public OWLEntity getOWLEntity() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}
	
	public boolean isFigActivityStep(){
		return false;
	}
	
	public FigActivityStep asFigActivityStep(){
		return null;
	}

}

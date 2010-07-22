/**
 * 
 */
package org.ugr.violet.view.graph.presentation;

import org.semanticweb.owl.model.OWLOntology;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.presentation.JOWLGraph;
import org.ugr.violet.ui.OWLPalette;
import org.ugr.violet.view.graph.OWLViewGraphModel;


/**
 * @author anab
 *
 */
public class JOWLViewGraph extends JOWLGraph{

	/**
	 * @param ont
	 * @param p
	 */
	public JOWLViewGraph(OWLPalette p) {
		super(p);
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.presentation.JOntologyGraph#generateGraphModel()
	 */
	@Override
	protected OWLGraphModel generateGraphModel() {
		// TODO Auto-generated method stub
		return new OWLViewGraphModel(null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1234337108223019086L;

}

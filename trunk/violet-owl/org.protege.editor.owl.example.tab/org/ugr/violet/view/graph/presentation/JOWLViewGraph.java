/**
 * 
 */
package org.ugr.violet.view.graph.presentation;

import org.semanticweb.owl.model.OWLOntology;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.presentation.JOWLGraph;
import org.ugr.violet.ui.OntologyPalette;
import org.ugr.violet.view.graph.OWLViewGraphModel;

import sun.tools.tree.ThisExpression;

/**
 * @author anab
 *
 */
public class JOWLViewGraph extends JOWLGraph{

	/**
	 * @param ont
	 * @param p
	 */
	public JOWLViewGraph(OWLOntology ont, OntologyPalette p) {
		super(ont, p);
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.presentation.JOntologyGraph#generateGraphModel()
	 */
	@Override
	protected OWLGraphModel generateGraphModel() {
		// TODO Auto-generated method stub
		return new OWLViewGraphModel(activa);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1234337108223019086L;

}

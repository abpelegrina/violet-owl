/**
 * 
 */
package org.ugr.violet.view.graph.presentation;

import org.semanticweb.owl.model.OWLOntology;
import org.ugr.violet.graph.OntologyGraphModel;
import org.ugr.violet.graph.presentation.JOntologyGraph;
import org.ugr.violet.ui.OntologyPalette;
import org.ugr.violet.view.graph.OntologyViewGraphModel;

import sun.tools.tree.ThisExpression;

/**
 * @author anab
 *
 */
public class JOntologyViewGraph extends JOntologyGraph{

	/**
	 * @param ont
	 * @param p
	 */
	public JOntologyViewGraph(OWLOntology ont, OntologyPalette p) {
		super(ont, p);
	}

	/* (non-Javadoc)
	 * @see org.ugr.violet.graph.presentation.JOntologyGraph#generateGraphModel()
	 */
	@Override
	protected OntologyGraphModel generateGraphModel() {
		// TODO Auto-generated method stub
		return new OntologyViewGraphModel(activa);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1234337108223019086L;

}

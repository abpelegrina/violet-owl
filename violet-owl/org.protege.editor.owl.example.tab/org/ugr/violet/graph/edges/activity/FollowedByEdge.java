/**
 * 
 */
package org.ugr.violet.graph.edges.activity;

import org.semanticweb.owl.model.OWLIndividual;
import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.ArrowHeadGreater;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigEdgePoly;
import org.ugr.violet.graph.edges.DisjointEdge;
import org.ugr.violet.graph.edges.EquivalentEdge;
import org.ugr.violet.graph.edges.IntersectionEdge;
import org.ugr.violet.graph.edges.InverseOfEdge;
import org.ugr.violet.graph.edges.OWLEdge;
import org.ugr.violet.graph.edges.RangeEdge;
import org.ugr.violet.graph.edges.RestrictionEdge;
import org.ugr.violet.graph.edges.SuperEdge;
import org.ugr.violet.graph.edges.UnionEdge;

/**
 * @author anab
 *
 */
public class FollowedByEdge extends OWLEdge {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5811090848351084320L;
	private FigEdgePoly enlace = new FigEdgePoly();
	private String label = "";
	
	private OWLIndividual domain = null;
	private OWLIndividual range = null;
	
	public FollowedByEdge(OWLIndividual p, OWLIndividual pp){
		super();
		domain = p;
		range = pp;
	}
	
	
	
	@Override
	public FigEdge makePresentation(Layer lay) {
	    enlace.setDashed(false);
	    
	    ArrowHeadGreater flechaDestino = new ArrowHeadGreater();
	    flechaDestino.setWidth(5);
	    
	    enlace.setDestArrowHead(flechaDestino);
	    enlace.setBetweenNearestPoints(true);
	    enlace.setLayer(lay);
		return enlace;
	}
	
	/**
	 * 
	 */
	public FigEdgePoly getFigEdge(){
		return enlace;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return domain.toString()+" followed_by "+ range.toString();
	}

	/**
	 * Cambia el texto del enlace
	 * @param aLabel
	 */
	public void setLabel(String aLabel){
		label = "<<" + aLabel + ">>";
	}
	
	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asRestrictionEdge()
	 */
	@Override
	public RestrictionEdge asRestrictionEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asSuperEdge()
	 */
	@Override
	public SuperEdge asSuperEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isRestrictionEdge()
	 */
	@Override
	public boolean isRestrictionEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isSuperEdge()
	 */
	@Override
	public boolean isSuperEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.NetPrimitive#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.layout.LayoutedEdge#layout()
	 */
	public void layout() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asDisjointEdge()
	 */
	@Override
	public DisjointEdge asDisjointEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isDisjointEdge()
	 */
	@Override
	public boolean isDisjointEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asEquivalentEdge()
	 */
	@Override
	public EquivalentEdge asEquivalentEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isEquivalentEdge()
	 */
	@Override
	public boolean isEquivalentEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asRangeEdge()
	 */
	@Override
	public RangeEdge asRangeEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isRangeEdge()
	 */
	@Override
	public boolean isRangeEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asInverseOfEdge()
	 */
	@Override
	public InverseOfEdge asInverseOfEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isInverseOfEdge()
	 */
	@Override
	public boolean isInverseOfEdge() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asUnionEdge()
	 */
	@Override
	public UnionEdge asUnionEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isUnionEdge()
	 */
	@Override
	public boolean isUnionEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.graph.edges.OntologyEdge#asIntersectionEdge()
	 */
	@Override
	public IntersectionEdge asIntersectionEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.graph.edges.OntologyEdge#isIntersectionEdge()
	 */
	@Override
	public boolean isIntersectionEdge() {
		// TODO Auto-generated method stub
		return false;
	}


}

/**
 * 
 */
package org.ugr.violet.graph.edges;

import java.awt.Color;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigEdgePoly;

/**
 * @author anab
 *
 */
public class EquivalentEdge extends OWLEdge {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3887336665856030517L;
	
	private FigEdgePoly enlace = new FigEdgePoly();
	private OWLEntity c1 = null;
	private OWLEntity c2 = null;
	
	/**
	 * Crea un nuevo enlace entre de equivalencia entre dos entidades OWL
	 * @param C1
	 * @param C2
	 */
	public EquivalentEdge(OWLEntity C1, OWLEntity C2){
		super();
		c1 = C1;
		c2 = C2;
	}
	
	public boolean checkLink(OWLEntity ent){
		if (ent.toString().equals(c1.toString()) )
			return true;
		else if (ent.toString().endsWith(c2.toString()))
			return true;
		else return false;
	}
	
	/* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.NetEdge#makePresentation(org.tigris.gef.base.Layer)
	 */
	@Override
	public FigEdge makePresentation(Layer lay) {
		 ArrowHeadTriangle at = new ArrowHeadTriangle();
		 at.setFillColor(Color.WHITE);
		 at.setHeight(18);
		 at.setWidth(10);
		 ArrowHeadTriangle at2 = new ArrowHeadTriangle();
		 at2.setFillColor(Color.WHITE);
		 at2.setHeight(18);
		 at2.setWidth(10);
		 enlace.setDestArrowHead(at);
		 enlace.setSourceArrowHead(at2);
		 enlace.setBetweenNearestPoints(true);
		 enlace.setLayer(lay);
		 return enlace;
	}
	
	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#getFigEdge()
	 */
	@Override
	public FigEdgePoly getFigEdge() {
		return enlace;
	}
	
	/**
	 * @return the c1
	 */
	public OWLEntity getC1() {
		return c1;
	}

	/**
	 * @param c1 the c1 to set
	 */
	public void setC1(OWLEntity c1) {
		this.c1 = c1;
	}

	/**
	 * @return the c2
	 */
	public OWLEntity getC2() {
		return c2;
	}

	/**
	 * @param c2 the c2 to set
	 */
	public void setC2(OWLEntity c2) {
		this.c2 = c2;
	}

	/* (non-Javadoc)
	 * @see org.tigris.gef.graph.presentation.NetPrimitive#getId()
	 */
	@Override
	public String getId() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.layout.LayoutedEdge#layout()
	 */
	public void layout() {

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
	 * @see org.ugr.ontology.presentation.OntologyEdge#asEquivalentEdge()
	 */
	@Override
	public EquivalentEdge asEquivalentEdge() {
		// TODO Auto-generated method stub
		return this;
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
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isDisjointEdge()
	 */
	@Override
	public boolean isDisjointEdge() {

		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isEquivalentEdge()
	 */
	@Override
	public boolean isEquivalentEdge() {
		return true;
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
		return false;
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

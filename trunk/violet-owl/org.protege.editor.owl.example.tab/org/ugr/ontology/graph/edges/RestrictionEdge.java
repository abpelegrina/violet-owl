package org.ugr.ontology.graph.edges;

import java.awt.Color;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadGreater;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigEdgePoly;
import org.tigris.gef.presentation.FigText;

public class RestrictionEdge extends OntologyEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1900975110170754458L;
	
	private FigEdgePoly enlace = new FigEdgePoly();
	private FigText mid = new FigText(10, 30, 90, 20);
	private String label = "";
	private OWLEntity objeto = null;
	private OWLEntity sujeto = null;
	
	public RestrictionEdge(OWLEntity sujeto, OWLEntity objeto){
		this.objeto = objeto;
		this.sujeto = sujeto;
	}
	
	public boolean isSujeto(OWLEntity entidad){
		if (entidad.toString().equals(sujeto.toString()))
			return true;
		else
			return false;
	}
	
	public boolean isObjeto(OWLEntity entidad){
		if (entidad.toString().equals(objeto.toString()))
			return true;
		else
			return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mid.getText();
	}

	public void setLabel(String aLabel){
		label = aLabel;
	}

	@Override
	public FigEdge makePresentation(Layer lay) {
		
	    mid.setText(label);
	    mid.setTextColor(Color.black);
	    mid.setTextFilled(false);
	    mid.setFilled(false);
	    mid.setLineWidth(0);
	    enlace.addPathItem(mid, new PathConvPercent(enlace, 50, 10));
	    ArrowHeadGreater at = new ArrowHeadGreater();
	    at.setFillColor(Color.WHITE);
	    at.setWidth(5);
	    enlace.setDestArrowHead(at);
	    enlace.setBetweenNearestPoints(true);
	    enlace.setLayer(lay);
		return enlace;
	}

	@Override
	public String getId() {
		return mid.getText();
	}

	public void layout() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 
	 */
	public FigEdgePoly getFigEdge(){
		return enlace;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asRestrictionEdge()
	 */
	@Override
	public RestrictionEdge asRestrictionEdge() {
		return this;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asSuperEdge()
	 */
	@Override
	public SuperEdge asSuperEdge() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isRestrictionEdge()
	 */
	@Override
	public boolean isRestrictionEdge() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isSuperEdge()
	 */
	@Override
	public boolean isSuperEdge() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#asDisjointEdge()
	 */
	@Override
	public DisjointEdge asDisjointEdge() {
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


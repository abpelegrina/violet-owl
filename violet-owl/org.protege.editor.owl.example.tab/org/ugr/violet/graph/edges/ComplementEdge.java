/**
 * 
 */
package org.ugr.violet.graph.edges;

import java.awt.Color;

import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLObjectComplementOf;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadGreater;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigEdgePoly;
import org.tigris.gef.presentation.FigText;

/**
 * @author anab
 *
 */
public class ComplementEdge extends OWLEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5811090848351084320L;
	private FigEdgePoly enlace = new FigEdgePoly();
	private FigText mid = new FigText(0, 0, 90, 20);
	private FigCircle circulo = new FigCircle(0, 0, 20, 20);
	private OWLObjectComplementOf interseccion = null;
	private OWLDescription clase = null;
	
	public ComplementEdge(OWLDescription c, OWLObjectComplementOf union){
		super();
		clase = c;
		this.interseccion = union;
	}
	
	public boolean checkLink(OWLDescription ent){
		if (ent.toString().equals(clase.toString()) )
			return true;
		else return false;
	}
	
	public boolean checkLink(OWLObjectComplementOf ent){
	
		if (ent.toString().equals(interseccion.toString()) )
			return true;
		else return false;
	}
	
	@Override
	public FigEdge makePresentation(Layer lay) {
	    mid.setText("¬");
	    mid.setTextColor(Color.white);
	    mid.setTextFilled(false);
	    mid.setFilled(false);
	    mid.setFontSize(16);
	    mid.setLineWidth(0);
	    circulo.setFillColor(Color.getHSBColor(0.1204f, 0.7941f, 0.8000f));
	    circulo.setLineColor(Color.white);
	    enlace.addPathItem(circulo, new PathConvPercent(enlace, 50, 0));
	    enlace.addPathItem(mid, new PathConvPercent(enlace, 50, 0));
	    
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
		return mid.getText();
	}

	/**
	 * Cambia el texto del enlace
	 * @param aLabel
	 */
	public void setLabel(String aLabel){
	}

	@Override
	public RestrictionEdge asRestrictionEdge() {
		return null;
	}

	@Override
	public SuperEdge asSuperEdge() {
		return null;
	}
	
	@Override
	public boolean isRestrictionEdge() {
		return false;
	}

	@Override
	public boolean isSuperEdge() {
		return false;
	}

	@Override
	public String getId() {
		return null;
	}

	public void layout() {
		
	}
	
	@Override
	public DisjointEdge asDisjointEdge() {
		return null;
	}

	@Override
	public boolean isDisjointEdge() {
		return false;
	}

	@Override
	public EquivalentEdge asEquivalentEdge() {
		return null;
	}

	@Override
	public boolean isEquivalentEdge() {
		return false;
	}

	@Override
	public RangeEdge asRangeEdge() {
		return null;
	}

	@Override
	public boolean isRangeEdge() {
		return false;
	}

	@Override
	public InverseOfEdge asInverseOfEdge() {
		return null;
	}

	@Override
	public boolean isInverseOfEdge() {
		return false;
	}
	
	@Override
	public UnionEdge asUnionEdge() {
		return null;
	}

	@Override
	public boolean isUnionEdge() {
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
	
	@Override
	public boolean isComplementEdge(){
		return true;
	}
	
	@Override 
	public ComplementEdge asComplementEdge(){
		return this;
	}

}

/**
 * 
 */
package org.ugr.ontology.graph.edges;

import java.awt.Color;

import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLObjectUnionOf;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadGreater;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigEdgePoly;
import org.tigris.gef.presentation.FigText;

/**
 * @author anab
 *
 */
public class UnionEdge extends OntologyEdge {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5811090848351084320L;
	private FigEdgePoly enlace = new FigEdgePoly();
	private FigText mid = new FigText(10, 30, 90, 20);
	private String label = "";
	
	private OWLObjectUnionOf union = null;
	private OWLDescription clase = null;
	
	public UnionEdge(OWLDescription c, OWLObjectUnionOf union){
		super();
		clase = c;
		this.union = union;
	}
	
	public boolean checkLink(OWLDescription ent){
		
		if (ent.toString().equals(clase.toString()) )
			return true;
		else return false;
	}
	
	public boolean checkLink(OWLObjectUnionOf ent){
	
		if (ent.equals(union) )
			return true;
		else return false;
	}
	
	@Override
	public FigEdge makePresentation(Layer lay) {
		
	    mid.setText(label);
	    mid.setTextColor(Color.black);
	    mid.setTextFilled(false);
	    mid.setFilled(false);
	    mid.setLineWidth(0);
	    enlace.addPathItem(mid, new PathConvPercent(enlace, 50, 10));
	    
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
		label = "<<" + aLabel + ">>";
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
		return this;
	}

	@Override
	public boolean isUnionEdge() {
		return true;
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

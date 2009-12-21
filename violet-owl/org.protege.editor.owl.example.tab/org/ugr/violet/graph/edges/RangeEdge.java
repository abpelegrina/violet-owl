/**
 * 
 */
package org.ugr.violet.graph.edges;

import java.awt.Color;

import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLObjectProperty;
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
public class RangeEdge extends OntologyEdge {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5811090848351084320L;
	private FigEdgePoly enlace = new FigEdgePoly();
	private FigText mid = new FigText(10, 30, 90, 20);
	private String label = "";
	
	private OWLEntity propiedad = null;
	private OWLEntity rango = null;
	
	public RangeEdge(OWLEntity propiedad, OWLEntity c){
		super();
		this.propiedad=propiedad;
		rango = c;
	}
	
	public boolean checkLink(OWLEntity ent){
		if (!ent.isOWLDataProperty() && !ent.isOWLDataProperty()) return false;
		if (ent.toString().equals(propiedad.toString()) )
			return true;
		else if (ent.toString().equals(rango.toString()) )
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
	    enlace.setDashed(true);
	    
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
		return this;
	}

	/* (non-Javadoc)
	 * @see org.ugr.ontology.presentation.OntologyEdge#isRangeEdge()
	 */
	@Override
	public boolean isRangeEdge() {
		// TODO Auto-generated method stub
		return true;
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

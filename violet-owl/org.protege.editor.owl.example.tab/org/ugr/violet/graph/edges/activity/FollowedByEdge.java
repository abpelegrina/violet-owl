/**
 * 
 */
package org.ugr.violet.graph.edges.activity;

import java.awt.Color;
import java.awt.Point;

import org.protege.owl.examples.tab.VioletEditor;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadGreater;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigEdgePoly;
import org.tigris.gef.presentation.FigText;
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
	protected FigText mid = new FigText(10, 30, 90, 20);
	private String label = "";
	
	private OWLIndividual domain = null;
	private OWLIndividual range = null;
	private OWLIndividual relation = null;
	private OWLIndividual guard = null;
	private String guard_label = null;
	
	public FollowedByEdge(OWLIndividual source, OWLIndividual target, OWLIndividual rel,  String guard_expression){
		super();
		domain = source;
		range = target;
		relation = rel;
		guard_label = guard_expression;		
	}
	
	
	
	@Override
	public FigEdge makePresentation(Layer lay) {
	    enlace.setDashed(false);
	    
	    ArrowHeadGreater flechaDestino = new ArrowHeadGreater();
	    flechaDestino.setWidth(5);
	    
	    if (guard_label != null && guard_label != "") {
		    mid.setText(label);
		    mid.setTextColor(Color.black);
		    mid.setTextFilled(false);
		    mid.setFilled(false);
		    mid.setText("[ " + guard_label + " ]");
		    mid.setLineWidth(0);
		    enlace.addPathItem(mid, new PathConvPercent(enlace, 50, 10));
	    }
	    
	    enlace.setDestArrowHead(flechaDestino);
	    enlace.setBetweenNearestPoints(true);
	    enlace.setLayer(lay);
	    
	    
	    
		return enlace;
	}
	
	public void update(OWLIndividual g) {
		this.guard = g;
		enlace.removePathItem(mid);
		
		if (guard != null){
			mid.setText(label);
		    mid.setTextColor(Color.black);
		    mid.setTextFilled(false);
		    mid.setFilled(false);
		    mid.setText("[ " + guard.toString() + " ]");
		    mid.setLineWidth(0);
		    enlace.addPathItem(mid, new PathConvPercent(enlace, 50, 10));
		}
		
	
		enlace.redraw();
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
		return relation.toString();
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

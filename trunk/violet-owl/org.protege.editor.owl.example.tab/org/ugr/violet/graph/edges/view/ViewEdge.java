/**
 * 
 */
package org.ugr.violet.graph.edges.view;

import java.awt.Color;

import org.semanticweb.owl.model.OWLEntity;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadGreater;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigDiamond;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigEdgePoly;
import org.tigris.gef.presentation.FigRRect;
import org.tigris.gef.presentation.FigRect;
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
import org.ugr.violet.presentation.view.FigViewParameter;

/**
 * @author anab
 * 
 */
public class ViewEdge extends OWLEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 958536637847702786L;

	private FigEdgePoly enlace = new FigEdgePoly();
	private FigText mid = new FigText(10, 30, 90, 20);
	protected FigViewParameter params = null;

	String label = "";

	private OWLEntity entity = null;
	private OWLEntity entity_aux = null;

	private OWLEntity source = null;
	private OWLEntity target = null;

	public ViewEdge(OWLEntity ent, FigViewParameter p) {
		super();
		params = p;
		entity = ent;

		label = entity.toString();
	}

	public ViewEdge(OWLEntity ent, FigViewParameter p, OWLEntity trg,
			OWLEntity src) {
		this(ent, p);
		target = trg;
		source = src;
	}

	public ViewEdge(OWLEntity ent, FigViewParameter p, OWLEntity trg,
			OWLEntity src, OWLEntity label) {
		this(ent, p, trg, src);
		entity_aux = label;
		this.label = entity_aux.toString();
	}

	@Override
	public FigEdge makePresentation(Layer lay) {

		// Aplicamos los parámetros de la figura

		ArrowHeadGreater flechaDestino = new ArrowHeadGreater();
		flechaDestino.setWidth(5);
		ArrowHeadGreater flechaOrigen = new ArrowHeadGreater();
		flechaOrigen.setWidth(5);

		switch (params.getShape()) {
			case FigViewParameter.ARROW:
				enlace.setDestArrowHead(flechaDestino);
				break;
			case FigViewParameter.DARROW:
				enlace.setSourceArrowHead(flechaOrigen);
				break;
		}

		mid.setText(label);
		mid.setTextColor(params.getLinecolor());
		mid.setTextFilled(false);
		mid.setFilled(false);
		mid.setLineWidth(0);
		enlace.addPathItem(mid, new PathConvPercent(enlace, 50, 10));
		enlace.setLineWidth(params.getLinewidth());
		enlace.setLineColor(params.getLinecolor());

		enlace.setBetweenNearestPoints(true);
		enlace.setLayer(lay);
		return enlace;
	}

	/**
	 * 
	 */
	public FigEdgePoly getFigEdge() {
		return enlace;
	}

	public boolean checkLink(OWLEntity ent) {
		if (ent.toString().equals(source.toString()))
			return true;
		else if (ent.toString().endsWith(target.toString()))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return mid.getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#asDisjointEdge()
	 */
	@Override
	public DisjointEdge asDisjointEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#asEquivalentEdge()
	 */
	@Override
	public EquivalentEdge asEquivalentEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#asIntersectionEdge()
	 */
	@Override
	public IntersectionEdge asIntersectionEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#asInverseOfEdge()
	 */
	@Override
	public InverseOfEdge asInverseOfEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#asRangeEdge()
	 */
	@Override
	public RangeEdge asRangeEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#asRestrictionEdge()
	 */
	@Override
	public RestrictionEdge asRestrictionEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#asSuperEdge()
	 */
	@Override
	public SuperEdge asSuperEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#asUnionEdge()
	 */
	@Override
	public UnionEdge asUnionEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#isDisjointEdge()
	 */
	@Override
	public boolean isDisjointEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#isEquivalentEdge()
	 */
	@Override
	public boolean isEquivalentEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#isIntersectionEdge()
	 */
	@Override
	public boolean isIntersectionEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#isInverseOfEdge()
	 */
	@Override
	public boolean isInverseOfEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#isRangeEdge()
	 */
	@Override
	public boolean isRangeEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#isRestrictionEdge()
	 */
	@Override
	public boolean isRestrictionEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#isSuperEdge()
	 */
	@Override
	public boolean isSuperEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.graph.edges.OWLEdge#isUnionEdge()
	 */
	@Override
	public boolean isUnionEdge() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tigris.gef.graph.presentation.NetPrimitive#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ugr.violet.layout.LayoutedEdge#layout()
	 */
	public void layout() {
		// TODO Auto-generated method stub

	}

}

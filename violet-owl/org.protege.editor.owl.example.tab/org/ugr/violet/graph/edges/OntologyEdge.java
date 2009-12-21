package org.ugr.ontology.graph.edges;

import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.presentation.FigEdgePoly;
import org.ugr.ontology.layout.LayoutedEdge;


public abstract class OntologyEdge extends NetEdge implements LayoutedEdge{

	/**
	 * 
	 */
	private static final long serialVersionUID = 259461833985418661L;
	
	abstract public FigEdgePoly getFigEdge();
	
	/**
	 * Comprueba si se trata de una arista entre clase y super-clase
	 * @return 
	 */
	public abstract boolean isSuperEdge();
	
	/**
	 * Comprueba si se trata de una arista de restriccion entre una clase y otra
	 * @return
	 */
	public abstract boolean isRestrictionEdge();
	
	/**
	 * Devuelve la arista como una arista tipo SuperEdge
	 * @return 
	 */
	public abstract SuperEdge asSuperEdge();
	
	public abstract RestrictionEdge asRestrictionEdge();
	
	public abstract DisjointEdge asDisjointEdge();
	
	public abstract boolean isDisjointEdge();
	
	public abstract boolean isEquivalentEdge();
	
	public abstract EquivalentEdge asEquivalentEdge();
	
	public abstract boolean isRangeEdge();
	
	public abstract RangeEdge asRangeEdge();
	
	public abstract InverseOfEdge asInverseOfEdge();
	
	public abstract boolean isInverseOfEdge();
	
	public abstract boolean isUnionEdge();
	
	public abstract UnionEdge asUnionEdge();
	
	public abstract boolean isIntersectionEdge();
	
	public abstract IntersectionEdge asIntersectionEdge();
	
	public boolean isComplementEdge(){
		return false;
	}

	public ComplementEdge asComplementEdge(){
		return null;
	}
	
	public OneOfEdge asOneOfEdge(){
		return null;
	}
	
	public boolean isOneOfEdge(){
		
		return false;
	}
	
	public boolean isDomainRangeEdge(){
		return false;
	}
	
	public DomainRangeEdge asDomainRangeEdge(){
		return null;
	}
}

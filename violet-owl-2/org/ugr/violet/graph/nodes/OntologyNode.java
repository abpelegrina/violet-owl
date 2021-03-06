package org.ugr.violet.graph.nodes;

import java.util.Hashtable;

import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.NetNode;
import org.ugr.violet.presentation.OntologyFig;

public abstract class OntologyNode extends NetNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5005676849252957456L;
	/**
	 * Ports to connect the node  with others class nodes
	 */
	public OntologyPort north, east, west, south;
	
	@Override
	public void initialize(Hashtable args) {
		// TODO Auto-generated method stub

	}

	@Override
	public OntologyFig makePresentation(Layer lay) {
		return null;
	}
	
	abstract public OntologyFig getOntologyFig();
	
	/**
	 * Comprueba si se trata de un nodo clase
	 * @return
	 */
	abstract public boolean isNodeClass();
	
	/**
	 * Comprueba si se trata de un nodo restriccion
	 * @return
	 */
	abstract public boolean isNodeRestriction();
	
	/**
	 * Comprueba si se trata de un nodo individuo
	 * @return
	 */
	abstract public boolean isNodeIndividual();
	
	/**
	 * Devuelve el nodo como un nodo clase, si es posible
	 * @return La nodo, null si no es posible
	 */
	abstract public NodeClass asNodeClass();
	
	/**
	 * Devuelve el nodo como un nodo restriccion, si es posible
	 * @return EL nodo, null si no es posible
	 */
	abstract public NodeRestriction asNodeRestriction();
	
	/**
	 * Devuelve el nodo como un nodo individuo, si es posible
	 * @return El nodo, null si no es posible
	 */
	abstract public NodeIndividual asNodeIndividual();

	/**
	 * Devuelve el nodo como un nodo individuo, si es posible
	 * @return El nodo, null si no es posible
	 */
	abstract public NodeObjectProperty asNodeObjectProperty();
	
	/**
	 * Devuelve el nodo como un nodo individuo, si es posible
	 * @return El nodo, null si no es posible
	 */
	abstract public boolean isNodeObjectProperty();
	
	abstract public boolean isNodeUnion();
	
	abstract public NodeUnion asNodeUnion();
	
	abstract public boolean isNodeIntersection();
	
	abstract public NodeIntersection asNodeIntersection();
	
	public boolean isNodeComplement(){
		return false;
	}
	
	public NodeComplement asNodeComplement(){
		return null;
	}
	
	public NodeOneOf asNodeOneOf(){
		return null;
	}
	public boolean isNodeOneOf(){
		return false;
	}
	
	public NodeDataProperty asDataProperty(){
		return null;
	}
	public boolean isDataProperty(){
		return false;
	}
	
	public boolean isClassRelataion(){
		return false;
	}
	
	public NodeNAryRelation asNodeClassRelation(){
		return null;
	}
}

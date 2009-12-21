package org.ugr.ontology.base;

import org.semanticweb.owl.model.OWLClass;

/**
 * Clase de conveniencia que representa un determinado tipo de restricci�n en la que se referencia a una clase OWL
 * @author anab
 *
 */
public class Restriction{

	/* Tipos de restricciones*/
	/**Restriccion existencial*/
	public static final int SOME_RESTRICTION = 1;
	/**Restriccion universal*/
	public static final int ALL_RESTRICTION = 2;
	/**Restriccion de minima cardinalidad*/
	public static final int MIN_RESTRICTION = 3;
	/**Restriccion de maxima cardinalidad*/
	public static final int MAX_RESTRICTION = 4;
	/**Restriccion de cardinalidad exacta*/
	public static final int EXACT_RESTRICTION = 5;
	/**Restriccion de valor*/
	public static final int VALUE_RESTRICTION = 6;

	/** Entero que codifica el tipo de restricci�n */
	private int restrictionType;

	/** Clase sobre la que se aplica la restricci�n	 */
	private OWLClass clase;

	/** Cardinalidad (si es que es aplicable de la restriccion) */
	int cardinalidad = -1;
	
	/**
	 * Constructor
	 * @param exp tipo de restriccion
	 * @param clase clase incluida en la restriccion
	 */
	public Restriction(int exp, OWLClass clase) {
		super();
		this.restrictionType = exp;
		this.clase = clase;
	}

	/**
	 * Constructor
	 * @param exp tipo de restriccion
	 * @param clase clase referenciada en la restriccion
	 * @param c cardinalidad
	 */
	public Restriction(int exp, OWLClass clase, int c) {
		super();
		this.restrictionType = exp;
		this.clase = clase;
		this.cardinalidad = c;
	}


	/**
	 * Recupera la cardinalidad de la relaci�n. 
	 * @return the cardinalidad; si no es aplicable devuelve -1
	 */
	public int getCardinalidad() {
		int c = this.cardinalidad;
		if (!admiteCardinalidad())
			c = -1;
		
		return c;
	}

	/**
	 * Comprueba si la propiedad admite el uso de cardinalidad
	 * @return
	 */
	private boolean admiteCardinalidad(){
		boolean res = false;
		if (this.restrictionType == MIN_RESTRICTION || this.restrictionType == MAX_RESTRICTION 
				|| this.restrictionType == EXACT_RESTRICTION)
			res = true;
		
		return res;
	}
	
	/**
	 * Recupera el tipo de restriccion
	 * @return the exp
	 */
	public int getRestrictionType() {
		return restrictionType;
	}

	/**
	 * Recupera la clase referenciada en la restriccion
	 * @return the clase
	 */
	public OWLClass getClase() {
		return clase;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
	    if (obj == null)
	    	return false;
	    if (getClass() != obj.getClass())
	    	return false;
	    final Restriction other = (Restriction) obj;
	    
	    if (other.restrictionType != restrictionType)
	    	return false;
	    if (!other.clase.toString().equals(clase.toString()))
	    	return false;
	    if (cardinalidad != other.cardinalidad)
	    	return false;
	    else
	    	return true;
	}
}
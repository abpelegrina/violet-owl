/**
 * 
 */
package org.ugr.violet.graph.edges;

import java.awt.Color;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadGreater;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigEdgePoly;
import org.tigris.gef.presentation.FigText;
import org.ugr.violet.graph.nodes.NodeNAryRelation;
import org.protege.owl.examples.tab.ExampleViewComponent;

/**
 * @author anab
 *
 */
public class DomainRangeEdge extends OWLEdge {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5811090848351084320L;
	protected FigEdgePoly enlace = new FigEdgePoly();
	protected FigText mid = new FigText(10, 30, 90, 20);
	protected String label = "";
	
	
	protected OWLClass rango = null;
	protected OWLClass dominio = null;
	
	protected OWLObjectProperty propiedad = null;
	
	public DomainRangeEdge(OWLClass  dom, OWLClass ran, OWLObjectProperty prop){
		super();
		rango = ran;
		dominio = dom;
		propiedad = prop;
	}
	
	/* * GETTERS Y SETTERS * */
	
	public boolean isRange(OWLClass aClass){
		return aClass.equals(rango);
	}
	
	public boolean isDomain(OWLClass aClass){
		return aClass.equals(dominio);
	}
	
	public boolean isProperty(OWLObjectProperty aProperty){
		return aProperty.equals(propiedad);
	}
	
	public OWLClass getRange(){
		return rango;
	}
	
	public OWLClass getDomain(){
		return dominio;
	}
	
	public OWLObjectProperty getProperty(){
		return propiedad;
	}
	
	@Override
	public FigEdge makePresentation(Layer lay) {
		
	    mid.setText(label);
	    mid.setTextColor(Color.black);
	    mid.setTextFilled(false);
	    mid.setFilled(false);
	    mid.setText(propiedad.toString());
	    mid.setLineWidth(0);
	    int porcentaje = 50;
	    if (NodeNAryRelation.claseBase.getSubClasses(ExampleViewComponent.manager.getActiveOntology()).contains(this.dominio))
	    	porcentaje = 80;
	    else if (NodeNAryRelation.claseBase.getSubClasses(ExampleViewComponent.manager.getActiveOntology()).contains(this.rango))
	    	porcentaje = 20;
	    else {
	    	ArrowHeadGreater flechaDestino = new ArrowHeadGreater();
		    flechaDestino.setWidth(5);
		    
		    enlace.setDestArrowHead(flechaDestino);
	    }
	    	
	    
	    enlace.addPathItem(mid, new PathConvPercent(enlace, porcentaje, 10));
	    
	    
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
	public boolean isDomainRangeEdge(){
		return true;
	}
	
	@Override
	public DomainRangeEdge asDomainRangeEdge(){
		return this;
	}

}

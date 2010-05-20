package org.ugr.violet.base;

import org.semanticweb.owl.model.OWLOntology;
import org.tigris.gef.base.Diagram;
import org.tigris.gef.base.LayerPerspective;
import org.ugr.violet.graph.OWLGraphModel;

/**
 * Diagrama asociado a una ontologia
 * @author anab
 *
 */
public class OWLDiagram extends Diagram {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8421235263247453872L;

	
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param ont Ontologia OWL a partir de la cual construir el diagrama
	 */
	public OWLDiagram(OWLOntology ont){
		super();
		OWLGraphModel model = new OWLGraphModel(ont);
		this.setGraphModel(model);
		model.setOwner(this);
	}
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param ont Ontologia OWL a partir de la cual construir el diagrama
	 */
	public OWLDiagram(String name, OWLOntology ont){
		super(name);
		OWLGraphModel model = new OWLGraphModel(ont);
		this.setGraphModel(model);
		model.setOwner(this);
	}
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param graphModel modelo grafico de la ontologia
	 */
	public OWLDiagram(String name, OWLGraphModel graphModel){
		super(name, graphModel);
		graphModel.setOwner(this);
	}

	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param graphModel modelo grafico de la ontologia
	 * @param layer layer del diagrama
	 */
	public OWLDiagram(String name, OWLGraphModel graphModel, LayerPerspective layer){
		super(name, graphModel, layer);
		graphModel.setOwner(this);
	}
	
	/**
	 * Devuelve el modelo del grafo asociado representado en el diagrama
	 * @return el modelo
	 */
	public OWLGraphModel getOntologyGraphModel(){
		return (OWLGraphModel) this.getGraphModel();
		
	}
}

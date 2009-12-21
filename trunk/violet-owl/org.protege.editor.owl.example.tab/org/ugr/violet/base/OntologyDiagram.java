package org.ugr.violet.base;

import org.semanticweb.owl.model.OWLOntology;
import org.tigris.gef.base.Diagram;
import org.tigris.gef.base.LayerPerspective;
import org.ugr.violet.graph.OntologyGraphModel;

/**
 * Diagrama asociado a una ontologia
 * @author anab
 *
 */
public class OntologyDiagram extends Diagram {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8421235263247453872L;

	
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param ont Ontologia OWL a partir de la cual construir el diagrama
	 */
	public OntologyDiagram(OWLOntology ont){
		super();
		OntologyGraphModel model = new OntologyGraphModel(ont);
		this.setGraphModel(model);
		model.setOwner(this);
	}
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param ont Ontologia OWL a partir de la cual construir el diagrama
	 */
	public OntologyDiagram(String name, OWLOntology ont){
		super(name);
		OntologyGraphModel model = new OntologyGraphModel(ont);
		this.setGraphModel(model);
		model.setOwner(this);
	}
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param graphModel modelo grafico de la ontologia
	 */
	public OntologyDiagram(String name, OntologyGraphModel graphModel){
		super(name, graphModel);
		graphModel.setOwner(this);
	}

	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param graphModel modelo grafico de la ontologia
	 * @param layer layer del diagrama
	 */
	public OntologyDiagram(String name, OntologyGraphModel graphModel, LayerPerspective layer){
		super(name, graphModel, layer);
		graphModel.setOwner(this);
	}
	
	/**
	 * Devuelve el modelo del grafo asociado representado en el diagrama
	 * @return el modelo
	 */
	public OntologyGraphModel getOntologyGraphModel(){
		return (OntologyGraphModel) this.getGraphModel();
		
	}
}

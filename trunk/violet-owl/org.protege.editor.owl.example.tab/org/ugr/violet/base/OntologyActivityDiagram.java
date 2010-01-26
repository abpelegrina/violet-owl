/**
 * 
 */
package org.ugr.violet.base;

import org.semanticweb.owl.model.OWLOntology;
import org.tigris.gef.base.LayerPerspective;
import org.ugr.violet.graph.OntologyActivityGraphModel;
import org.ugr.violet.graph.OntologyGraphModel;

/**
 * @author anab
 *
 */
public class OntologyActivityDiagram extends OntologyDiagram {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8936782713339037801L;

	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param ont Ontologia OWL a partir de la cual construir el diagrama
	 */
	public OntologyActivityDiagram(OWLOntology ont){
		super(ont);
		OntologyGraphModel model = new OntologyGraphModel(ont);
		this.setGraphModel(model);
		model.setOwner(this);
	}
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param ont Ontologia OWL a partir de la cual construir el diagrama
	 */
	public OntologyActivityDiagram(String name, OWLOntology ont){
		super(name, ont);
		OntologyGraphModel model = new OntologyActivityGraphModel(ont);
		this.setGraphModel(model);
		model.setOwner(this);
	}
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param graphModel modelo grafico de la ontologia
	 */
	public OntologyActivityDiagram(String name, OntologyActivityGraphModel graphModel){
		super(name, graphModel);
		graphModel.setOwner(this);
	}

	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param graphModel modelo grafico de la ontologia
	 * @param layer layer del diagrama
	 */
	public OntologyActivityDiagram(String name, OntologyActivityGraphModel graphModel, LayerPerspective layer){
		super(name, graphModel, layer);
		graphModel.setOwner(this);
	}
	
	/**
	 * Devuelve el modelo del grafo asociado representado en el diagrama
	 * @return el modelo
	 */
	public OntologyActivityGraphModel getOntologyActivityGraphModel(){
		return (OntologyActivityGraphModel) this.getGraphModel();
		
	}
}

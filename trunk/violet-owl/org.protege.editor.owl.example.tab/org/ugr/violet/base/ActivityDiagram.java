/**
 * 
 */
package org.ugr.violet.base;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLOntology;
import org.tigris.gef.base.LayerPerspective;
import org.ugr.violet.graph.ActivityGraphModel;
import org.ugr.violet.graph.OWLGraphModel;

/**
 * @author anab
 *
 */
public class ActivityDiagram extends OWLDiagram {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8936782713339037801L;

	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param ont Ontologia OWL a partir de la cual construir el diagrama
	 */
	public ActivityDiagram(OWLOntology ont){
		super();
		OWLGraphModel model = new OWLGraphModel();
		this.setGraphModel(model);
		model.setOwner(this);
	}
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param ont Ontologia OWL a partir de la cual construir el diagrama
	 */
	public ActivityDiagram(String name, OWLIndividual Task){
		super(name);
		OWLGraphModel model = new ActivityGraphModel(Task);
		this.setGraphModel(model);
		model.setOwner(this);
	}
	
	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param graphModel modelo grafico de la ontologia
	 */
	public ActivityDiagram(String name, OWLGraphModel graphModel){
		super(name, graphModel);
		graphModel.setOwner(this);
	}

	/**
	 * Crea un nuevo diagrama para una ontologia
	 * @param name nombre del diagrama
	 * @param graphModel modelo grafico de la ontologia
	 * @param layer layer del diagrama
	 */
	public ActivityDiagram(String name, ActivityGraphModel graphModel, LayerPerspective layer){
		super(name, graphModel, layer);
		graphModel.setOwner(this);
	}
	
	/**
	 * Devuelve el modelo del grafo asociado representado en el diagrama
	 * @return el modelo
	 */
	public ActivityGraphModel getOntologyActivityGraphModel(){
		return (ActivityGraphModel) this.getGraphModel();
		
	}
}

/**
 * 
 */
package org.ugr.violet.view;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.protege.owl.examples.tab.VioletViewEditor;
import org.semanticweb.owl.model.AddAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLDataProperty;
import org.semanticweb.owl.model.OWLDescription;
import org.semanticweb.owl.model.OWLImportsDeclaration;
import org.semanticweb.owl.model.OWLIndividual;
import org.semanticweb.owl.model.OWLObjectProperty;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.ugr.violet.graph.nodes.view.ViewNode;
import org.ugr.violet.presentation.view.FigViewParameter;
import org.xml.sax.SAXException;

/**
 * @author anab
 *
 */
public class ViewManager {
	
	private ViewParser parser = null;
	
	private URI uri = null;
	
	OWLOntology ont = null;
	
	Map<OWLClass, FigViewParameter> classes = null;
	Map<OWLClass, FigViewParameter> individuals = null;
	Map<OWLObjectProperty, FigViewParameter> objectProperties = null;
	Map<OWLDataProperty, FigViewParameter> dataProperties = null;
	
	/*********************************************************
	 ***************** * INITIALIZATION * ******************** 
	 *********************************************************/
	
	/**
	 * Creates a new ViewManager
	 * @param file path to the view file
	 */
	public ViewManager(String file){
		
		try {			
			// parses the view file
			parser = new ViewParser("");
			parser.parse(); 
			
			// load the ontology
			this.loadOntology();
			
			// save de current active ontology
			OWLOntology activa = VioletViewEditor.manager.getActiveOntology();
			
			//load de imported ontology
			ont = VioletViewEditor.manager.loadOntology(uri);
			
			// set the active ontology
			VioletViewEditor.manager.setActiveOntology(activa);	
			
			// clases
			classes = new Hashtable<OWLClass, FigViewParameter>();
			this.loadClasses();
			
			// individuos
			individuals = new Hashtable<OWLClass, FigViewParameter>();
			this.loadIndividuals();
			
			// propiedades de objetos
			objectProperties = new Hashtable<OWLObjectProperty, FigViewParameter>();
			this.loadObjectProperty();
			
			// propiedades de datos
			dataProperties = new Hashtable<OWLDataProperty, FigViewParameter>();
			this.loadDataProperty();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Load the data properties declared in the  view file
	 */
	private void loadDataProperty() {
		int numDataProperty = parser.getDataPropertyConnectors().size();
		FigViewParameter param;
		
		// 
		for (int i=0; i<numDataProperty; i++){
			
			param = parser.getDataPropertyConnectors().get(i);
			System.out.println("Valor de la propiedad de objetos " + param);
			
			//create the OWL Entitiy
			OWLDataProperty c = VioletViewEditor.manager.getOWLDataProperty(param.getOWLEntityName());
			
			//add the pair to the hashtable
			dataProperties.put(c, param);
		}
	}


	/**
	 * Load the object properties declared in the  view file
	 */
	private void loadObjectProperty() {
		
		int numObjectProperty = parser.getObjectPropertyConnectors().size();
		FigViewParameter param;
		
		for (int i=0; i<numObjectProperty; i++){
			
			param = parser.getObjectPropertyConnectors().get(i);
			
			System.out.println("Valor de la propiedad de objetos " + param);
			
			//create the OWL Entitiy
			OWLObjectProperty c = VioletViewEditor.manager.getOWLObjectProperty(param.getOWLEntityName());
			
			//add the pair to the hashtable
			objectProperties.put(c, param);
		}
	}


	/**
	 * Load the individuals declared in the view file
	 */
	private void loadIndividuals() {
		int numIndividuals = parser.getIndividualNodes().size();
		FigViewParameter param;
		
		for (int i=0; i<numIndividuals; i++){
			
			param = parser.getIndividualNodes().get(i);
			
			System.out.println("Valor de la propiedad de objetos " + param);
			
			//create the OWL Entitiy
			OWLClass c = VioletViewEditor.manager.getOWLClass(param.getOWLEntityName());
			
			//add the pair to the hashtable
			individuals.put(c, param);
		}
	}

	/**
	 * Load de ontology used in view
	 * @return
	 */
	private URI loadOntology(){
		
		try {
			uri = new URI(parser.getOntologyURIs());
			new URI(parser.getOntologyFiles());
			
			OWLImportsDeclaration imprt = VioletViewEditor.manager.getOWLDataFactory().getOWLImportsDeclarationAxiom(VioletViewEditor.manager.getActiveOntology(), uri);
			// agregamos el axioma a la ontología
			AddAxiom addAx3 = new AddAxiom(VioletViewEditor.manager.getActiveOntology(), imprt);

			// apply changes
			VioletViewEditor.manager.applyChange(addAx3);		
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;		
	}
	
	/**
	 * Load the classes declared in the view file
	 */
	private void loadClasses(){
		
		int numClasses = parser.getClassNodes().size();
		FigViewParameter param;
		
		for (int i=0; i<numClasses; i++){
			
			param = parser.getClassNodes().get(i);
			
			System.out.println("Valor de la propiedad de objetos " + param);
			
			//create the OWL Entitiy
			OWLClass c = VioletViewEditor.manager.getOWLClass(param.getOWLEntityName());
			
			//add the pair to the hashtable
			classes.put(c, param);
		}
	}
	
	/*********************************************************
	 ******************* * ADD FIGURES * ********************* 
	 *********************************************************/
	
	public boolean addOWLClass(OWLClass c){
		FigViewParameter figura = classes.get(c);
		
		// buscamos la clase
		if (figura != null){
			
			new ViewNode(figura, c);
			return true;
		}
		else
			return false;
	}
	
	public boolean addOWLObjectProperty(OWLObjectProperty op){
		FigViewParameter figura = objectProperties.get(op);
		
		if (figura != null)
			return true;
		else
			return false;
	}
	
	public boolean addOWLDataProperty(OWLDataProperty op){
		FigViewParameter figura = dataProperties.get(op);
		
		if (figura != null)
			return true;
		else
			return false;
	}
	
	public boolean addOWLIndividual(OWLIndividual op){
		
		Set<OWLDescription> classes = op.getTypes(ont);
		
		for(OWLDescription d : classes){
			if (!d.isAnonymous() && 
					individuals.get(d.asOWLClass())!=null){
				return true;
			}
		}
		
		return false;
	}
	
	
}

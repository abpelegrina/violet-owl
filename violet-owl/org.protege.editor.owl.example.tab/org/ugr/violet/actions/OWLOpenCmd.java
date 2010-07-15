/**
 * 
 */
package org.ugr.violet.actions;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.tigris.gef.base.CmdOpen;
import org.ugr.violet.graph.OWLGraphModel;

/**
 * @author anab
 *
 */
public class OWLOpenCmd extends CmdOpen {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7188812338233739604L;
	private Hashtable<String, Point> nodos = new Hashtable<String, Point>();
	
	/**
	 * 
	 */
	public OWLOpenCmd() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.tigris.gef.base.CmdOpen#doIt()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doIt() {
		
		
		// 1. Seleccionamos el fichero
		String filename = OWLSaveCmd.dameNombreFichero();
		
		if (filename == "") return;
		String URIactiva = "";
		
		// 2. Abrimos el fichero y recuperamos el mapa
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try
		{
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			URIactiva = (String) in.readObject();
			nodos = (Hashtable<String, Point>)in.readObject();
			in.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		catch(ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		
		
		// 3. Ajustamos las ontologías
		OWLGraphModel m = (OWLGraphModel) ExampleViewComponent.getLienzoActual().getGraphModel();
		
		// Comparamos la ontología activa con la del diagrama y si difieren cargar la del diagrama. Avisando antes al usuario.
		if (!m.activeOntology().getURI().equals(URI.create(URIactiva))) {
			int res = JOptionPane.showConfirmDialog(null, "The diagram you are triying to open refers to another onotology (not the active ontology), Do you want to load this ontology?", "Ontology not loaded", JOptionPane.YES_NO_OPTION);
			
			// el usuario no quiere cargar la nueva ontologías, salimos
			if (res == JOptionPane.NO_OPTION) return;
			
			// el usuario quiere cargar la nueva ontología
			else {
				// cargamos la ontología nueva
				try {
					ExampleViewComponent.manager.loadOntology(URI.create(URIactiva));
				} catch (OWLOntologyCreationException e) {
					JOptionPane.showMessageDialog(null, "Error loading the ontology. The diagram will not be opened.");
					return;
				}
			}
		}
			
		// 4. Agregamos las entidades al diagrama
		Point location;
		for (String name : nodos.keySet()){
			
			location = nodos.get(name);
			m.addEntity(name, location);
				
		}
		
		OWLSaveCmd.filename = filename;
	}
	
	

}

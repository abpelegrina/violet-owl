/**
 * 
 */
package org.ugr.violet.actions;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.tigris.gef.base.CmdOpen;
import org.ugr.violet.graph.OWLGraphModel;

/**
 * @author anab
 *
 */
public class OWLOpenCmd extends CmdOpen {

	
	private Hashtable<String, Point> nodos = new Hashtable<String, Point>();
	
	/**
	 * 
	 */
	public OWLOpenCmd() {
		super();
	}

	/**
	 * @param filterPattern
	 */
	public OWLOpenCmd(String filterPattern) {
		super(filterPattern);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.tigris.gef.base.CmdOpen#doIt()
	 */
	@Override
	public void doIt() {
		// 1. Seleccionamos el fichero
		String filename = OWLSaveCmd.dameNombreFichero();
		
		if (filename == "") return;
		
		// 2. Abrimos el fichero y recuperamos el mapa
		FileInputStream fis = null;
		ObjectInputStream in = null;
		
		try
		{
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
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
		
		// 3. Agregamos las entidades al diagrama
		OWLGraphModel m = (OWLGraphModel) ExampleViewComponent.getLienzoActual().getGraphModel();
		
		Point location;
		
		for (String name : nodos.keySet()){
			
			location = nodos.get(name);
			
			m.addEntity(name, location);
				
		}
	}
	
	

}

/**
 * 
 */
package org.ugr.violet.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.tigris.gef.base.CmdSave;
import org.tigris.gef.base.SaveAction;
import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.nodes.OWLNode;

/**
 * @author anab
 *
 */
public class OWLSaveCmd extends CmdSave {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2383517249910284242L;
	private Hashtable<String, Point> nodos = new Hashtable<String, Point>();

	/* (non-Javadoc)
	 * @see org.tigris.gef.base.SaveAction#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void doIt() {
    	
		
		//1. Recuperar el nombre del fichero
		String filename = dameNombreFichero();
		
		if (filename == "") return;
		
    	//2. Recuperar el graph model
		OWLGraphModel m = (OWLGraphModel) ExampleViewComponent.getLienzoActual().getGraphModel(); 
		
		//3. Recorrer todos los nodos, guardando la entidad y la posición
		OWLNode nodo;
		for (Object n : m.getNodes()){
			
			System.err.println("Vuelta");
			
			// si el nodo es instancia de OWLNode
			if (OWLNode.class.isInstance(n)){
				
				nodo = (OWLNode) n;
				
				nodos.put(nodo.getOWLEntity().toString(), nodo.getOntologyFig().getLocation());
				
				System.err.println("("+ nodo.getOWLEntity() +","+nodo.getOntologyFig().getLocation()+")");
			}
		}
		
		//4. Gaurdarlo todo en un fichero
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		
		try
		{
			fos = new FileOutputStream(filename + ".diagram");
			out = new ObjectOutputStream(fos);
			out.writeObject(nodos);
			out.close();
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(null, "An error occured while saving the diagram. The diagram is not saved");			
		}
		
		
		//5. ??
		
		//6. Profit!
	}

	/**
	 * @return
	 */
	public static String dameNombreFichero() {
		String filename = "";
		
		JFileChooser fc = new JFileChooser();
		
		int rc = fc.showDialog(null, "Select Data File");
		
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			filename = file.getAbsolutePath();
		}
		return filename;
	}

}

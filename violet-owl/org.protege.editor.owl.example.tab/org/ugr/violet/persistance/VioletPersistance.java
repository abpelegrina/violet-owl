/**
 * 
 */
package org.ugr.violet.persistance;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import org.ugr.violet.graph.OWLGraphModel;
import org.ugr.violet.graph.nodes.OWLNode;

/**
 * @author anab
 *
 */
public class VioletPersistance {
	
	public static void saveDiagram(OWLGraphModel model){
		
		//TODO guardar los nodos y su posicion
		
		try {
			FileOutputStream fos = null;
			ObjectOutputStream out = null;
		    
			fos = new FileOutputStream("/Users/anab/prueba.txt");
			out = new ObjectOutputStream(fos);
		
		for (Object o:model.getNodes()){
			OWLNode n = (OWLNode) o;
			
			System.err.println("trabajando cn el nodo" + n);
			
			
			
			// Clases
			if (n.isNodeClass()){
				out.writeObject(n.asNodeClass().getOWLClass().toString());
				out.writeObject(n.asNodeClass().getLocation());
			}
			
			
			else if (n.isNodeIndividual()){
				/*out.writeObject(n.asNodeClass().getOWLClass());
				out.writeObject(n.asNodeClass().getLocation());*/
			}
			else if (n.isNodeObjectProperty()){
				//layout += n.asNodeObjectProperty().getPropiedad().toString() + "\t" + n.asNodeObjectProperty().getLocation() + "\n";
			}
			else if (n.isDataProperty()){
				//layout += n.asn.getClass().toString() + "\t" + n.asNodeClass().getLocation() + "\n";
			}
			
			out.close();
			
			
		}
		
		
		    JOptionPane.showMessageDialog(null, "Successful storing. Bye!!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error storing the diagram. Saving canceled.");
		}
	}
	
	public static void loadDiagram(OWLGraphModel model){
		
		FileInputStream fis = null;
		ObjectInputStream in = null;
		String name;
		Point location;
		
		try
		  {
		     fis = new FileInputStream("/Users/anab/prueba.txt");
		     in = new ObjectInputStream(fis);
		     
		     do {
		    	 name = (String) in.readObject();
		    	 location = (Point) in.readObject();
		     } while(name != null && location != null);
		     
		     
		     in.close();
		   }
		   catch(IOException ex)
		   {
		     ex.printStackTrace();
		   } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

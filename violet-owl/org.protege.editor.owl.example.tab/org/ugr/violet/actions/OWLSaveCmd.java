package org.ugr.violet.actions;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.protege.owl.examples.tab.VioletEditor;
import org.tigris.gef.base.CmdSave;
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
	public static String filename = "";
	public static JFileChooser fc = new JFileChooser();

	
	/* (non-Javadoc)
	 * @see org.tigris.gef.base.SaveAction#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void doIt() {
    	
		
		//1. si todavía no hemos guardado el fichero
		if (filename == ""){
		
			//1. Recuperar el nombre del fichero
			filename = dameNombreFichero();
		
			if (filename == "") return;
		}
		
    	//2. Recuperar el graph model
		OWLGraphModel m = (OWLGraphModel) VioletEditor.getLienzoActual().getGraphModel(); 
		
		//3. Recuperar las ontologías
		String URIactiva = m.getActiveOntology().getURI().toString();
		
		//4. Recorrer todos los nodos, guardando la entidad y la posición
		OWLNode nodo;
		for (Object n : m.getNodes()){
			
			// si el nodo es instancia de OWLNode
			if (OWLNode.class.isInstance(n)){
				
				nodo = (OWLNode) n;
				nodos.put(nodo.getOWLEntity().toString(), nodo.getOntologyFig().getLocation());
			}
		}
		
		//5. Gaurdarlo todo en un fichero
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		
		try
		{
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(URIactiva);
			out.writeObject(nodos);
			out.close();
		}
		catch(IOException ex)
		{
			JOptionPane.showMessageDialog(null, "An error occured while saving the diagram. The diagram is not saved");			
		}
		
		
		//6. ??????
		
		//7. Profit!
	}

	/**
	 * @return
	 */
	public static String dameNombreFichero() {
		String filename = "";
		
		fc = new JFileChooser();
		fc.setFileFilter(new FileFilter(){
			public final static String diagExt = "diagram";

			/* (non-Javadoc)
			 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
			 */
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				
				String s = f.getName();
		        int i = s.lastIndexOf('.');
		        String ext = "";

		        if (i > 0 &&  i < s.length() - 1) {
		            ext = s.substring(i+1).toLowerCase();
		        }
		        
		        if (ext.equals(diagExt)){
		        	return true;
		        }
		        else			
		        	return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Filter for OWL diagrams";
			}
		});
		
		int rc = fc.showDialog(null, "Select Data File");
		
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			filename = file.getAbsolutePath();
		}
		return filename;
	}


}

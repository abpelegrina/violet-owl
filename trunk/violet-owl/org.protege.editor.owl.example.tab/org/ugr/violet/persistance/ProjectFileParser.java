/**
 * 
 */
package org.ugr.violet.persistance;

import java.io.File;
import java.util.List;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 


public class ProjectFileParser {

	/**
	 * Crea una instancia del parseador del fichero
	 * @param fileName
	 */
	public ProjectFileParser(String fileName){
	}
	
	/**
	 * Recupera las ontologías asociadas a un proyecto
	 * @return
	 */
	public List<String> getOntologies(){
		return null;
	}
	
	//public List 
	

	public static void main(String argv[]) {

		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File("org/ugr/ontology/persistance/ejemploProyecto.xml"));

			// normalize text representation
			doc.getDocumentElement ().normalize ();
			System.out.println ("Root element of the doc is \"" + 
					doc.getDocumentElement().getNodeName() + "\"");


			NodeList listaDeDiagramas = doc.getElementsByTagName("diagram");
			int totalPersons = listaDeDiagramas.getLength();
			System.out.println("Number of diagrams : " + totalPersons);

			
			for(int s=0; s<listaDeDiagramas.getLength() ; s++){


				// recuperamos el primer diagrama
				Node firstPersonNode = listaDeDiagramas.item(s);
				
				if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE){


					Element firstPersonElement = (Element)firstPersonNode;

					//-------
					NodeList nodeElementList = firstPersonElement.getElementsByTagName("node");
					
					for (int i = 0; i < nodeElementList.getLength(); ++i){
						Element nodeElement = (Element)nodeElementList.item(i);
						System.out.println("\tNode: " + nodeElement.getAttribute("name"));
					}
					

					//-------
					NodeList lastNameList = firstPersonElement.getElementsByTagName("edge");
					for (int i = 0; i < lastNameList.getLength(); ++i){
						Element edgeElement = (Element)lastNameList.item(i);
						System.out.println("\tEdge: " + edgeElement.getAttribute("name"));
					}



				}//end of if clause


			}//end of for loop with s var


		}catch (SAXParseException err) {
			System.out.println ("** Parsing error" + ", line " 
					+ err.getLineNumber () + ", uri " + err.getSystemId ());
			System.out.println(" " + err.getMessage ());

		}catch (SAXException e) {
			Exception x = e.getException ();
			((x == null) ? e : x).printStackTrace ();

		}catch (Throwable t) {
			t.printStackTrace ();
		}
		//System.exit (0);

	}//end of main
}
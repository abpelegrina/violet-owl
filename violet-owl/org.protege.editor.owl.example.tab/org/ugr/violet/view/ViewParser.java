/**
 * 
 */
package org.ugr.violet.view;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.ugr.violet.actions.OWLSaveCmd;
import org.ugr.violet.presentation.view.FigViewParameter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parses a view
 * @author anab
 */
public class ViewParser {
	
	Document doc = null;
	
	String ontologyURI = null;
	String ontologyFile = null;
	List<FigViewParameter> classNodes = null;
	List<FigViewParameter> individualNodes = null;
	List<FigViewParameter> dataTypeNodes = null;
	List<FigViewParameter> objectPropertyConnectors = null;
	List<FigViewParameter> dataPropertyConnectors = null;
	
	public ViewParser() throws ParserConfigurationException, SAXException, 
    IOException{
		//construimos el documento
		String Filename = OWLSaveCmd.dameNombreFichero();
		
		if (Filename == "") return;
		
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); 
        DocumentBuilder builder = domFactory.newDocumentBuilder();		
		doc = builder.parse(Filename);
		
		classNodes = new ArrayList<FigViewParameter>();
		individualNodes = new ArrayList<FigViewParameter>();
		dataTypeNodes = new ArrayList<FigViewParameter>();
		objectPropertyConnectors = new ArrayList<FigViewParameter>();
		dataPropertyConnectors = new ArrayList<FigViewParameter>();
	}
	
	public ViewParser(String Filename) throws ParserConfigurationException, SAXException, 
    IOException{
		//construimos el documento
		
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); 
        DocumentBuilder builder = domFactory.newDocumentBuilder();		
		doc = builder.parse(Filename);
		
		classNodes = new ArrayList<FigViewParameter>();
		individualNodes = new ArrayList<FigViewParameter>();
		dataTypeNodes = new ArrayList<FigViewParameter>();
		objectPropertyConnectors = new ArrayList<FigViewParameter>();
		dataPropertyConnectors = new ArrayList<FigViewParameter>();
	}
	
	public void parse() throws ParserConfigurationException, SAXException, 
    IOException, XPathExpressionException {
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		// recuperamos las ontologías
		XPathExpression expr = xpath.compile("/view/ontology/@id");
	    NodeList URIs = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	    
	    ontologyFile = URIs.item(0).getNodeValue();
	    
	    // class nodes
	    XPathExpression exprEntity = xpath.compile("/view/ontology/nodes/nodeclass/owlentity/@uri");
	    XPathExpression exprFigure = xpath.compile("/view/ontology/nodes/nodeclass/figure/@class");
	    
	    NodeList entities = (NodeList) exprEntity.evaluate(doc, XPathConstants.NODESET);	    
	    NodeList figures = (NodeList) exprFigure.evaluate(doc, XPathConstants.NODESET);
	    
	    for (int i=0; i<entities.getLength(); ++i){
	    	System.out.println(entities.item(i).getNodeValue());
	    	System.out.println(figures.item(i).getNodeValue());
	    }
	    
	    
	    // entitiy nodes
	    exprEntity = xpath.compile("/view/ontology/nodes/nodeindividual/owlentity/@uri");
	    exprFigure = xpath.compile("/view/ontology/nodes/nodeindividual/figure/@class");
	    
	    entities = (NodeList) exprEntity.evaluate(doc, XPathConstants.NODESET);	    
	    figures = (NodeList) exprFigure.evaluate(doc, XPathConstants.NODESET);
	    
	    for (int i=0; i<entities.getLength(); ++i){
	    	System.out.println(entities.item(i).getNodeValue());
	    	System.out.println(figures.item(i).getNodeValue());
	    }
	    
	    // dataproperty nodes
	    exprEntity = xpath.compile("/view/ontology/nodes/nodedataproperty/owlentity/@uri");
	    exprFigure = xpath.compile("/view/ontology/nodes/nodedataproperty/figure/@class");
	    
	    entities = (NodeList) exprEntity.evaluate(doc, XPathConstants.NODESET);	    
	    figures = (NodeList) exprFigure.evaluate(doc, XPathConstants.NODESET);
	    
	    for (int i=0; i<entities.getLength(); ++i){
	    	System.out.println(entities.item(i).getNodeValue());
	    	System.out.println(figures.item(i).getNodeValue());
	    }
	    
	    // objectproperty nodes
	    exprEntity = xpath.compile("/view/ontology/nodes/nodeobjectproperty/owlentity/@uri");
	    exprFigure = xpath.compile("/view/ontology/nodes/nodeobjectproperty/figure/@class");
	    
	    entities = (NodeList) exprEntity.evaluate(doc, XPathConstants.NODESET);	    
	    figures = (NodeList) exprFigure.evaluate(doc, XPathConstants.NODESET);
	    
	    for (int i=0; i<entities.getLength(); ++i){
	    	System.out.println(entities.item(i).getNodeValue());
	    	System.out.println(figures.item(i).getNodeValue());
	    }
	    
	    
	    
	    /*
		// recuperamos los nodos para clases
	    this.findFigures("/view/nodes/nodeclass",
				 "/view/nodes/node/figure",
				 xpath,
				 classNodes);
	    
	    // recuperamos los nodos para individuos
	    this.findFigures("/view/nodes/nodeindividual",
				 "/view/nodes/node/figure",
				 xpath,
				 individualNodes);
	    
	    // Recuperar las conexiones de propiedades de objetos
	    this.findFigures("/view/connectors/connector/owlentity[@type='objectproperty']/text()",
	    				 "/view/connectors/connector/figure",
	    				 xpath,
	    				 objectPropertyConnectors);
	    
	    // Recuperar las conexiones de propiedades de objetos
	    this.findFigures("/view/connectors/connector/owlentity[@type='dataproperty']/text()",
	    				 "/view/connectors/connector/figure",
	    				 xpath,
	    				 dataPropertyConnectors);	    
	    				 */
	}
	
	private void findFigures(String filterEntity, String filterFigure, XPath xpath, List<FigViewParameter> parameters) 
	throws XPathExpressionException{
		XPathExpression exprEntity = xpath.compile(filterEntity);
		XPathExpression exprFigure = xpath.compile(filterFigure);

	    NodeList entities = (NodeList) exprEntity.evaluate(doc, XPathConstants.NODESET);	    
	    NodeList figures = (NodeList) exprFigure.evaluate(doc, XPathConstants.NODESET);
	    
	    for (int i = 0; i < entities.getLength(); i++) {
		     Element figure = (Element) figures.item(i);
		    	
		    FigViewParameter prm = this.getFigureParameters(figure, xpath);
		    prm.setOWLEntity(entities.item(i).getNodeValue());
		    //prm.setOWLEntityType("dataproperty");
		  
		    parameters.add(prm);
		}
	}
	
	private FigViewParameter getFigureParameters(Element nodoXMLFigure, XPath xpath){
		FigViewParameter params = new FigViewParameter();
		
		try {
			String  shape = xpath.evaluate("shape", nodoXMLFigure);
	    	String  color = xpath.evaluate("color", nodoXMLFigure);
	    	String  line = xpath.evaluate("line", nodoXMLFigure);
	    	String  linewidth = xpath.evaluate("linewidth", nodoXMLFigure);
	    	String  linecolor = xpath.evaluate("linecolor", nodoXMLFigure);
	    	
	    	
	    	params.setShape(shape);
	    	params.setLine(line);
	    	params.setColor(color);
	    	params.setLineColor(linecolor);
	    	params.setLineWidth(linewidth);
	    	
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return params;
	}
	
	/***********************************************************************
	 ************************* GETTERS & SETTERS *************************** 
	 ***********************************************************************/
	
	/**
	 * @return the ontologyURIs
	 */
	public String getOntologyURIs() {
		return ontologyURI;
	}

	/**
	 * @return the ontologyFiles
	 */
	public String getOntologyFiles() {
		return ontologyFile;
	}

	/**
	 * @return the classNodes
	 */
	public List<FigViewParameter> getClassNodes() {
		return classNodes;
	}

	/**
	 * @return the individualNodes
	 */
	public List<FigViewParameter> getIndividualNodes() {
		return individualNodes;
	}

	/**
	 * @return the objectPropertyConnectors
	 */
	public List<FigViewParameter> getObjectPropertyConnectors() {
		return objectPropertyConnectors;
	}

	/**
	 * @return the dataPropertyConnectors
	 */
	public List<FigViewParameter> getDataPropertyConnectors() {
		return dataPropertyConnectors;
	}
	
	//******************* MAIN ****************************************
	
	public static void main(String[] args){
		ViewParser v;
		try {
			v = new ViewParser("/Users/anab/Desktop/view.xml");
			
			v.parse();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

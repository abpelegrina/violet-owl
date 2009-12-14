package org.ugr.ontology.layout;

import java.awt.Dimension;

import org.ugr.ontology.base.OntologyDiagram;
import org.ugr.ontology.graph.nodes.OntologyNode;

public class OntologyDiagramLayouter implements Layouter {
	
	private OntologyDiagram ontologia;
	
	
	public OntologyDiagramLayouter(OntologyDiagram ontologia) {
		super();
		this.ontologia = ontologia;
	}

	public void add(LayoutedObject obj) {
		
	}

	public Dimension getMinimumDiagramSize() {
		// TODO Auto-generated method stub
		return null;
	}

	public LayoutedObject getObject(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public LayoutedObject[] getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * EJecuta el algoritmo de Layout autom�tico. De momento se trata slo de un stub.
	 */
	//TOOD: incorporar algoritmo de verdad
	public void layout() {
		
	/*	*/
		
		int x=0, y=0;
		int xOffset = 150, yOffset = 110;
		final int xLimit=1200;
		OntologyNode n = null;
		
		for (Object o: ontologia.getOntologyGraphModel().getNodes()){
		
		// situamos la posicion de la figura OJO! no hacerlo hasta despues de agregar el nodo al modelo
			n = (OntologyNode) o;
			n.getOntologyFig().setLocation(x, y);
			
			// "layouter" primitivo 
			// nos desplazamos en el lienzo
			xOffset = n.getOntologyFig().getWidth() + 20;
			x += xOffset;
			if (x>xLimit) { // si hemos alcanzado el limite pasamos a la siguiente l�nea
				y += yOffset;
				x = 0;
			}
			
			
		}
	}

	public void remove(LayoutedObject obj) {
		// TODO Auto-generated method stub
	}
}

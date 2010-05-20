package org.ugr.violet.layout;

import java.awt.Dimension;

import org.ugr.violet.base.OWLDiagram;
import org.ugr.violet.graph.nodes.OWLNode;

public class OntologyDiagramLayouter implements Layouter {
	
	private OWLDiagram ontologia;
	
	
	public OntologyDiagramLayouter(OWLDiagram ontologia) {
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
		OWLNode n = null;
		
		for (Object o: ontologia.getOntologyGraphModel().getNodes()){
		
		// situamos la posicion de la figura OJO! no hacerlo hasta despues de agregar el nodo al modelo
			n = (OWLNode) o;
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

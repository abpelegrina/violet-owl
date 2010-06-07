/**
 * 
 */
package org.ugr.violet.actions;

import java.awt.event.ActionEvent;

import org.protege.owl.examples.tab.ExampleViewComponent;
import org.tigris.gef.base.SaveAction;
import org.ugr.violet.persistance.VioletPersistance;

/**
 * @author anab
 *
 */
public class OWLSaveAction extends SaveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2383517249910284242L;


	/* (non-Javadoc)
	 * @see org.tigris.gef.base.SaveAction#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
    	
    	//TODO guardar la configuración del diagrama
    	VioletPersistance.saveDiagram(ExampleViewComponent.lienzos.get(0).getOWLGraphModel());
    	
    	
    	//tabs.removeChangeListener(cl);
	}

}

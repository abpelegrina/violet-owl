/**
 * 
 */
package org.protege.owl.examples.tab;

import javax.swing.JLabel;

import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

/**
 * @author anab
 *
 */
public class ProjectNavigator extends AbstractOWLViewComponent {

	/**
	 * 
	 */
	public ProjectNavigator() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.protege.editor.owl.ui.view.AbstractOWLViewComponent#disposeOWLView()
	 */
	@Override
	protected void disposeOWLView() {

	}

	/* (non-Javadoc)
	 * @see org.protege.editor.owl.ui.view.AbstractOWLViewComponent#initialiseOWLView()
	 */
	@Override
	protected void initialiseOWLView() throws Exception {
		JLabel label = new JLabel("hola mundo!");
		this.add(label);
	}

}

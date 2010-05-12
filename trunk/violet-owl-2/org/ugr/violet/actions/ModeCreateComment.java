/**
 * 
 */
package org.ugr.violet.actions;

import java.awt.event.MouseEvent;

import org.tigris.gef.base.ModeCreate;
import org.tigris.gef.presentation.Fig;
import org.ugr.violet.presentation.FigComment;

/**
 * @author anab
 *
 */
public class ModeCreateComment extends ModeCreate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 496757185182126835L;
	
	
	public String instructions() {
        return "Drag to define a comment";
    }

	/* (non-Javadoc)
	 * @see org.tigris.gef.base.ModeCreate#createNewItem(java.awt.event.MouseEvent, int, int)
	 */
	@Override
	public Fig createNewItem(MouseEvent me, int snapX, int snapY) {
		// TODO Auto-generated method stub
		return new FigComment("Prueba Comentario");
	}

}

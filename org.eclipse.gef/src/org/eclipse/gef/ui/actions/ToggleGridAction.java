/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.ui.actions;

import org.eclipse.jface.action.Action;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.internal.GEFMessages;

/**
 * An action that toggles the grid.  This action keeps the visibility and enablement
 * properties in sync, i.e., it toggles both at the same time.
 * 
 * @author Pratik Shah
 * @since 3.0
 */
public class ToggleGridAction
	extends Action 
{
	
private GraphicalViewer diagramViewer;

/**
 * Constructor
 * @param	diagramViewer	the GraphicalViewer on which the grid enablement is set
 */
public ToggleGridAction(GraphicalViewer diagramViewer) {
	super(GEFMessages.ToggleGrid_Label, AS_CHECK_BOX);
	this.diagramViewer = diagramViewer;
	setToolTipText(GEFMessages.ToggleGrid_Tooltip);
	setId(GEFActionConstants.TOGGLE_GRID_VISIBILITY);
	setActionDefinitionId(GEFActionConstants.TOGGLE_GRID_VISIBILITY);
	setChecked(isChecked());
}

/**
 * @see org.eclipse.jface.action.IAction#isChecked()
 */
public boolean isChecked() {
	Boolean val = (Boolean)diagramViewer.getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
	if (val != null)
		return val.booleanValue();
	return false;
}

/**
 * @see org.eclipse.jface.action.IAction#run()
 */
public void run() {
	boolean val = !isChecked();
	diagramViewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, new Boolean(val));
	diagramViewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, new Boolean(val));
}

}
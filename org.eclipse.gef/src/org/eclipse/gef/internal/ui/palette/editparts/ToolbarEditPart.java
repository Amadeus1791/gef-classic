/*******************************************************************************
 * Copyright (c) 2008, 2024 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.gef.internal.ui.palette.editparts;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;

import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.ui.palette.PaletteViewerPreferences;

/**
 * An editpart for the <code>PaletteToolbar</code>.
 *
 * @author crevells
 * @since 3.4
 */
public class ToolbarEditPart extends GroupEditPart {

	/**
	 * Creates a new instance.
	 *
	 * @param model the <code>PaletteToolbar</code>
	 */
	public ToolbarEditPart(PaletteToolbar model) {
		super(model);
	}

	@Override
	public IFigure createFigure() {
		IFigure figure = new Figure() {

			@Override
			protected void paintFigure(Graphics graphics) {
				super.paintFigure(graphics);

				// draw top border
				graphics.setForegroundColor(getColorProvider().getListBackground());
				graphics.drawLine(getBounds().getTopLeft(), getBounds().getTopRight());

				// draw bottom border
				graphics.setForegroundColor(getColorProvider().getButtonDarker(0.7));
				graphics.drawLine(getBounds().getBottomLeft().getTranslated(0, -1),
						getBounds().getBottomRight().getTranslated(0, -1));
			}

		};
		figure.setOpaque(true);
		figure.setBackgroundColor(getColorProvider().getButton());
		figure.setBorder(new MarginBorder(2, 1, 1, 1));

		return figure;
	}

	@Override
	protected int getLayoutSetting() {
		return PaletteViewerPreferences.LAYOUT_ICONS;
	}

	@Override
	public boolean isToolbarItem() {
		return true;
	}

}

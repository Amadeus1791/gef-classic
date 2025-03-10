/*******************************************************************************
 * Copyright (c) 2000, 2024 IBM Corporation and others.
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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.ui.palette.editparts.PaletteEditPart;

/**
 * EditPart for the PaletteSeparator
 *
 * @author Pratik Shah
 */
public class SeparatorEditPart extends PaletteEditPart {

	/**
	 * Constructor
	 *
	 * @param separator The PaletteSeparator for which this EditPart is being
	 *                  created
	 */
	public SeparatorEditPart(PaletteSeparator separator) {
		super(separator);
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		return new SeparatorFigure();
	}

	/**
	 * @see org.eclipse.gef.ui.palette.editparts.PaletteEditPart#getToolTipText()
	 */
	@Override
	protected String getToolTipText() {
		return null;
	}

	/**
	 * Figure for the separator
	 *
	 * @author Pratik Shah
	 */
	class SeparatorFigure extends Figure {
		/**
		 * @see org.eclipse.draw2d.IFigure#getPreferredSize(int, int)
		 */
		@Override
		public Dimension getPreferredSize(int wHint, int hHint) {
			if (getBackgroundColor().equals(getColorProvider().getButton())) {
				return new Dimension(wHint, 4);
			}
			return new Dimension(wHint, 5);
		}

		private static final Insets CROP = new Insets(1, 3, 2, 4);

		/**
		 *
		 * @see org.eclipse.draw2d.Figure#paintFigure(Graphics)
		 */
		@Override
		protected void paintFigure(Graphics g) {
			Rectangle r = getBounds().getShrinked(CROP);
			if (getBackgroundColor().equals(getColorProvider().getListBackground())) {
				g.setForegroundColor(getColorProvider().getButtonDarker());
				g.drawLine(r.getLeft(), r.getRight());
			} else {
				g.setForegroundColor(getColorProvider().getButtonDarker());
				g.drawLine(r.getBottomLeft(), r.getTopLeft());
				g.drawLine(r.getTopLeft(), r.getTopRight());

				g.setForegroundColor(ColorConstants.buttonLightest);
				g.drawLine(r.getBottomLeft(), r.getBottomRight());
				g.drawLine(r.getBottomRight(), r.getTopRight());
			}
		}
	}

}

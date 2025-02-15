/*******************************************************************************
 * Copyright (c) 2011, 2024 Stephan Schwiebert and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Stephan Schwiebert - initial API and implementation
 ******************************************************************************/
package org.eclipse.zest.examples.cloudio.application.about;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.zest.cloudio.TagCloud;
import org.eclipse.zest.cloudio.Word;
import org.eclipse.zest.cloudio.layout.DefaultLayouter;
import org.eclipse.zest.examples.cloudio.application.Messages;

/**
 *
 * @author sschwieb
 *
 */
public class AboutDialog extends Dialog {

	private static final int RE_LAYOUT = 2;
	private TagCloud tc;

	public AboutDialog(Shell parentShell) {
		super(parentShell);
		setBlockOnOpen(false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setLayout(new GridLayout());
		tc = new TagCloud(parent, SWT.NONE) {

			@Override
			public Rectangle getClientArea() {
				return new Rectangle(0, 0, 400, 330);
			}

		};
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 330;
		data.widthHint = 400;
		tc.setLayoutData(data);
		tc.setMaxFontSize(50);
		tc.setMinFontSize(15);
		tc.setLayouter(new DefaultLayouter(5, 0));
		List<Word> values = new ArrayList<>();
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Color[] colors = new Color[5];
		colors[0] = Display.getDefault().getSystemColor(SWT.COLOR_DARK_CYAN);
		colors[1] = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN);
		colors[2] = Display.getDefault().getSystemColor(SWT.COLOR_DARK_MAGENTA);
		colors[3] = Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW);
		colors[4] = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		Word w = getWord(Messages.AboutDialog_Cloudio, fontNames, colors);
		w.weight = 0.95;
		w.angle = -35;
		w.setColor(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		values.add(w);
		w = getWord(Messages.AboutDialog_InspiredBy, fontNames, colors);
		w.setColor(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		w.angle = (float) (Math.random() * 90);
		if (Math.random() < 0.5) {
			w.angle = -w.angle;
		}
		w.weight = 0.2;
		if (Math.random() < 0.5) {
			w.angle = -w.angle;
		}
		values.add(w);
		w = getWord(Messages.bind(Messages.AboutDialog_UsedBy, System.getProperty("user.name")), fontNames, colors); //$NON-NLS-1$
		w.setColor(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		w.weight = 0.2;
		values.add(w);
		for (int i = 0; i < 20; i++) {
			w = getWord(Messages.AboutDialog_Cloudio, fontNames, colors);
			values.add(w);
			w = getWord(Messages.AboutDialog_TagCloud, fontNames, colors);
			values.add(w);
		}
		tc.setWords(values, null);
		Label l = new Label(parent, SWT.NONE);
		l.setText(Messages.AboutDialog_WrittenBy);
		return tc;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, RE_LAYOUT, Messages.AboutDialog_ReLayout, false);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == RE_LAYOUT) {
			tc.layoutCloud(null, false);
		} else {
			super.buttonPressed(buttonId);
		}
	}

	private Word getWord(String string, String[] fontNames, Color[] colors) {
		Word w = new Word(string);
		w.setColor(colors[(int) (Math.random() * colors.length - 1)]);
		w.weight = Math.random() / 2;
		w.setFontData(getShell().getFont().getFontData());
		w.angle = (float) (Math.random() * 20);
		if (Math.random() < 0.5) {
			w.angle = -w.angle;
		}
		String name = fontNames[(int) (Math.random() * (fontNames.length - 1))];
		for (FontData fd : w.getFontData()) {
			fd.setName(name);
		}
		return w;
	}

	@Override
	public boolean close() {
		tc.dispose();
		return super.close();
	}

}

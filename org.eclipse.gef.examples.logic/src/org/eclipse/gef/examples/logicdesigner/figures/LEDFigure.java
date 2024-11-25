package org.eclipse.gef.examples.logicdesigner.figures;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.handles.HandleBounds;

import org.eclipse.gef.examples.logicdesigner.model.LED;

public class LEDFigure extends NodeFigure implements HandleBounds {
	public static final Dimension SIZE = new Dimension(61, 47);

	public static final Color DISPLAY_TEXT = new Color(null, 255, 187, 51); // Warmer amber for better readability
	public static final Color BODY_COLOR = new Color(null, 67, 160, 120); // More vibrant teal-green
	public static final Color CONNECTOR_COLOR = new Color(null, 57, 150, 110); // Slightly darker for connectors

	// Modern font for display
	protected static final Font DISPLAY_FONT = new Font(null, "Consolas", 16, SWT.NORMAL);

	// Display rectangle
	protected static Rectangle displayRectangle = new Rectangle(15, 11, 31, 25);

	protected static PointList connector = new PointList();
	protected static PointList bottomConnector = new PointList();

	static {
		// Smoother connector shape
		connector.addPoint(-2, 0);
		connector.addPoint(2, 0);
		connector.addPoint(3, 1);
		connector.addPoint(3, 4);
		connector.addPoint(-2, 4);
		connector.addPoint(-2, 1);

		bottomConnector.addPoint(-2, 0);
		bottomConnector.addPoint(2, 0);
		bottomConnector.addPoint(3, -1);
		bottomConnector.addPoint(3, -4);
		bottomConnector.addPoint(-2, -4);
		bottomConnector.addPoint(-2, -1);
	}

	protected static final int[] GAP_CENTERS_X = { 8, 23, 38, 53 };
	protected static final int Y1 = 2;
	protected static final int Y2 = 44;
	private static final int CORNER_RADIUS = 6;

	protected String value;

	public LEDFigure() {
		FixedConnectionAnchor c;
		c = new FixedConnectionAnchor(this);
		c.offsetH = 51;
		connectionAnchors.put(LED.TERMINAL_1_IN, c);
		inputConnectionAnchors.add(c);
		c = new FixedConnectionAnchor(this);
		c.offsetH = 36;
		connectionAnchors.put(LED.TERMINAL_2_IN, c);
		inputConnectionAnchors.add(c);
		c = new FixedConnectionAnchor(this);
		c.offsetH = 21;
		connectionAnchors.put(LED.TERMINAL_3_IN, c);
		inputConnectionAnchors.add(c);
		c = new FixedConnectionAnchor(this);
		c.offsetH = 6;
		connectionAnchors.put(LED.TERMINAL_4_IN, c);
		inputConnectionAnchors.add(c);
		c = new FixedConnectionAnchor(this);
		c.offsetH = 51;
		c.topDown = false;
		connectionAnchors.put(LED.TERMINAL_1_OUT, c);
		outputConnectionAnchors.add(c);
		c = new FixedConnectionAnchor(this);
		c.offsetH = 36;
		c.topDown = false;
		connectionAnchors.put(LED.TERMINAL_2_OUT, c);
		outputConnectionAnchors.add(c);
		c = new FixedConnectionAnchor(this);
		c.offsetH = 21;
		c.topDown = false;
		connectionAnchors.put(LED.TERMINAL_3_OUT, c);
		outputConnectionAnchors.add(c);
		c = new FixedConnectionAnchor(this);
		c.offsetH = 6;
		c.topDown = false;
		connectionAnchors.put(LED.TERMINAL_4_OUT, c);
		outputConnectionAnchors.add(c);
	}

	@Override
	protected void paintFigure(Graphics g) {
		Rectangle r = getBounds().getCopy();
		g.translate(r.getLocation());

		// Draw main body with rounded corners using new color
		g.setBackgroundColor(BODY_COLOR);
		Rectangle mainBody = new Rectangle(0, 2, r.width, r.height - 4);
		g.fillRoundRectangle(mainBody, CORNER_RADIUS, CORNER_RADIUS);

		// Draw connectors with new colors
		drawConnectors(g, r);

		// Draw display with rounded corners
		g.setBackgroundColor(ColorConstants.black);
		g.fillRoundRectangle(displayRectangle, CORNER_RADIUS, CORNER_RADIUS);

		// Draw text
		if (value != null) {
			drawModernText(g);
		}
	}

	private void drawConnectors(Graphics g, Rectangle r) {
		for (int i = 0; i < 4; i++) {
			// Draw connector gaps
			g.setForegroundColor(ColorConstants.listBackground);
			g.drawLine(GAP_CENTERS_X[i] - 2, Y1, GAP_CENTERS_X[i] + 3, Y1);
			g.drawLine(GAP_CENTERS_X[i] - 2, Y2, GAP_CENTERS_X[i] + 3, Y2);

			// Use new connector color
			g.setForegroundColor(CONNECTOR_COLOR);
			g.setBackgroundColor(CONNECTOR_COLOR);

			connector.translate(GAP_CENTERS_X[i], 0);
			g.fillPolygon(connector);
			connector.translate(-GAP_CENTERS_X[i], 0);

			bottomConnector.translate(GAP_CENTERS_X[i], r.height - 1);
			g.fillPolygon(bottomConnector);
			bottomConnector.translate(-GAP_CENTERS_X[i], -(r.height - 1));
		}
	}

	private void drawModernText(Graphics g) {
		g.setFont(DISPLAY_FONT);
		Dimension textSize = FigureUtilities.getTextExtents(value, DISPLAY_FONT);

		// Center the text both horizontally and vertically
		int x = displayRectangle.x + (displayRectangle.width - textSize.width) / 2;
		int y = displayRectangle.y + (displayRectangle.height - textSize.height) / 2;

		g.setForegroundColor(DISPLAY_TEXT);
		g.drawText(value, new Point(x, y));
	}

	@Override
	public Rectangle getHandleBounds() {
		return getBounds().getShrinked(new Insets(2, 0, 2, 0));
	}

	@Override
	public Dimension getPreferredSize(int wHint, int hHint) {
		return SIZE;
	}

	public void setValue(int val) {
		String newValue = String.valueOf(val);
		if (val < 10) {
			newValue = "0" + newValue;
		}
		if (newValue.equals(value)) {
			return;
		}
		value = newValue;
		repaint();
	}

	@Override
	public String toString() {
		return "LEDFigure";
	}

	public void dispose() {
		DISPLAY_TEXT.dispose();
		BODY_COLOR.dispose();
		CONNECTOR_COLOR.dispose();
		DISPLAY_FONT.dispose();
	}

}
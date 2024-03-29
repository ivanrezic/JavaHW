package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;

/**
 * <code>BarChartComponent</code> represents bar chart as component.
 * It provided x,y axis wit description plus bar as rectangle for each xyValue given.
 *
 * @author Ivan Rezic
 */
public class BarChartComponent extends JComponent {

	/** Constant HATCH_LENGTH. */
	private static final int HATCH_LENGTH = 5;

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constant PADDING. */
	private static final int PADDING = 40;

	/** XYvalues. */
	private List<XYValue> values;

	/** X axis description. */
	private String xDescription;

	/** Y axis description. */
	private String yDescription;

	/** Max y on y axis if possible. */
	private int maxY;

	/** Min y on y axis. */
	private int minY;

	/** Spacing between each y grid. */
	private int spacing;

	/** X axis length. */
	private int xAxisLength;

	/** Y axis length. */
	private int yAxisLength;

	/** Each bar starting point. */
	private HashMap<Integer, Integer> barStartingPoints;

	/**
	 * Constructor which instantiates new bar chart component.
	 *
	 * @param barChart
	 *            {@linkplain BarChart}
	 */
	public BarChartComponent(BarChart barChart) {
		values = barChart.getValues();
		xDescription = barChart.getXDescription();
		yDescription = barChart.getYDescription();
		minY = barChart.getMinY();
		spacing = barChart.getSpacing();
		maxY = setProperMaxY(barChart.getMaxY());
		barStartingPoints = new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		yAxisLength = getHeight() - 2 * PADDING;
		xAxisLength = getWidth() - 2 * PADDING;
		int gap = (int) (1.0 / values.size() * xAxisLength);

		setWhiteBackground(g2d);
		setHatchMarksAndGridLinesForX(g2d, gap);
		setHatchMarksAndGridLinesForY(g2d);
		drawAxis(g2d);
		addDescriptions(g2d);
		drawRectangles(g2d, gap);
	}

	/**
	 * Get proper max y according to spacing needed.
	 *
	 * @param max
	 *            Current max y value.
	 * @return New max y value.
	 */
	private int setProperMaxY(int max) {
		while ((max++ - minY) % spacing != 0)
			;
		return max - 1;
	}

	/**
	 * Draw rectangles.
	 *
	 * @param g2d
	 *            {@linkplain Graphics2D}
	 * @param gap
	 *            The gap between hatches on x axis.
	 */
	private void drawRectangles(Graphics2D g2d, int gap) {
		for (int i = 0; i < values.size(); i++) {
			drawBar(g2d, values.get(i), gap);
		}
	}

	/**
	 * Draws bars.
	 *
	 * @param g2d
	 *            {@linkplain Graphics2D}
	 * @param xyValue
	 *            {@linkplain XYValue}
	 * @param gap
	 *            The gap between hatches on x axis.
	 */
	private void drawBar(Graphics2D g2d, XYValue xyValue, int gap) {
		int startX = barStartingPoints.get(xyValue.getX());
		int height = (int) (xyValue.getY() * (yAxisLength * 1.0 / maxY));

		g2d.setPaint(new Color(0f, 0f, 0f, 0.2f));
		g2d.fillRect(startX + 5, getHeight() - PADDING - height, gap, height);
		g2d.setColor(Color.decode("#f47748"));
		g2d.fillRect(startX + 1, getHeight() - PADDING - height - 1, gap, height);
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(0.5f));
		g2d.drawRect(startX + 1, getHeight() - PADDING - height - 1, gap, height);
	}

	/**
	 * Draws hatch marks and grid lines for Y.
	 *
	 * @param g2d
	 *            {@linkplain Graphics2D}
	 */
	private void setHatchMarksAndGridLinesForY(Graphics2D g2d) {
		int rows = (int) Math.ceil((maxY - minY) / (1.0 * spacing));

		int x0 = PADDING;
		int x1 = PADDING - HATCH_LENGTH;

		for (int i = 0; i <= rows; i++) {
			int y0 = PADDING + (int) (i * 1.0 / rows * yAxisLength);
			int y1 = y0;
			g2d.drawLine(x0, y0, x1, y1);

			String s = String.valueOf(maxY * (rows - i) / rows);
			FontMetrics fontMetrics = g2d.getFontMetrics();
			g2d.setFont(new Font("default", Font.BOLD, 12));
			g2d.drawString(s, x1 - fontMetrics.stringWidth(s), y1 + 5);

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawLine(x0, y0, x0 + xAxisLength, y1);
			g2d.setColor(Color.BLACK);
		}
	}

	/**
	 * Draws hatch marks and grid lines for X.
	 *
	 * @param g2d
	 *            {@linkplain Graphics2D}
	 * @param gap
	 *            Gap between hatches.
	 */
	private void setHatchMarksAndGridLinesForX(Graphics2D g2d, int gap) {
		int columns = values.size();
		int y0 = getHeight() - PADDING;
		int y1 = y0 + HATCH_LENGTH;

		for (int i = 0; i <= columns; i++) {
			int x0 = PADDING + i * gap;
			int x1 = x0;
			g2d.drawLine(x0, y0, x1, y1);

			if (i < columns) {
				String s = String.valueOf(i + 1);
				g2d.setFont(new Font("default", Font.BOLD, 12));
				g2d.drawString(s, x0 + gap / 2, y1 + 10);
				barStartingPoints.put(i + 1, x0);
			}

			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawLine(x0, y0, x1, y0 - yAxisLength);
			g2d.setColor(Color.BLACK);
		}
	}

	/**
	 * Method which sets white background for chart.
	 *
	 * @param g2d
	 *            {@linkplain Graphics2D}
	 */
	private void setWhiteBackground(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(PADDING, PADDING, getWidth() - 2 * PADDING, getHeight() - 2 * PADDING);
		g2d.setColor(Color.BLACK);
	}

	/**
	 * Draws axis.
	 *
	 * @param g2d
	 *            {@linkplain Graphics2D}
	 */
	private void drawAxis(Graphics2D g2d) {
		g2d.drawLine(PADDING, PADDING, PADDING, getHeight() - PADDING);// y-axis
		g2d.drawLine(PADDING, getHeight() - PADDING, getWidth() - PADDING, getHeight() - PADDING);// x-axis
		g2d.fillPolygon(new int[] { PADDING - 3, PADDING, PADDING + 3 }, new int[] { PADDING, PADDING - 6, PADDING },
				3);
		g2d.fillPolygon(new int[] { getWidth() - PADDING, getWidth() - PADDING + 6, getWidth() - PADDING },
				new int[] { getHeight() - PADDING - 3, getHeight() - PADDING, getHeight() - PADDING + 3 }, 3);
	}

	/**
	 * Adds the descriptions besides x and y axis.
	 *
	 * @param g2d
	 *            {@linkplain Graphics2D}
	 */
	private void addDescriptions(Graphics2D g2d) {
		int xDescriptionWidth = g2d.getFontMetrics().stringWidth(xDescription);
		int yDescriptionWidth = g2d.getFontMetrics().stringWidth(yDescription);

		AffineTransform defaultAt = g2d.getTransform();
		g2d.drawString(xDescription, getWidth() / 2 - xDescriptionWidth / 2, getHeight() - (PADDING / 4));
		AffineTransform at = AffineTransform.getQuadrantRotateInstance(3);
		g2d.setTransform(at);
		g2d.drawString(yDescription, -getHeight() / 2 - yDescriptionWidth / 2, PADDING / 3);
		g2d.setTransform(defaultAt);
	}

}

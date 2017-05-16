package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * A bar chart or bar graph is a chart or graph that presents grouped data with
 * rectangular bars with lengths proportional to the values that they
 * represent.
 *
 * @author Ivan Rezic
 */
public class BarChart {

	/** List containing {@linkplain XYValue}s. */
	private List<XYValue> values;

	/** X axis description. */
	private String xDescription;

	/** Y axis description. */
	private String yDescription;

	/** Max y value on y axis. */
	private int maxY;

	/** Max x value on x axis. */
	private int minY;

	/** Spacing between hatches on x and y axis. */
	private int spacing;

	/**
	 * Constructor which instantiates new bar chart.
	 *
	 * @param values
	 *            {@link #values}
	 * @param xDescription
	 *            {@link #xDescription}
	 * @param yDescription
	 *            {@link #yDescription}
	 * @param minY
	 *            {@link #minY}
	 * @param maxY
	 *            {@link #maxY}
	 * @param spacing
	 *            {@link #spacing}
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int spacing) {
		super();
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.maxY = maxY;
		this.minY = minY;
		this.spacing = spacing;
	}

	/**
	 * Method used for getting property <code>Values</code>.
	 *
	 * @return values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Method used for getting property <code>XDescription</code>.
	 *
	 * @return x description
	 */
	public String getXDescription() {
		return xDescription;
	}

	/**
	 * Method used for getting property <code>YDescription</code>.
	 *
	 * @return y description
	 */
	public String getYDescription() {
		return yDescription;
	}

	/**
	 * Method used for getting property <code>MaxY</code>.
	 *
	 * @return max Y
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Method used for getting property <code>MinY</code>.
	 *
	 * @return min Y
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Method used for getting property <code>Spacing</code>.
	 *
	 * @return spacing
	 */
	public int getSpacing() {
		return spacing;
	}

}

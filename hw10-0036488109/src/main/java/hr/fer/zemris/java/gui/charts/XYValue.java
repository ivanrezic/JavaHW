package hr.fer.zemris.java.gui.charts;

/**
 * <code>XYValue</code> class represents bar position and value in
 * {@linkplain BarChartComponent}.
 *
 * @author Ivan Rezic
 */
public class XYValue {

	/** Bar position in chart. */
	private final int x;

	/** Bar value in chart. */
	private final int y;

	/**
	 * Constructor which instantiates new XY value.
	 *
	 * @param x
	 *            {@link #x}
	 * @param y
	 *            {@link #x}
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Method used for getting property <code>X</code>.
	 *
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Method used for getting property <code>Y</code>.
	 *
	 * @return y
	 */
	public int getY() {
		return y;
	}

}

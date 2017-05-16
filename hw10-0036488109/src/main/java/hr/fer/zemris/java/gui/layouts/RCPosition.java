package hr.fer.zemris.java.gui.layouts;

/**
 * <code>RCPosition</code> class which represents component position in
 * calculator layout.
 *
 * @author Ivan Rezic
 */
public class RCPosition {

	/** Row position. */
	private final int row;

	/** Column position. */
	private final int column;

	/**
	 * Constructor which instantiates new RC position.
	 *
	 * @param row
	 *            the row
	 * @param column
	 *            the column
	 */
	public RCPosition(int row, int column) {
		if (row <= 0 || column <= 0) {
			throw new IllegalArgumentException("Number of rows and columns must be greater than zero.");
		}

		this.row = row;
		this.column = column;
	}

	/**
	 * Method used for getting property <code>Row</code>.
	 *
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Method used for getting property <code>Column</code>.
	 *
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}

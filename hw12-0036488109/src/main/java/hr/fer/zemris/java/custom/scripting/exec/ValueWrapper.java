package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * <code>ValueWrapper</code> represents container which wraps
 * <code>Object</code> type values. Allowed value types are:
 * Integer,Double,null(which will be represented as Integer with value of 0) and
 * String which is converted to Integer or Double. Also it provides arithmetic
 * operations and comparison between its value and given values, despite they
 * may be different type.
 * 
 * @author Ivan Rezic
 */
public class ValueWrapper {

	/** Value wrapped in <code>ValueWrapper</code> */
	private Object value;

	/**
	 * Constructor which instantiates new value wrapper.
	 *
	 * @param value
	 *            value to be wrapped
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Method used for getting property <code>Value</code>.
	 *
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Method which sets new value as value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Method which adds given value to callers object value. After performing
	 * this action, given value is not changed while callers value is.
	 *
	 * @param incValue
	 *            value to be added
	 */
	public void add(Object incValue) {
		executeAction(incValue, (a, b) -> a.doubleValue() + b.doubleValue());
	}

	/**
	 * Method which subtracts given value to callers object value. After
	 * performing this action, given value is not changed while callers value
	 * is.
	 *
	 * @param incValue
	 *            value which will be subtracted from current one
	 */
	public void subtract(Object decValue) {
		executeAction(decValue, (a, b) -> a.doubleValue() - b.doubleValue());
	}

	/**
	 * Method which multiplies given value with callers object value. After
	 * performing this action, given value is not changed while callers value
	 * is.
	 *
	 * @param decValue
	 *            value which will be multiplied with current one
	 */
	public void multiply(Object mulValue) {
		executeAction(mulValue, (a, b) -> a.doubleValue() * b.doubleValue());
	}

	/**
	 * Method which multiplies given value with callers object value. After
	 * performing this action, given value is not changed while callers value
	 * is.
	 *
	 * @param mulValue
	 *            value which will be divided with current one
	 * 
	 * @throws ArithmeticException
	 *             if value is null or 0
	 */
	public void divide(Object divValue) {
		Number help = getCorrectType(divValue);

		if (help.doubleValue() == 0.0) {
			throw new ArithmeticException("Can not divide with 0 or null");
		}

		executeAction(divValue, (a, b) -> a.doubleValue() / b.doubleValue());
	}

	/**
	 * Method which compares two values.
	 *
	 * @param withValue
	 *            value to be compared with
	 * @return
	 *         <ul>
	 *         <li>0 if equal</li>
	 *         <li>-1 if current is less than given value</li>
	 *         <li>1 if current is greater than given value</li>
	 *         </ul>
	 */
	public int numCompare(Object withValue) {
		Double first = getCorrectType(value).doubleValue();
		Double second = getCorrectType(withValue).doubleValue();

		return first.compareTo(second);
	}

	/**
	 * Helper method which transform null to Integer with value 0, or string to
	 * Double if contains "." , "E", "e" otherwise to Integer.
	 *
	 * @param value
	 *            the value to be correcte if needed
	 * @return corrected value as type <code>Number</code>
	 * 
	 * @throws RuntimeException
	 *             if given type is not null, Integer, Double or String.
	 */
	private Number getCorrectType(Object value) {
		if (value == null) {
			return Integer.valueOf(0);
		} else if (value instanceof String) {
			String help = (String) value;

			return parseStringValue(help);
		} else if (value instanceof Double || value instanceof Integer) {
			return (Number) value;
		}

		throw new RuntimeException("Type not allowed.");
	}

	/**
	 * {@link #getCorrectType(Object)} helper method which parses string.
	 *
	 * @param help
	 *            string to be parsed
	 * 
	 * @return number value of string
	 * 
	 * @throws RuntimeException
	 *             if string cannot be parsed to <code>Number</code>
	 */
	private Number parseStringValue(String help) {
		try {
			if (help.contains(".") || help.contains("E") || help.contains("e")) {
				return Double.parseDouble(help);
			} else {
				return Integer.parseInt(help);
			}
		} catch (NumberFormatException e) {
			throw new RuntimeException("Can not add string to numbers.");
		}
	}

	/**
	 * Helper method which conducts arithmetic operations defined by BiFunction
	 * interface.
	 *
	 * @param other
	 *            value which will be used for arithmetic operations
	 * @param function
	 *            arithmetic operation
	 */
	private void executeAction(Object other, BiFunction<Number, Number, Number> function) {
		Number first = getCorrectType(value);
		Number second = getCorrectType(other);

		if (first instanceof Integer && second instanceof Integer) {
			this.value = (Integer) function.apply(first, second).intValue();
		} else {
			this.value = (Double) function.apply(first, second);
		}
	}
//	private void executeAction(Object other, BiFunction<Number, Number, Number> function) {
//		Number first = getCorrectType(value);
//		Number second = getCorrectType(other);
//
//		if (first instanceof Integer && second instanceof Integer) {
//			Double variable = (Double) function.apply(first, second);
//			if ((variable == Math.floor(variable)) && !Double.isInfinite(variable)) {
//				this.value = (Integer) variable.intValue();
//				return;
//			}
//
//			this.value = variable;
//		} else {
//			this.value = (Double) function.apply(first, second);
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value + "";
	}
}

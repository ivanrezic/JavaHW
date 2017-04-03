package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

public class ValueWrapper {
	private Object value;

	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void add(Object incValue) {
		executeAction(incValue, (a, b) -> a.doubleValue() + b.doubleValue());
	}

	public void subtract(Object decValue) {
		executeAction(decValue, (a, b) -> a.doubleValue() - b.doubleValue());
	}

	public void multiply(Object mulValue) {
		executeAction(mulValue, (a, b) -> a.doubleValue() * b.doubleValue());
	}

	public void divide(Object divValue) {
		Number help = getCorrectType(divValue);

		if (help.doubleValue() == 0.0) {
			throw new ArithmeticException("Can not divide with 0 or null");
		}

		executeAction(divValue, (a, b) -> a.doubleValue() / b.doubleValue());
	}

	public int numCompare(Object withValue) {
		Double first = getCorrectType(value).doubleValue();
		Double second = getCorrectType(withValue).doubleValue();
		
		return first.compareTo(second);
	}

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

	private void executeAction(Object other, BiFunction<Number, Number, Number> function) {
		Number first = getCorrectType(value);
		Number second = getCorrectType(other);

		if (first instanceof Integer && second instanceof Integer) {
			this.value = (Integer)function.apply(first, second).intValue();
		}else{
			this.value = (Double) function.apply(first, second);			
		}
	}

	@Override
	public String toString() {
		return value + "";
	}

	public static void main(String[] args) {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores
								// null.
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores
								// Integer(1).
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores
								// Integer(1).
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
//		v7.add(v8.getValue()); // throws RuntimeException
		
		System.out.println(v1.numCompare(v2.getValue())); // 0
		System.out.println(v3.numCompare(v4.getValue())); // 1
		System.out.println(v5.numCompare(v6.getValue())); // 1
		System.out.println(v6.numCompare(v5.getValue())); // -1
	}
}

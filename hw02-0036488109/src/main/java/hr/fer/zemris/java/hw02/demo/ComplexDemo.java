package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

public class ComplexDemo {

	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];

		ComplexNumber c9 = ComplexNumber.fromMagnitudeAndAngle(c3.getMagnitude(), c3.getAngle());
		System.out.println(c9);
		System.out.println();

		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);

	}

}

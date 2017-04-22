package hr.fer.zemris.bf.demo;

import java.util.Arrays;

import hr.fer.zemris.bf.utils.Util;

/**
 * <code>ForEachDemo1</code> class is demonstration class. It prints to standard
 * output all table of truth combinations for given variables.
 *
 * @author Ivan Rezic
 */
public class ForEachDemo1 {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		Util.forEach(Arrays.asList("A", "B", "C"),
				values -> System.out.println(Arrays.toString(values).replaceAll("true", "1").replaceAll("false", "0")));
	}

}

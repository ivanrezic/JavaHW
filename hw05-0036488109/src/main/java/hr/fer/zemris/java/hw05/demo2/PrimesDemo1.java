package hr.fer.zemris.java.hw05.demo2;

/**
 * <code>PrimesDemo1</code> is class use dfor demonstration purposes.
 *
 * @author Ivan Rezic
 */
public class PrimesDemo1 {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args the arguments from command line, not used here
	 */
	public static void main(String[] args) {

		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}

package hr.fer.zemris.java.hw05.demo2;

/**
 * <code>PrimesDemo2</code> is class used for demonstration purposes.
 *
 * @author Ivan Rezic
 */
public class PrimesDemo2 {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}

}

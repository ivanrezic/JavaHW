package hr.fer.zemris.bf.demo2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <code>Logiranje</code> is demonstration class to show how each logging level
 * operates and which messages outputs..
 *
 * @author Ivan Rezic
 */
public class Logiranje {

	/** Constant LOG. */
	private static final Logger LOG = Logger.getLogger("demo2");

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		Level[] levels = new Level[] { Level.SEVERE, Level.WARNING, Level.INFO, Level.CONFIG, Level.FINE, Level.FINER,
				Level.FINEST };
		for (Level l : levels) {
			LOG.log(l, "Ovo je poruka " + l + " razine.");
		}
	}
}
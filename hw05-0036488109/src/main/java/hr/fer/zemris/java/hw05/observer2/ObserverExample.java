package hr.fer.zemris.java.hw05.observer2;

/**
 * <code>ObserverExample</code> is demonstration class.
 *
 * @author Ivan Rezic
 */
public class ObserverExample {
	
	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		
		istorage.addObserver(new SquareValue());
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}

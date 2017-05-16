package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * <code>PrimDemo</code> is demonstration class. It implements JFrame which
 * enables us to display our data as swing component consisting two list in
 * which generated prime numbers are stored.Each number is generated upon
 * pressing 'Next prime' button.
 *
 * @author Ivan Rezic
 */
public class PrimDemo extends JFrame {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new primDemo.
	 *
	 * @param name
	 *            name displayed on our frame
	 */
	public PrimDemo(String name) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(name);
		setLocation(20, 20);
		setSize(50, 300);

		initGUI();
	}

	/**
	 * Inits the GUI.
	 */
	private void initGUI() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridLayout(1, 2));
		PrimListModel model = new PrimListModel();
		model.addElement(1);
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		panel.add(new JScrollPane(list1));
		panel.add(new JScrollPane(list2));

		JButton next = new JButton("Next prime");
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.next();
			}
		});
		container.add(panel, BorderLayout.CENTER);
		container.add(next, BorderLayout.SOUTH);

	}

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo("PrimDemo").setVisible(true));
	}
}

package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * <code>BarChartDemo</code> is program which for given file path containing
 * proper data, draws bar chart.
 * 
 * <pre>
 * Example of valid file:
 * 
 * Number of people in the car	description on X
 * Frequency			description on X
 * 1,8 2,20 3,22 4,10 5,4		xyValues divided by space
 * 0				minY
 * 22				maxY
 * 2				spacing
 * </pre>
 * 
 * @author Ivan Rezic
 */
public class BarChartDemo extends JFrame {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Lines of input file. */
	private List<String> lines;

	/** Path to the input file. */
	private String path;

	/**
	 * Constructor which instantiates new bar chart demo.
	 *
	 * @param lines
	 *            {@link #lines}
	 * @param path
	 *            {@link #path}
	 * @param name
	 *            Program frame name.
	 */
	public BarChartDemo(List<String> lines, Path path, String name) {
		this.path = path.toString();
		this.lines = lines;

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(name);
		setLocation(20, 20);
		setSize(600, 400);
		initGUI();
	}

	/**
	 * Helper method which initializes the GUI.
	 */
	private void initGUI() {
		Container frame = getContentPane();

		JLabel label = new JLabel(path);
		label.setHorizontalAlignment(JLabel.CENTER);
		BarChart model = getBarChartModel(lines);

		frame.setLayout(new BorderLayout());
		frame.add(new BarChartComponent(model), BorderLayout.CENTER);
		frame.add(label, BorderLayout.NORTH);
	}

	/**
	 * Method used for extracting <code>BarChartModel</code> from file.
	 *
	 * @param data
	 *            the data
	 * @return bar chart model
	 */
	private BarChart getBarChartModel(List<String> data) {
		String descriptionX = data.get(0);
		String descriptionY = data.get(1);
		String[] xy = data.get(2).split(" ");
		List<XYValue> values = extractValues(xy);
		int minY = Integer.parseInt(data.get(3));
		int maxY = Integer.parseInt(data.get(4));
		int spacing = Integer.parseInt(data.get(5));

		return new BarChart(values, descriptionX, descriptionY, minY, maxY, spacing);
	}

	/**
	 * Helper method which from array of xyValues extracts xyValues one by one.
	 *
	 * @param xy
	 *            Array containing xyValue objects.
	 * @return the list
	 */
	private List<XYValue> extractValues(String[] xy) {
		List<XYValue> values = new ArrayList<>();

		for (String xyValue : xy) {
			String[] parts = xyValue.split(",");
			values.add(new XYValue(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		}

		return values;
	}

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, one argument used which
	 *            represents path to data file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected one argument(file path).");
			System.exit(1);
		}

		Path path = Paths.get(args[0].trim());
		List<String> lines = Files.readAllLines(path);

		SwingUtilities.invokeLater(() -> new BarChartDemo(lines, path, "BarChart").setVisible(true));
	}
}

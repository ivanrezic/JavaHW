package hr.fer.zemris.java.gui.charts;

import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	private List<String> lines;

	public BarChartDemo(List<String> lines, String name) {
		this.lines = lines;

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(name);
		setLocation(20, 20);
		setSize(600, 400);
		initGUI();
	}

	private void initGUI() {
		Container frame = getContentPane();

		BarChart model = getBarChartModel(lines);

		frame.add(new BarChartComponent(model));
	}

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

	private List<XYValue> extractValues(String[] xy) {
		List<XYValue> values = new ArrayList<>();

		for (String xyValue : xy) {
			String[] parts = xyValue.split(",");
			values.add(new XYValue(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		}

		return values;
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected one argument(file path).");
			System.exit(1);
		}

		Path path = Paths.get(args[0].trim());
		List<String> lines = Files.readAllLines(path);

		SwingUtilities.invokeLater(() -> new BarChartDemo(lines, "BarChart").setVisible(true));
	}
}

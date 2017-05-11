package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {

	private List<XYValue> values;
	private String xDescription;
	private String yDescription;
	private int maxY;
	private int minY;
	private int spacing;

	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int spacing) {
		super();
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.maxY = maxY;
		this.minY = minY;
		this.spacing = spacing;
	}

	public List<XYValue> getValues() {
		return values;
	}

	public String getXDescription() {
		return xDescription;
	}

	public String getYDescription() {
		return yDescription;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMinY() {
		return minY;
	}

	public int getSpacing() {
		return spacing;
	}

	
}

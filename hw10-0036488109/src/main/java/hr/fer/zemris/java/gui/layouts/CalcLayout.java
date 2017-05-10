package hr.fer.zemris.java.gui.layouts;

import static java.lang.Math.max;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class CalcLayout implements LayoutManager2 {
	private final int MAX_COMPONENTS = 31;
	private final int COLUMNS = 7;
	private final int ROWS = 5;

	private int spacing;
	private Map<Component, RCPosition> componentPosition;

	public CalcLayout() {
		this(0);
	}

	public CalcLayout(int spacing) {
		if (spacing < 0) {
			throw new IllegalArgumentException("Spacing can not be less than zero.");
		}

		this.spacing = spacing;
		this.componentPosition = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void layoutContainer(Container parent) {
		Dimension dimension = dimensionForEach(parent);
		RCPosition first = new RCPosition(1, 1);

		for (Map.Entry<Component, RCPosition> entry : componentPosition.entrySet()) {
			Component component = entry.getKey();
			RCPosition position = entry.getValue();

			if (position.equals(first)) {
				component.setBounds(0, 0, 5 * dimension.width + 4 * spacing, dimension.height);
				continue;
			}

			int row = position.getRow();
			int col = position.getColumn();
			component.setBounds((col - 1) * (dimension.width + spacing), (dimension.height + spacing) * (row - 1),
					dimension.width, dimension.height);
		}
	}

	private Dimension dimensionForEach(Container parent) {
		Dimension dimension = preferredLayoutSize(parent);
		Insets insets = parent.getInsets();

		int width = dimension.width - insets.left - insets.right - (COLUMNS - 1) * spacing;
		int height = dimension.height - insets.top - insets.bottom - (ROWS - 1) * spacing;

		height *= (parent.getHeight() * 1.0) / (dimension.height * ROWS);
		width *= (parent.getWidth() * 1.0) / (dimension.width * COLUMNS);

		return new Dimension(width, height);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimension(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimension(parent, Component::getPreferredSize);
	}

	private Dimension getDimension(Container parent, Function<Component, Dimension> unary) {
		RCPosition first = new RCPosition(1, 1);
		int height = 0;
		int width = 0;

		for (Map.Entry<Component, RCPosition> entry : componentPosition.entrySet()) {
			Dimension dimension = unary.apply(entry.getKey());
			RCPosition component = entry.getValue();

			if (dimension != null) {
				height = max(height, dimension.height);
				if (!component.equals(first)) {
					width = max(width, dimension.width);
				}
			}
		}

		Insets insets = parent.getInsets();
		return new Dimension(COLUMNS * width + insets.left + insets.right + (COLUMNS - 1) * spacing,
				ROWS * height + insets.top + insets.bottom + (ROWS - 1) * spacing);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		componentPosition.remove(comp);
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp, "Null component given.");
		if (componentPosition.size() == MAX_COMPONENTS) {
			throw new IllegalArgumentException("Layout components space exceeded.");
		}

		if (constraints instanceof RCPosition) {
			componentPosition.put(comp, (RCPosition) constraints);
		} else {
			try {
				String[] parts = ((String) constraints).split(",", 2);
				int row = Integer.parseInt(parts[0]);
				int column = Integer.parseInt(parts[1]);

				componentPosition.put(comp, new RCPosition(row, column));
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException | ClassCastException e) {
				throw new IllegalArgumentException("Given constraints are not valid type.");
			}
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getDimension(target, Component::getMaximumSize);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("CalcLayout");

		frame.setLocation(20, 20);
		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("x"), "1,1");
		p.add(new JLabel("y"), "2,3");
		p.add(new JLabel("z"), "2,7");
		p.add(new JLabel("w"), "4,2");
		p.add(new JLabel("a"), "4,5");
		p.add(new JLabel("b"), "4,7");


		frame.add(p);

		frame.setVisible(true);

	}
}

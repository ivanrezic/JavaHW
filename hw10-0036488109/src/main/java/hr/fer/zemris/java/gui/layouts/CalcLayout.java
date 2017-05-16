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

/**
 * <code>CalcLayout</code> represents out calculator layout, it consists of one
 * position saved for screen and 29 others for buttons and one for checkboxes.
 *
 * @author Ivan Rezic
 */
public class CalcLayout implements LayoutManager2 {

	/** Max components in layout. */
	private final int MAX_COMPONENTS = 31;

	/** Number of columns. */
	private final int COLUMNS = 7;

	/** Number of rows */
	private final int ROWS = 5;

	/** Spacing between components. */
	private int spacing;

	/** Component positions. */
	private Map<Component, RCPosition> componentPosition;

	/**
	 * Constructor which instantiates new calcLayout.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor which instantiates new calcLayout.
	 *
	 * @param spacing
	 *            the spacing
	 */
	public CalcLayout(int spacing) {
		if (spacing < 0) {
			throw new IllegalArgumentException("Spacing can not be less than zero.");
		}

		this.spacing = spacing;
		this.componentPosition = new HashMap<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
	 * java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
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

	/**
	 * Helper method which get prefered dimension for each component except
	 * first one.
	 *
	 * @param parent
	 *            the parent container
	 * @return the dimension
	 */
	private Dimension dimensionForEach(Container parent) {
		Dimension dimension = preferredLayoutSize(parent);
		Insets insets = parent.getInsets();

		int width = dimension.width - insets.left - insets.right - (COLUMNS - 1) * spacing;
		int height = dimension.height - insets.top - insets.bottom - (ROWS - 1) * spacing;

		height *= (parent.getHeight() * 1.0) / (dimension.height * ROWS);
		width *= (parent.getWidth() * 1.0) / (dimension.width * COLUMNS);

		return new Dimension(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimension(parent, Component::getMinimumSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimension(parent, Component::getPreferredSize);
	}

	/**
	 * Helper method which gets wanted size for given container.
	 *
	 * @param parent
	 *            the parent container
	 * @param unary
	 *            the unary operator
	 * @return dimension
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		componentPosition.remove(comp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component,
	 * java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
	 */
	@Override
	public void invalidateLayout(Container target) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getDimension(target, Component::getMaximumSize);
	}
}

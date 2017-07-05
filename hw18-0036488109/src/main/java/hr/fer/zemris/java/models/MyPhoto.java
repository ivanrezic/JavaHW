package hr.fer.zemris.java.models;

import java.util.List;

/**
 * <code>MyPhoto</code> is model which represents one picture and its descriptions in our "database".
 *
 * @author Ivan Rezic
 */
public class MyPhoto {
	
	/** Picture name. */
	private String name;

	/** Picture description. */
	private String description;

	/** Picture tags. */
	private List<String> tags;

	/**
	 * Constructor which instantiates new my photo.
	 *
	 * @param name the name
	 * @param description the description
	 * @param tags the tags
	 */
	public MyPhoto(String name, String description, List<String> tags) {
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Method used for getting property <code>Name</code>.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method used for getting property <code>Description</code>.
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method used for getting property <code>Tags</code>.
	 *
	 * @return tags
	 */
	public List<String> getTags() {
		return tags;
	}
}

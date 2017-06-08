package hr.fer.zemris.java.p12.model;

/**
 * <code>Poll</code> represents Polls entry.
 *
 * @author Ivan Rezic
 */
public class Poll {

	/** Polls entry id. */
	private long id;
	
	/** Polls entry title. */
	private String title;
	
	/** Polls entry message. */
	private String message;

	/**
	 * Constructor which instantiates Polls entry model.
	 *
	 * @param Entry id.
	 * @param Entry title.
	 * @param Entry message.
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Method used for getting property <code>Id</code>.
	 *
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Method used for getting property <code>Title</code>.
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method used for getting property <code>Message</code>.
	 *
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

}

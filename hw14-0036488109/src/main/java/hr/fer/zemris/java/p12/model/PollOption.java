package hr.fer.zemris.java.p12.model;

/**
 * <code>PollOptions</code> represents PollOptions entry.
 *
 * @author Ivan Rezic
 */
public class PollOption implements Comparable<PollOption>{

	/** PollOptions entry id. */
	private long id;
	
	/** PollOptions entry title. */
	private String optionTitle;
	
	/** PollOptions entry link. */
	private String optionLink;
	
	/** PollOptions entry poll ID. */
	private long pollID;
	
	/** PollOptions entry votes count. */
	private long votesCount;

	/**
	 * Constructor which instantiates new PollOptions model.
	 *
	 * @param id PollOptions entry id.
	 * @param optionTitle PollOptions entry title.
	 * @param optionLink PollOptions entry link.
	 * @param pollID PollOptions entry poll ID.
	 * @param votesCount PollOptions entry votes count.
	 */
	public PollOption(long id, String optionTitle, String optionLink, long pollID, long votesCount) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
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
	 * Method used for getting property <code>OptionTitle</code>.
	 *
	 * @return option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Method used for getting property <code>OptionLink</code>.
	 *
	 * @return option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Method used for getting property <code>PollID</code>.
	 *
	 * @return poll ID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Method used for getting property <code>VotesCount</code>.
	 *
	 * @return votes count
	 */
	public long getVotesCount() {
		return votesCount;
	}
	
	/**
	 * Method which sets new value as votesCount.
	 *
	 * @param votesCount the new votes count
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PollOption o) {
		return -Long.compareUnsigned(this.votesCount, o.votesCount);
	}

}

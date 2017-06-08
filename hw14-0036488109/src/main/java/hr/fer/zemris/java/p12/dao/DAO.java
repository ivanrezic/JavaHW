package hr.fer.zemris.java.p12.dao;

import java.nio.file.Path;
import java.sql.Connection;
import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Interface which provides database connectivity and communication(storing and
 * retrieving data).
 * 
 * @author Ivan Rezic
 *
 */

public interface DAO {
	
	/**
	 * Creates the table Polls.
	 *
	 * @param con Connection to the database.
	 */
	public void createTablePolls(Connection con);
	
	/**
	 * Creates the table PollOptions.
	 *
	 * @param con Connection to the database.
	 */
	public void createTablePollOptions(Connection con);

	/**
	 * Checks if is table empty.
	 *
	 * @param tableName Database table name.
	 * @param con Connection to the database.
	 * @return True, if it is table empty, false otherwise.
	 */
	public boolean isTableEmpty(String tableName, Connection con);

	/**
	 * Populate Polls.
	 *
	 * @param Path to text files containing data.
	 * @param con Connection to the database.
	 */
	public void populatePolls(Path path, Connection con);

	/**
	 * Populate PollOptions.
	 *
	 * @param path the path
	 * @param con Connection to the database.
	 */
	public void populatePollOptions(Path path, Connection con);

	/**
	 * Gets all Polls entries.
	 *
	 * @return {@linkplain Poll}
	 */
	public List<Poll> getPollsData();

	/**
	 * Gets all options for specified poll.
	 *
	 * @param id Poll id.
	 * @return {@linkplain PollOptions}
	 */
	public List<PollOption> getPollOptions(long id);

	/**
	 * Updates option votes count in PollOptions for specified id.
	 *
	 * @param selectedOptionId Selected option id.
	 */
	public void updateOptionVotesCount(long selectedOptionId);
}
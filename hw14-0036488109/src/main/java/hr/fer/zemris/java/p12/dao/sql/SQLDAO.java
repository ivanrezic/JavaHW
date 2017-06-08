package hr.fer.zemris.java.p12.dao.sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This is DAO interface implementation with SQL technology. This implementation
 * expects that there is always available connection through
 * {@link SQLConnectionProvider} class.
 * 
 * @author Ivan Rezic
 */
public class SQLDAO implements DAO {
	
	/** Status code which tells us that table already exists. */
	private static final String TABLE_EXISTS = "X0Y32";
	

	@Override
	public void createTablePolls(Connection con) {
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement("CREATE TABLE Polls ("
 					+ "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " title VARCHAR(150) NOT NULL,"
 					+ " message CLOB(2048) NOT NULL)"
					);
			pst.execute();
		}catch (SQLException e) {
			if (!e.getSQLState().equals(TABLE_EXISTS)) {
				e.printStackTrace();
				System.exit(0);
			}
		}finally {
			try {
				pst.close();
			} catch (Exception ignorable) {
			}
		}
	}

	@Override
	public void createTablePollOptions(Connection con) {
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement("CREATE TABLE PollOptions ("
 					+ "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " optionTitle VARCHAR(100) NOT NULL,"
 					+ " optionLink VARCHAR(150) NOT NULL,"
 					+ " pollID BIGINT,"
 					+ " votesCount BIGINT,"
 					+ " FOREIGN KEY (pollID) REFERENCES Polls(id))"
					);
			pst.execute();
		}catch (SQLException e) {
			if (!e.getSQLState().equals(TABLE_EXISTS)) {
				e.printStackTrace();
				System.exit(0);
			}
		}finally {
			try {
				pst.close();
			} catch (Exception ignorable) {
			}
		}
	}

	@Override
	public boolean isTableEmpty(String tableName, Connection con) {
		PreparedStatement pst = null;
		int count = 0;
		
		try {
			pst = con.prepareStatement("SELECT COUNT(*) FROM " + tableName );
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						count = rs.getInt(1);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error during count query.", ex);
		}
		
		return count == 0;
	}

	@Override
	public void populatePolls(Path path, Connection con) {
		PreparedStatement pst = null;
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(path);
		} catch (IOException ignorable) {}
		
		for (String line : lines) {
			String[] parts = line.split("\t");
			
			try {
				pst = con.prepareStatement(
					"INSERT INTO Polls (title, message) values (?,?)", 
					Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, parts[0]);
				pst.setString(2, parts[1]);

				pst.executeUpdate();
			} catch(SQLException ex) {
				ex.printStackTrace();
			} finally {
				try { pst.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void populatePollOptions(Path path, Connection con) {
		PreparedStatement pst = null;
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(path);
		} catch (IOException ignorable) {}
		
		for (String line : lines) {
			String[] parts = line.split("\t");
			
			try {
				pst = con.prepareStatement(
					"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)", 
					Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, parts[0]);
				pst.setString(2, parts[1]);
				pst.setString(3, parts[2]);
				pst.setString(4, parts[3]);

				pst.executeUpdate();
			} catch(SQLException ex) {
				ex.printStackTrace();
			} finally {
				try { pst.close(); } catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}		
	}

	@Override
	public List<Poll> getPollsData() {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						polls.add(new Poll(rs.getLong(1), rs.getString(2), rs.getString(3)));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error while fetching data.", ex);
		}
		
		return polls;
	}

	@Override
	public List<PollOption> getPollOptions(long pollID) {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, optionTitle, optionLink, pollID, votesCount FROM PollOptions WHERE pollID=? ORDER BY id");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollOption option = new PollOption(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4), rs.getLong(5));
						options.add(option);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Error while fetching data.", ex);
		}
		
		return options;
	}

	@Override
	public void updateOptionVotesCount(long selectedOptionId) {
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("UPDATE PollOptions SET votesCount=votesCount+1 WHERE id=?");
			pst.setLong(1, selectedOptionId);
			pst.executeUpdate();
		} catch(Exception ex) {
			throw new DAOException("Error while fetching data.", ex);
		}finally {
			try { pst.close(); } catch(Exception ignorable) {}
		}
	}

}
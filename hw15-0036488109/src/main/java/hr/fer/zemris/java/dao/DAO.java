package hr.fer.zemris.java.dao;

import java.util.List;

import hr.fer.zemris.java.model.BlogComment;
import hr.fer.zemris.java.model.BlogEntry;
import hr.fer.zemris.java.model.BlogUser;

/**
 * <code>DAO</code> class which encapsulates all JPQL statements.
 *
 * @author Ivan Rezic
 */
public interface DAO {
	
	/**
	 * Method used for getting all users.
	 *
	 * @return users
	 */
	public List<BlogUser> getUsers();
	
	/**
	 * User by nick and password.
	 *
	 * @param nick user nick
	 * @param password user password
	 * @return the blog user
	 */
	public BlogUser userByNickAndPassword(String nick, String password);
	
	/**
	 * Adds the blog user.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param nick the nick
	 * @param password the password
	 */
	public void addBlogUser(String firstName, String lastName, String email, String nick, String password);
	
	/**
	 * Method used for getting user by nickname.
	 *
	 * @param nick user nick
	 * @return blog user by nick
	 */
	public BlogUser getBlogUserByNick(String nick);
	
	/**
	 * Method used for getting all user blogs by nick.
	 *
	 * @param nick the nick
	 * @return user blogs by nick
	 */
	public List<BlogEntry> getUserBlogsByNick(String nick);
	
	/**
	 * Method used for getting blog by author id.
	 *
	 * @param author id
	 * @return blog by id
	 */
	public BlogEntry getBlogById(String id);
	
	/**
	 * Method used for getting blog comments by blog entry id.
	 *
	 * @param blogEntry the blog entry
	 * @return blog comments by blog entry
	 */
	public List<BlogComment> getBlogCommentsByBlogEntry(BlogEntry blogEntry);
	
	/**
	 * Adds the blog comment.
	 *
	 * @param email the email
	 * @param message the message
	 * @param blogEntryID the blog entry ID
	 */
	public void addBlogComment(String email, String message, long blogEntryID);
	
	/**
	 * Update blog entry.
	 *
	 * @param title the title
	 * @param text the text
	 * @param blogID the blog ID
	 */
	public void updateBlogEntry(String title, String text, String blogID);
	
	/**
	 * Insert new blog entry.
	 *
	 * @param title the title
	 * @param text the text
	 * @param blogUserID the blog user ID
	 */
	public void insertNewBlogEntry(String title, String text, String blogUserID);
}
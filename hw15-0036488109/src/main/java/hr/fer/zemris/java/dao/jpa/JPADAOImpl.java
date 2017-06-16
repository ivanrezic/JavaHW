package hr.fer.zemris.java.dao.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.model.BlogComment;
import hr.fer.zemris.java.model.BlogEntry;
import hr.fer.zemris.java.model.BlogUser;

/**
 * <code>JPADAOImpl</code> provides code implementation of DAO interface.
 *
 * @author Ivan Rezic
 */
public class JPADAOImpl implements DAO {

	/**
	 * Digests password using SHA-1.
	 *
	 * @param password the password
	 * @return Digested password.
	 */
	public static String digestPassword(String password) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException ignorable) {
		}
		
		return bytetohex(sha.digest(password.getBytes()));
	}
	
	/**
	 * Helper method which transforms byte array to hexadecimal string.
	 *
	 * @param byteArray the byte array
	 * @return the hex string
	 */
	private static String bytetohex(byte[] byteArray) {
		StringBuilder hex = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			hex.append(Character.forDigit((byteArray[i] >> 4) & 0xF, 16));
			hex.append(Character.forDigit((byteArray[i] & 0xF), 16));
		}

		return hex.toString();
	}

	@Override
	public List<BlogUser> getUsers() {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em.createQuery("SELECT e FROM BlogUser e").getResultList();

		return users;
	}

	@Override
	public BlogUser userByNickAndPassword(String nick, String password) {
		EntityManager em = JPAEMProvider.getEntityManager();

		BlogUser user = (BlogUser) em
				.createQuery("SELECT e FROM BlogUser as e WHERE e.nick = :nick and e.passwordHash = :pw")
				.setParameter("nick", nick).setParameter("pw", digestPassword(password)).getSingleResult();

		return user;
	}

	@Override
	public void addBlogUser(String firstName, String lastName, String email, String nick, String password) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser blogUser = new BlogUser();
		blogUser.setFirstName(firstName);;
		blogUser.setLastName(lastName);
		blogUser.setNick(nick);
		blogUser.setPasswordHash(digestPassword(password));
		blogUser.setEmail(email);
		
		long count = (long) em
				.createQuery("SELECT COUNT(e) FROM BlogUser as e WHERE e.nick = :nick or e.email = :email")
				.setParameter("nick", nick).setParameter("email", email).getSingleResult();
		
		if (count > 0) {
			throw new DAOException("User with same nick or email already exists.");
		}
		
		em.persist(blogUser);
	}

	@Override
	public BlogUser getBlogUserByNick(String nick) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser user = (BlogUser) em
				.createQuery("SELECT e FROM BlogUser as e WHERE e.nick = :nick")
				.setParameter("nick", nick).getSingleResult();

		return user;
	}

	@Override
	public List<BlogEntry> getUserBlogsByNick(String nick) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<BlogEntry> entires = em.createQuery("SELECT e FROM BlogEntry e WHERE e.author = :user")
				.setParameter("user", getBlogUserByNick(nick)).getResultList();

		return entires;
	}

	@Override
	public BlogEntry getBlogById(String id) {
		EntityManager em = JPAEMProvider.getEntityManager();

		BlogEntry entry = (BlogEntry) em
				.createQuery("SELECT e FROM BlogEntry as e WHERE e.id = :id")
				.setParameter("id", Long.valueOf(id)).getSingleResult();

		return entry;
	}

	@Override
	public List<BlogComment> getBlogCommentsByBlogEntry(BlogEntry blogEntry) {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogComment> comments = (List<BlogComment>) em
				.createQuery("SELECT b FROM BlogComment as b where b.blogEntry=:be").setParameter("be", blogEntry)
				.getResultList();

		return comments;
	}

	@Override
	public void addBlogComment(String email, String message, long blogEntryID) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);

		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail(email);
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(message);
		blogComment.setBlogEntry(blogEntry);

		em.persist(blogComment);

		blogEntry.getComments().add(blogComment);
		
	}

	@Override
	public void updateBlogEntry(String title, String text, String blogID) {
		EntityManager em = JPAEMProvider.getEntityManager();

		em.createQuery("UPDATE BlogEntry c SET title = :title, text = :text WHERE c.id = :id")
				.setParameter("title", title).setParameter("text", text).setParameter("id", Long.valueOf(blogID)).executeUpdate();
	}

	@Override
	public void insertNewBlogEntry(String title, String text, String blogUserID) {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser blogUser = em.find(BlogUser.class, Long.valueOf(blogUserID));
		
		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle(title);
		blogEntry.setText(text);
		blogEntry.setAuthor(blogUser);

		em.persist(blogEntry);
		
	}

	
}
package hr.fer.zemris.java.web.init;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import hr.fer.zemris.java.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.model.BlogComment;
import hr.fer.zemris.java.model.BlogEntry;
import hr.fer.zemris.java.model.BlogUser;

/**
 * <code>Util</code> is heler class used for initail database seeding.
 *
 * @author Ivan Rezic
 */
public class Util {
	
	/**
	 * Inits the database.
	 *
	 * @param emf the emf
	 */
	public static void initDatabase(EntityManagerFactory emf) {
		addBlogUser(emf, "Luka", "Lukic", "Luko", "sifra3", "l@gmail.com");
		addBlogUser(emf, "Goran", "Goranic", "Gogo", "sifra4", "g@gmail.com");
		
		
		BlogUser blogUser1 = addBlogUser(emf, "Ivan", "Ivanic", "Ivo", "sifra1", "i@gmail.com");
		BlogEntry blogEntry1 = addBlogEntry(emf,"Ivanov prvi blog", "Moj blog", blogUser1.getId());
		Long blogEntryID1 = blogEntry1.getId();
		addBlogComment(emf, blogEntryID1, "Blog ti je super!");
		addBlogComment(emf, blogEntryID1, "Još jedan komentar.");

		BlogUser blogUser2 = addBlogUser(emf, "Marko", "Markic", "Maro", "sifra2", "m@gmail.com");
		BlogEntry blogEntry2 = addBlogEntry(emf,"Markov blog", "Ovo je moj prvi blog zapis.", blogUser2.getId());
		Long blogEntryID2 = blogEntry2.getId();
		addBlogComment(emf, blogEntryID2, "Vau!");
		addBlogComment(emf, blogEntryID2, "Vau uaV!");
	}

	/**
	 * Adds the blog entry.
	 *
	 * @param emf the emf
	 * @param title the title
	 * @param text the text
	 * @param blogUserID the blog user ID
	 * @return the blog entry
	 */
	public static BlogEntry addBlogEntry(EntityManagerFactory emf, String title, String text, Long blogUserID) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		BlogUser blogUser = em.find(BlogUser.class, blogUserID);
		
		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
		blogEntry.setTitle(title);
		blogEntry.setText(text);
		blogEntry.setAuthor(blogUser);

		em.persist(blogEntry);

		em.getTransaction().commit();
		em.close();

		return blogEntry;
	}

	/**
	 * Adds the blog comment.
	 *
	 * @param emf the emf
	 * @param blogEntryID the blog entry ID
	 * @param message the message
	 */
	public static void addBlogComment(EntityManagerFactory emf, Long blogEntryID, String message) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);

		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail("ivica@host.com");
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(message);
		blogComment.setBlogEntry(blogEntry);

		em.persist(blogComment);

		blogEntry.getComments().add(blogComment);

		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Adds the blog user.
	 *
	 * @param emf the emf
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param nick the nick
	 * @param password the password
	 * @param email the email
	 * @return the blog user
	 */
	public static BlogUser addBlogUser(EntityManagerFactory emf, String firstName, String lastName, String nick, String password, String email) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		BlogUser blogUser = new BlogUser();
		blogUser.setFirstName(firstName);;
		blogUser.setLastName(lastName);
		blogUser.setNick(nick);
		blogUser.setPasswordHash(JPADAOImpl.digestPassword(password));
		blogUser.setEmail(email);

		em.persist(blogUser);

		em.getTransaction().commit();
		em.close();
		
		return blogUser;
	}
	
}

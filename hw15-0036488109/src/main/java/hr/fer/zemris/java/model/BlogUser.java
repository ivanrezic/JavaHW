package hr.fer.zemris.java.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * <code>BlogUser</code> is model used as entity in blog_users table..
 *
 * @author Ivan Rezic
 */
@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {

	/** id. */
	private Long id;
	
	/** first name. */
	private String firstName;
	
	/** last name. */
	private String lastName;
	
	/** nick. */
	private String nick;
	
	/** email. */
	private String email;
	
	/** password hash. */
	private String passwordHash;
	
	/** blog entries. */
	private List<BlogEntry> blogEntries = new ArrayList<>();
	
	/**
	 * Method used for getting property <code>BlogEntries</code>.
	 *
	 * @return blog entries
	 */
	@OneToMany(mappedBy = "author")
	@OrderBy("createdAt")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}
	
	/**
	 * Method which sets new value as blog entries.
	 *
	 * @param blogEntries the new blog entries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}
	
	/**
	 * Method used for getting property <code>Id</code>.
	 *
	 * @return id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Method which sets new value as id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Method used for getting property <code>FirstName</code>.
	 *
	 * @return first name
	 */
	@Column(length = 30, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method which sets new value as first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Method used for getting property <code>LastName</code>.
	 *
	 * @return last name
	 */
	@Column(length = 30, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method which sets new value as last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Method used for getting property <code>Nick</code>.
	 *
	 * @return nick
	 */
	@Column(length = 30, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * Method which sets new value as nick.
	 *
	 * @param nick the new nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Method used for getting property <code>Email</code>.
	 *
	 * @return email
	 */
	@Column(length=100,nullable=false,unique = true)
	public String getEmail() {
		return email;
	}

	/**
	 * Method which sets new value as email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Method used for getting property <code>PasswordHash</code>.
	 *
	 * @return password hash
	 */
	@Column(length=100,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Method which sets new value as password hash.
	 *
	 * @param passwordHash the new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}

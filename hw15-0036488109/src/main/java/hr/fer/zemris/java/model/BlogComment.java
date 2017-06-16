package hr.fer.zemris.java.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <code>BlogComment</code> is model used as entity in blog_comments table.
 *
 * @author Ivan Rezic
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/** id. */
	private Long id;
	
	/** blog entry. */
	private BlogEntry blogEntry;
	
	/** users E mail. */
	private String usersEMail;
	
	/** message. */
	private String message;
	
	/** posted on. */
	private Date postedOn;
	
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
	 * Method used for getting property <code>BlogEntry</code>.
	 *
	 * @return blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Method which sets new value as blog entry.
	 *
	 * @param blogEntry the new blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Method used for getting property <code>UsersEMail</code>.
	 *
	 * @return users E mail
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Method which sets new value as users E mail.
	 *
	 * @param usersEMail the new users E mail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Method used for getting property <code>Message</code>.
	 *
	 * @return message
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Method which sets new value as message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Method used for getting property <code>PostedOn</code>.
	 *
	 * @return posted on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Method which sets new value as posted on.
	 *
	 * @param postedOn the new posted on
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
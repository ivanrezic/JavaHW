package hr.fer.zemris.java.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <code>BlogEntry</code> is model used as entity in blog_entries table..
 *
 * @author Ivan Rezic
 */
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/** id. */
	private Long id;
	
	/** comments. */
	private List<BlogComment> comments = new ArrayList<>();
	
	/** created at. */
	private Date createdAt;
	
	/** last modified at. */
	private Date lastModifiedAt;
	
	/** title. */
	private String title;
	
	/** text. */
	private String text;
	
	/** author. */
	private BlogUser author;
	
	/**
	 * Method used for getting property <code>Author</code>.
	 *
	 * @return author
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getAuthor() {
		return author;
	}
	
	/**
	 * Method which sets new value as author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(BlogUser author) {
		this.author = author;
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
	 * Method used for getting property <code>Comments</code>.
	 *
	 * @return comments
	 */
	@OneToMany(
			mappedBy = "blogEntry",
			fetch = FetchType.LAZY,
			cascade = CascadeType.PERSIST,
			orphanRemoval = true)
	@OrderBy("postedOn") //ASC ili DESC se moze dodati
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Method which sets new value as comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Method used for getting property <code>CreatedAt</code>.
	 *
	 * @return created at
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Method which sets new value as created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Method used for getting property <code>LastModifiedAt</code>.
	 *
	 * @return last modified at
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Method which sets new value as last modified at.
	 *
	 * @param lastModifiedAt the new last modified at
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Method used for getting property <code>Title</code>.
	 *
	 * @return title
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Method which sets new value as title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method used for getting property <code>Text</code>.
	 *
	 * @return text
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Method which sets new value as text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
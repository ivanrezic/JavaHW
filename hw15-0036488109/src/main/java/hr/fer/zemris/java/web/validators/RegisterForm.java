package hr.fer.zemris.java.web.validators;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;

/**
 * <code>RegisterForm</code> is class which is used to validate register form.
 *
 * @author Ivan Rezic
 */
public class RegisterForm {

	/** first name. */
	private String firstName;

	/** last name. */
	private String lastName;

	/** email. */
	private String email;

	/** nick. */
	private String nick;

	/** password. */
	private String password;

	/** error. */
	private boolean error = false;

	/** error message. */
	private String errorMsg = "";

	/**
	 * Checks for error.
	 *
	 * @return true, if it contains error, false otherwise
	 */
	public boolean hasError() {
		return error;
	}

	/**
	 * Fill properties from http request.
	 *
	 * @param req
	 *            the request
	 */
	public void fillFromHttpRequest(HttpServletRequest req) {
		firstName = req.getParameter("fname");
		lastName = req.getParameter("lname");
		email = req.getParameter("email");
		nick = req.getParameter("nick");
		password = req.getParameter("password");
	}

	/**
	 * Validate properties.
	 */
	public void validate() {
		if (firstName.length() == 0 || firstName.length() > 30) {
			errorMsg = "Max name length is 30 and min length is 1.";
			error = true;
		} else if (lastName.length() == 0 || lastName.length() > 30) {
			errorMsg = "Max surname length is 30 and min length is 1.";
			error = true;
		} else if (nick.length() == 0 || nick.length() > 30) {
			errorMsg = "Max nickname length is 30 and min length is 1.";
			error = true;
		} else if (!email.matches(".+@.+\\..+")) {
			errorMsg = "Invalid email provided.";
			error = true;
		} else if (!password.matches(".*\\d+.*")) {
			errorMsg = "Password must contain at least 1 number.";
			error = true;
		}
	}

	/**
	 * Insert into database.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void insertIntoDatabase() throws Exception {
		try {
			DAOProvider.getDAO().addBlogUser(firstName, lastName, email, nick, password);
		} catch (DAOException e) {
			errorMsg = e.getMessage();
			throw new Exception();
		}
	}

	/**
	 * Method used for getting property <code>FirstName</code>.
	 *
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method used for getting property <code>LastName</code>.
	 *
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method used for getting property <code>Email</code>.
	 *
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method used for getting property <code>Nick</code>.
	 *
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Method used for getting property <code>Password</code>.
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method used for getting property <code>ErrorMsg</code>.
	 *
	 * @return error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
}

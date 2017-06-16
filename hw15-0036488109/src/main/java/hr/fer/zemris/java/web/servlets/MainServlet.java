package hr.fer.zemris.java.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.BlogUser;

/**
 * <code>MainServlet</code> is servlet which provides data for home page display.
 *
 * @author Ivan Rezic
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** users. */
	List<BlogUser> users;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("logout") != null) {
			req.getSession().invalidate();
		}
		
		users = DAOProvider.getDAO().getUsers();
		req.getSession().setAttribute("users", users);

		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getParameter("nick");
		String password = (String) req.getParameter("password");

		if (nick.length() == 0 || password.length() == 0) {
			sendErrorMsg(req, resp, "Please provide both, nick and password.");
			return;
		}

		BlogUser currentUser;
		try {
			currentUser = DAOProvider.getDAO().userByNickAndPassword(nick, password);
		} catch (NoResultException e) {
			sendErrorMsg(req, resp, "Please register, before logging in.");
			return;
		}

		req.getSession().setAttribute("currentUser", currentUser);
		resp.sendRedirect("main");
	}

	/**
	 * Send error msg.
	 *
	 * @param req the req
	 * @param resp the resp
	 * @param string the string
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void sendErrorMsg(HttpServletRequest req, HttpServletResponse resp, String string) throws ServletException, IOException {
		req.setAttribute("error", string);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
}

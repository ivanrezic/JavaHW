package hr.fer.zemris.java.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;

/**
 * <code>NewBlogServlet</code> is servlet which enables new blog entry creation and storing.
 *
 * @author Ivan Rezic
 */
@WebServlet("/servleti/new")
public class NewBlogServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String blogUserID = req.getParameter("userID");
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		
		DAOProvider.getDAO().insertNewBlogEntry(title, text, blogUserID);
		resp.sendRedirect(req.getContextPath());
	}
}

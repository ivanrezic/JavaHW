package hr.fer.zemris.java.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;

/**
 * <code>UpdateBlogServlet</code> is servlet which updates edited blog entry.
 *
 * @author Ivan Rezic
 */
@WebServlet("/servleti/update")
public class UpdateBlogServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String blogID = req.getParameter("blogID");
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		
		DAOProvider.getDAO().updateBlogEntry(title, text, blogID);
		resp.sendRedirect(req.getContextPath());
	}
}

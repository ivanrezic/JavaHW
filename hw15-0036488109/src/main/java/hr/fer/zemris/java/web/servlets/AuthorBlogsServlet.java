package hr.fer.zemris.java.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.BlogComment;
import hr.fer.zemris.java.model.BlogEntry;

/**
 * <code>AuthorBlogsServlet</code> which provides all functionalities after
 * choosing author from main page.
 *
 * @author Ivan Rezic
 */
@WebServlet("/servleti/author/*")
public class AuthorBlogsServlet extends HttpServlet {
	
	/** blog id. */
	private String blogId;

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		int count = path.length() - path.replace("/", "").length();
		
		if (count == 1) {
			getUserBlogs(req,resp,path);
		}else if(path.endsWith("new")){
			req.getRequestDispatcher("/WEB-INF/pages/addBlog.jsp").forward(req, resp);			
		}else if (path.endsWith("edit")) {
			req.getRequestDispatcher("/WEB-INF/pages/editBlog.jsp").forward(req, resp);			
		}else {
			getSpecificEntry(req,resp,path);
		}
	}

	/**
	 * Method used for getting <code>UserBlogs</code>.
	 *
	 * @param req the req
	 * @param resp the resp
	 * @param path the path
	 * @return user blogs
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void getUserBlogs(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		String nick = path.substring(1);
		
		List<BlogEntry> entries = DAOProvider.getDAO().getUserBlogsByNick(nick);
		
		req.setAttribute("blogs", entries);
		req.getSession().setAttribute("selectedUser", nick);
		req.getRequestDispatcher("/WEB-INF/pages/author_blogs.jsp").forward(req, resp);
	}
	
	/**
	 * Method used for getting selected entry.
	 *
	 * @param req the req
	 * @param resp the resp
	 * @param path the path
	 * @return specific entry
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void getSpecificEntry(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		blogId = path.substring(path.indexOf("/", path.indexOf("/") + 1)).substring(1);
		
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogById(blogId);
		List<BlogComment> comments = DAOProvider.getDAO().getBlogCommentsByBlogEntry(blogEntry);
		
		req.setAttribute("comments", comments);
		req.getSession().setAttribute("blog", blogEntry);
		req.getRequestDispatcher("/WEB-INF/pages/selected_blog.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String message = req.getParameter("text");
		
		DAOProvider.getDAO().addBlogComment(email, message, Long.valueOf(blogId));
		resp.sendRedirect(req.getRequestURL().toString());
	}
}

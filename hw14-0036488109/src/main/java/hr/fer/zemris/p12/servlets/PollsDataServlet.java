package hr.fer.zemris.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * <code>PollsDataServlet</code> fetches data from Polls table and forwards to
 * index.jsp.
 *
 * @author Ivan Rezic
 */
@WebServlet("/servleti/index.jsp")
public class PollsDataServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPollsData();
		
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}

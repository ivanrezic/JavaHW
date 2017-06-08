package hr.fer.zemris.java.p12.servlets.voting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * <code>VotingServlet</code> is servlet which reads all data from database and
 * forwards it to the glasanjeIndex.jsp.
 *
 * @author Ivan Rezic
 */
@WebServlet("servleti/glasanje")
public class VotingServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PollOption> options = new ArrayList<>();
		
		long pollId = Long.valueOf(req.getParameter("pollID"));
		options = DAOProvider.getDao().getPollOptions(pollId);

		req.setAttribute("options", options);
		req.getSession().setAttribute("pollID", pollId);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}

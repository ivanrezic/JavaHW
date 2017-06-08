package hr.fer.zemris.java.p12.servlets.voting;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * <code>VotingVoteServlet</code> is servlet which retrieves all data that will
 * be used in glasanjeRez.jsp.
 *
 * @author Ivan Rezic
 */
@WebServlet("servleti/glasanje-glasaj")
public class VotingVoteServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = (Long) req.getSession().getAttribute("pollID");
		long id = Long.valueOf(req.getParameter("id"));
		
		DAOProvider.getDao().updateOptionVotesCount(id);
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID);
		
		setProperAtributes(req, results);	
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	private void setProperAtributes(HttpServletRequest req, List<PollOption> results) {
		List<PollOption> sorted = results.stream().sorted().collect(Collectors.toList());
		long maxValue = sorted.get(0).getVotesCount();

		req.getSession().setAttribute("sorted", sorted);
		req.getSession().setAttribute("maxValues", sorted.stream().filter(e -> e.getVotesCount() == maxValue).collect(Collectors.toList()));
	}
}

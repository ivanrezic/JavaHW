package hr.fer.zemris.java.servlets.voting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.voting.VotingServlet.Band;

/**
 * <code>VotingVoteServlet</code> is servlet which retrieves all data that will
 * be used in "/glasanjeRez".
 *
 * @author Ivan Rezic
 */
@WebServlet("/glasanje-glasaj")
public class VotingVoteServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		Map<String, Integer> score = new HashMap<>();
		Path file = Paths.get(fileName);

		if (Files.exists(file)) {
			score = getScores(req, file);
		} else {
			List<Band> bands = (List<Band>) req.getServletContext().getAttribute("bands");
			for (int i = 1; i <= bands.size(); i++) {
				score.put(String.valueOf(i), 0);
			}
		}

		String id = (String) req.getParameter("id");
		Integer value = score.get(id) + 1;
		score.put(id, value);

		writeIntoFile(score, file);
		Map<String, Integer> sorted = sortByValue(score);
		List<Integer> winningId = getWiningID(sorted);

		req.getServletContext().setAttribute("scores", sorted);
		req.setAttribute("winning", winningId);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Method used for converting data out of given file path.
	 *
	 * @param req
	 *            the request
	 * @param file
	 *            the file
	 * @return scores
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private Map<String, Integer> getScores(HttpServletRequest req, Path file) throws IOException {
		Map<String, Integer> score = new HashMap<>();

		List<String> lines = Files.readAllLines(file);
		for (String line : lines) {
			String[] parts = line.split("\t");
			score.put(parts[0], Integer.valueOf(parts[1]));
		}

		return score;
	}

	/**
	 * Write map data into as file onto given path.
	 *
	 * @param score
	 *            the score
	 * @param file
	 *            the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeIntoFile(Map<String, Integer> score, Path file) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(file);

		for (Map.Entry<String, Integer> entry : score.entrySet()) {
			writer.write(String.format("%s\t%d%n", entry.getKey(), entry.getValue()));
		}

		writer.close();
	}

	/**
	 * Helper method which sorts given map by value.
	 *
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param map
	 *            Map to be sorted.
	 * @return Sorted map.
	 */
	private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	/**
	 * Helper method which extracts all wining id-s from voting results.
	 *
	 * @param sorted
	 *            map containing data
	 * @return wining ID
	 */
	private List<Integer> getWiningID(Map<String, Integer> sorted) {
		List<Integer> result = new ArrayList<>();
		int max = Collections.max(sorted.values());

		for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
			if (entry.getValue() == max) {
				result.add(Integer.valueOf(entry.getKey()));
			}
		}

		return result;
	}
}

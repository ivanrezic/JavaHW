package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>VotingServlet</code> is servlet which reads all data from given path
 * and forwards it to the glasanjeIndex view.
 *
 * @author Ivan Rezic
 */
@WebServlet("/glasanje")
public class VotingServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * <code>Band</code> is helper class containg all relevant data about band.
	 *
	 * @author Ivan Rezic
	 */
	public static class Band {

		/** Band id. */
		String id;

		/** Band name. */
		String name;

		/** Band song. */
		String song;

		/**
		 * Constructor which instantiates new band.
		 *
		 * @param id
		 *            the id
		 * @param name
		 *            the name
		 * @param song
		 *            the song
		 */
		public Band(String id, String name, String song) {
			this.id = id;
			this.name = name;
			this.song = song;
		}

		/**
		 * Method used for getting property <code>Id</code>.
		 *
		 * @return id
		 */
		public String getId() {
			return id;
		}

		/**
		 * Method used for getting property <code>Name</code>.
		 *
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Method used for getting property <code>Song</code>.
		 *
		 * @return song
		 */
		public String getSong() {
			return song;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<Band> bands = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		for (String line : lines) {
			String[] parts = line.split("\t");
			bands.add(new Band(parts[0], parts[1], parts[2]));
		}

		req.getServletContext().setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}

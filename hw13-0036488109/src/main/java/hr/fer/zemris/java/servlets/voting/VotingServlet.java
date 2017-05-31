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

@WebServlet("/glasanje")
public class VotingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static class Band {
		String id;
		String name;
		String song;

		public Band(String id, String name, String song) {
			this.id = id;
			this.name = name;
			this.song = song;
		}
		
		public String getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
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

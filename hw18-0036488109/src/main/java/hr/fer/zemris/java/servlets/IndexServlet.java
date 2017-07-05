package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.models.MyPhoto;

/**
 * <code>IndexServlet</code> used for initialization purposes and filling maps
 * used in further operations.
 *
 * @author Ivan Rezic
 */
@WebServlet("/")
public class IndexServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path path = Paths.get(req.getServletContext().getRealPath("/WEB-INF/opisnik.txt"));

		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		List<MyPhoto> result = new ArrayList<>();
		for (int i = 0, j = lines.size(); i + 2 < j; i += 3) {
			result.add(new MyPhoto(lines.get(i), lines.get(i + 1), Arrays.asList(lines.get(i + 2).split(","))));
		}

		Map<String, List<MyPhoto>> tags = new HashMap<>();
		for (MyPhoto myPhoto : result) {
			for (String tag : myPhoto.getTags()) {
				if (tags.containsKey(tag)) {
					tags.get(tag).add(myPhoto);
				} else {
					List<MyPhoto> photos = new ArrayList<>();
					photos.add(myPhoto);
					tags.put(tag, photos);
				}

			}
		}

		req.getSession().setAttribute("photos", result);
		req.getSession().setAttribute("tags", tags);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
}

package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.models.MyPhoto;

/**
 * <code>TagServlet</code> fetches all tags and forwards ih to index.jsp.
 *
 * @author Ivan Rezic
 */
@WebServlet(urlPatterns = "/tagovi")
public class TagServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 6442289158776003082L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, List<MyPhoto>> tags = (Map<String, List<MyPhoto>>) req.getSession().getAttribute("tags");

		Gson gson = new Gson();
		String json = gson.toJson(tags.keySet().stream().toArray(String[]::new));

		resp.getWriter().write(json);
		resp.getWriter().flush();
	}
}
package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.models.MyPhoto;

/**
 * <code>SlikaInfoServlet</code> fetches info for each picture which is used for
 * further operations like resizing.
 *
 * @author Ivan Rezic
 */
@WebServlet(urlPatterns = "/slikaInfo")
public class SlikaInfoServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 5557106642739389297L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		String name = req.getParameter("info");
		List<MyPhoto> photoInfo = (List<MyPhoto>) req.getSession().getAttribute("photos");

		List<String> data = new ArrayList<>();
		for (MyPhoto info : photoInfo) {
			if (info.getName().equals(name)) {
				data.add(info.getName());
				data.add(info.getDescription());
				data.add(info.getTags().toString());
			}
		}

		resp.getWriter().write(gson.toJson(data.stream().toArray(String[]::new)));
		resp.getWriter().flush();
	}
}
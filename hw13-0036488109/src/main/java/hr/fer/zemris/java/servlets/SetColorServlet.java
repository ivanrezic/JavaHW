package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>SetColorServlet</code> is servlet used for choosing color for all pages
 * on our web app.
 *
 * @author Ivan Rezic
 */
@WebServlet(name = "setColor", urlPatterns = { "/setcolor" })
public class SetColorServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");

		if (color != null && !color.matches("red|green|cyan")) {
			color = "white";
		}

		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

}

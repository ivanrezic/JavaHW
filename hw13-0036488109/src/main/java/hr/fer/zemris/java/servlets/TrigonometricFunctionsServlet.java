package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>TrigonometricFunctionsServlet</code> is servlet used for creation of
 * table consisting sinus and cosinus for given number range.
 *
 * @author Ivan Rezic
 */
@WebServlet(name = "trigonometricFunctions", urlPatterns = { "/trigonometric" })
public class TrigonometricFunctionsServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * <code>Triplet</code> is helper class containg triplets of wanted data.
	 *
	 * @author Ivan Rezic
	 */
	public static class Triplet {

		/** number. */
		Integer number;

		/** sinus. */
		Double sinus;

		/** cosinus. */
		Double cosinus;

		/**
		 * Constructor which instantiates new triplet.
		 *
		 * @param number
		 *            the number
		 * @param sinus
		 *            the sinus
		 * @param cosinus
		 *            the cosinus
		 */
		public Triplet(Integer number, Double sinus, Double cosinus) {
			this.number = number;
			this.sinus = sinus;
			this.cosinus = cosinus;
		}

		/**
		 * Method used for getting property <code>Number</code>.
		 *
		 * @return number
		 */
		public Integer getNumber() {
			return number;
		}

		/**
		 * Method used for getting property <code>Sinus</code>.
		 *
		 * @return sinus
		 */
		public Double getSinus() {
			return sinus;
		}

		/**
		 * Method used for getting property <code>Cosinus</code>.
		 *
		 * @return cosinus
		 */
		public Double getCosinus() {
			return cosinus;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = 0, b = 360;
		try {
			a = Integer.valueOf(req.getParameter("a"));
			b = Integer.valueOf(req.getParameter("b"));
		} catch (NumberFormatException ignorable) {
		}

		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		if (b > a + 720) {
			b = a + 720;
		}

		List<Triplet> results = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			Triplet triplet = new Triplet(i, Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i)));
			results.add(triplet);
		}

		req.setAttribute("results", results);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}

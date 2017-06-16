package hr.fer.zemris.java.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.web.validators.RegisterForm;

/**
 * <code>RegisterServlet</code> is servlet which enables new user registration.
 *
 * @author Ivan Rezic
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet{

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RegisterForm form = new RegisterForm();
		form.fillFromHttpRequest(req);
		form.validate();
		
		if (form.hasError()) {
			req.setAttribute("error", form);
			req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
			return;
		}
		
		try {
			form.insertIntoDatabase();			
		} catch (Exception e) {
			req.setAttribute("error", form);
			req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
			return;
		}
		
		resp.sendRedirect(req.getContextPath());
	}
}

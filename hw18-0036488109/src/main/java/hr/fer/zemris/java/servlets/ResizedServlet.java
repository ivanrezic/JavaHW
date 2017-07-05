package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>ResizedServlet</code> fetches full size picture from "slike" folder and
 * displays it.
 *
 * @author Ivan Rezic
 */
@WebServlet(urlPatterns = "/resized")
public class ResizedServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 4189476563655315018L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path originPath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike"), req.getParameter("info"));
		resp.setContentType("image/png");
		ImageIO.write(ImageIO.read(originPath.toFile()), "png", resp.getOutputStream());

	}
}
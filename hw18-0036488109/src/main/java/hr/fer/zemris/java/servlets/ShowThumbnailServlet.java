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
 * <code>ShowThumbnailServlet</code> fetches thumbnails from thumbnails folder
 * and provides it to client.
 *
 * @author Ivan Rezic
 */
@WebServlet(urlPatterns = "/thumbnail")
public class ShowThumbnailServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 2826492993675217909L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path originPath = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"),
				req.getParameter("info"));
		resp.setContentType("image/png");
		ImageIO.write(ImageIO.read(originPath.toFile()), "png", resp.getOutputStream());
	}
}
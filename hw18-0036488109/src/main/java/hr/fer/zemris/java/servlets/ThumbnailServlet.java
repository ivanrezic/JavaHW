package hr.fer.zemris.java.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.models.MyPhoto;

/**
 * <code>ThumbnailServlet</code> creates thumbnails map if it does'nt exist.
 *
 * @author Ivan Rezic
 */
@WebServlet(urlPatterns = "/createThumbnail")
public class ThumbnailServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = -6243312984608769965L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, List<MyPhoto>> tags = (Map<String, List<MyPhoto>>) req.getSession().getAttribute("tags");
		List<MyPhoto> photoInfos = tags.get(req.getParameter("tag"));

		for (MyPhoto info : photoInfos) {
			String pictureName = info.getName();
			Path thumbnailsDir = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"));
			if (!Files.exists(thumbnailsDir)) {
				Files.createDirectory(thumbnailsDir);
			}

			thumbnailsDir = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"), pictureName);
			Path originalPhoto = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike"), pictureName);

			Image scaledVersion = ImageIO.read(originalPhoto.toFile()).getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			BufferedImage thumbnail = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = thumbnail.createGraphics();
			g2d.drawImage(scaledVersion, 0, 0, null);
			g2d.dispose();

			ImageIO.write(thumbnail, "png", thumbnailsDir.toFile());
		}

		Gson gson = new Gson();
		String text = gson.toJson(photoInfos.stream().map(MyPhoto::getName).toArray(String[]::new));

		resp.getWriter().write(text);
		resp.getWriter().flush();
	}
}
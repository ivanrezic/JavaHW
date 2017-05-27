package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * <code>CircleWorker</code> is worker which prints image in png format. It has
 * dimensions 200 by 200. Within it there is circle whose diameter is as wide as
 * picture containing it.
 *
 * @author Ivan Rezic
 */
public class CircleWorker implements IWebWorker {

	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		context.setMimeType("png");
		Graphics2D g2d = bim.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawOval(100, 100, 200, 200);
		g2d.setColor(Color.CYAN);
		g2d.fillOval(0, 0, 200, 200);

		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

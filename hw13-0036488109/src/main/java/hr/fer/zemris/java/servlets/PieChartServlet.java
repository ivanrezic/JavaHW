package hr.fer.zemris.java.servlets;

import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

@WebServlet(name = "usagePieChart", urlPatterns = { "/reportImage" })
public class PieChartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		ServletOutputStream os = resp.getOutputStream();

		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, "OS Usage");

		RenderedImage chartImage = chart.createBufferedImage(400, 300);
		ImageIO.write(chartImage, "png", os);
		os.flush();
		os.close();
	}

	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();

		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);

		return result;
	}

	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}
}

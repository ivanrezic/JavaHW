package hr.fer.zemris.java.servlets.voting;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

import hr.fer.zemris.java.servlets.voting.VotingServlet.Band;

@WebServlet("/glasanje-grafika")
public class VotingChartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		ServletOutputStream os = resp.getOutputStream();

		List<Band> bands = (List<Band>) req.getServletContext().getAttribute("bands");
		Map<String, Integer> scores = (Map<String, Integer>) req.getServletContext().getAttribute("scores");
		PieDataset dataset = createDataset(bands,scores);
		JFreeChart chart = createChart(dataset, "Favorite bands");

		RenderedImage chartImage = chart.createBufferedImage(400, 400);
		ImageIO.write(chartImage, "png", os);
		os.flush();
		os.close();
	}

	private PieDataset createDataset(List<Band> bands, Map<String, Integer> scores) {
		DefaultPieDataset result = new DefaultPieDataset();

		for (Band band : bands) {
			result.setValue(band.name, scores.get(band.id));
		}

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

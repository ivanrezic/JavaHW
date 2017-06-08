package hr.fer.zemris.p12.servlets.voting;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.List;

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

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * <code>VotingChartServlet</code> is servlet used for creation of pie chart out
 * of retrieved data from database.
 *
 * @author Ivan Rezic
 */
@WebServlet("servleti/glasanje-grafika")
public class VotingChartServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		ServletOutputStream os = resp.getOutputStream();

		Long pollID = (Long) req.getSession().getAttribute("pollID");
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID);

		DefaultPieDataset result = new DefaultPieDataset();
		results.forEach(e -> result.setValue(e.getOptionTitle(), e.getVotesCount()));

		JFreeChart chart = createChart(result, "Favorite options");

		RenderedImage chartImage = chart.createBufferedImage(400, 400);
		ImageIO.write(chartImage, "png", os);
		os.flush();
		os.close();
	}

	/**
	 * Creates the chart from given dataset.
	 *
	 * @param dataset
	 *            Dataset.
	 * @param title
	 *            Chart title.
	 * @return Plotted chart.
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);

		return chart;
	}
}

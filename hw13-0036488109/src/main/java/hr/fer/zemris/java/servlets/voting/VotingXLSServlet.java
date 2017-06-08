package hr.fer.zemris.java.servlets.voting;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servlets.voting.VotingServlet.Band;

/**
 * <code>VotingXLSServlet</code> is servlet whose job is to provide XLS file
 * creation out of data retrieved from global attributes.
 *
 * @author Ivan Rezic
 */
@WebServlet("/glasanje-xls")
public class VotingXLSServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = "./VotingResults.xls";
		HSSFWorkbook workbook = new HSSFWorkbook();

		List<Band> bands = (List<Band>) req.getServletContext().getAttribute("bands");
		Map<String, Integer> scores = (Map<String, Integer>) req.getServletContext().getAttribute("scores");

		int i = 1;
		HSSFSheet sheet = workbook.createSheet("Results");
		for (Map.Entry<String, Integer> entry : scores.entrySet()) {
			HSSFRow rowhead = sheet.createRow((int) i++);
			rowhead.createCell(0).setCellValue(bands.get(Integer.valueOf(entry.getKey()) - 1).name);
			rowhead.createCell(1).setCellValue(entry.getValue());
		}

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			workbook.close();
			fileOut.close();
		} catch (IOException e) {
			sendRequestMessage(req, resp, "Ups, something went wrong, please try again later.");
		}

		sendRequestMessage(req, resp, "VotingResults.xls has been created.");
	}

	/**
	 * Send request message.
	 *
	 * @param req the request
	 * @param resp the respond
	 * @param message message to be displayed at redirected site
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void sendRequestMessage(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("message", message);
		req.getRequestDispatcher("/WEB-INF/pages/info.jsp").forward(req, resp);
	}
}

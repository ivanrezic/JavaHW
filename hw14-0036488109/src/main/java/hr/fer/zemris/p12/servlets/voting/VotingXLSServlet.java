package hr.fer.zemris.p12.servlets.voting;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * <code>VotingXLSServlet</code> is servlet whose job is to provide XLS file
 * creation out of data retrieved from database.
 *
 * @author Ivan Rezic
 */
@WebServlet("servleti/glasanje-xls")
public class VotingXLSServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=PollResults.xls");

		Long pollID = (Long) req.getSession().getAttribute("pollID");
		List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID);
		Collections.sort(results);

		int i = 1;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Results");
		for (PollOption option : results) {
			HSSFRow rowhead = sheet.createRow(i++);
			rowhead.createCell(0).setCellValue(option.getOptionTitle());
			rowhead.createCell(1).setCellValue(option.getVotesCount());
		}
		
		workbook.write(resp.getOutputStream());
		workbook.close();
	}
}

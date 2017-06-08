package hr.fer.zemris.java.servlets;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <code>PowersServlet</code> is servlet whose job is to provide XLS file
 * creation out of data retrieved from url parameters.
 *
 * @author Ivan Rezic
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = null;
		Integer b = null;
		Integer n = null;

		try {		
			a = Integer.valueOf(req.getParameter("a"));
			b = Integer.valueOf(req.getParameter("b"));
			n = Integer.valueOf(req.getParameter("n"));
		} catch (NumberFormatException e) {
			sendRequestMessage(req, resp, "Parameters a,b,n should be provided, and they should be integers.");
		}

		checkParameters(req, resp, a, b, n);
		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}

		createExcelFile(req, resp, a, b, n);
	}

	/**
	 * Check parameter values if in valid range.
	 *
	 * @param req the request
	 * @param resp the respond
	 * @param a the a parameter
	 * @param b the b parameter
	 * @param n the n parameter
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void checkParameters(HttpServletRequest req, HttpServletResponse resp, Integer a, Integer b, Integer n)
			throws ServletException, IOException {
		if (a < -100 || a > 100) {
			sendRequestMessage(req, resp, "Parameter a should be in range of [-100,100].");
		} else if (b < -100 || b > 100) {
			sendRequestMessage(req, resp, "Parameter b should be in range of [-100,100].");
		} else if (n < 1 || n > 5) {
			sendRequestMessage(req, resp, "Parameter n should be in range of [1,5].");
		}
	}

	/**
	 * Creates the excel file out of given parameters.
	 *
	 * @param req the request
	 * @param resp the respond
	 * @param a the a parameter
	 * @param b the b parameter
	 * @param n the n parameter
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void createExcelFile(HttpServletRequest req, HttpServletResponse resp, Integer a, Integer b, Integer n)
			throws ServletException, IOException {
		String fileName = "./GeneratedExcelFile.xls";
		HSSFWorkbook workbook = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = workbook.createSheet(String.valueOf(i));
			for (int j = a, row = 0; j <= b; j++, row++) {
				HSSFRow rowhead = sheet.createRow((int) row);
				rowhead.createCell(0).setCellValue(String.valueOf(j));
				rowhead.createCell(1).setCellValue(String.valueOf(Math.pow(j, i)));
			}
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

		sendRequestMessage(req, resp, "GeneratedExcelFile.xls has been created.");
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

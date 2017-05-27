package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * <code>EchoParams</code> is worker which prints given parameters in table.
 * Each parameter should be defined as "key=value" and if there is more than one
 * key-value pair, they should be divided by '&'.
 *
 * @author Ivan Rezic
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Map<String, String> parameters = context.getParameters();

		try {
			context.write("<html><body>");
			context.write("<style> table, th, td { border: 1px solid green; } </style>");
			context.write("<table>");

			context.write("<tr>");
			for (String key : parameters.keySet()) {
				context.write("<th>" + key + "</th>");
			}
			context.write("</tr>");
			context.write("<tr>");
			for (String values : parameters.values()) {
				context.write("<td>" + values + "</td>");
			}
			context.write("</tr>");

			context.write("</table>");
			context.write("</body></html>");
		} catch (IOException ex) {
			// Log exception to servers log...
			ex.printStackTrace();
		}
	}

}

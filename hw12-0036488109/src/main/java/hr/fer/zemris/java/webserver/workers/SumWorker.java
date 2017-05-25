package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String a = context.getParameter("a");
		String b = context.getParameter("b");
		
		if (a == null || !a.matches("\\d+")) {
			a = "1";
		}
		if (b == null || !b.matches("\\d+")) {
			b = "2";
		}
		
		Integer zbroj = Integer.parseInt(a) + Integer.parseInt(b);
		context.setTemporaryParameter("zbroj", String.valueOf(zbroj));
		context.setTemporaryParameter("a", a);
		context.setTemporaryParameter("b", b);
		
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
		
		try {
			context.write("<html><body>");
			context.write("<style>");
			context.write("table, th, td { border: 1px solid green; } </style>");	
			context.write("<table>");
			
			context.write("<tr>");
//			for (String key : parameters.keySet()) {
//				context.write("<th>" + key + "</th>");
//			}
//			context.write("</tr>");
//			context.write("<tr>");			
//			for (String values : parameters.values()) {
//				context.write("<td>" + values + "</td>");
//			}
			context.write("</tr>");

			context.write("</table>");
			context.write("</body></html>");
		} catch (IOException ex) {
			// Log exception to servers log...
			ex.printStackTrace();
		}
	}

}

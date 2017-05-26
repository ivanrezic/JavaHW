package hr.fer.zemris.java.webserver.workers;

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
		
		context.removeTemporaryParameter("zbroj");
		context.removeTemporaryParameter("a");
		context.removeTemporaryParameter("b");
	}

}

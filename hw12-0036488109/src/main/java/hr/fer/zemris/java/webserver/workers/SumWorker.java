package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * <code>SumWorker</code> is worker which takes two parameters and prints their
 * sum into table. Parameters should be integers. If parameter is not integer or
 * it has not been defined, it is set to default value: a=1 and b=2.
 *
 * @author Ivan Rezic
 */
public class SumWorker implements IWebWorker {

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

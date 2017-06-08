package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The listener interface for receiving app events. It initializes global
 * attribute upon web app starting.
 *
 * @author Ivan Rezic
 */
public class AppListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}

package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * <code>Initialization</code> is context listener implementation. It creates
 * default tables and populates iz in provided database, if not already there.
 *
 * @author Ivan Rezic
 */
@WebListener
public class Initialization implements ServletContextListener {
	
	/** Data Access Object */
	private DAO dao = DAOProvider.getDao();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String connectionURL = loadProperties(sce);

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pool initialization error.", e1);
		}
		
		cpds.setJdbcUrl(connectionURL);
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		tablesInitialization(sce,cpds);
	}

	/**
	 * Helper method which loads database connection properties.
	 *
	 * @param sce
	 *            Servlet Context Event.
	 * @return ConnectionURL.
	 */
	private String loadProperties(ServletContextEvent sce) {
		Properties properties = new Properties();
		String propPath = sce.getServletContext().getRealPath("WEB-INF/dbsettings.properties");
		
		try {
			properties.load(Files.newInputStream(Paths.get(propPath)));
		} catch (IOException e) {
			System.out.println("Properties file missing in WEB-INF folder.");
			System.exit(-1);
		}
		String name = properties.getProperty("name");
		String host = properties.getProperty("host");
		String port = properties.getProperty("port");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		if (name == null || host == null || port == null || user == null || password == null) {
			System.out.println("Properties file missing in WEB-INF folder.");
			System.exit(-1);
		}
		
		return String.format("jdbc:derby://%s:%s/%s;user=%s;password=%s", host, port, name, user, password);
	}

	/**
	 * Creates tables Polls and PollsOptions and populates it, if not alredy
	 * done.
	 *
	 * @param sce
	 *            Servlet Context Event.
	 * @param cpds
	 *            ComboPooledDataSource.
	 */
	private void tablesInitialization(ServletContextEvent sce, ComboPooledDataSource cpds) {
		String data = sce.getServletContext().getRealPath("WEB-INF/initialPollsData.txt");
		String options = sce.getServletContext().getRealPath("WEB-INF/initialPoolOptionsData.txt");
		
		Connection connection = null;
		try {
			connection = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		dao.createTablePolls(connection);
		dao.createTablePollOptions(connection);
		
		if (dao.isTableEmpty("Polls", connection)) {
			dao.populatePolls(Paths.get(data), connection);
			dao.populatePollOptions(Paths.get(options), connection);
		}

	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
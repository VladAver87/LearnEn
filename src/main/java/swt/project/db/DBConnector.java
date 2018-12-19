package swt.project.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBConnector {
	
	public static DBConnector dbconnector = new DBConnector();
	private String url;
	private String username;
	private String password;
	private String driver;
	private Shell connectShell = new Shell();
	private final Logger log = LoggerFactory.getLogger(DBConnector.class);
	
	private DBConnector() {
		getConnectProperties(connectShell);
	}
	
	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	
	public void getConnectProperties(Shell shell) {
		
		Properties props = new Properties();

		try (FileInputStream in = new FileInputStream("db_connect.properties")) {
			props.load(in);
		} catch (IOException e) {
			log.error("Not found db_connect.properties file", e);
			MessageBox messageBox = new MessageBox(shell);
			messageBox.setMessage("Unable to read file 'db_connect.properties' "
					+ "\n Please, check for the file 'db_connect.properties' in program directory");
			messageBox.open();
			shell.close();
		}

		this.url = props.getProperty("jdbc.url");
		this.username = props.getProperty("jdbc.username");
		this.password = props.getProperty("jdbc.password");
		this.driver = props.getProperty("jdbc.driver");
		
	}
	
	public void tryConnectFromDB(Shell shell) {
		
		if (driver != null) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				log.error("JDBC Driver not found", e);
				shell.close();
			}
		}

		try (Connection connect = DriverManager.getConnection(url, username, password)) {			
			
		} catch (SQLException ex) {
			log.error("Unable to connect to DB", ex);;
			MessageBox messageBox = new MessageBox(shell);
			messageBox.setMessage("Unable to connect to database"
					+ "\n Please, check your connection settings in 'db_connect.properties'");
			messageBox.open();
			shell.close();	
		}
	}

}

package swt.project.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


public class DBConnector {
	private String url;
	private String username;
	private String password;
	
	public DBConnector() {
		getConnectProperties(null);
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

			MessageBox messageBox = new MessageBox(shell);
			messageBox.setMessage("Unable to read file 'db_connect.properties' "
					+ "\n Please, check for the file 'db_connect.properties' in program directory");
			messageBox.open();
			shell.dispose();
		}

		this.url = props.getProperty("jdbc.url");
		this.username = props.getProperty("jdbc.username");
		this.password = props.getProperty("jdbc.password");
		String driver = props.getProperty("jdbc.driver");

		if (driver != null) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		try (Connection connect = DriverManager.getConnection(url, username, password)) {			
			
		} catch (SQLException ex) {
			MessageBox messageBox = new MessageBox(shell);
			messageBox.setMessage("Unable to connect to database"
					+ "\n Please, check your connection settings in 'db_connect.properties'");
			messageBox.open();
			shell.dispose();	
		}
	}

}
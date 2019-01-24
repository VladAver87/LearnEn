package swt.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import swt.project.utils.Utils;

public class UserDAO implements IUserDAO{
	public static UserDAO userDAO= new UserDAO (DBConnector.dbconnector);
	private DBConnector dbconnector = DBConnector.dbconnector;
	private final Logger log = LoggerFactory.getLogger(UserDAO.class);
	private Shell userShell = new Shell();
	
	private UserDAO(DBConnector dbconnector) {
		
	}

	@Override
	public void put(String login, String password) {
		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			String sqlQuery = "INSERT INTO users (login, password) VALUES (?, ?)";
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, login);
			st.setString(2, Utils.passCoding(password));
			st.execute();
			log.info("User {} add to database", login);
			MessageBox messageBox = new MessageBox(userShell);
			messageBox.setMessage("Registration successful! Please login!");
			messageBox.open();
		} catch (SQLException ex) {
			log.error("add user error", ex);
			MessageBox messageBox = new MessageBox(userShell);
			messageBox.setMessage("Unable to add new user from DataBase");
			messageBox.open();
		}
		
	}

	@Override
	public boolean check(String login, String password) {
		boolean result = false;
		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			Statement st = connect.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM users WHERE login = '" + login + "'AND password = '" + Utils.passCoding(password) + "'");
			if (res.next()) {
				result = true;
			}
			log.info("password check successful");
		} catch (SQLException ex) {
			log.error("password check failed", ex);
		}
		return result;
	}

}

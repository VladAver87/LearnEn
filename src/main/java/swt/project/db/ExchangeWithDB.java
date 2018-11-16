package swt.project.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ExchangeWithDB implements IExchangeWithDB {

	@Override
	public void putToDB(String word, String translate) {

		String sqlQuery = "INSERT INTO DICT_TABLE (word, translate) VALUES (?, ?)";

		Properties props = new Properties();

		try (FileInputStream in = new FileInputStream("db_connect.properties")) {
			props.load(in);			
		} catch (IOException e) {

			e.printStackTrace();
		}

		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		String driver = props.getProperty("jdbc.driver");

		if (driver != null) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		try (Connection connect = DriverManager.getConnection(url, username, password)) {
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, word);
			st.setString(2, translate);
			st.execute();
		} catch (SQLException ex) {
			System.out.println("no connect");
		}
	}

	@Override
	public void delFromDB(String word) {
		
		String sqlQuery = "DELETE FROM DICT_TABLE WHERE word = ?";
		Properties props = new Properties();

		try (FileInputStream in = new FileInputStream("db_connect.properties")) {
			props.load(in);
		} catch (IOException e) {

			e.printStackTrace();
		}

		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		String driver = props.getProperty("jdbc.driver");

		if (driver != null) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		try (Connection connect = DriverManager.getConnection(url, username, password)) {
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, word);
			st.execute();
		} catch (SQLException ex) {
			System.out.println("no connect");
		}
	}
}

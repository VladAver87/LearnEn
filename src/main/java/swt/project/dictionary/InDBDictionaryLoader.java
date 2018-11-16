package swt.project.dictionary;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InDBDictionaryLoader implements IDictionaryLoader {

	@Override
	public Map<String, String> load() {
		Map<String, String> dict = new HashMap<String, String>();
		Properties props = new Properties();

		try (FileInputStream in = new FileInputStream("db_connect.properties")) {
			props.load(in);
			in.close();
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
			Statement st = connect.createStatement();
			ResultSet res = st.executeQuery("SELECT word, translate FROM DICT_TABLE");
			while (res.next()) {
				dict.put(res.getString("word"), res.getString("translate"));
			}
			connect.close();
		} catch (SQLException ex) {
			System.out.println("no connect");
		}
		return dict;
	}
}

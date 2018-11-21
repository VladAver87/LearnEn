package swt.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WordsDAO implements IWordsDAO {
	
	private DBConnector dbconnector;
	private final Logger log = LoggerFactory.getLogger(WordsDAO.class);
	
	public WordsDAO (DBConnector dbconnector) {
		this.dbconnector = dbconnector;
	}


	@Override
	public void putToDB(String word, String translate) {


		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
			String sqlQuery = "INSERT INTO DICT_TABLE (word, translate) VALUES (?, ?)";	
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, word);
			st.setString(2, translate);
			st.execute();
			log.debug("Word {} add to dict", word);
		} catch (SQLException ex) {
			log.debug("add word error");
		}
	}

	@Override
	public void delFromDB(String word) {
		
		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
			String sqlQuery = "DELETE FROM DICT_TABLE WHERE word = ?";
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, word);
			st.execute();
			log.debug("Word {} del from dict", word);
		} catch (SQLException ex) {
			log.debug("delete word error");
		}
	}

	@Override
	public Map<String, String> load() {
		Map<String, String> dict = new HashMap<String, String>();
		
		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
			Statement st = connect.createStatement();
			ResultSet res = st.executeQuery("SELECT word, translate FROM DICT_TABLE");
			while (res.next()) {
				dict.put(res.getString("word"), res.getString("translate"));
			}	
			log.debug("Dictionary is load");
		} catch (SQLException ex) {
			log.debug("Dictionary is not load");
		}
		return dict;
	}
	
}

package swt.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordsDAO implements IWordsDAO {

	public static WordsDAO wordsDAO = new WordsDAO(DBConnector.dbconnector);
	private DBConnector dbconnector = DBConnector.dbconnector;
	private final Logger log = LoggerFactory.getLogger(WordsDAO.class);
	private Shell dbErrorShell = new Shell();

	private WordsDAO(DBConnector dbconnector) {

	}

	@Override
	public void putToDB(String user, String word, String translate) {

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			String sqlQuery = "INSERT INTO DICT_TABLE (word, translate) VALUES (?, ?)";
			String sqlQuery2 = "INSERT INTO user_islearned (login, word, islearned) VALUES (?, ?, ?)";
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, word);
			st.setString(2, translate);
			st.execute();
			log.info("Word {} add to dict", word);
			PreparedStatement st2 = connect.prepareStatement(sqlQuery2);
			st2.setString(1, user);
			st2.setString(2, word);
			st2.setBoolean(3, false);
			st2.execute();
			log.info("flag false to {} add to user_islearned", word);
		} catch (SQLException ex) {
			log.error("add word error", ex);
			MessageBox messageBox = new MessageBox(dbErrorShell);
			messageBox.setMessage("Unable to add word from DataBase");
			messageBox.open();
		}
	}

	public void putAnswerToDB(String user, String word, Boolean answer) {

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			String sqlQuery = "UPDATE user_islearned SET islearned = ? WHERE word = ? AND login = ?";
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setBoolean(1, answer);
			st.setString(2, word);
			st.setString(3, user);
			st.execute();
			log.info("Answer to {} add to dict", word);
		} catch (SQLException ex) {
			log.error("add answer error", ex);
			MessageBox messageBox = new MessageBox(dbErrorShell);
			messageBox.setMessage("Unable to add word from DataBase");
			messageBox.open();
		}
	}


	@Override
	public void delFromDB(String word) {

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			String sqlQuery = "DELETE FROM DICT_TABLE WHERE word = ?";
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, word);
			st.execute();
			log.info("Word {} del from dict", word);
		} catch (SQLException ex) {
			log.error("delete word error", ex);
			MessageBox messageBox = new MessageBox(dbErrorShell);
			messageBox.setMessage("Unable to delete word from DataBase");
			messageBox.open();
		}
	}

	@Override
	public Map<String, String> load() {
		Map<String, String> dict = new HashMap<String, String>();

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			Statement st = connect.createStatement();
			ResultSet res = st.executeQuery("SELECT word, translate FROM DICT_TABLE");
			while (res.next()) {
				dict.put(res.getString("word"), res.getString("translate"));
			}
			log.info("Dictionary is load");
		} catch (SQLException ex) {
			log.error("Dictionary is not load", ex);
		}
		return dict;
	}
	
	public Map<String, Boolean> loadAnswer(String user) {
		Map<String, Boolean> answer = new HashMap<String, Boolean>();

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			Statement st = connect.createStatement();
			ResultSet res = st.executeQuery("SELECT word, islearned FROM user_islearned WHERE login = '"+ user +"'");
			while (res.next()) {
				answer.put(res.getString("word"), res.getBoolean("islearned"));
			}
			log.info("AnswerMap is load");
		} catch (SQLException ex) {
			log.error("AnswerMap is not load", ex);
		}
		return answer;
	}
}

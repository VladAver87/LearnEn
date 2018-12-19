package swt.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WordsDAO implements IWordsDAO {
	
	public static WordsDAO exchangeWithDB = new WordsDAO(DBConnector.dbconnector);
	private DBConnector dbconnector = DBConnector.dbconnector;
	private final Logger log = LoggerFactory.getLogger(WordsDAO.class);
	private Shell dbErrorShell = new Shell();
	
	private WordsDAO (DBConnector dbconnector) {

	}


	@Override
	public void putToDB(String word, String translate) {


		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
			String sqlQuery = "INSERT INTO DICT_TABLE (word, translate) VALUES (?, ?)";	
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, word);
			st.setString(2, translate);
			st.execute();
			log.info("Word {} add to dict", word);
		} catch (SQLException ex) {
			log.error("add word error", ex);
			MessageBox messageBox = new MessageBox(dbErrorShell);
			messageBox.setMessage("Unable to add word from DataBase");
			messageBox.open();
		}
	}
	
	public void putToDBsavedDicts(String name) {


		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
			String sqlQuery = "INSERT INTO saved_dicts (name) VALUES (?)";
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, name);
			st.execute();
			log.info("new dict is Sucssesfuly added", name);
		} catch (SQLException ex) {
			log.error("add dict error", ex);
			MessageBox messageBox = new MessageBox(dbErrorShell);
			messageBox.setMessage("Unable to add name from DataBase");
			messageBox.open();
		}
	}
		
		public void putToDBwordsForSavedDicts(String name, String word) {


			try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
				String sqlQuery = "INSERT INTO dict_saved_dicts (name, word) VALUES (?, ?)";	
				PreparedStatement st = connect.prepareStatement(sqlQuery);
				st.setString(1, name);
				st.setString(2, word);
				st.execute();
				log.info("new words to saved Dict is Sucssesfuly added", name);
			} catch (SQLException ex) {
				log.error("add to saved Dict word error", ex);
				MessageBox messageBox = new MessageBox(dbErrorShell);
				messageBox.setMessage("Unable to add words to saved Dict from DataBase");
				messageBox.open();
			}
	}

	@Override
	public void delFromDB(String word) {
		
		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
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
	
	public void delFromSavedDictDB(String name) {
		
		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
			String sqlQuery = "DELETE FROM saved_dicts WHERE name = ?";
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, name);
			st.execute();
			log.info("Dictionary {} del from BD", name);
		} catch (SQLException ex) {
			log.error("delete dict error", ex);
			MessageBox messageBox = new MessageBox(dbErrorShell);
			messageBox.setMessage("Unable to delete word from DataBase");
			messageBox.open();
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
			log.info("Dictionary is load");
		} catch (SQLException ex) {
			log.error("Dictionary is not load", ex);
		}
		return dict;
	}
	
	public List<String> loadSavedDicts() {
		List<String> savedDict = new ArrayList<>();
		
		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
			Statement st = connect.createStatement();
			ResultSet res = st.executeQuery("SELECT name FROM saved_dicts");
			while (res.next()) {
				savedDict.add(res.getString("name"));
			}	
			log.info("Saved Dictionaries is load");
		} catch (SQLException ex) {
			log.error("Saved Dictionaries is not load", ex);
		}
		return savedDict;
	}
	
	public List<String> loadWordsFromSavedDict(String nameDict) {
		List<String> wordsList = new ArrayList<>();
		
		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(), dbconnector.getPassword())) {
			String sqlQuery = "SELECT word FROM dict_saved_dicts WHERE name = ?";			
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, nameDict);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				wordsList.add(rs.getString("word"));
			}
			st.execute();		
			log.info("Select all words from {} dictionary", nameDict);
		} catch (SQLException ex) {
			log.error("get words from saved dictionary error", ex);
		}
		return wordsList;
	}
}

package swt.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SavedDictionariesDAO {
	public static SavedDictionariesDAO savedDictionariesDAO = new SavedDictionariesDAO(DBConnector.dbconnector);
	private DBConnector dbconnector = DBConnector.dbconnector;
	private final Logger log = LoggerFactory.getLogger(SavedDictionariesDAO.class);
	private Shell savedDictErrorshell = new Shell();

	private SavedDictionariesDAO(DBConnector dbconnector) {

	}

	public void putToDBSavedDicts(String user, String dictName, ArrayList<String> wordsList) {

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			connect.setAutoCommit(false);
			String addDict = "INSERT INTO saved_dicts (name, login) VALUES (?, ?)";
			String addWordsToDict = "INSERT INTO dict_saved_dicts (name, word) VALUES (?, ?)";
			try (PreparedStatement st1 = connect.prepareStatement(addDict)) {
				st1.setString(1, dictName);
				st1.setString(2, user);
				st1.execute();
				log.info("new dict is Sucssesfuly added", dictName);
				PreparedStatement st2 = connect.prepareStatement(addWordsToDict);
				st2.setString(1, dictName);
				for (String s : wordsList) {
					st2.setString(2, s);
					st2.execute();				
				}
				connect.commit();
				log.info("new words to saved Dict is Sucssesfuly added", dictName);
			} catch (SQLException ex) {
				connect.rollback();
				log.error("add to saved Dict word error", ex);
				MessageBox messageBox = new MessageBox(savedDictErrorshell);
				messageBox.setMessage("Unable to add words to saved Dict from DataBase");
				messageBox.open();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delFromSavedDictDB(String name) {

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			String sqlQuery = "DELETE FROM saved_dicts WHERE name = ?";
			PreparedStatement st = connect.prepareStatement(sqlQuery);
			st.setString(1, name);
			st.execute();
			log.info("Dictionary {} del from BD", name);
		} catch (SQLException ex) {
			log.error("delete dict error", ex);
			MessageBox messageBox = new MessageBox(savedDictErrorshell);
			messageBox.setMessage("Unable to delete word from DataBase");
			messageBox.open();
		}
	}

	public List<String> loadSavedDicts(String user) {
		List<String> savedDict = new ArrayList<>();

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
			Statement st = connect.createStatement();
			ResultSet res = st.executeQuery("SELECT name FROM saved_dicts WHERE login = '"+ user +"'");
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

		try (Connection connect = DriverManager.getConnection(dbconnector.getUrl(), dbconnector.getUsername(),
				dbconnector.getPassword())) {
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

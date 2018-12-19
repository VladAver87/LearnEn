package swt.project.dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



import swt.project.db.DBConnector;
import swt.project.db.WordsDAO;
import swt.project.utils.Utils;

public class Dictionary implements IDictionary {
	
	public static Dictionary dictionary = new Dictionary(DBConnector.dbconnector, WordsDAO.exchangeWithDB);
	private Map<String, String> dict;
	private Map<String, String> supportDict;
	private WordsDAO exchangeWithDB = WordsDAO.exchangeWithDB;

	
	private Dictionary(DBConnector dbconnector, WordsDAO exchangeWithDB) {
		this.dict = exchangeWithDB.load();
		this.supportDict = putWordsToSupportDict();

	}
	
	private Map<String, String> putWordsToSupportDict() {
		supportDict = new HashMap<>();
		for (Entry<String, String> entry : dict.entrySet()) {
			supportDict.put(Utils.concatString(entry.getKey() , entry.getValue()), entry.getKey());
		}
		return supportDict;		
	}
	
	public String getWordToDel(String value) {
		String key = supportDict.get(value);
		return key;
	}
	
	@Override
	public void addWord(String word, String translate) {
		dict.put(word, translate);
		supportDict.put(Utils.concatString(word, translate) , word);
		exchangeWithDB.putToDB(word, translate);
	}

	@Override
	public void removeWord(String word) {
		dict.remove(word);
		supportDict.values().remove(word);
		exchangeWithDB.delFromDB(word);
	}
	
	@Override
	public Map<String, String> showAllWords() {
		return dict;
		
	}

}

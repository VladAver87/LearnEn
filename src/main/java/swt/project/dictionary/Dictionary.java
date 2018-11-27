package swt.project.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import swt.project.db.DBConnector;
import swt.project.db.WordsDAO;
import swt.project.utils.Utils;

public class Dictionary implements IDictionary {

	private Map<String, String> dict;
	private Map<String, String> supportDict;
	private WordsDAO exchangeWithDB;
	
	public Dictionary(DBConnector dbconnector, WordsDAO exchangeWithDB) {
		this.dict = exchangeWithDB.load();
		this.supportDict = putWordsToSupportDict();
		this.exchangeWithDB = exchangeWithDB;
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
	
	public void addWordToSupportDict(String word, String translate) {
		supportDict.put(word, translate);

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
	
	private List<String> DictToList(){
		List<String> wordList = new ArrayList<String>(dict.keySet());
		return wordList;
		
	}
	
	public String getNextWord(int x) {
		String nextWord = DictToList().get(x);
		return nextWord;
		
	}

}

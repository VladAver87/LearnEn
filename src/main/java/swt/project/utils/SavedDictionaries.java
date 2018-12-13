package swt.project.utils;

import java.util.ArrayList;
import java.util.List;

import swt.project.db.WordsDAO;

public class SavedDictionaries {
	private List<String> savedDicts = new ArrayList<>();
	private WordsDAO exchangeWithDB;
	
	public SavedDictionaries(WordsDAO exchangeWithDB) {
		this.savedDicts = exchangeWithDB.loadSavedDicts();
		this.exchangeWithDB = exchangeWithDB;
		
	}

	public void addToListSavedDicts(String name) {
		savedDicts.add(name);

	}
	
	public void addToBDSavedDicts(String name) {
		exchangeWithDB.putToDBsavedDicts(name);

	}
	
	public void addToBDSavedDicts(String name, String word) {
		exchangeWithDB.putToDBwordsForSavedDicts(name, word);

	}
	
	public void removeFromDBSavedDict(String savedDict) {
		exchangeWithDB.delFromSavedDictDB(savedDict);

	}
	
	public List<String> showWordsInSelectedDict(String name) {
		return exchangeWithDB.loadWordsFromSavedDict(name);
	}
	
	
	public List<String> showSavedDicts() {
		return savedDicts;
		
	}
}

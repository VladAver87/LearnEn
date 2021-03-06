package swt.project.utils;


import java.util.ArrayList;
import java.util.List;

import swt.project.db.ISavedDictionariesDAO;
import swt.project.db.SavedDictionariesDAO;

public class SavedDictionaries implements ISavedDictionariesDAO {
	private List<String> savedDicts = new ArrayList<>();
	private SavedDictionariesDAO savedDictionariesDAO = SavedDictionariesDAO.savedDictionariesDAO;
	
	public SavedDictionaries(SavedDictionariesDAO savedDictionariesDAO) {
		this.savedDicts = savedDictionariesDAO.loadSavedDicts();
		
	}

	@Override
	public void addToBDSavedDicts(String name, ArrayList<String> words) throws Exception {
		if (!savedDicts.contains(name))
		{
		savedDicts.add(name);		
		savedDictionariesDAO.putToDBSavedDicts(name, words);	
		}
		else 
		{
			throw new Exception();
		}

	}
	@Override
	public void removeFromDBSavedDict(String savedDict) {
		savedDictionariesDAO.delFromSavedDictDB(savedDict);

	}
	@Override
	public List<String> showWordsInSelectedDict(String name) {
		return savedDictionariesDAO.loadWordsFromSavedDict(name);
	}
	
	public List<String> showSavedDicts() {
		return savedDicts;
		
	}
}

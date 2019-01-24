package swt.project.db;

import java.util.ArrayList;
import java.util.List;

public interface ISavedDictionariesDAO {
	public void addToBDSavedDicts(String user, String name, ArrayList<String> words) throws Exception;
	public void removeFromDBSavedDict(String savedDict);
	public List<String> showWordsInSelectedDict(String name);
	
}

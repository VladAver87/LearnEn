package swt.project.db;

import java.util.Map;

public interface IWordsDAO {
	public void putToDB(String word, String translate);
	public void delFromDB(String word);
	public Map<String, String> load();
	
}

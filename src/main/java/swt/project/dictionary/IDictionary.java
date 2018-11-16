package swt.project.dictionary;

import java.util.List;
import java.util.Map;

public interface IDictionary {
	
	public void addWord(String word, String translate);
	public  void removeWord(String word);
	public Map<String, String> showAllWords ();
}
package swt.project.dictionary;


import java.util.Map;

public interface IDictionary {
	
	public void addWord(String user, String word, String translate);
	public  void removeWord(String word);
	public Map<String, String> showAllWords ();
}
package swt.project.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Dictionary implements IDictionary {

	private Map<String, String> dict;
	private Map<String, String> supportDict;
	
	public Dictionary() {
		IDictionaryLoader loader = new InDBDictionaryLoader();
		this.dict = loader.load();
		this.supportDict = putWordsToSupportDict();
	}
	
	private Map<String, String> putWordsToSupportDict() {
		supportDict = new HashMap<>();
		for (Entry<String, String> entry : dict.entrySet()) {
			supportDict.put(entry.getKey() + " - " + entry.getValue(), entry.getKey());
		}
		return supportDict;		
	}
	
	public  Map<String, String> showSupportDict(){
		return supportDict;
		
	}
	
	public void addWordToSupportDict(String word, String translate) {
		supportDict.put(word, translate);

	}
	
	@Override
	public void addWord(String word, String translate) {
		dict.put(word, translate);

	}

	@Override
	public void removeWord(String word) {
		dict.remove(word);

	}

	@Override
	public Map<String, String> chooseWords(List<String> listWords) {
		Map<String, String> dictFilter = new HashMap<String, String>();
		for (String s : listWords) {
			if (dict.containsKey(s)) {
				dictFilter.put(s, dict.get(s));
			}
		}
		return dictFilter;
	}
	
	@Override
	public Map<String, String> showAllWords() {

		return dict;
	}

}

package swt.project.dictionary;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDictionaryLoader implements IDictionaryLoader {
	
	@Override
	public Map<String, String> load() {
		Map<String, String> dict = new HashMap<String, String>();
		dict.put("go", "ходить");
		dict.put("help", "помощь");
		dict.put("drink", "пить");
		dict.put("driver", "водитель");
		dict.put("map", "карта");
		return dict;
	}

}

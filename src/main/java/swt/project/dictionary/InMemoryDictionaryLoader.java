package swt.project.dictionary;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDictionaryLoader implements IDictionaryLoader {
	
	@Override
	public Map<String, String> load() {
		Map<String, String> dict = new HashMap<String, String>();
		dict.put("go", "������");
		dict.put("help", "������");
		dict.put("drink", "����");
		dict.put("driver", "��������");
		dict.put("map", "�����");
		return dict;
	}

}

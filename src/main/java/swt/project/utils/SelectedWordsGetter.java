package swt.project.utils;

import java.util.ArrayList;
import java.util.List;

import swt.project.ListWindow;
import swt.project.dictionary.Dictionary;

public class SelectedWordsGetter {
	
	private ListWindow listWords;
	private Dictionary dictionary;
	
	public SelectedWordsGetter(ListWindow listWords, Dictionary dictionary) {
		this.listWords = listWords;
		this.dictionary = dictionary;
		getSelectedWordsFromList();
	}
	
	public List<String> getSelectedWordsFromList() {
		List<String> list = new ArrayList<>();
		int[] selectedItems = listWords.getListWords().getSelectionIndices();			
		for (int i = 0 ; i < selectedItems.length; i++) {			
			String wordToPut = listWords.getListWords().getItem(selectedItems[i]);		
			String tmp = dictionary.getWordToDel(wordToPut);
			list.add(tmp);	
			}
		return list;
	}
	
	

}

package swt.project.utils;

import java.util.ArrayList;
import java.util.List;

import swt.project.ListWindow;
import swt.project.dictionary.Dictionary;

public class SelectedWordsGetter {
	
	private ListWindow listWords;
	private Dictionary dictionary;
	private List<String> selectedWordsList = new ArrayList<>();
	
	public SelectedWordsGetter(ListWindow listWords, Dictionary dictionary) {
		this.listWords = listWords;
		this.dictionary = dictionary;

	}
	
	public List<String> getSelectedList(){
		return selectedWordsList;
	}
	
	public void getSelectedWordsFromList() {		
		int[] selectedItems = listWords.getListWords().getSelectionIndices();			
		for (int i = 0 ; i < selectedItems.length; i++) {			
			String wordToPut = listWords.getListWords().getItem(selectedItems[i]);		
			String tmp = dictionary.getWordToDel(wordToPut);
			selectedWordsList.add(tmp);	
			}
	}
	

}

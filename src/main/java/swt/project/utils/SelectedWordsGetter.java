package swt.project.utils;

import java.util.ArrayList;
import java.util.List;

import swt.project.ListWindow;
import swt.project.SavedDictWindow;
import swt.project.dictionary.Dictionary;

public class SelectedWordsGetter {
	
	private ListWindow listWords;
	private SavedDictWindow savedDictWindow;
	private Dictionary dictionary = Dictionary.dictionary;
	private SavedDictionaries savedDictionaries;
	private List<String> selectedWordsList = new ArrayList<>();
	
	public SelectedWordsGetter(ListWindow listWords, Dictionary dictionary, SavedDictWindow savedDictWindow, SavedDictionaries savedDictionaries) {
		this.listWords = listWords;
		this.savedDictWindow = savedDictWindow;
		this.savedDictionaries = savedDictionaries;
	}
	
	public List<String> getSelectedList(){
		return selectedWordsList;
	}
	
	public void getSelectedWordsFromList() {
		selectedWordsList.clear();
		int[] selectedItems = listWords.getListWords().getSelectionIndices();			
		for (int i = 0 ; i < selectedItems.length; i++) {			
			String wordToPut = listWords.getListWords().getItem(selectedItems[i]);		
			String tmp = dictionary.getWordToDel(wordToPut);
			selectedWordsList.add(tmp);	
			}
	}
	
	public void getWordsFromSelectedDict() {
		selectedWordsList.clear();
		int selectedItems = savedDictWindow.getListOfDict().getSelectionIndex();
		String dictToDel = savedDictWindow.getListOfDict().getItem(selectedItems);
		for (String s : savedDictionaries.showWordsInSelectedDict(dictToDel)) {
			selectedWordsList.add(s);

			}
	}
	

}

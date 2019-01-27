package swt.project;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import swt.project.dictionary.Dictionary;
import swt.project.users.User;
import swt.project.utils.SavedDictionaries;

public class SavedDictWindow {

	private Shell savedDictShell = new Shell(SWT.CLOSE);
	private List listOfDict = new List(savedDictShell, SWT.SINGLE | SWT.V_SCROLL);
	private List wordsInSavedDict = new List(savedDictShell, SWT.MULTI | SWT.V_SCROLL);
	private SavedDictionaries savedDictionaries;
	private int selectedItems;
	private Dictionary dictionary = Dictionary.dictionary;
	private User user;

	public SavedDictWindow(SavedDictionaries savedDictionaries, User user) {
		this.savedDictionaries = savedDictionaries;
		this.user = user;
	}

	public Shell getSavedDictShell() {
		return savedDictShell;
	}

	public List getListOfDict() {
		return listOfDict;
	}

	private String getWordToAddFromList() {
		selectedItems = listOfDict.getSelectionIndex();
		return listOfDict.getItem(selectedItems);
	}

	private void putWordsToListOfDict() {
		for (String s : savedDictionaries.getSavedDicts()) {
			listOfDict.add(s);
		}
	}

	private ArrayList<String> getSelectedWords() {
		ArrayList<String> selectedWordsList = new ArrayList<>();
		for (String s : savedDictionaries.showWordsInSelectedDict(getWordToAddFromList())) {
			selectedWordsList.add(s);
		}
		return selectedWordsList;
	}

	private void removeFromSavedDict() {
		selectedItems = listOfDict.getSelectionIndex();
		String dictToDel = listOfDict.getItem(selectedItems);
		savedDictionaries.removeFromDBSavedDict(dictToDel);
		listOfDict.remove(selectedItems);
	}

	private void init() {
		Button refreshButton = new Button(savedDictShell, SWT.PUSH);
		Button learnDictButton = new Button(savedDictShell, SWT.PUSH);
		Button showWordsButton = new Button(savedDictShell, SWT.PUSH);
		Button backButton = new Button(savedDictShell, SWT.ARROW | SWT.LEFT);
		Button delDictButton = new Button(savedDictShell, SWT.PUSH);
		Font listOfDictfont = new Font(null, "Arial", 14, SWT.NORMAL);
		listOfDict.setBounds(5, 50, 290, 400);
		listOfDict.setFont(listOfDictfont);
		listOfDict.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectedItems != -1) {
					delDictButton.setEnabled(true);
					showWordsButton.setEnabled(true);
					learnDictButton.setEnabled(true);
				}
			}
		});

		wordsInSavedDict.setBounds(5, 50, 290, 400);
		wordsInSavedDict.setFont(listOfDictfont);
		wordsInSavedDict.setVisible(false);
		wordsInSavedDict.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});

		showWordsButton.setSize(110, 30);
		showWordsButton.setLocation(20, 520);
		showWordsButton.setText("Show Words");
		showWordsButton.setEnabled(false);
		showWordsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (String s : savedDictionaries.showWordsInSelectedDict(getWordToAddFromList())) {
					wordsInSavedDict.add(s);
				}
				listOfDict.setVisible(false);
				wordsInSavedDict.setVisible(true);
				delDictButton.setEnabled(false);
				showWordsButton.setEnabled(false);
				learnDictButton.setEnabled(false);
				backButton.setEnabled(true);
			}
		});

		learnDictButton.setSize(110, 30);
		learnDictButton.setLocation(20, 470);
		learnDictButton.setText("Learn Dict");
		learnDictButton.setEnabled(false);
		learnDictButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new LearnWindow(dictionary, getSelectedWords(), user).open();
				savedDictShell.close();
			}
		});

		backButton.setSize(50, 40);
		backButton.setLocation(10, 5);
		backButton.setText("Refresh");
		backButton.setEnabled(false);
		backButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listOfDict.setVisible(true);
				wordsInSavedDict.setVisible(false);
				delDictButton.setEnabled(true);
				showWordsButton.setEnabled(true);
				learnDictButton.setEnabled(true);
				wordsInSavedDict.removeAll();

			}
		});

		refreshButton.setSize(80, 40);
		refreshButton.setLocation(210, 5);
		refreshButton.setText("Refresh");
		refreshButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				savedDictShell.close();
				new SavedDictWindow(savedDictionaries, user).open();
			}
		});

		delDictButton.setSize(110, 30);
		delDictButton.setLocation(170, 520);
		delDictButton.setText("Delete");
		delDictButton.setEnabled(false);
		delDictButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeFromSavedDict();
			}
		});

	}

	public void open() {
		savedDictShell.setData("savedDicts");
		savedDictShell.setText("Saved Dictionaries");
		savedDictShell.setSize(300, 600);
		savedDictShell.open();
		init();
		putWordsToListOfDict();
	}

}

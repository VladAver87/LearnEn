package swt.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import swt.project.utils.SavedDictionaries;

public class SavedDictWindow {

	private Shell savedDictShell = new Shell(SWT.CLOSE);
	private List listOfDict = new List(savedDictShell, SWT.SINGLE | SWT.V_SCROLL);
	private List wordsInSavedDict = new List(savedDictShell, SWT.MULTI | SWT.V_SCROLL);
	private SavedDictionaries savedDictionaries;
	private int selectedItems;

	public SavedDictWindow(SavedDictionaries savedDictionaries) {
		this.savedDictionaries = savedDictionaries;
	}

	public List getListOfDict() {
		return listOfDict;
	}

	private void putWordsToListOfDict() {
		for (String s : savedDictionaries.showSavedDicts()) {
			listOfDict.add(s);
		}
	}

	private void removeFromSavedDict() {
		selectedItems = listOfDict.getSelectionIndex();
		String dictToDel = listOfDict.getItem(selectedItems);
		savedDictionaries.removeFromDBSavedDict(dictToDel);
		listOfDict.remove(selectedItems);
	}

	private void init() {
		Button refreshButton = new Button(savedDictShell, SWT.PUSH);
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
				selectedItems = listOfDict.getSelectionIndex();
				String dictToDel = listOfDict.getItem(selectedItems);
				for (String s : savedDictionaries.showWordsInSelectedDict(dictToDel)) {
					wordsInSavedDict.add(s);
				}
				listOfDict.setVisible(false);
				wordsInSavedDict.setVisible(true);
			}
		});

		backButton.setSize(50, 40);
		backButton.setLocation(10, 5);
		backButton.setText("Refresh");
		backButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listOfDict.setVisible(true);
				wordsInSavedDict.setVisible(false);
				wordsInSavedDict.removeAll();
			}
		});

		refreshButton.setSize(80, 40);
		refreshButton.setLocation(210, 5);
		refreshButton.setText("Refresh");
		refreshButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// как то сделать refresh
				listOfDict.update();
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

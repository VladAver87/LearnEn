package swt.project;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import swt.project.dictionary.Dictionary;
import swt.project.users.User;
import swt.project.utils.SavedDictionaries;
import swt.project.utils.Utils;

public class ListWindow {

	private int[] selectedItems;
	private int selectionIndex;
	private Shell listshell = new Shell(SWT.CLOSE);
	private Table listWords = new Table(listshell, SWT.MULTI | SWT.V_SCROLL);
	private Dictionary dictionary = Dictionary.dictionary;
	private SavedDictionaries savedDictionaries;
	private Color wrongAnswerColor = new Color(null, 255, 0, 0);
	private Color rightAnswerColor = new Color(null, 000, 0, 0);
	private User user;

	public ListWindow(Dictionary dictionary, SavedDictionaries savedDictionaries, User user) {
		this.savedDictionaries = savedDictionaries;
		this.user = user;
	}

	public Table getListWords() {
		return listWords;
	}

	public ArrayList<String> getSelectedWords() {
		ArrayList<String> selectedWordsList = new ArrayList<>();
		selectedItems = listWords.getSelectionIndices();
		for (int i = 0; i < selectedItems.length; i++) {
			TableItem wordToPut = listWords.getItem(selectedItems[i]);
			String tmp = dictionary.getWordToDel(wordToPut.getText());
			selectedWordsList.add(tmp);
		}
		return selectedWordsList;
	}

	private void wordsFromDictToList(Table listWords) {
		if (listWords != null) {
			for (Entry<String, String> entry : dictionary.showAllWords().entrySet()) {
				TableItem item = new TableItem(listWords, SWT.BORDER);
				item.setText(Utils.concatString(entry.getKey(), entry.getValue()));
				if (!dictionary.getAnswer(entry.getKey())) {
					item.setForeground(wrongAnswerColor);
				}
				else 
				{
					item.setForeground(rightAnswerColor);
				}
			}
		}
	}


	private void removeFromDict() {
		selectedItems = listWords.getSelectionIndices();
		for (int i = 0; i < selectedItems.length; i++) {
			TableItem wordToDel = listWords.getItem(selectedItems[i]);
			String tmp = dictionary.getWordToDel(wordToDel.getText());
			dictionary.removeWord(tmp);
		}
		listWords.remove(selectedItems);
	}

	public ArrayList<String> allWordsInListWindow(Table listWords) {
		ArrayList<String> list = new ArrayList<>();
		TableItem[] allwords = listWords.getItems();
		for (TableItem s : allwords) {
			String word = dictionary.getWordToDel(s.getText());
			list.add(word);
		}

		return list;

	}

	private void init() {
		Button saveDictButton = new Button(listshell, SWT.PUSH);
		Button openSaveDictButton = new Button(listshell, SWT.PUSH);
		Button addButton = new Button(listshell, SWT.PUSH);
		Button delButton = new Button(listshell, SWT.PUSH);
		Button learnSelectedButton = new Button(listshell, SWT.PUSH);
		Button selectAllButton = new Button(listshell, SWT.PUSH);
		Font listWordsfont = new Font(null, "Arial", 14, SWT.NORMAL);
		listWords.setSize(390, 595);
		listWords.setLocation(5, 50);
		listWords.setFont(listWordsfont);
		listWords.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectionIndex != -1) {
					delButton.setEnabled(true);
					learnSelectedButton.setEnabled(true);
					saveDictButton.setEnabled(true);
				}
			}
		});

		addButton.setSize(110, 30);
		addButton.setLocation(50, 715);
		addButton.setText("Add Word");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell[] shells = Display.getCurrent().getShells();
				for (Shell shell : shells) {
					String data = (String) shell.getData();
					if (data != null && data.equals("addWord")) {
						shell.setFocus();
						return;
					}
				}
				new AddWordWindow(ListWindow.this, dictionary).open();
			}
		});

		delButton.setSize(110, 30);
		delButton.setLocation(250, 715);
		delButton.setText("Delete Word");
		delButton.setEnabled(false);
		delButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeFromDict();
			}
		});

		learnSelectedButton.setSize(110, 30);
		learnSelectedButton.setLocation(50, 665);
		learnSelectedButton.setText("Learn Selected");
		learnSelectedButton.setEnabled(false);
		learnSelectedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new LearnWindow(dictionary, getSelectedWords()).open();
				listshell.close();
			}
		});

		selectAllButton.setSize(110, 30);
		selectAllButton.setLocation(250, 665);
		selectAllButton.setText("Select all");
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listWords.selectAll();
				delButton.setEnabled(true);
				learnSelectedButton.setEnabled(true);
			}
		});

		openSaveDictButton.setSize(150, 30);
		openSaveDictButton.setLocation(230, 10);
		openSaveDictButton.setText("Saved Dictionaries");
		openSaveDictButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell[] shells = Display.getCurrent().getShells();
				for (Shell shell : shells) {
					String data = (String) shell.getData();
					if (data != null && data.equals("savedDicts")) {
						shell.setFocus();
						return;
					}
				}
				new SavedDictWindow(savedDictionaries).open();
			}
		});

		saveDictButton.setSize(195, 30);
		saveDictButton.setLocation(15, 10);
		saveDictButton.setEnabled(false);
		saveDictButton.setText("Save selected to new Dictionary");
		saveDictButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell[] shells = Display.getCurrent().getShells();
				for (Shell shell : shells) {
					String data = (String) shell.getData();
					if (data != null && data.equals("savedDictsDialog")) {
						shell.setFocus();
						return;
					}
				}
				new SaveDictDialogWindow(savedDictionaries, ListWindow.this, user).open();

			}
		});

	}

	public void open() {
		listshell.setData("list");
		listshell.setLayout(new FillLayout(SWT.VERTICAL));
		listshell.setText("Main Dictionary");
		listshell.setSize(400, 800);
		listshell.open();
		init();
		wordsFromDictToList(listWords);
	}
}

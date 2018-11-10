package swt.project;


import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import swt.project.dictionary.Dictionary;


public class ListWindow {
		
	private int[] selectedItems;
	private int selectionIndex;
	private Shell listshell = new Shell(SWT.CLOSE);
	private List listWords = new List(listshell, SWT.MULTI | SWT.V_SCROLL);
	private Dictionary dictionary;

	public ListWindow(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public List getListWords() {
		return listWords;
	}
	
	private void wordsFromDictToList(List listWords) {
		if (listWords != null) {
		for (Entry<String, String> entry : dictionary.showAllWords().entrySet()) {
			listWords.add(entry.getKey() + " - " + entry.getValue());
			}
		}	
	}	
	
	private void removeFromDict(){
		String wordToDel;
		selectedItems = listWords.getSelectionIndices();			
		for (int i = 0 ; i < selectedItems.length; i++) {			
			wordToDel = listWords.getItem(selectedItems[i]);			
			String tmp = dictionary.showSupportDict().get(wordToDel);
				dictionary.removeWord(tmp);
			}			
		}
		
	private List init() {
		Font listWordsfont = new Font(null, "Arial", 14, SWT.NORMAL);
		listWords.setBounds(5, 5, 390, 695);
		listWords.setFont(listWordsfont);

		listWords.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});

		Button addButton = new Button(listshell, SWT.PUSH);
		addButton.setSize(110, 30);
		addButton.setLocation(50, 715);
		addButton.setText("Add Word");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			Shell[] shells = Display.getCurrent().getShells();
			for (Shell shell : shells) {
				String data = (String) shell.getData();
				if(data != null && data.equals("addWord")) {
					shell.setFocus();
					return;
				}
			}
				new AddWordWindow(ListWindow.this,dictionary).open();
			}
		});

		Button delButton = new Button(listshell, SWT.PUSH);
		delButton.setSize(110, 30);
		delButton.setLocation(250, 715);
		delButton.setText("Delete Word");
//		delButton.setEnabled(selectionIndex != -1);
		delButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeFromDict();
				if (listWords != null) {
					listWords.remove(selectedItems);									
					}
				
			}
		});
		return listWords;

	}

	public void open() {
		listshell.setData("list");
		listshell.setLayout(new FillLayout(SWT.VERTICAL));
		listshell.setText("List Words");
		listshell.setSize(400, 800);
		listshell.open();
		init();
		wordsFromDictToList(listWords);
	}
}
// � ����� ������ ��������/�������, ������� �� ������� ���� ����� �� �������,
// ��� ���������� ����������� ����� ���� �����/�������

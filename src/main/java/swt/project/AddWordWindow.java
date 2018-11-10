package swt.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swt.project.dictionary.Dictionary;

public class AddWordWindow {
	
	private Shell addWordshell = new Shell(SWT.CLOSE);
	private ListWindow listWords;
	private Dictionary dictionary;
	private MessageBox messageBox;


	public AddWordWindow(ListWindow listWords, Dictionary dictionary) {
		this.listWords = listWords;
		this.dictionary = dictionary;
	}

	private void initAddWordWindow(Shell addWordshell) {
		Text addWord = new Text(addWordshell, SWT.BORDER);
		addWord.setText("Type Word");
		addWord.setLocation(20, 20);
		addWord.setSize(200, 40);
		addWord.setTextLimit(30);
		addWord.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				addWord.setText("");
			}
		});

		Text addTranslate = new Text(addWordshell, SWT.BORDER);
		addTranslate.setText("Type Translate");
		addTranslate.setLocation(240, 20);
		addTranslate.setSize(200, 40);
		addTranslate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				addTranslate.setText("");
			}
		});

		Button addWordButton = new Button(addWordshell, SWT.PUSH);
		addWordButton.setSize(110, 30);
		addWordButton.setLocation(20, 100);
		addWordButton.setText("add");
		addWordButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dictionary.addWordToSupportDict(addWord.getText() + " - " + addTranslate.getText(), addWord.getText());
				dictionary.addWord(addWord.getText(), addTranslate.getText());
				try {
					if (listWords.getListWords().indexOf(addWord.getText() + " - " + addTranslate.getText()) == -1) {
						listWords.getListWords().add(addWord.getText() + " - " + addTranslate.getText());					
					} else
						messageBox = new MessageBox(addWordshell);
						messageBox.setMessage("this one is already add");
						messageBox.open();		
					} catch (NullPointerException ex) {
						
					}
			}
		});
	}

	public void open() {
		addWordshell.setData("addWord");
		addWordshell.setText("All Words List");
		addWordshell.setSize(480, 200);
		addWordshell.open();
		initAddWordWindow(addWordshell);
	}
}

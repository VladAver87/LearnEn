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

import swt.project.utils.SavedDictionaries;
import swt.project.utils.SelectedWordsGetter;


public class SaveDictDialogWindow {
	
	private Shell SaveDictDialogShell = new Shell(SWT.APPLICATION_MODAL|SWT.CLOSE);
	private SavedDictionaries savedDictionaries;
	private SelectedWordsGetter selectedWordsGetter;
	
	
	public SaveDictDialogWindow(SavedDictionaries savedDictionaries, SelectedWordsGetter selectedWordsGetter) {
		this.savedDictionaries = savedDictionaries;
		this.selectedWordsGetter = selectedWordsGetter;
		
	}
	
	private void init() {
		Button saveButton = new Button(SaveDictDialogShell, SWT.PUSH);
		Button cancelButton = new Button(SaveDictDialogShell, SWT.PUSH);
		Text dictName = new Text(SaveDictDialogShell, SWT.BORDER);
		dictName.setText("Type name");
		dictName.setLocation(20, 10);
		dictName.setSize(250, 35);
		dictName.setTextLimit(30);
		dictName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				dictName.setText("");
			}
		});
		
		saveButton.setSize(110, 30);
		saveButton.setLocation(20, 70);
		saveButton.setText("Save");
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				
				if (savedDictionaries.showSavedDicts().contains(dictName.getText())) {
					MessageBox messageBox = new MessageBox(SaveDictDialogShell);
					messageBox.setMessage("this name is busy");
					messageBox.open();
					
				}else 
				{				
					selectedWordsGetter.getSelectedWordsFromList();				
					savedDictionaries.addToListSavedDicts(dictName.getText());
					savedDictionaries.addToBDSavedDicts(dictName.getText());
					for (String s : selectedWordsGetter.getSelectedList()){
					savedDictionaries.addToBDSavedDicts(dictName.getText(), s);
				}
					SaveDictDialogShell.close();
				}
				
				
			}
		});		
		
		cancelButton.setSize(110, 30);
		cancelButton.setLocation(160, 70);
		cancelButton.setText("Cancel");
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				SaveDictDialogShell.close();
			}
		});		
	}
	
	public void open() {
		SaveDictDialogShell.setData("savedDictsDialog");
		SaveDictDialogShell.setText("Set name to save dictionary");
		SaveDictDialogShell.setSize(290, 150);
		SaveDictDialogShell.open();
		init();
	}

}
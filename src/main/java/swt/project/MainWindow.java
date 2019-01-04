package swt.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import swt.project.db.DBConnector;
import swt.project.db.SavedDictionariesDAO;
import swt.project.dictionary.Dictionary;
import swt.project.utils.SavedDictionaries;

public class MainWindow {

	private final Shell shell;
	private DBConnector dbconnector = DBConnector.dbconnector;
	private Dictionary dictionary = Dictionary.dictionary;
	private SavedDictionariesDAO savedDictionariesDAO = SavedDictionariesDAO.savedDictionariesDAO;
	private SavedDictionaries savedDictionaries = new SavedDictionaries(savedDictionariesDAO);

	public MainWindow(Shell shell) {
		this.shell = shell;
	}

	private void setButtons() {
		Font mainWindowButtonsFont = new Font(null, "Arial", 12, SWT.NORMAL);
		
		Button allWordsButton = new Button(shell, SWT.PUSH);
		allWordsButton.setSize(140, 50);
		allWordsButton.setLocation(130, 160);
		allWordsButton.setText("Words to learn");
		allWordsButton.setFont(mainWindowButtonsFont);
		allWordsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				Shell[] shells = Display.getCurrent().getShells();
				for (Shell shell : shells) {
					String data = (String) shell.getData();
					if (data != null && data.equals("list")) {
						shell.setFocus();
						shell.setVisible(true);
						return;
					}
				}
				new ListWindow(dictionary, savedDictionaries).open();
			}
		});
	}

	private void setLabels() {

		Label outWrd = new Label(shell, SWT.CENTER);
		Font mainWindowLabelFont = new Font(null, "Arial", 14, SWT.NORMAL);
		outWrd.setText("Add some words to dictionary \nand hit 'learnWords' to start a test");
		outWrd.setLocation(20, 20);
		outWrd.setSize(360, 80);
		outWrd.setFont(mainWindowLabelFont);
	}

	public void init() {
		setButtons();
		setLabels();
		dbconnector.getConnectProperties(shell);
		dbconnector.tryConnectFromDB(shell);
	}
}

package swt.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swt.project.db.DBConnector;
import swt.project.db.WordsDAO;
import swt.project.dictionary.Dictionary;

public class MainWindow {

	private final Shell shell;
	private DBConnector dbconnector = new DBConnector();
	private WordsDAO exchangeWithDB = new WordsDAO(dbconnector);
	private Dictionary dictionary = new Dictionary(dbconnector, exchangeWithDB);

	public MainWindow(Shell shell) {
		this.shell = shell;
	}

	private void setButtons() {
		Font mainWindowButtonsFont = new Font(null, "Arial", 12, SWT.NORMAL);
		Button learnButton = new Button(shell, SWT.PUSH);
		learnButton.setSize(120, 45);
		learnButton.setLocation(50, 160);
		learnButton.setText("Learn Words");
		learnButton.setFont(mainWindowButtonsFont);
		learnButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell[] shells = Display.getCurrent().getShells();
				for (Shell shell : shells) {
					String data = (String) shell.getData();
					if (data != null && data.equals("learnWindow")) {
						shell.setFocus();
						return;
					}
				}
				new LearnWindow(dictionary).open();
			
			}
		});
		
		Button allWordsButton = new Button(shell, SWT.PUSH);
		allWordsButton.setSize(120, 45);
		allWordsButton.setLocation(240, 160);
		allWordsButton.setText("All Words List");
		allWordsButton.setFont(mainWindowButtonsFont);
		allWordsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				Shell[] shells = Display.getCurrent().getShells();
				for (Shell shell : shells) {
					String data = (String) shell.getData();
					if (data != null && data.equals("list")) {
						shell.setFocus();
						return;
					}
				}
				new ListWindow(dictionary, exchangeWithDB).open();
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
	}
}

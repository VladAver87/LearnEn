package swt.project;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swt.project.db.ExchangeWithDB;
import swt.project.dictionary.Dictionary;

public class MainWindow {

	private final Shell shell;
	private Dictionary dictionary = new Dictionary();
	private ExchangeWithDB exchangeWithDB = new ExchangeWithDB();
	public MainWindow(Shell shell) {
		this.shell = shell;
	}
	
	private void connectionCheck() {
		
		Properties props = new Properties();

		try (FileInputStream in = new FileInputStream("db_connect.properties")) {
			props.load(in);
			in.close();
		} catch (IOException e) {

			MessageBox messageBox = new MessageBox(shell);
			messageBox.setMessage("Unable to read file 'db_connect.properties' "
					+ "\n Please, check for the file 'db_connect.properties' in program directory");
			messageBox.open();
			shell.dispose();
		}

		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		String driver = props.getProperty("jdbc.driver");

		if (driver != null) {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		try (Connection connect = DriverManager.getConnection(url, username, password)) {			
			
		} catch (SQLException ex) {
			MessageBox messageBox = new MessageBox(shell);
			messageBox.setMessage("Unable to connect to database"
					+ "\n Please, check your connection settings in 'db_connect.properties'");
			messageBox.open();
			shell.dispose();	
		}
	}

	private void setButtons() {
		Button learnButton = new Button(shell, SWT.PUSH);
		learnButton.setSize(110, 30);
		learnButton.setLocation(50, 300);
		learnButton.setText("Learn Words");

		Button allWordsButton = new Button(shell, SWT.PUSH);
		allWordsButton.setSize(110, 30);
		allWordsButton.setLocation(50, 350);
		allWordsButton.setText("All Words List");
		allWordsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				
				Shell[] shells = Display.getCurrent().getShells();
				for (Shell shell : shells) {
					String data = (String) shell.getData();
					if(data != null && data.equals("list")) {
						shell.setFocus();
						return;
					}
				}
				new ListWindow(dictionary, exchangeWithDB).open();				
			}
		});

		Button addTranslateButton = new Button(shell, SWT.PUSH);
		addTranslateButton.setSize(110, 30);
		addTranslateButton.setLocation(240, 300);
		addTranslateButton.setText("Add Translate");
	}

	private void setLabels() {

		Label outWrd = new Label(shell, SWT.BORDER);
		outWrd.setText("Output word");
		outWrd.setLocation(90, 20);
		outWrd.setSize(200, 40);

	}

	private void setText() {

		Text inWrd = new Text(shell, SWT.BORDER);
		inWrd.setText("Type translate in here");
		inWrd.setLocation(90, 80);
		inWrd.setSize(200, 40);

	}

	public void init() {
		setButtons();
		setLabels();
		setText();
		connectionCheck();

	}

}

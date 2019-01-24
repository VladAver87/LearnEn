package swt.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swt.project.db.DBConnector;
import swt.project.db.SavedDictionariesDAO;
import swt.project.db.UserDAO;
import swt.project.dictionary.Dictionary;
import swt.project.users.User;
import swt.project.utils.SavedDictionaries;

public class MainWindow {

	private final Shell shell;
	private DBConnector dbconnector = DBConnector.dbconnector;
	private Dictionary dictionary = Dictionary.dictionary;
	private SavedDictionariesDAO savedDictionariesDAO = SavedDictionariesDAO.savedDictionariesDAO;
	private SavedDictionaries savedDictionaries = new SavedDictionaries(savedDictionariesDAO);
	private UserDAO userDAO = UserDAO.userDAO;
	private User user;

	public MainWindow(Shell shell) {
		this.shell = shell;
	}

	private void init() {
		Font mainWindowButtonsFont = new Font(null, "Arial", 12, SWT.NORMAL);
		Color userColor = new Color(null, 255, 1, 0);
		
		Label outWrd = new Label(shell, SWT.CENTER);
		Label userLabel = new Label(shell, SWT.CENTER);
		Text addLogin = new Text(shell, SWT.BORDER);
		Text addPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		Button loginButton = new Button(shell, SWT.PUSH);
		Button acceptLoginButton = new Button(shell, SWT.PUSH);
		Button registerButton = new Button(shell, SWT.PUSH);
		Button allWordsButton = new Button(shell, SWT.PUSH);
		allWordsButton.setEnabled(false);
		allWordsButton.setSize(140, 50);
		allWordsButton.setLocation(130, 200);
		allWordsButton.setText("Dictionary");
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
				new ListWindow(dictionary, savedDictionaries, user).open();
			}
		});
		
		loginButton.setSize(140, 50);
		loginButton.setLocation(130, 110);
		loginButton.setText("Login");
		loginButton.setFont(mainWindowButtonsFont);
		loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loginButton.setVisible(false);
				addLogin.setVisible(true);
				addPassword.setVisible(true);
				acceptLoginButton.setVisible(true);
				registerButton.setVisible(true);
			}	
		});
		
		acceptLoginButton.setSize(80, 28);
		acceptLoginButton.setLocation(110, 140);
		acceptLoginButton.setText("login");
		acceptLoginButton.setVisible(false);
		acceptLoginButton.setFont(mainWindowButtonsFont);
		acceptLoginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String login = addLogin.getText();
				String pass = addPassword.getText();
				if (userDAO.check(login, pass)) {
					user = new User(login);
					registerButton.setVisible(false);
					acceptLoginButton.setVisible(false);
					addLogin.setVisible(false);
					addPassword.setVisible(false);
					userLabel.setVisible(true);
					userLabel.setText("Hello " + login);
					allWordsButton.setEnabled(true);
					savedDictionaries.setSavedDicts(savedDictionariesDAO.loadSavedDicts(user.getUserName()));
					
				}
				else 
				{
					MessageBox messageBox = new MessageBox(shell);
					messageBox.setMessage("User does not exist! \n Please, register!");
					messageBox.open();
				}
					
			}	
		});
		
		registerButton.setSize(80, 28);
		registerButton.setLocation(205, 140);
		registerButton.setText("register");
		registerButton.setVisible(false);
		registerButton.setFont(mainWindowButtonsFont);
		registerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				userDAO.put(addLogin.getText(), addPassword.getText());
			}	
		});
		
		Font mainWindowLabelFont = new Font(null, "Arial", 14, SWT.NORMAL);
		outWrd.setText("Add some words to dictionary \nand hit 'learnWords' to start a test");
		outWrd.setLocation(20, 20);
		outWrd.setSize(360, 80);
		outWrd.setFont(mainWindowLabelFont);
		
		userLabel.setLocation(130, 110);
		userLabel.setForeground(userColor);
		userLabel.setSize(140, 50);
		userLabel.setVisible(true);
		userLabel.setFont(mainWindowLabelFont);
		
		addLogin.setText("login");
		addLogin.setLocation(130, 70);
		addLogin.setSize(140, 28);
		addLogin.setVisible(false);
		addLogin.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				addLogin.setText("");
			}
		});
		
		addPassword.setText("password");
		addPassword.setLocation(130, 100);
		addPassword.setSize(140, 28);
		addPassword.setEchoChar('*');
		addPassword.setVisible(false);
		addPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				addPassword.setText("");
			}
		});
	}
	

	public void open() {
		init();
		dbconnector.getConnectProperties(shell);
		dbconnector.tryConnectFromDB(shell);
	}
}

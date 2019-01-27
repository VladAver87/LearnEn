package swt.project;

import org.eclipse.swt.SWT;
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
import swt.project.db.WordsDAO;
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
	private WordsDAO wordsDAO = WordsDAO.wordsDAO;

	public MainWindow(Shell shell) {
		this.shell = shell;
	}

	private void init() {
		Font mainWindowButtonsFont = new Font(null, "Arial", 12, SWT.NORMAL);
		Font mainWindowBHelloFont = new Font(null, "Arial", 20, SWT.COLOR_BLUE);
		Font mainWindowTextFont = new Font(null, "Tahoma", 9, SWT.COLOR_DARK_BLUE);
		Color userColor = new Color(null, 255, 1, 0);

		Label outWrd = new Label(shell, SWT.CENTER);
		Label loginLabel = new Label(shell, SWT.CENTER);
		Label passwordLabel = new Label(shell, SWT.CENTER);
		Label userLabel = new Label(shell, SWT.CENTER);
		Text addLogin = new Text(shell, SWT.BORDER);
		Text addPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		Button loginButton = new Button(shell, SWT.PUSH);
		Button acceptLoginButton = new Button(shell, SWT.PUSH);
		Button registerButton = new Button(shell, SWT.PUSH);
		Button allWordsButton = new Button(shell, SWT.PUSH);
		allWordsButton.setEnabled(false);
		allWordsButton.setSize(140, 50);
		allWordsButton.setLocation(130, 240);
		allWordsButton.setText("Dictionary");
		allWordsButton.setFont(mainWindowButtonsFont);
		allWordsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ListWindow(dictionary, savedDictionaries, user).open();

			}
		});

		loginButton.setSize(140, 50);
		loginButton.setLocation(130, 140);
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
				loginLabel.setVisible(true);
				passwordLabel.setVisible(true);
			}
		});

		acceptLoginButton.setSize(100, 35);
		acceptLoginButton.setLocation(150, 140);
		acceptLoginButton.setText("login");
		acceptLoginButton.setVisible(false);
		acceptLoginButton.setFont(mainWindowButtonsFont);
		acceptLoginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String login = addLogin.getText();
				String pass = addPassword.getText();
				if (login.isEmpty() || pass.isEmpty()) {
					MessageBox messageBox = new MessageBox(shell);
					messageBox.setMessage("Login field or password fied is epmty!");
					messageBox.open();
				} else if (userDAO.check(login, pass)) {
					user = new User(login);
					registerButton.setVisible(false);
					acceptLoginButton.setVisible(false);
					addLogin.setVisible(false);
					addPassword.setVisible(false);
					userLabel.setVisible(true);
					userLabel.setText("Hello " + login);
					allWordsButton.setEnabled(true);
					loginLabel.setVisible(false);
					passwordLabel.setVisible(false);
					savedDictionaries.setSavedDicts(savedDictionariesDAO.loadSavedDicts(user.getUserName()));
					dictionary.setAnswer(wordsDAO.loadAnswer(user.getUserName()));
				}
			}
		});

		registerButton.setSize(100, 35);
		registerButton.setLocation(150, 180);
		registerButton.setText("New User");
		registerButton.setVisible(false);
		registerButton.setFont(mainWindowButtonsFont);
		registerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new RegisterWindow().open();
			}
		});

		Font mainWindowLabelFont = new Font(null, "Arial", 14, SWT.NORMAL);
		outWrd.setText("Add some words to dictionary \nand hit 'learnWords' to start a test");
		outWrd.setLocation(20, 20);
		outWrd.setSize(360, 80);
		outWrd.setFont(mainWindowLabelFont);

		userLabel.setLocation(10, 110);
		userLabel.setForeground(userColor);
		userLabel.setSize(380, 50);
		userLabel.setVisible(false);
		userLabel.setFont(mainWindowBHelloFont);

		loginLabel.setLocation(83, 73);
		loginLabel.setText("login");
		loginLabel.setSize(50, 30);
		loginLabel.setVisible(false);
		loginLabel.setFont(mainWindowTextFont);

		passwordLabel.setLocation(50, 103);
		passwordLabel.setText("password");
		passwordLabel.setSize(80, 50);
		passwordLabel.setVisible(false);
		passwordLabel.setFont(mainWindowTextFont);

		addLogin.setLocation(130, 70);
		addLogin.setSize(140, 28);
		addLogin.setTextLimit(30);
		addLogin.setFont(mainWindowTextFont);
		addLogin.setVisible(false);

		addPassword.setLocation(130, 100);
		addPassword.setSize(140, 28);
		addPassword.setEchoChar('*');
		addPassword.setVisible(false);
		addPassword.setFont(mainWindowTextFont);

	}

	public void open() {
		init();
		dbconnector.getConnectProperties(shell);
		dbconnector.tryConnectFromDB(shell);
	}
}

package swt.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swt.project.db.UserDAO;

public class RegisterWindow {
	private Shell registerShell = new Shell(SWT.APPLICATION_MODAL | SWT.CLOSE);
	private UserDAO userDAO = UserDAO.userDAO;

	public RegisterWindow() {

	}

	private void init() {
		Font registerWindowTextFont = new Font(null, "Tahoma", 9, SWT.COLOR_DARK_BLUE);
		Button regButton = new Button(registerShell, SWT.PUSH);
		Button cancelButton = new Button(registerShell, SWT.PUSH);
		Text loginText = new Text(registerShell, SWT.BORDER);
		Text passwordText = new Text(registerShell, SWT.BORDER | SWT.PASSWORD);
		Label passwordLabel = new Label(registerShell, SWT.CENTER);
		Label loginLabel = new Label(registerShell, SWT.CENTER);

		loginLabel.setLocation(30, 23);
		loginLabel.setText("New user");
		loginLabel.setSize(70, 50);
		loginLabel.setFont(registerWindowTextFont);

		passwordLabel.setLocation(30, 63);
		passwordLabel.setText("Password");
		passwordLabel.setSize(70, 50);
		passwordLabel.setFont(registerWindowTextFont);

		loginText.setLocation(130, 15);
		loginText.setSize(140, 30);
		loginText.setTextLimit(30);
		loginText.setFont(registerWindowTextFont);
		loginText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				loginText.setText("");
			}
		});

		passwordText.setLocation(130, 55);
		passwordText.setSize(140, 30);
		passwordText.setEchoChar('*');
		passwordText.setFont(registerWindowTextFont);
		passwordText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordText.setText("");
			}
		});

		regButton.setSize(110, 30);
		regButton.setLocation(30, 120);
		regButton.setText("Register");
		regButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (loginText.getText().isEmpty()) 
				{
					MessageBox messageBox = new MessageBox(registerShell);
					messageBox.setMessage("Login field is empty!");
					messageBox.open();
				}
				else if (passwordText.getText().isEmpty()) 
				{
					MessageBox messageBox = new MessageBox(registerShell);
					messageBox.setMessage("Password field is empty!");
					messageBox.open();
				}
				else
				{
					userDAO.add(loginText.getText(), passwordText.getText());
					registerShell.close();
				}

			}
		});

		cancelButton.setSize(110, 30);
		cancelButton.setLocation(160, 120);
		cancelButton.setText("Cancel");
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				registerShell.close();
			}
		});
	}

	public void open() {
		registerShell.setData("register");
		registerShell.setText("Registration");
		registerShell.setSize(300, 200);
		registerShell.open();
		init();
	}
}

package swt.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

	public static void run() {
		Display localDisplay = new Display();
		Shell shell = new Shell(localDisplay, SWT.CLOSE);
		MainWindow mainWindow = new MainWindow(shell);
		mainWindow.open();
		shell.setText("LearnWords");
		shell.setSize(400, 350);
		shell.open();
		while (!shell.isDisposed()) {
			if (!localDisplay.readAndDispatch()) {
				localDisplay.sleep();
			}
		}
		shell.dispose();
	}

	public static void main(String[] args) {

		run();

	}
}

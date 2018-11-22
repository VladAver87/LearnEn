package swt.project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import swt.project.dictionary.Dictionary;
import swt.project.utils.Utils;

public class LearnWindow {
	private Shell learnWindowShell = new Shell(SWT.CLOSE);
	private Dictionary dictionary;
	private int counter = 0;
	
	
	public LearnWindow(Dictionary dictionary) {
		this.dictionary = dictionary;

	}
	
	private void disabledAll() {
		
	}
	
	
	private void init() {
		Label engWord = new Label(learnWindowShell, SWT.CENTER);
		Font learnWindowLabelFont = new Font(null, "Tahoma", 16, SWT.NORMAL);		
		engWord.setLocation(90, 100);
		engWord.setSize(200, 40);
		engWord.setFont(learnWindowLabelFont);
		
		Label countLabel = new Label(learnWindowShell, SWT.CENTER);
		countLabel.setLocation(240, 20);
		countLabel.setSize(50, 30);
		countLabel.setFont(learnWindowLabelFont);
		
		Label spaceLabel = new Label(learnWindowShell, SWT.CENTER);
		spaceLabel.setText("/");
		spaceLabel.setLocation(290, 20);
		spaceLabel.setSize(10, 30);
		spaceLabel.setFont(learnWindowLabelFont);
		
		Label countSumLabel = new Label(learnWindowShell, SWT.CENTER);
		countSumLabel.setLocation(300, 20);
		countSumLabel.setSize(50, 30);
		countSumLabel.setFont(learnWindowLabelFont);
		countSumLabel.setText(String.valueOf(dictionary.showAllWords().size()));
		

		Text translateWord = new Text(learnWindowShell, SWT.BORDER);
		translateWord.setText("Type Translate");
		translateWord.setLocation(90, 155);
		translateWord.setSize(200, 40);
		
		Button nextButton = new Button(learnWindowShell, SWT.PUSH);
		nextButton.setSize(120, 45);
		nextButton.setLocation(130, 250);
		nextButton.setText("OK! NEXT");
		nextButton.setEnabled(false);
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				counter = counter + 1;
				if (counter < dictionary.showAllWords().size()) {
					countLabel.setText(String.valueOf(counter + 1));
					engWord.setText(dictionary.getNextWord(counter));
				} else 
					nextButton.setText("SHOW RESULT");
					engWord.setEnabled(false);
					translateWord.setEnabled(false);
					//открыть окно с резалтом
			}
		});
		
		
		Button startButton = new Button(learnWindowShell, SWT.PUSH);
		startButton.setSize(120, 45);
		startButton.setLocation(35, 20);
		startButton.setText("START");
		startButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				engWord.setText(dictionary.getNextWord(counter));	
				startButton.setEnabled(false);
				nextButton.setEnabled(true);
				countLabel.setText(String.valueOf(counter + 1));
			}
		});
		
		
	}
	
	public void open() {
		learnWindowShell.setData("learnWindow");
		learnWindowShell.setText("Learning");
		learnWindowShell.setSize(380, 400);
		learnWindowShell.open();
		init();

	}
}

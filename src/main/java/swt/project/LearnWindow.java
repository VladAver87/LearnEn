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

import swt.project.dictionary.Dictionary;
import swt.project.utils.SelectedWordsGetter;


public class LearnWindow {
	private Shell learnWindowShell = new Shell(SWT.CLOSE);
	private Dictionary dictionary;
	private int counterAllWords;
	private int counterRightAnswer;
	private SelectedWordsGetter selectedWordsGetter;
	
	public LearnWindow(Dictionary dictionary, SelectedWordsGetter selectedWordsGetter) {
		this.dictionary = dictionary;
		this.selectedWordsGetter = selectedWordsGetter;
	}	
	
	
	
	private void init() {
		Button showResultButton = new Button(learnWindowShell, SWT.PUSH);
		Button startButton = new Button(learnWindowShell, SWT.PUSH);
		Button nextButton = new Button(learnWindowShell, SWT.PUSH);
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
		countSumLabel.setText(String.valueOf(selectedWordsGetter.getSelectedList().size()));	
		

		Text translateWord = new Text(learnWindowShell, SWT.BORDER);
		translateWord.setText("Type Translate");
		translateWord.setLocation(90, 155);
		translateWord.setSize(200, 40);
		translateWord.setEnabled(false);
		translateWord.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				translateWord.setText("");
			}
		});
		
		showResultButton.setSize(120, 45);
		showResultButton.setLocation(130, 250);
		showResultButton.setText("SHOW RESULT");
		showResultButton.setEnabled(false);
		showResultButton.setVisible(false);
		showResultButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showResultButton.setVisible(false);
				MessageBox messageBox = new MessageBox(learnWindowShell);
				messageBox.setMessage("You have" + " " + counterRightAnswer + " " 
					+ "of" + " " + String.valueOf(selectedWordsGetter.getSelectedList().size()) + " " + "right answers" );				
				messageBox.open();
			}
		});
		
		nextButton.setSize(120, 45);
		nextButton.setLocation(130, 250);
		nextButton.setText("OK! NEXT");
		nextButton.setEnabled(false);
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				String temp = dictionary.showAllWords().get(engWord.getText());	
				if (temp.equals(translateWord.getText())) {
					counterRightAnswer++;
				}
				counterAllWords = counterAllWords + 1;
				translateWord.setFocus();
				if (counterAllWords < selectedWordsGetter.getSelectedList().size())      
				{
					countLabel.setText(String.valueOf(counterAllWords + 1));
					engWord.setText(selectedWordsGetter.getSelectedList().get(counterAllWords));
				}
					else 
				{
					showResultButton.setVisible(true);
					showResultButton.setEnabled(true);	
					startButton.setEnabled(true);
					translateWord.setEnabled(false);
					engWord.setText(" ");
					nextButton.setEnabled(false);
				}
			}
		});
		
		
		startButton.setSize(120, 45);
		startButton.setLocation(35, 20);
		startButton.setText("START");
		startButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				counterAllWords = 0;
				counterRightAnswer = 0;
				engWord.setText(selectedWordsGetter.getSelectedList().get(counterAllWords));
				translateWord.setEnabled(true);
				translateWord.setFocus();
				startButton.setEnabled(false);
				nextButton.setEnabled(true);
				nextButton.setVisible(true);
				countLabel.setText(String.valueOf(counterAllWords + 1));
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

package edu.cmu.emfta.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SetEventToRequirementTraceabilityPageOne extends WizardPage {

	private Text text1;
	private Composite container;

	protected SetEventToRequirementTraceabilityPageOne() {
		super("Page One");
		setTitle("Set Event To Requirement Traceability - Step One");
		setDescription("Wizard Step 1");
	}

	@Override
	public void createControl(Composite parent) {
//		container = new Composite(parent, SWT.NONE);
//		GridLayout layout = new GridLayout();
//		container.setLayout(layout);
//		layout.numColumns = 2;
//
//		Label label1 = new Label(container, SWT.NONE);
//		label1.setText("Select a ");
//
//		text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
//		text1.setText("");
//		text1.addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				if (!text1.getText().isEmpty()) {
//					setPageComplete(true);
//
//				}
//			}
//
//		});
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		text1.setLayoutData(gd);

//		// required to avoid an error in the system
//		setControl(container);
//		setPageComplete(false);

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Press me");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = parent.getShell();
				openDialogs(shell);
			}
		});

	}

	private void openDialogs(Shell shell) {
		// File standard dialog
		FileDialog fileDialog = new FileDialog(shell);
		// Set the text
		fileDialog.setText("Select File");
		// Set filter on .txt files
		fileDialog.setFilterExtensions(new String[] { "*.reqspec" });
		// Put in a readable name for the filter
		fileDialog.setFilterNames(new String[] { "Textfiles(*.txt)" });
		// Open Dialog and save result of selection
		String selected = fileDialog.open();
		System.out.println(selected);

		// Directly standard selection
		DirectoryDialog dirDialog = new DirectoryDialog(shell);
		dirDialog.setText("Select your home directory");
		String selectedDir = dirDialog.open();
		System.out.println(selectedDir);

		// Select Font
		FontDialog fontDialog = new FontDialog(shell);
		fontDialog.setText("Select your favorite font");
		FontData selectedFont = fontDialog.open();
		System.out.println(selectedFont);

		// Select Color
		ColorDialog colorDialog = new ColorDialog(shell);
		colorDialog.setText("Select your favorite color");
		RGB selectedColor = colorDialog.open();
		System.out.println(selectedColor);

		// Message
		MessageBox messageDialog = new MessageBox(shell, SWT.ERROR);
		messageDialog.setText("Evil Error has happend");
		messageDialog.setMessage("This is fatal!!!");
		int returnCode = messageDialog.open();
		System.out.println(returnCode);

		// Message with ok and cancel button and info icon
		messageDialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
		messageDialog.setText("My info");
		messageDialog.setMessage("Do you really want to do this.");
		returnCode = messageDialog.open();
		System.out.println(returnCode);
	}

	public String getText1() {
		return text1.getText();
	}

}
package edu.cmu.emfta.wizards;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.uiuc.statecharts.StatechartReader;

import Y2U.DataStructure.State;

public class SetEventToDesignTraceabilityPageOne extends WizardPage {

	private Text text1;
	private Composite container;

	private String fileContent;
	private Table table;
	private int rowCount = 5, columnCount = 4;
//	private ParseHelper<ReqSpec> reqspecParser;
	private StatechartReader sr;

	protected SetEventToDesignTraceabilityPageOne() {
		super("Page One");
		setTitle("Set Event To Design Traceability - Step One");
		setDescription("Wizard Step 1");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;

		Label label1 = new Label(container, SWT.LEFT);
		label1.setText("Select a Yakindu statechart file (*.sct)");
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

		Button button = new Button(container, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = parent.getShell();
				openDialogs(shell);
			}
		});

		// Add new table
//		Display display = new Display();
//		Shell shell = new Shell(display);
//		final Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		table = new Table(container, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Print column title
		for (int i = 0; i < columnCount; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);

			switch (i) {

			case 0:
				column.setText("Region");
				break;

			case 1:
				column.setText("State Name");
				break;

			case 2:
				column.setText("ID");
				break;

			case 3:
				column.setText("Behavior");
				break;
			}
		}

		for (int i = 0; i < columnCount; i++) {
			table.getColumn(i).pack();
		}

		Point size = table.computeSize(SWT.DEFAULT, 400);
		table.setSize(size);
//		shell.pack();

		// required to avoid an error in the system
		setControl(container);
		setPageComplete(false); // original
	}

	public String getText1() {
		return text1.getText();
	}

	private void openDialogs(Shell shell) {
		// File standard dialog
		FileDialog fileDialog = new FileDialog(shell);
		// Set the text
		fileDialog.setText("Select File");
		// Set filter on .txt files
		fileDialog.setFilterExtensions(new String[] { "*.sct" });
		// Put in a readable name for the filter
//		fileDialog.setFilterNames(new String[] { "Textfiles(*.txt)" });
		fileDialog.setFilterNames(new String[] { "Statechart Model (*.sct)" });

		// Open Dialog and save result of selection
		String selectedFilePath = fileDialog.open();
		System.out.println(selectedFilePath);

		if (!selectedFilePath.isEmpty()) {

			sr = new StatechartReader(selectedFilePath);
			sr.read();
			List<Entry<String, State>> states = sr.getStates();
			renderTable(states);
		}

		System.out.println(fileContent);

//		// Directly standard selection
//		DirectoryDialog dirDialog = new DirectoryDialog(shell);
//		dirDialog.setText("Select your home directory");
//		String selectedDir = dirDialog.open();
//		System.out.println(selectedDir);

//		// Message
//		MessageBox messageDialog = new MessageBox(shell, SWT.ERROR);
//		messageDialog.setText("Evil Error has happend");
//		messageDialog.setMessage("This is fatal!!!");
//		int returnCode = messageDialog.open();
//		System.out.println(returnCode);
//
//		// Message with ok and cancel button and info icon
//		messageDialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
//		messageDialog.setText("My info");
//		messageDialog.setMessage("Do you really want to do this.");
//		returnCode = messageDialog.open();
//		System.out.println(returnCode);
	}

//	private void renderTable(String input) {
	private void renderTable(List<Entry<String, State>> input) {

		// Print data by rows
//		for (int i = 0; i < rowCount; i++) {
//		for (int i = 0; i < input.size(); i++) {
//			TableItem item = new TableItem(table, SWT.NONE);
//			item.setText(0, input.get);
//			item.setText(1, input.get(i).getName());
//			item.setText(2, input.get(i).getId());
//			item.setText(3, "behavior (tbc)");
//		}

		for (Map.Entry<String, State> entry : input) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, entry.getKey());
			item.setText(1, entry.getValue().getName());
			item.setText(2, entry.getValue().getId());
			item.setText(3, "behavior (tbc)");
		}

		for (int i = 0; i < columnCount; i++) {
			table.getColumn(i).pack();
		}

		Point size = table.computeSize(SWT.DEFAULT, 400);
		table.setSize(size);

		// Print the selected row in the table
		table.addListener(SWT.MouseDown, event -> {
			Point pt = new Point(event.x, event.y);
			TableItem item = table.getItem(pt);
			if (item == null) {
				return;
			}

			System.out.println("[Region]" + item.getText(0));
			System.out.println("[State]" + item.getText(1));
			System.out.println("[ID]" + item.getText(2));
			System.out.println("[Behavior]" + item.getText(3));

			//			for (int i = 0; i < columnCount; i++) {
//				Rectangle rect = item.getBounds(i);
//				if (rect.contains(pt)) {
//					int index = table.indexOf(item);
//					System.out.println("Item " + index + "-" + i);
//				}
//			}
		});

		// shell.open();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//		display.dispose();
	}
}
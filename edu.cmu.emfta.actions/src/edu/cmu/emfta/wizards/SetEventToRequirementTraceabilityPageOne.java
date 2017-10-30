package edu.cmu.emfta.wizards;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.eclipse.emf.common.util.EList;
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
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.Connection;
import org.osate.aadl2.ConnectionEnd;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.modelsupport.util.AadlUtil;
import org.uiuc.reqif.parser.ReqifParser;

public class SetEventToRequirementTraceabilityPageOne extends WizardPage {

	private Text text1;
	private Composite container;
	private String fileContent;
	private Table table;
	private int rowCount = 5, columnCount = 4;
	private ReqifParser reqifParser;

	protected SetEventToRequirementTraceabilityPageOne() {
		super("Page One");
		setTitle("Set Event To Requirement Traceability - Step One");
		setDescription("Wizard Step 1");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;

		Label label1 = new Label(container, SWT.LEFT);
		label1.setText("Select a Requirement Interchange Format file (*.reqif) ");

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
//		});
//
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		text1.setLayoutData(gd);


		Button button = new Button(container, SWT.PUSH);
		button.setText("Select");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = parent.getShell();
				openDialogs(shell);
//				readAllAadlFiles();
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

			switch(i) {

			case 0:
				column.setText("Number");
				break;

			case 1:
				column.setText("Requirement");
				break;

			case 2:
				column.setText("Identifier");
				break;

			case 3:
				column.setText("Others");
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
		setPageComplete(false);

	}

	private void openDialogs(Shell shell) {
		// File standard dialog
		FileDialog fileDialog = new FileDialog(shell);
		// Set the text
		fileDialog.setText("Select File");

		// Set filter on .txt files
		fileDialog.setFilterExtensions(new String[] { "*.reqif" });
		// Put in a readable name for the filter
//		fileDialog.setFilterNames(new String[] { "Textfiles(*.txt)" });
		fileDialog.setFilterNames(new String[] { "Requirement Intercahnge Format (*.reqif)" });

		// Open Dialog and save result of selection
		String selectedFilePath = fileDialog.open();
		System.out.println(selectedFilePath);

		if (selectedFilePath != null) {

			reqifParser = new ReqifParser(selectedFilePath);
			reqifParser.parse();
			List<Entry<String, String>> idAndRequirementList = reqifParser.getIdentifierAndRequirementList();
			renderTable(idAndRequirementList);
		}

//		System.out.println(fileContent);

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

	public String getText1() {
		return text1.getText();
	}

	private String readFile(String pathname) throws IOException {

		File file = new File(pathname);
		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");

		try {
			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}

	private void renderTable(List<Entry<String, String>> idAndRequirementList) {


//		ReqSpec reqspec;
//		try {
//			reqspec = reqspecParser.parse(input);
//			System.out.println("[reqspec.getParts().toString()] " + "" + reqspec.getParts().toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		//		Print data by rows
		int counter = 1;
		for (Map.Entry<String, String> entry : idAndRequirementList) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(counter++));
			item.setText(1, entry.getValue());
			item.setText(2, entry.getKey());
			item.setText(3, "");
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

			System.out.println("[Number]" + item.getText(0));
			System.out.println("[Description]" + item.getText(1));
			System.out.println("[ID]" + item.getText(2));
			System.out.println("[null]" + item.getText(3));

		});

		// shell.open();

//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//		display.dispose();
	}

	private void readAllAadlFiles() {

//		TraverseWorkspace.getInstanceModelFilesInWorkspace();
		EList<ComponentImplementation> impls = AadlUtil.getAllComponentImpl();

		for (ComponentImplementation compImpl : impls) {

//			compImpl.getIngoingConnections(feature);

			EList<Subcomponent> subscomponents = compImpl.getAllSubcomponents();

			for (Subcomponent subcomp : subscomponents) {

				System.out.println("[Subcomponent getFullName]: " + subcomp.getFullName());
				System.out.println("[Subcomponent getName]:     " + subcomp.getName());

			}

			EList<Connection> connections = compImpl.getAllConnections();

			for (Connection conn : connections) {
				ConnectionEnd ceDst = conn.getAllDestination();
				ConnectionEnd ceSrc = conn.getAllSource();

				ConnectionEnd ceAllLastDestination = conn.getAllLastDestination();
				ConnectionEnd ceAllLastSource = conn.getAllLastSource();

				System.out.println("[readAllAadlFiles ceDst]: " + ceDst.getFullName());
				System.out.println("[readAllAadlFiles ceSrc]: " + ceSrc.getFullName());

				System.out.println("[readAllAadlFiles ceAllLastDestination]: " + ceAllLastDestination.getFullName());
				System.out.println("[readAllAadlFiles ceAllLastSource]:      " + ceAllLastSource.getFullName());
			}
		}
	}
}
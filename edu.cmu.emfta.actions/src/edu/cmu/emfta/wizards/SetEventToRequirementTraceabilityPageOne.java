package edu.cmu.emfta.wizards;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
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

import edu.cmu.emfta.Event;
import edu.uiuc.traceability.artifacts.RequirementArtifact;
import edu.uiuc.traceability.reqif.parser.ReqifParser;

public class SetEventToRequirementTraceabilityPageOne extends WizardPage {

	private Event selectedRootEvent;
	private Text selectedRootEventText;

	private Text selectedFilePathText;
	private Text selectedRequirementIdentifierText;
	private Text selectedRequirementLastChangeText;
	private Text selectedRequirementDescriptionText;

	private Composite composite;
	private Table table;
	private int rowCount = 5, columnCount = 4;

	private ReqifParser reqifParser;
	private RequirementArtifact selectedRequirementArtifact;
	private List<Entry<String, RequirementArtifact>> requirementArtifactList;

	protected SetEventToRequirementTraceabilityPageOne() {
		super("Page One");
		setTitle("Set Event To Requirement Traceability");
		setDescription("Wizard Step 1");
	}

	@Override
	public void createControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);


		Label selectFileLable = new Label(composite, SWT.LEFT);
		selectFileLable.setText("Select a Requirement Interchange Format file (*.reqif) ");
		GridData gridDataSelectFileLable = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataSelectFileLable.horizontalSpan = 2;
		selectFileLable.setLayoutData(gridDataSelectFileLable);

		Button selectFileButton = new Button(composite, SWT.PUSH);
		selectFileButton.setText("Select");
		selectFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = parent.getShell();
				openDialogs(shell);
//				readAllAadlFiles();
			}
		});

		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.VERTICAL_ALIGN_CENTER, false,
				false);
		gridData.horizontalSpan = 1;
		selectFileButton.setLayoutData(gridData);


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




		// Add new table
//		Display display = new Display();
//		Shell shell = new Shell(display);
//		final Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		table = new Table(composite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);

		GridData gridDataTable = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.VERTICAL_ALIGN_CENTER,
				false, false);
		gridDataTable.horizontalSpan = 3;
		gridDataTable.grabExcessHorizontalSpace = true;
		table.setLayoutData(gridDataTable);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		// Print column title
		for (int i = 0; i < columnCount; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);

			switch(i) {

			case 0:
				column.setText("Index");
				break;

			case 1:
				column.setText("Description");
				break;

			case 2:
				column.setText("Last Change");
				break;

			case 3:
				column.setText("Identifier");
				break;
			}
		}

		for (int i = 0; i < columnCount; i++) {
			table.getColumn(i).pack();
		}

		Point size = table.computeSize(800, 400);
		table.setSize(size);

		// Print the selected row in the table
		table.addListener(SWT.MouseDown, event -> {
			Point pt = new Point(event.x, event.y);
			TableItem item = table.getItem(pt);
			if (item == null) {
				return;
			}

			this.selectedRequirementArtifact = (this.requirementArtifactList.get(Integer.valueOf(item.getText(0)) - 1))
					.getValue();

			this.selectedRequirementDescriptionText.setText(item.getText(1));
			this.selectedRequirementLastChangeText.setText(item.getText(2));
			this.selectedRequirementIdentifierText.setText(item.getText(3));

			System.out.println("[Index]" + item.getText(0));
			System.out.println("[Description]" + item.getText(1));
			System.out.println("[LastChange]" + item.getText(2));
			System.out.println("[Identifier]" + item.getText(3));

			if (this.selectedRequirementArtifact != null) {
				setPageComplete(true);
			}

		});

		// Display result
		Label selectedRootEventLabel = new Label(composite, SWT.LEFT);
		selectedRootEventLabel.setText("Root event name: ");
		selectedRootEventText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		selectedRootEventText.setText(selectedRootEvent.getName());
		GridData gridDataSelectedRootEvent = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridDataSelectedRootEvent.horizontalSpan = 2;
		selectedRootEventText.setLayoutData(gridDataSelectedRootEvent);

		Label seselectedFilePathLabel = new Label(composite, SWT.LEFT);
		seselectedFilePathLabel.setText("Reqif file path: ");
		selectedFilePathText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridData gridDataSelectedFilePath = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridDataSelectedFilePath.horizontalSpan = 2;
		selectedFilePathText.setLayoutData(gridDataSelectedFilePath);

		Label selectedRequirementDescriptionLabel = new Label(composite, SWT.LEFT);
		selectedRequirementDescriptionLabel.setText("Requirement Description: ");
		selectedRequirementDescriptionText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridData gridDataSelectedRequirementDescription = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridDataSelectedRequirementDescription.horizontalSpan = 2;
		selectedRequirementDescriptionText.setLayoutData(gridDataSelectedRequirementDescription);


		Label selectedRequirementLastChangeLabel = new Label(composite, SWT.LEFT);
		selectedRequirementLastChangeLabel.setText("Requirement Last Change: ");
		selectedRequirementLastChangeText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridData gridDataSelectedRequirementLastChange = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridDataSelectedRequirementLastChange.horizontalSpan = 2;
		selectedRequirementLastChangeText.setLayoutData(gridDataSelectedRequirementLastChange);

		Label selectedRequirementIdentifierLabel = new Label(composite, SWT.LEFT);
		selectedRequirementIdentifierLabel.setText("Requirement Identifier: ");
		selectedRequirementIdentifierText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridData gridDataRequirementIdentifier = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridDataRequirementIdentifier.horizontalSpan = 2;
		selectedRequirementIdentifierText.setLayoutData(gridDataRequirementIdentifier);

//		shell.pack();
		parent.pack();

		// required to avoid an error in the system
		setControl(composite);
//		setPageComplete(true);

	}

	private void openDialogs(Shell shell) {
		// File standard dialog
		FileDialog fileDialog = new FileDialog(shell);
		// Set the text
		fileDialog.setText("Select File");

		fileDialog.setFilterExtensions(new String[] { "*.reqif" });
		fileDialog.setFilterNames(new String[] { "Requirement Intercahnge Format (*.reqif)" });

		// Open Dialog and save result of selection
		String selectedFilePath = fileDialog.open();
		this.selectedFilePathText.setText(selectedFilePath);
		System.out.println(selectedFilePath);

		if (selectedFilePath != null) {

			reqifParser = new ReqifParser(selectedFilePath);
			reqifParser.parse();
			requirementArtifactList = reqifParser.getRequirementArtifacts();
			renderTable(requirementArtifactList);
		}

		shell.pack();

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

	private void renderTable(List<Entry<String, RequirementArtifact>> requirementArtifactsList) {

		//		Print data by rows
		int counter = 1;
//		for (Map.Entry<String, RequirementArtifact> entry : requirementArtifactsList) {

		for (int i = 0; i < requirementArtifactsList.size(); i++) {

			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(i + 1));
			item.setText(1, requirementArtifactsList.get(i).getValue().getOriginDescription());
			item.setText(2, requirementArtifactsList.get(i).getValue().getOriginLastChange());
			item.setText(3, requirementArtifactsList.get(i).getKey());
		}

		for (int i = 0; i < columnCount; i++) {
			table.getColumn(i).pack();
		}

		Point size = table.computeSize(SWT.DEFAULT, 400);
		table.setSize(size);


		// shell.open();

//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//		display.dispose();
	}

	public Event getSelectedRootEvent() {
		return selectedRootEvent;
	}

	public void setSelectedRootEvent(Event selectedRootEvent) {
		this.selectedRootEvent = selectedRootEvent;
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

	public RequirementArtifact getSelectedRequirementArtifact() {
		return selectedRequirementArtifact;
	}
}
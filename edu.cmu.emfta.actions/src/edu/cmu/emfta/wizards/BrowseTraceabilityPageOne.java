package edu.cmu.emfta.wizards;

import java.util.List;
import java.util.Map.Entry;

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

import edu.cmu.emfta.Event;
import edu.uiuc.traceability.artifacts.AutomataArtifact;
import edu.uiuc.traceability.statecharts.StatechartReader;

public class BrowseTraceabilityPageOne extends WizardPage {

	private Text text1;
	private Composite composite;

	private Event selectedBasicEvent;
	private Text selectedBasicEventText;
	private Text selectedBasicEventSafeGuardText;

	private Text selectedFilePathText;
	private Text selectedAutomataNameText;
	private Text selectedAutomataIdentifierText;
	private Text selectedAutomataLastChangeText;

	private String fileContent;
	private Table tableRootEventToRequirement;
	private Table tableBasicEventToStatechart;
	private int rowCount = 5, columnCount = 4;
//	private ParseHelper<ReqSpec> reqspecParser;
	private StatechartReader sr;

	private AutomataArtifact selectedAutomataArtifact;
	private List<Entry<String, AutomataArtifact>> automataArtifactList;

	private int LAYOUT_NUM_COLUMNS = 5;

	protected BrowseTraceabilityPageOne() {
		super("Page One");
		setTitle("Set Event To Design Traceability");
		setDescription("Wizard Step 1");
	}

	@Override
	public void createControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = LAYOUT_NUM_COLUMNS;
		composite.setLayout(layout);

		Label selectFileLable = new Label(composite, SWT.LEFT);
		selectFileLable.setText("Traceabilit Graph file:");
		GridData gridDataSelectFileLable = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataSelectFileLable.horizontalSpan = 1;
		selectFileLable.setLayoutData(gridDataSelectFileLable);

		Text traceabiltyGraphPathText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		traceabiltyGraphPathText.setText("path name");
		GridData gridDataSelectedRootEvent = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridDataSelectedRootEvent.horizontalSpan = 2;
		traceabiltyGraphPathText.setLayoutData(gridDataSelectedRootEvent);

		Button selectFileButton = new Button(composite, SWT.PUSH);
		selectFileButton.setText("Change");
		selectFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				Shell shell = parent.getShell();
//			openDialogs(shell);
//				readAllAadlFiles();
			}
		});

		GridData gridDataSelectButton = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false,
				false);
		gridDataSelectButton.horizontalSpan = 1;
		selectFileButton.setLayoutData(gridDataSelectButton);

		Button refereshButton = new Button(composite, SWT.PUSH);
		refereshButton.setText("Refresh");
		refereshButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
////				Shell shell = parent.getShell();
////				openDialogs(shell);
////				readAllAadlFiles();
			}
		});

		GridData gridDataRefereshButton = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataRefereshButton.horizontalSpan = 1;
		selectFileButton.setLayoutData(gridDataRefereshButton);

		// Create a horizontal separator
		Label separatorUp = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridDataSeparatorUp = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.VERTICAL_ALIGN_CENTER,
				true, false);
		gridDataSeparatorUp.horizontalSpan = LAYOUT_NUM_COLUMNS;
		separatorUp.setLayoutData(gridDataSeparatorUp);

		//
		Label rootEventToRequirementLable = new Label(composite, SWT.LEFT);
		rootEventToRequirementLable.setText("Root Event To Requirement Traceability:");
		GridData gridDataRootEventToRequirementLable = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataRootEventToRequirementLable.horizontalSpan = LAYOUT_NUM_COLUMNS;
		rootEventToRequirementLable.setLayoutData(gridDataRootEventToRequirementLable);

		// Add tableRootEventToRequirement
		tableRootEventToRequirement = new Table(composite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		GridData gridDataTable = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.VERTICAL_ALIGN_CENTER,
				false, false);
		gridDataTable.horizontalSpan = 4;
		gridDataTable.verticalSpan = 3;
		gridDataTable.grabExcessHorizontalSpace = true;
		tableRootEventToRequirement.setLayoutData(gridDataTable);
		tableRootEventToRequirement.setHeaderVisible(true);
		tableRootEventToRequirement.setLinesVisible(true);

		// Print column title
		for (int i = 0; i < columnCount; i++) {
			TableColumn column = new TableColumn(tableRootEventToRequirement, SWT.NONE);

			switch (i) {

			case 0:
				column.setText("Number");
				break;

			case 1:
				column.setText("Automata Name");
				break;

			case 2:
				column.setText("ID");
				break;

			case 3:
				column.setText("Others");
				break;
			}
		}

		for (int i = 0; i < columnCount; i++) {
			tableRootEventToRequirement.getColumn(i).pack();
		}

		Point size = tableRootEventToRequirement.computeSize(1200, 400);
		tableRootEventToRequirement.setSize(size);

		// Print the selected row in the table
		tableRootEventToRequirement.addListener(SWT.MouseDown, event -> {
			Point pt = new Point(event.x, event.y);
			TableItem item = tableRootEventToRequirement.getItem(pt);
			if (item == null) {
				return;
			}

			System.out.println("[Index]" + item.getText(0));
			this.selectedAutomataArtifact = (this.automataArtifactList.get(Integer.valueOf(item.getText(0)) - 1))
					.getValue();

			this.selectedAutomataNameText.setText(item.getText(1));
			this.selectedAutomataIdentifierText.setText(item.getText(2));

			System.out.println("[Index]" + item.getText(0));
			System.out.println("[Name]" + item.getText(1));
			System.out.println("[Identifier]" + item.getText(2));
			System.out.println("[Others]" + item.getText(3));

			if (this.selectedAutomataArtifact != null) {
				setPageComplete(true);
			}

		});

		Button viewButton = new Button(composite, SWT.PUSH);
		viewButton.setText("View");
		viewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
////				Shell shell = parent.getShell();
////				openDialogs(shell);
////				readAllAadlFiles();
			}
		});

		GridData gridDataViewButton = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataViewButton.horizontalSpan = 1;
		viewButton.setLayoutData(gridDataViewButton);

		Button updateButton = new Button(composite, SWT.PUSH);
		updateButton.setText("Update");
		updateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
////				Shell shell = parent.getShell();
////				openDialogs(shell);
////				readAllAadlFiles();
			}
		});

		GridData gridDataUpdateButton = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataUpdateButton.horizontalSpan = 1;
		updateButton.setLayoutData(gridDataUpdateButton);

		Button removeButton = new Button(composite, SWT.PUSH);
		removeButton.setText("Remove");
		removeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
////				Shell shell = parent.getShell();
////				openDialogs(shell);
////				readAllAadlFiles();
			}
		});

		GridData gridDataRemoveButton = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataRemoveButton.horizontalSpan = 1;
		removeButton.setLayoutData(gridDataRemoveButton);

		// Create a horizontal separator
		Label separatorMid = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridDataSeparatorMid = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, true, false);
		gridDataSeparatorMid.horizontalSpan = LAYOUT_NUM_COLUMNS;
		separatorMid.setLayoutData(gridDataSeparatorMid);

		//
		Label basicEventToStatechartTable = new Label(composite, SWT.LEFT);
		basicEventToStatechartTable.setText("Basic Event To Statechart Traceability:");
		GridData gridDataBasicEventToStatechartTable = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataBasicEventToStatechartTable.horizontalSpan = LAYOUT_NUM_COLUMNS;
		basicEventToStatechartTable.setLayoutData(gridDataBasicEventToStatechartTable);

		// Add BasicEventToStatechartTable
		// Add tableBasicEventToStatechart
		tableBasicEventToStatechart = new Table(composite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		GridData gridDataBasicEventToStatechart = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataBasicEventToStatechart.horizontalSpan = 4;
		gridDataBasicEventToStatechart.verticalSpan = 3;
		gridDataBasicEventToStatechart.grabExcessHorizontalSpace = true;
		tableBasicEventToStatechart.setLayoutData(gridDataBasicEventToStatechart);
		tableBasicEventToStatechart.setHeaderVisible(true);
		tableBasicEventToStatechart.setLinesVisible(true);

		// Print column title
		for (int i = 0; i < columnCount; i++) {
			TableColumn column = new TableColumn(tableBasicEventToStatechart, SWT.NONE);

			switch (i) {

			case 0:
				column.setText("Number");
				break;

			case 1:
				column.setText("Automata Name");
				break;

			case 2:
				column.setText("ID");
				break;

			case 3:
				column.setText("Others");
				break;
			}
		}

		for (int i = 0; i < columnCount; i++) {
			tableBasicEventToStatechart.getColumn(i).pack();
		}

		Point sizeBasicEventToStatechart = tableBasicEventToStatechart.computeSize(800, 400);
		tableBasicEventToStatechart.setSize(sizeBasicEventToStatechart);

		// Print the selected row in the table
		tableBasicEventToStatechart.addListener(SWT.MouseDown, event -> {
			Point pt = new Point(event.x, event.y);
			TableItem item = tableBasicEventToStatechart.getItem(pt);
			if (item == null) {
				return;
			}

			System.out.println("[Index]" + item.getText(0));
			this.selectedAutomataArtifact = (this.automataArtifactList.get(Integer.valueOf(item.getText(0)) - 1))
					.getValue();

			this.selectedAutomataNameText.setText(item.getText(1));
			this.selectedAutomataIdentifierText.setText(item.getText(2));

			System.out.println("[Index]" + item.getText(0));
			System.out.println("[Name]" + item.getText(1));
			System.out.println("[Identifier]" + item.getText(2));
			System.out.println("[Others]" + item.getText(3));

			if (this.selectedAutomataArtifact != null) {
				setPageComplete(true);
			}

		});

		Button viewButtonBasicEventToStatechart = new Button(composite, SWT.PUSH);
		viewButtonBasicEventToStatechart.setText("View");
		viewButtonBasicEventToStatechart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
////						Shell shell = parent.getShell();
////						openDialogs(shell);
////						readAllAadlFiles();
			}
		});

		GridData gridDataViewButtonBasicEventToStatechart = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataViewButtonBasicEventToStatechart.horizontalSpan = 1;
		viewButtonBasicEventToStatechart.setLayoutData(gridDataViewButtonBasicEventToStatechart);

		Button updateButtonBasicEventToStatechart = new Button(composite, SWT.PUSH);
		updateButtonBasicEventToStatechart.setText("Update");
		updateButtonBasicEventToStatechart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
////						Shell shell = parent.getShell();
////						openDialogs(shell);
////						readAllAadlFiles();
			}
		});

		GridData gridDataUpdateButtonBasicEventToStatechart = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataUpdateButtonBasicEventToStatechart.horizontalSpan = 1;
		updateButtonBasicEventToStatechart.setLayoutData(gridDataUpdateButtonBasicEventToStatechart);

		Button removeButtonBasicEventToStatechart = new Button(composite, SWT.PUSH);
		removeButtonBasicEventToStatechart.setText("Remove");
		removeButtonBasicEventToStatechart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
////						Shell shell = parent.getShell();
////						openDialogs(shell);
////						readAllAadlFiles();
			}
		});

		GridData gridDataRemoveButtonBasicEventToStatechart = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataRemoveButtonBasicEventToStatechart.horizontalSpan = 1;
		removeButtonBasicEventToStatechart.setLayoutData(gridDataRemoveButtonBasicEventToStatechart);

//		// Display result
//		Label selectedBasicEventLabel = new Label(composite, SWT.LEFT);
//		selectedBasicEventLabel.setText("Basic event name: ");
//		selectedBasicEventText = new Text(composite, SWT.BORDER | SWT.SINGLE  | SWT.READ_ONLY);
//		selectedBasicEventText.setText(selectedBasicEvent.getName());
//		GridData gridDataSelectedBasicEvent = new GridData(GridData.FILL, GridData.CENTER, true, false);
//		gridDataSelectedBasicEvent.horizontalSpan = 2;
//		selectedBasicEventText.setLayoutData(gridDataSelectedBasicEvent);
//
//		Label selectedBasicEventSafeGuardLabel = new Label(composite, SWT.LEFT);
//		selectedBasicEventSafeGuardLabel.setText("Is the selected basic event a safe guard? ");
//		selectedBasicEventSafeGuardText = new Text(composite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
//		selectedBasicEventSafeGuardText.setText(this.selectedBasicEvent.isSafeGuard() ? "true" : "false");
//		GridData gridDataSelectedBasicEventSafeGuard = new GridData(GridData.FILL, GridData.CENTER, true, false);
//		gridDataSelectedBasicEventSafeGuard.horizontalSpan = 2;
//		selectedBasicEventSafeGuardText.setLayoutData(gridDataSelectedBasicEventSafeGuard);
//
//		Label seselectedFilePathLabel = new Label(composite, SWT.LEFT);
//		seselectedFilePathLabel.setText("Statechart file path: ");
//		selectedFilePathText = new Text(composite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
//		GridData gridDataSelectedFilePath = new GridData(GridData.FILL, GridData.CENTER, true, false);
//		gridDataSelectedFilePath.horizontalSpan = 2;
//		selectedFilePathText.setLayoutData(gridDataSelectedFilePath);
//
////		private Text selectedAutomataIdentifierText;
////		private Text selectedAutomataLastChangeText;
//
//		Label selectedAutomataName = new Label(composite, SWT.LEFT);
//		selectedAutomataName.setText("Automata Name: ");
//		selectedAutomataNameText = new Text(composite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
//		GridData gridDataSelectedAutomataName = new GridData(GridData.FILL, GridData.CENTER, true, false);
//		gridDataSelectedAutomataName.horizontalSpan = 2;
//		selectedAutomataNameText.setLayoutData(gridDataSelectedAutomataName);
//
//		Label selectedAutomataIdentifierLabel = new Label(composite, SWT.LEFT);
//		selectedAutomataIdentifierLabel.setText("Automata Identifier: ");
//		selectedAutomataIdentifierText = new Text(composite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
//		GridData gridDataAutomataIdentifier = new GridData(GridData.FILL, GridData.CENTER, true, false);
//		gridDataAutomataIdentifier.horizontalSpan = 2;
//		selectedAutomataIdentifierText.setLayoutData(gridDataAutomataIdentifier);
//
//		Label selectedAutomataLastChangeLabel = new Label(composite, SWT.LEFT);
//		selectedAutomataLastChangeLabel.setText("Automata Last Change: ");
//		selectedAutomataLastChangeText = new Text(composite, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
//		GridData gridDataSelectedAutomataLastChange = new GridData(GridData.FILL, GridData.CENTER, true, false);
//		gridDataSelectedAutomataLastChange.horizontalSpan = 2;
//		selectedAutomataLastChangeText.setLayoutData(gridDataSelectedAutomataLastChange);

		parent.pack();

		// required to avoid an error in the system
		setControl(composite);
//		setPageComplete(true);
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
		this.selectedFilePathText.setText(selectedFilePath);
//		this.selectedAutomataLastChangeText.setText();
		System.out.println(selectedFilePath);

		if (selectedFilePath != null) {

			sr = new StatechartReader(selectedFilePath);
			sr.read();
			this.automataArtifactList = sr.getAutomataArtifacts();
			renderTable(this.automataArtifactList);
		}

		shell.pack();

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
	private void renderTable(List<Entry<String, AutomataArtifact>> automatas) {

		if (automatas.size() > 0) {
			this.selectedAutomataLastChangeText.setText(automatas.get(0).getValue().getOriginLastChange());
		}

		for (int i = 0; i < automatas.size(); i++) {

			TableItem item = new TableItem(tableRootEventToRequirement, SWT.NONE);
			item.setText(0, String.valueOf(i + 1));
			item.setText(1, automatas.get(i).getValue().getOriginName());
			item.setText(2, automatas.get(i).getValue().getOriginIdentifier());
			item.setText(3, "");
		}

		for (int i = 0; i < columnCount; i++) {
			tableRootEventToRequirement.getColumn(i).pack();
		}

		Point size = tableRootEventToRequirement.computeSize(SWT.DEFAULT, 400);
		tableRootEventToRequirement.setSize(size);
	}

	/**
	 * @return the selectedBasicEvent
	 */
	public Event getSelectedBasicEvent() {
		return selectedBasicEvent;
	}

	/**
	 * @param selectedBasicEvent the selectedBasicEvent to set
	 */
	public void setSelectedBasicEvent(Event selectedBasicEvent) {
		this.selectedBasicEvent = selectedBasicEvent;
	}

	/**
	 * @return the selectedAutomataArtifact
	 */
	public AutomataArtifact getSelectedAutomataArtifact() {
		return selectedAutomataArtifact;
	}
}
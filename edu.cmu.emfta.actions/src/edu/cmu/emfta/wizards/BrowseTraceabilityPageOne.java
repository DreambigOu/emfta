package edu.cmu.emfta.wizards;

import java.util.List;
import java.util.Map;
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

import analysis.RequirementChangeAnalysis;
import edu.cmu.emfta.Event;
import edu.uiuc.traceability.artifacts.AutomataArtifact;
import edu.uiuc.traceability.defaults.AutomataFields;
import edu.uiuc.traceability.defaults.EventFields;
import edu.uiuc.traceability.defaults.RequirementFields;
import edu.uiuc.traceability.defaults.TraceabilityConfigs;
import edu.uiuc.traceability.io.TraceabilityGraphIO;
import edu.uiuc.traceability.models.BasicEventToAutomataTraceLink;
import edu.uiuc.traceability.models.RootEventToRequirementTraceLink;
import edu.uiuc.traceability.models.TraceabilityGraph;
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
	private Table tableBasicEventToAutomata;
	private int rowCount = 5, columnCount = 4;
	private int columnsRootEventToRequirement = edu.uiuc.traceability.defaults.EventFields.values().length
			+ edu.uiuc.traceability.defaults.RequirementFields.values().length;

	private int columnsBasicEventToAutomata = edu.uiuc.traceability.defaults.EventFields.values().length
			+ edu.uiuc.traceability.defaults.AutomataFields.values().length;

	private StatechartReader sr;

	private AutomataArtifact selectedAutomataArtifact;

	private List<Entry<String, AutomataArtifact>> automataArtifactList;

	private static Map<String, RootEventToRequirementTraceLink> mapRootEventToRequirement = null;
	private static Map<String, BasicEventToAutomataTraceLink> mapBasicEventToAutomata = null;

	private int LAYOUT_NUM_COLUMNS = 5;

	private TraceabilityGraph tg;
	private String selectedRequirementId;
	protected String selectedRequirementFilePath;

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
		traceabiltyGraphPathText.setText(TraceabilityConfigs.DEFAULT_TRACEABILITY_GRAPH_PATH);
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

				tg = TraceabilityGraph.getInstance();
				TraceabilityGraphIO tgIO = tg.getTraceabilityGraphReader(traceabiltyGraphPathText.getText());
				tgIO.read();

				mapRootEventToRequirement = TraceabilityGraph.getMapRootEventToRequirement();
				mapBasicEventToAutomata = TraceabilityGraph.getMapBasicEventToAutomata();

				renderTableBasicEventToAutomata();
				renderTableRootEventToRequirement();

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

		// generate table
		generateRootEventToRequirementTable();

		// Create a horizontal separator
		Label separatorMid = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridDataSeparatorMid = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, true, false);
		gridDataSeparatorMid.horizontalSpan = LAYOUT_NUM_COLUMNS;
		separatorMid.setLayoutData(gridDataSeparatorMid);

		generateBasicEventToAutomataTable();

		parent.pack();

		// required to avoid an error in the system
		setControl(composite);
//		setPageComplete(true);
	}

	/**
	 *
	 */
	private void generateRootEventToRequirementTable() {
		// Table
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
		for (int i = 0; i < EventFields.values().length; i++) {
			TableColumn column = new TableColumn(tableRootEventToRequirement, SWT.NONE);

			if (i == EventFields.EventId.ordinal()) {
				column.setText(EventFields.EventId.name());

			} else if (i == EventFields.Name.ordinal()) {
				column.setText(EventFields.Name.name());

			} else if (i == EventFields.EventType.ordinal()) {
				column.setText(EventFields.EventType.name());

			} else if (i == EventFields.EventUri.ordinal()) {
				column.setText(EventFields.EventUri.name());

			} else if (i == EventFields.FilePath.ordinal()) {
				column.setText(EventFields.FilePath.name());

			} else if (i == EventFields.SafeGuard.ordinal()) {
				column.setText(EventFields.SafeGuard.name());
			}
		}

//		Print column title
		for (int i = 0; i < RequirementFields.values().length; i++) {
			TableColumn column = new TableColumn(tableRootEventToRequirement, SWT.NONE);

			if (i == RequirementFields.OriginDescription.ordinal()) {
				column.setText(RequirementFields.OriginDescription.name());

			} else if (i == RequirementFields.OriginIdentifier.ordinal()) {
				column.setText(RequirementFields.OriginIdentifier.name());

			} else if (i == RequirementFields.ReqType.ordinal()) {
				column.setText(RequirementFields.ReqType.name());

			} else if (i == RequirementFields.OriginLastChange.ordinal()) {
				column.setText(RequirementFields.OriginLastChange.name());

			} else if (i == RequirementFields.FilePath.ordinal()) {
				column.setText(RequirementFields.FilePath.name());

			}
		}

		for (int i = 0; i < columnsRootEventToRequirement; i++) {
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

			this.selectedRequirementId = item
					.getText(EventFields.values().length + RequirementFields.OriginIdentifier.ordinal());
			this.selectedRequirementFilePath = item
					.getText(EventFields.values().length + RequirementFields.FilePath.ordinal());

			System.out.println(
					"[EventFields.values().length]" + EventFields.values().length);

			System.out.println(
					"[RequirementFields.OriginIdentifier.ordinal()]" + RequirementFields.OriginIdentifier.ordinal());
			System.out.println("[RequirementFields.FilePath.ordinal()]" + RequirementFields.FilePath.ordinal());

			System.out.println("[selectedRequirementId]" + this.selectedRequirementId);
			System.out.println("[selectedRequirementFilePath]" + this.selectedRequirementFilePath);


			if (this.selectedAutomataArtifact != null) {
				setPageComplete(true);
			}

		});

		Button viewButton = new Button(composite, SWT.PUSH);
		viewButton.setText("Check Requirement");
		viewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (selectedRequirementFilePath != "" && selectedRequirementId != "") {
					List<String> result = null;

					RequirementChangeAnalysis analysis = new RequirementChangeAnalysis(selectedRequirementFilePath);
					if (analysis.isRequirementUpdated(selectedRequirementId)) {

						result = analysis.getImpactedFaultTreeFilePaths(selectedRequirementId);
					}

					if (result != null) {
						for (String entry : result) {

							System.out.println("[Impacted fault tree path: ]" + entry);
						}
					} else {
						System.out.println("No impacted fault tree is found");
					}
				}
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
	}

	/**
	 *
	 */
	private void generateBasicEventToAutomataTable() {
		//
		Label basicEventToStatechartTable = new Label(composite, SWT.LEFT);
		basicEventToStatechartTable.setText("Basic Event To Statechart Traceability:");
		GridData gridDataBasicEventToStatechartTable = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataBasicEventToStatechartTable.horizontalSpan = LAYOUT_NUM_COLUMNS;
		basicEventToStatechartTable.setLayoutData(gridDataBasicEventToStatechartTable);

		// Add BasicEventToStatechartTable
		// Add tableBasicEventToStatechart
		tableBasicEventToAutomata = new Table(composite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
		GridData gridDataBasicEventToStatechart = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
				GridData.VERTICAL_ALIGN_CENTER, false, false);
		gridDataBasicEventToStatechart.horizontalSpan = 4;
		gridDataBasicEventToStatechart.verticalSpan = 3;
		gridDataBasicEventToStatechart.grabExcessHorizontalSpace = true;
		tableBasicEventToAutomata.setLayoutData(gridDataBasicEventToStatechart);
		tableBasicEventToAutomata.setHeaderVisible(true);
		tableBasicEventToAutomata.setLinesVisible(true);

		// Print column title
		for (int i = 0; i < EventFields.values().length; i++) {
			TableColumn column = new TableColumn(tableBasicEventToAutomata, SWT.NONE);

			if (i == EventFields.EventId.ordinal()) {
				column.setText(EventFields.EventId.name());

			} else if (i == EventFields.Name.ordinal()) {
				column.setText(EventFields.Name.name());

			} else if (i == EventFields.EventType.ordinal()) {
				column.setText(EventFields.EventType.name());

			} else if (i == EventFields.EventUri.ordinal()) {
				column.setText(EventFields.EventUri.name());

			} else if (i == EventFields.FilePath.ordinal()) {
				column.setText(EventFields.FilePath.name());

			} else if (i == EventFields.SafeGuard.ordinal()) {
				column.setText(EventFields.SafeGuard.name());
			}
		}

		for (int i = 0; i < AutomataFields.values().length; i++) {
			TableColumn column = new TableColumn(tableBasicEventToAutomata, SWT.NONE);

			if (i == AutomataFields.OriginName.ordinal()) {
				column.setText(AutomataFields.OriginName.name());

			} else if (i == AutomataFields.OriginIdentifier.ordinal()) {
				column.setText(AutomataFields.OriginIdentifier.name());

			} else if (i == AutomataFields.OriginLastChange.ordinal()) {
				column.setText(AutomataFields.OriginLastChange.name());

			} else if (i == AutomataFields.FilePath.ordinal()) {
				column.setText(AutomataFields.FilePath.name());

			}
		}

		for (int i = 0; i < columnsBasicEventToAutomata; i++) {
			tableBasicEventToAutomata.getColumn(i).pack();
		}

		Point sizeBasicEventToStatechart = tableBasicEventToAutomata.computeSize(800, 400);
		tableBasicEventToAutomata.setSize(sizeBasicEventToStatechart);

		// Print the selected row in the table
		tableBasicEventToAutomata.addListener(SWT.MouseDown, event -> {
			Point pt = new Point(event.x, event.y);
			TableItem item = tableBasicEventToAutomata.getItem(pt);
			if (item == null) {
				return;
			}

			System.out.println("[Index]" + item.getText(0));
//			this.selectedAutomataArtifact = (this.automataArtifactList.get(Integer.valueOf(item.getText(0)) - 1))
//					.getValue();
//
//			this.selectedAutomataNameText.setText(item.getText(1));
//			this.selectedAutomataIdentifierText.setText(item.getText(2));

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

	private void renderTableRootEventToRequirement() {

		System.out.println("[mapRootEventToRequirement]: " + mapRootEventToRequirement.size());

		if (mapRootEventToRequirement.size() == 0 || mapRootEventToRequirement == null) {
			return;
		}

		for (Entry<String, RootEventToRequirementTraceLink> entry : mapRootEventToRequirement.entrySet()) {

			TableItem item = new TableItem(tableRootEventToRequirement, SWT.NONE);

			// Print column title
			for (int i = 0; i < EventFields.values().length; i++) {

				if (i == EventFields.EventId.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getUuid());

				} else if (i == EventFields.Name.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getName());

				} else if (i == EventFields.EventType.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getEventType().toString());

				} else if (i == EventFields.EventUri.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getEventUri());

				} else if (i == EventFields.FilePath.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getFilePath());

				} else if (i == EventFields.SafeGuard.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getSafeGuard().toString());
				}
			}

//			Print column title
			for (int i = 0; i < RequirementFields.values().length; i++) {

				if (i == RequirementFields.OriginDescription.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getOriginDescription());

				} else if (i == RequirementFields.OriginIdentifier.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getOriginIdentifier());

				} else if (i == RequirementFields.ReqType.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getReqType().name());

				} else if (i == RequirementFields.OriginLastChange.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getOriginLastChange());

				} else if (i == RequirementFields.FilePath.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getFilePath());

				}
			}
		}

		for (int i = 0; i < columnsRootEventToRequirement; i++) {
			tableRootEventToRequirement.getColumn(i).pack();
		}

		Point size = tableRootEventToRequirement.computeSize(SWT.DEFAULT, 400);
		tableRootEventToRequirement.setSize(size);
	}

	private void renderTableBasicEventToAutomata() {

		System.out.println("[mapBasicEventToAutomata size]: " + mapBasicEventToAutomata.size());

		if (mapBasicEventToAutomata.size() == 0 || mapBasicEventToAutomata == null) {
			return;
		}


		for (Entry<String, BasicEventToAutomataTraceLink> entry : mapBasicEventToAutomata.entrySet()) {

			TableItem item = new TableItem(tableBasicEventToAutomata, SWT.NONE);
//			item.setText(0, String.valueOf(i + 1));

			// Print column title
			for (int i = 0; i < EventFields.values().length; i++) {

				if (i == EventFields.EventId.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getUuid());

				} else if (i == EventFields.Name.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getName());

				} else if (i == EventFields.EventType.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getEventType().toString());

				} else if (i == EventFields.EventUri.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getEventUri());

				} else if (i == EventFields.FilePath.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getFilePath());

				} else if (i == EventFields.SafeGuard.ordinal()) {
					item.setText(i, entry.getValue().getSrc().getSafeGuard().toString());
				}
			}

//			Print column title
			for (int i = 0; i < AutomataFields.values().length; i++) {

				if (i == AutomataFields.OriginName.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getOriginName());

				} else if (i == AutomataFields.OriginIdentifier.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getOriginIdentifier());

				} else if (i == AutomataFields.OriginLastChange.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getOriginLastChange());

				} else if (i == AutomataFields.FilePath.ordinal()) {
					item.setText(EventFields.values().length + i, entry.getValue().getDst().getFilePath());

				}
			}
		}

		for (int i = 0; i < columnsBasicEventToAutomata; i++) {
			tableBasicEventToAutomata.getColumn(i).pack();
		}

		Point size = tableRootEventToRequirement.computeSize(SWT.DEFAULT, 400);
		tableBasicEventToAutomata.setSize(size);
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
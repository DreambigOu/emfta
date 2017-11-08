package edu.cmu.emfta.actions;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DNodeSpec;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DSemanticDiagramSpec;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import edu.cmu.emfta.Event;
import edu.cmu.emfta.wizards.BrowseTraceabilityWizard;

public class BrowseTraceabilityAction implements IExternalJavaAction {

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {

		// System.out.println("[GenerateURIAction] calling execute");
		for (EObject eo : selections) {
			EObject target = null;

			// System.out.println("[GenerateURIAction] eobject = " + eo);

			if (eo instanceof DSemanticDiagramSpec) {
				DSemanticDiagramSpec ds = (DSemanticDiagramSpec) eo;
				target = ds.getTarget();
				//
				// System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());
				//
				// System.out.println("[GenerateURIAction] target = " + target);
			}

			if (eo instanceof DNodeSpec) {
				DNodeSpec ds = (DNodeSpec) eo;
				target = ds.getTarget();
				//
				// System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());
				//
				// System.out.println("[GenerateURIAction] target = " + target);
			}

			if (target != null) {

				if (target instanceof edu.cmu.emfta.Event) {

					openTraceabilityBrowser((Event) target);

					return;

				}
			}

			MessageBox dialog = new MessageBox(Display.getDefault().getActiveShell(), SWT.ERROR | SWT.ICON_ERROR);
			dialog.setText("Error");
			dialog.setMessage("Please select an event in the FTA tree");

			dialog.open();
		}
	}


	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		/**
		 * For now, we return true all the time. Might need to optimize
		 * it to make it more user-friendly.
		 */
//		System.out.println("[GenerateURIAction] calling canExecute");
		for (EObject eo : selections) {
//			System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());

			if (eo instanceof DSemanticDiagramSpec) {
				DSemanticDiagramSpec ds = (DSemanticDiagramSpec) eo;
				EObject target = ds.getTarget();

//				System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());
//
//				System.out.println("[GenerateURIAction] target = " + target);

			}

			if (eo instanceof DNodeSpec) {
				DNodeSpec ds = (DNodeSpec) eo;
				EObject target = ds.getTarget();

//				System.out.println("[GenerateURIAction] eobject class= " + eo.getClass());
//
//				System.out.println("[GenerateURIAction] target = " + target);

				if (target instanceof edu.cmu.emfta.Event) {
					return true;
				}
			}

		}
		return false;
	}

//	private Shell openTraceabilityBrowserShell(final Display display, Event target) {

	private void openTraceabilityBrowser(Event target) {

		Shell shell = new Shell();
		WizardDialog dialog = new WizardDialog(shell, new BrowseTraceabilityWizard(target));
		dialog.open();

//		GridLayout layout = new GridLayout();
//		layout.numColumns = 5;
//		shell.setLayout(layout);
//
//		Label selectFileLable = new Label(shell, SWT.LEFT);
//		selectFileLable.setText("Traceabilit Graph file:");
//		GridData gridDataSelectFileLable = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
//				GridData.VERTICAL_ALIGN_CENTER, false, false);
//		gridDataSelectFileLable.horizontalSpan = 1;
//		selectFileLable.setLayoutData(gridDataSelectFileLable);
//
//		Text traceabiltyGraphPathText = new Text(shell, SWT.BORDER | SWT.SINGLE);
//		traceabiltyGraphPathText.setText("path name");
//		GridData gridDataSelectedRootEvent = new GridData(GridData.FILL, GridData.CENTER, true, false);
//		gridDataSelectedRootEvent.horizontalSpan = 2;
//		traceabiltyGraphPathText.setLayoutData(gridDataSelectedRootEvent);
//
//		Button selectFileButton = new Button(shell, SWT.PUSH);
//		selectFileButton.setText("Change");
//		selectFileButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
////				Shell shell = parent.getShell();
////				openDialogs(shell);
////				readAllAadlFiles();
//			}
//		});
//
//		GridData gridDataSelectButton = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
//				GridData.VERTICAL_ALIGN_CENTER, false,
//				false);
//		gridDataSelectButton.horizontalSpan = 1;
//		selectFileButton.setLayoutData(gridDataSelectButton);
//
//		Button refereshButton = new Button(shell, SWT.PUSH);
//		refereshButton.setText("Refresh");
//		refereshButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
////				Shell shell = parent.getShell();
////				openDialogs(shell);
////				readAllAadlFiles();
//			}
//		});
//
//		GridData gridDataRefereshButton = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING,
//				GridData.VERTICAL_ALIGN_CENTER,
//				false, false);
//		gridDataRefereshButton.horizontalSpan = 1;
//		selectFileButton.setLayoutData(gridDataRefereshButton);

		// table


	}
}

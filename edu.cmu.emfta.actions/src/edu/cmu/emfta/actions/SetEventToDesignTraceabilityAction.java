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
import edu.cmu.emfta.wizards.SetEventToDesignTraceabilityWizard;

public class SetEventToDesignTraceabilityAction implements IExternalJavaAction {

	public SetEventToDesignTraceabilityAction() {
		// TODO Auto-generated constructor stub
	}

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
					// generateURI(((edu.cmu.emfta.Event) target));
					openInputFileDialog();
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

					if (((Event) target).getGate() == null
							|| ((Event) target).getType() == edu.cmu.emfta.EventType.BASIC) {
						return true;
					}
				}
			}

		}
		return false;
	}

	private void openInputFileDialog() {

		Shell shell = new Shell();
		WizardDialog dialog = new WizardDialog(shell, new SetEventToDesignTraceabilityWizard());
		dialog.open();
	}

}

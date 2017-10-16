package edu.cmu.emfta.wizards;

import org.eclipse.jface.wizard.Wizard;

public class SetEventToRequirementTraceabilityWizard extends Wizard {
	protected SetEventToRequirementTraceabilityPageOne one;
	protected SetEventToRequirementTraceabilityPageTwo two;

	public SetEventToRequirementTraceabilityWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return "Export My Data";
	}

	@Override
	public void addPages() {
		one = new SetEventToRequirementTraceabilityPageOne();
		two = new SetEventToRequirementTraceabilityPageTwo();
		addPage(one);
		addPage(two);
	}

	@Override
	public boolean performFinish() {
		// Print the result to the console
		System.out.println(one.getText1());
		System.out.println(two.getText1());

		return true;
	}

}

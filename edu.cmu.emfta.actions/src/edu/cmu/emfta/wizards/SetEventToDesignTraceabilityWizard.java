package edu.cmu.emfta.wizards;

import org.eclipse.jface.wizard.Wizard;

public class SetEventToDesignTraceabilityWizard extends Wizard {
	protected SetEventToDesignTraceabilityPageOne one;
	protected SetEventToDesignTraceabilityPageTwo two;

	public SetEventToDesignTraceabilityWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return "Export My Data";
	}

	@Override
	public void addPages() {
		one = new SetEventToDesignTraceabilityPageOne();
		two = new SetEventToDesignTraceabilityPageTwo();
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

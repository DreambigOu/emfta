package edu.cmu.emfta.wizards;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.wizard.Wizard;

import edu.cmu.emfta.Event;
import edu.uiuc.traceability.artifacts.EventArtifact;
import edu.uiuc.traceability.artifacts.EventType;
import edu.uiuc.traceability.defaults.Traceability;
import edu.uiuc.traceability.models.RootEventToRequirementTraceLink;
import edu.uiuc.traceability.models.TraceabilityGraph;
import edu.uiuc.traceability.models.TraceabilityGraphReader;

public class SetEventToRequirementTraceabilityWizard extends Wizard {
	protected SetEventToRequirementTraceabilityPageOne one;
//	protected SetEventToRequirementTraceabilityPageTwo two;
	protected Event selectedRootEvent;

	public SetEventToRequirementTraceabilityWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	public SetEventToRequirementTraceabilityWizard(Event selectedRootEvent) {
		super();
		this.selectedRootEvent = selectedRootEvent;
		setNeedsProgressMonitor(true);
	}

	@Override
	public String getWindowTitle() {
		return "Set Event To Requirement Traceability";
	}

	@Override
	public void addPages() {
		one = new SetEventToRequirementTraceabilityPageOne();
//		two = new SetEventToRequirementTraceabilityPageTwo();
		one.setSelectedRootEvent(selectedRootEvent);
		addPage(one);
//		addPage(two);
	}

	@Override
	public boolean performFinish() {

		TraceabilityGraph tg = TraceabilityGraph.getInstance();

		TraceabilityGraphReader tgReader = tg.getTraceabilityGraphReader(Traceability.DEFAULT_TRACEABILITY_GRAPH_PATH);
		tgReader.getTraceabilityGraph();

		if (one.isPageComplete()) {

			RootEventToRequirementTraceLink traceLink = new RootEventToRequirementTraceLink();

			EventArtifact eventArtifact = new EventArtifact();
			eventArtifact.setName(this.selectedRootEvent.getName());
			eventArtifact.setUuid(this.selectedRootEvent.getUuid());
			eventArtifact.setEventType(EventType.ROOT_EVENT);
			eventArtifact.setEventUri(this.selectedRootEvent.getURI());
			eventArtifact.setFilePath(EcoreUtil.getURI(selectedRootEvent).toPlatformString(true));
			eventArtifact.setSafeGuard(false);

			System.out.println(eventArtifact.toString());

			String uuid = tg.addRootEventToRequirementTraceLink(eventArtifact, one.getSelectedRequirementArtifact());
			System.out.println("uuid: " + uuid);
			System.out.println(tg.getRootEventToRequirementTraceLink(uuid).toString());

		}

		tgReader.write();

		return one.isPageComplete();
	}

	public Event getSelectedRootEvent() {
		return selectedRootEvent;
	}

	public void setSelectedRootEvent(Event selectedRootEvent) {
		this.selectedRootEvent = selectedRootEvent;
	}

}

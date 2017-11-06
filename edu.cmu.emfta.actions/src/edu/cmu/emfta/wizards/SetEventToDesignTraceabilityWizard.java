package edu.cmu.emfta.wizards;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.wizard.Wizard;

import edu.cmu.emfta.Event;
import edu.uiuc.traceability.artifacts.EventArtifact;
import edu.uiuc.traceability.artifacts.EventType;
import edu.uiuc.traceability.models.TraceabilityGraph;

public class SetEventToDesignTraceabilityWizard extends Wizard {
	protected SetEventToDesignTraceabilityPageOne one;
//	protected SetEventToDesignTraceabilityPageTwo two;
	protected Event selectedBasicEvent;

	public SetEventToDesignTraceabilityWizard(Event target) {
		super();
		setNeedsProgressMonitor(true);
		this.selectedBasicEvent = target;
	}

	@Override
	public String getWindowTitle() {
		return "Set Event To Design Traceability";
	}

	@Override
	public void addPages() {
		one = new SetEventToDesignTraceabilityPageOne();
		one.setSelectedBasicEvent(selectedBasicEvent);
		addPage(one);
	}

	@Override
	public boolean performFinish() {

		TraceabilityGraph tg = TraceabilityGraph.getInstance();

		if (one.isPageComplete()) {

			EventArtifact eventArtifact = new EventArtifact();
			eventArtifact.setName(this.selectedBasicEvent.getName());
			eventArtifact.setUuid(this.selectedBasicEvent.getUuid());
			eventArtifact.setEventType(EventType.BASIC_EVENT);
			eventArtifact.setEventUri(this.selectedBasicEvent.getURI());
			eventArtifact.setFilePath(EcoreUtil.getURI(selectedBasicEvent).toPlatformString(true));
			eventArtifact.setSafeGuard(this.selectedBasicEvent.isSafeGuard());

			System.out.println(eventArtifact.toString());

			String uuid = tg.addBasicEventToAutomataTraceLink(eventArtifact, one.getSelectedAutomataArtifact());
			System.out.println("uuid: " + uuid);
			System.out.println(tg.getBasicEventToAutomataTraceLink(uuid).toString());
		}

		return one.isPageComplete();
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

}

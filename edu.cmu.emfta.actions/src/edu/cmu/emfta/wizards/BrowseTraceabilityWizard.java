package edu.cmu.emfta.wizards;

import org.eclipse.jface.wizard.Wizard;

import edu.cmu.emfta.Event;

public class BrowseTraceabilityWizard extends Wizard {
	protected BrowseTraceabilityPageOne one;
//	protected SetEventToDesignTraceabilityPageTwo two;
	protected Event selectedBasicEvent;

	public BrowseTraceabilityWizard(Event target) {
		super();
		setNeedsProgressMonitor(true);
		this.selectedBasicEvent = target;
	}

	@Override
	public String getWindowTitle() {
		return "Browse Traceability";
	}

	@Override
	public void addPages() {
		one = new BrowseTraceabilityPageOne();
		one.setSelectedBasicEvent(selectedBasicEvent);
		addPage(one);
	}

	@Override
	public boolean performFinish() {

//		TraceabilityGraph tg = TraceabilityGraph.getInstance();
//
//		if (one.isPageComplete()) {
//
//			EventArtifact eventArtifact = new EventArtifact();
//			eventArtifact.setName(this.selectedBasicEvent.getName());
//			eventArtifact.setUuid(this.selectedBasicEvent.getUuid());
//			eventArtifact.setEventType(EventType.BASIC_EVENT);
//			eventArtifact.setEventUri(this.selectedBasicEvent.getURI());
//			eventArtifact.setFilePath(EcoreUtil.getURI(selectedBasicEvent).toPlatformString(true));
//			eventArtifact.setSafeGuard(this.selectedBasicEvent.isSafeGuard());
//
//			System.out.println(eventArtifact.toString());
//
//			String uuid = tg.addBasicEventToAutomataTraceLink(eventArtifact, one.getSelectedAutomataArtifact());
//			System.out.println("uuid: " + uuid);
//			System.out.println(tg.getBasicEventToAutomataTraceLink(uuid).toString());
//		}

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

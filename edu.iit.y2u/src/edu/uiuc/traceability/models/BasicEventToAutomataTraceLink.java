package edu.uiuc.traceability.models;

import edu.uiuc.traceability.artifacts.AutomataArtifact;
import edu.uiuc.traceability.artifacts.EventArtifact;
import edu.uiuc.traceability.artifacts.EventType;

public class BasicEventToAutomataTraceLink {

	private String id;
	private EventArtifact src;
	private AutomataArtifact dst;

	public BasicEventToAutomataTraceLink() {
		super();
//		this.src.setEventType(EventType.BASIC_EVENT);
	}

	public BasicEventToAutomataTraceLink(EventArtifact src, AutomataArtifact dst) {
		super();
		this.src = src;
		this.src.setEventType(EventType.BASIC_EVENT);
		this.dst = dst;
	}

	public EventArtifact getSrc() {
		return src;
	}

	public void setSrc(EventArtifact src) {
		this.src = src;
	}

	public AutomataArtifact getDst() {
		return dst;
	}

	public void setDst(AutomataArtifact dst) {
		this.dst = dst;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasicEventToAutomataTraceLink [id=" + id + ", src=" + src + ", dst=" + dst + "]";
	}
}

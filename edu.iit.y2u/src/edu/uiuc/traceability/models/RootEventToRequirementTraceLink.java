package edu.uiuc.traceability.models;

import edu.uiuc.traceability.artifacts.EventArtifact;
import edu.uiuc.traceability.artifacts.EventType;
import edu.uiuc.traceability.artifacts.RequirementArtifact;

public class RootEventToRequirementTraceLink {

	private String id;
	private EventArtifact src;
	private RequirementArtifact dst;

	public RootEventToRequirementTraceLink() {
		super();
//		this.src.setEventType(EventType.ROOT_EVENT);
	}

	public RootEventToRequirementTraceLink(EventArtifact src, RequirementArtifact dst) {
		super();
		this.src = src;
		this.src.setEventType(EventType.ROOT_EVENT);
		this.dst = dst;
	}

	public EventArtifact getSrc() {
		return src;
	}

	public void setSrc(EventArtifact src) {
		this.src = src;
	}

	public RequirementArtifact getDst() {
		return dst;
	}

	public void setDst(RequirementArtifact dst) {
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
		return "RootEventToRequirementTraceLink [id=" + id + ", src=" + src + ", dst=" + dst + "]";
	}

}

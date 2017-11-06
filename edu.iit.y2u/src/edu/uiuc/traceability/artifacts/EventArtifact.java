package edu.uiuc.traceability.artifacts;

public class EventArtifact {

	private String eventUri;
	private String filePath;
	private String name;
	private String uuid;
	private Boolean safeGuard;

	private EventType eventType;

	public EventArtifact() {
		super();
	}

	public EventArtifact(String eventUri, String filePath, String name, EventType eventType) {
		super();
		this.eventUri = eventUri;
		this.filePath = filePath;
		this.name = name;
		this.eventType = eventType;
	}

	public String getEventUri() {
		return eventUri;
	}

	public void setEventUri(String eventUri) {
		this.eventUri = eventUri;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the safeGuard
	 */
	public Boolean getSafeGuard() {
		return safeGuard;
	}

	/**
	 * @param safeGuard the safeGuard to set
	 */
	public void setSafeGuard(Boolean safeGuard) {
		this.safeGuard = safeGuard;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EventArtifact [eventUri=" + eventUri + ", filePath=" + filePath + ", name=" + name + ", uuid=" + uuid
				+ ", safeGuard=" + safeGuard + ", eventType=" + eventType + "]";
	}
}

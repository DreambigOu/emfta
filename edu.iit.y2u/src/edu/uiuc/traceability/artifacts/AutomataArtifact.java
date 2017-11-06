package edu.uiuc.traceability.artifacts;

public class AutomataArtifact {

	private String originIdentifier;
	private String originLastChange;
	private String originName;
	private String filePath;

	public AutomataArtifact() {
		super();
	}

	public AutomataArtifact(String originIdentifier, String originLastChange, String originName, String filePath) {
		super();
		this.originIdentifier = originIdentifier;
		this.originLastChange = originLastChange;
		this.originName = originName;
		this.filePath = filePath;
	}

	public String getOriginIdentifier() {
		return originIdentifier;
	}

	public void setOriginIdentifier(String originIdentifier) {
		this.originIdentifier = originIdentifier;
	}

	public String getOriginLastChange() {
		return originLastChange;
	}

	public void setOriginLastChange(String originLastChange) {
		this.originLastChange = originLastChange;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "AutomataArtifact [originIdentifier=" + originIdentifier + ", originLastChange=" + originLastChange
				+ ", originName=" + originName + ", filePath=" + filePath + "]";
	}

}

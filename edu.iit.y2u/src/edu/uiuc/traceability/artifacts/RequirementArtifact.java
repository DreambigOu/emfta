package edu.uiuc.traceability.artifacts;

public class RequirementArtifact {

	private String originIdentifier;
	private String originLastChange;
	private String originDescription;

	private String filePath;
	private RequirementType reqType;

	public RequirementArtifact(String originIdentifier, String originLastChange, String originDescription,
			String filePath, RequirementType reqType) {
		super();
		this.originIdentifier = originIdentifier;
		this.originLastChange = originLastChange;
		this.originDescription = originDescription;
		this.filePath = filePath;
		this.reqType = reqType;
	}

	public RequirementArtifact() {
		// TODO Auto-generated constructor stub
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

	public String getOriginDescription() {
		return originDescription;
	}

	public void setOriginDescription(String originDescription) {
		this.originDescription = originDescription;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public RequirementType getReqType() {
		return reqType;
	}

	public void setReqType(RequirementType reqType) {
		this.reqType = reqType;
	}

	@Override
	public String toString() {
		return "RequirementArtifact [originIdentifier=" + originIdentifier + ", originLastChange=" + originLastChange
				+ ", originDescription=" + originDescription + ", filePath=" + filePath + ", reqType=" + reqType + "]";
	}

}

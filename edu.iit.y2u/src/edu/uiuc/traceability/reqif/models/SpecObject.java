package edu.uiuc.traceability.reqif.models;

public class SpecObject {
	String Identifier;
	String LastChange;

	Values values;
	Type type;

	public String getIdentifier() {
		return Identifier;
	}

	public void setIdentifier(String identifier) {
		Identifier = identifier;
	}

	public String getLastChange() {
		return LastChange;
	}

	public void setLastChange(String lastChange) {
		LastChange = lastChange;
	}

	public Values getValues() {
		return values;
	}

	public void setValues(Values values) {
		this.values = values;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}

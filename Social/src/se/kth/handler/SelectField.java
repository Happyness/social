package se.kth.handler;

public class SelectField {
	public String label;
	public String value;

	public SelectField(String label, String value){
		this.label = label;
		this.value = value;
	}

	public String getLabel(){
		return label;
	}

	public String getValue(){
		return value;
	}
}
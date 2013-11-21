package se.kth.frontend.handler;


/**
 * @author Mats Maatson and Joel Denke
 * @description A selectfield helper for the bean, to output select field with ease.
 */
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
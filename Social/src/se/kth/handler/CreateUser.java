package se.kth.handler;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;

import se.kth.model.UserHandler;
import se.kth.model.bo.UserProfile;

@ManagedBean
public class CreateUser implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String dobYearSelect = "1980";
	private String dobMonthSelect;
	private String dobDaySelect;
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String response;
	
	public String getDobYearSelect() {
		return dobYearSelect;
	}
 
	public void setDobYearSelect(String dobYear) {
		this.dobYearSelect = dobYear;
	}

	public String getDobMonthSelect() {
		return dobMonthSelect;
	}

	public void setDobMonthSelect(String dobMonthSelect) {
		this.dobMonthSelect = dobMonthSelect;
	}

	public String getDobDaySelect() {
		return dobDaySelect;
	}

	public void setDobDaySelect(String dobDaySelect) {
		this.dobDaySelect = dobDaySelect;
	}
 
	public SelectField[] dobYear;
	public SelectField[] dobMonth;
	public SelectField[] dobDay;
 
	public SelectField[] getDobYear()
	{
		int startYear = 1980;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		dobYear = new SelectField[currentYear-startYear+1];
		
		for (int i = 1980, j=0; i <= Calendar.getInstance().get(Calendar.YEAR); i++, j++) {
			dobYear[j] = new SelectField(Integer.toString(i), Integer.toString(i));
		}
 
		return dobYear;
	}
	
	public SelectField[] getDobMonth()
	{	
		dobMonth = new SelectField[12];
		
		for (int i = 1, j=0; i <= 12; i++, j++) {
			dobMonth[j] = new SelectField(Integer.toString(i), Integer.toString(i));
		}
 
		return dobMonth;
	}
	
	public SelectField[] getDobDay()
	{	
		dobDay = new SelectField[31];
		
		for (int i = 1, j=0; i <= 31; i++, j++) {
			dobDay[j] = new SelectField(Integer.toString(i), Integer.toString(i));
		}
 
		return dobDay;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void save()
	{
		UserHandler uh = new UserHandler();
		UserProfile up = new UserProfile();   
		up.setFirstName(name);
		up.setSurname(surname);
		up.setEmail(email);
		
		String dobio = dobYearSelect + "-" + dobMonthSelect + "-" + dobDaySelect;
		up.setDateOfBirth(java.sql.Date.valueOf(dobio));
		
		response = uh.createUser(username, password, up);
	}
}

package se.kth.frontend.handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import se.kth.backend.model.dao.UserDao;
import se.kth.backend.resource.SecurityUtils;
import se.kth.common.Converter;
import se.kth.common.UserResource;
import se.kth.common.UsersResource;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserLogMessage;
import se.kth.common.model.bo.UserProfile;
import se.kth.frontend.handler.service.UserService;

import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@ManagedBean
public class UserHandler implements Serializable
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
	
	public UserHandler() {

	}
	
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
		UserProfile up = new UserProfile();   
		up.setFirstName(name);
		up.setSurname(surname);
		up.setEmail(email);
		
		String dobio = dobYearSelect + "-" + dobMonthSelect + "-" + dobDaySelect;
		up.setDateOfBirth(java.sql.Date.valueOf(dobio));
	  
		User user = new User();
		user.setUsername(username);
		user.setPassword(SecurityUtils.getHash(password));
		user.setUserProfile(up);
		up.setUser(user);
		
    	String jsonString = Converter.toJson(user);
    	JsonRepresentation jsonRep = new JsonRepresentation(jsonString);
    	
    	/*
		Form form = new Form();
		form.add("username", username);
		form.add("password", password);
		form.add("name", name);
		form.add("surname", surname);
		form.add("email", email);
		form.add("dobYearSelect", dobYearSelect);
		form.add("dobMonthSelect", dobMonthSelect);
		form.add("dobDaySelect", dobDaySelect);*/
		
		UserResource ur = ClientHandler.getObjectResource("/user/store", UserResource.class);
		try {
			response = ur.createUser(jsonRep).getText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return ClientHandler.server + "/user/store";
		/*UserService uh = new UserService();
		UserProfile up = new UserProfile();   
		up.setFirstName(name);
		up.setSurname(surname);
		up.setEmail(email);
		
		String dobio = dobYearSelect + "-" + dobMonthSelect + "-" + dobDaySelect;
		up.setDateOfBirth(java.sql.Date.valueOf(dobio));
		
		response = uh.createUser(username, password, up);*/
	}
	
	public List<User> getUsers() throws IOException
	{
		UsersResource ur = ClientHandler.getObjectResource("/users", UsersResource.class);
		JsonRepresentation jsonRep = new JsonRepresentation(ur.getUsers());
		List<User> users = Converter.fromJsonToList(jsonRep.getText(), new TypeToken<List<User>>() {}.getType());
		
		return users;
	}
}

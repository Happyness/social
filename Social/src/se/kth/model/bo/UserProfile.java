package se.kth.model.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class UserProfile implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	private String firstName;
	private String surname;
	private String email;
	private Date dob;
	private User user;
	
	public UserProfile() {
		
	}
	
	public UserProfile(int id, String firstName, String surname, String email, Date dob, User user) {
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.email = email;
		this.dob = dob;
		this.setUser(user);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

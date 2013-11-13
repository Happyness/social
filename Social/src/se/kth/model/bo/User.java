package se.kth.model.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String password;
	private Date timestamp;
	private UserProfile userProfile;
	private Set<FromUserToUserPrivateMessageJoin> sentPrivateMessagesList;
	private Set<FromUserToUserPrivateMessageJoin> receivedPrivateMessagesList;

	public User() {
	}

	public User(int id, String username, String password, Date timestamp,
			UserProfile userProfile, Set<FromUserToUserPrivateMessageJoin> receivedPrivateMessagesList,
			Set<FromUserToUserPrivateMessageJoin> sentPrivateMessagesList) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.timestamp = timestamp;
		this.userProfile = userProfile;
		this.receivedPrivateMessagesList = receivedPrivateMessagesList;
		this.sentPrivateMessagesList = sentPrivateMessagesList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Set<FromUserToUserPrivateMessageJoin> getSentPrivateMessagesList() {
		return sentPrivateMessagesList;
	}

	public void setSentPrivateMessagesList(
			Set<FromUserToUserPrivateMessageJoin> sentPrivateMessagesList) {
		this.sentPrivateMessagesList = sentPrivateMessagesList;
	}

	public Set<FromUserToUserPrivateMessageJoin> getReceivedPrivateMessagesList() {
		return receivedPrivateMessagesList;
	}

	public void setReceivedPrivateMessagesList(
			Set<FromUserToUserPrivateMessageJoin> receivedPrivateMessagesList) {
		this.receivedPrivateMessagesList = receivedPrivateMessagesList;
	}

}

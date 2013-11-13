package se.kth.model.bo;

// Generated Nov 13, 2013 3:03:35 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * UserLogMessage generated by hbm2java
 */
public class UserLogMessage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer userLogMessageId;
	private User user;
	private String message;
	private Date messageSent;

	public UserLogMessage() {
	}

	public UserLogMessage(User user, Date messageSent) {
		this.user = user;
		this.messageSent = messageSent;
	}

	public UserLogMessage(User user, String message, Date messageSent) {
		this.user = user;
		this.message = message;
		this.messageSent = messageSent;
	}

	public Integer getUserLogMessageId() {
		return this.userLogMessageId;
	}

	public void setUserLogMessageId(Integer userLogMessageId) {
		this.userLogMessageId = userLogMessageId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessageSent() {
		return this.messageSent;
	}

	public void setMessageSent(Date messageSent) {
		this.messageSent = messageSent;
	}

}

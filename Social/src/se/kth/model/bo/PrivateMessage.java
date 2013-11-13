package se.kth.model.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class PrivateMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private FromUserToUserPrivateMessageJoin fromUserToUserPrivateMessageJoin;
	private String message;
	private Date timestamp;
	
	public PrivateMessage(int id, String message, Date timestamp, FromUserToUserPrivateMessageJoin fromUserToUserPrivateMessageJoin) {
		this.id = id;
		this.fromUserToUserPrivateMessageJoin = fromUserToUserPrivateMessageJoin;
		this.message = message;
		this.timestamp = timestamp;
	}

	public PrivateMessage() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public FromUserToUserPrivateMessageJoin getFromUserToUserPrivateMessageJoin() {
		return fromUserToUserPrivateMessageJoin;
	}

	public void setFromUserToUserPrivateMessageJoin(
			FromUserToUserPrivateMessageJoin fromUserToUserPrivateMessageJoin) {
		this.fromUserToUserPrivateMessageJoin = fromUserToUserPrivateMessageJoin;
	}
	
	

}

package se.kth.model.bo;

import java.io.Serializable;

public class FromUserToUserPrivateMessageJoin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PrivateMessage privateMessage;
	private User toUser;
	private User fromUser;
	
	public FromUserToUserPrivateMessageJoin() {
		
	}
	
	public FromUserToUserPrivateMessageJoin(PrivateMessage privateMessage, User toUser, User fromUser) {
		this.privateMessage = privateMessage;
		this.toUser = toUser;
		this.fromUser = fromUser;
	}

	public PrivateMessage getPrivateMessage() {
		return privateMessage;
	}

	public void setPrivateMessage(PrivateMessage privateMessage) {
		this.privateMessage = privateMessage;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	

}

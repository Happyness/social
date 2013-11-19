package se.kth.frontend.handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import se.kth.common.Converter;
import se.kth.common.PrivateMessageResource;
import se.kth.common.PrivateMessagesResource;
import se.kth.common.UserResource;
import se.kth.common.UsersResource;
import se.kth.common.model.bo.PrivateMessage;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserLogMessage;
import se.kth.common.model.bo.UserProfile;
import se.kth.frontend.handler.security.TokenSession;
import se.kth.frontend.handler.service.PrivateMessageService;

@ManagedBean
@SessionScoped
public class PrivateMessagesHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String to_id;
	private String message;
	private String toUserSelect;
	private String response;
	private List<PrivateMessage> messagesToUser;
 
	public List<User> toUser;
	
    @ManagedProperty(value = "#{tokenSession}")
    private TokenSession tokenSession;
    private UserProfile profile;
    
    public void setTokenSession(TokenSession tokenSession)
    {
    	this.tokenSession = tokenSession;
    }
    
    public TokenSession getTokenSession()
    {
    	return tokenSession;
    }
    
    public List<PrivateMessage> getMessagesToUser() throws IOException
    {
    	if (tokenSession.getProfile() != null) {
    		int id = tokenSession.getProfile().getUserProfileId();
    		PrivateMessagesResource pmr = ClientHandler.getObjectResource("/messages/" + id, PrivateMessagesResource.class);
    		Representation jsonRep = new JsonRepresentation(pmr.getMessages());
    		messagesToUser = Converter.fromJsonToList(jsonRep.getText(), new TypeToken<List<PrivateMessage>>() {}.getType());
    		
    		//messagesToUser = ur.getMessages();
    		//messagesToUser = new PrivateMessageService().getMessagesToUser();
    		//response += tokenSession.getProfile().getUserProfileId();
    	} else {
    		messagesToUser = new ArrayList<PrivateMessage>();
    	}
    	
		return messagesToUser;
    }

	public List<User> getToUser() throws IOException
	{
		List<User> users = null;
		
		UsersResource ur = ClientHandler.getObjectResource("/users", UsersResource.class);
		Representation jsonRep = new JsonRepresentation(ur.getUsers());
		users = Converter.fromJsonToList(jsonRep.getText(), new TypeToken<List<User>>() {}.getType());

		int id = -1;
		if (tokenSession.getProfile() != null) {
			id = tokenSession.getProfile().getUserProfileId();
		}
		
		if (users != null && users.size() > 1) {
			toUser = new ArrayList<User>();
			
			for (int i = 0; i < users.size(); i++) {
				User u = users.get(i);
				
				if (u.getUserId() != id) {
					toUser.add(u);
				}
			}
		}
 
		return toUser;
	}
	
	public String save()
	{
		
		return ClientHandler.server + "/message/" + toUserSelect;
		//PrivateMessageResource pmr = ClientHandler.getObjectResource("/message/" + toUserSelect,
		//		PrivateMessageResource.class);
    	//pmr.sendMessage(new Representation(""));
    	
		/*
		PrivateMessageService pmh = new PrivateMessageService();
		UserProfile profile = tokenSession.getProfile();
		
		if (profile != null) {
			setResponse(pmh.createMessage(Integer.parseInt(toUserSelect), profile.getUserProfileId(), message));
		} else {
			setResponse("No profile available");
		}*/
	}

	public String getTo_id()
	{
		return to_id;
	}

	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToUserSelect() {
		return toUserSelect;
	}

	public void setToUserSelect(String toUserSelect) {
		this.toUserSelect = toUserSelect;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
}

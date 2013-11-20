package se.kth.frontend.handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import com.google.gson.reflect.TypeToken;

import se.kth.common.Converter;
import se.kth.common.WallResource;
import se.kth.common.model.bo.UserLogMessage;
import se.kth.common.model.bo.UserProfile;
import se.kth.frontend.handler.security.TokenSession;

@ManagedBean
@SessionScoped
public class WallHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String message;
	private String response;
	private int id = -1;
	
    @ManagedProperty(value = "#{tokenSession}")
    private TokenSession tokenSession;
    private UserProfile profile;
    
    public void load()
    {
		Map<String, String> map = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String id = map.get("id");
		
		if (id != null) {
			this.id = Integer.parseInt(id);
		} else {
	    	UserProfile profile = tokenSession.getProfile();
	    	if (profile != null) {
	    		this.id = profile.getUserProfileId();
	    	}
		}
    }
    
    public void setTokenSession(TokenSession tokenSession)
    {
    	this.tokenSession = tokenSession;
    }
    
    public TokenSession getTokenSession()
    {
    	return tokenSession;
    }
    
    public boolean getMyOwn()
    {
    	UserProfile profile = tokenSession.getProfile();
    	if (profile != null) {
    		return (profile.getUserProfileId() == id);
    	}
    	return false;
    }
    
    public List<UserLogMessage> getMessagesByUser()
    {
    	List<UserLogMessage> messages = new ArrayList<UserLogMessage>();
		System.out.println("DEBUG id: " + this.id);
    	if (this.id > 0) {
    		Representation wr = ClientHandler.getNormalResource("/wall/" + this.id);
    		JsonRepresentation jsonRep;
			try {
				jsonRep = new JsonRepresentation(wr);
				messages = Converter.fromJsonToList(jsonRep.getText(), new TypeToken<List<UserLogMessage>>() {}.getType());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			ClientHandler.releaseResource(wr);
    	}
    	
    	return messages;
    }
	
	public void save()
	{
    	String jsonString = Converter.toJson(message);
    	JsonRepresentation jsonRep = new JsonRepresentation(jsonString);
    	System.out.println("Sending: " + jsonString);
    	WallResource wr = ClientHandler.getObjectResource("/wall/" + getTokenSession().getProfile().getUserProfileId(), WallResource.class);
		
    	try {
			setResponse(wr.postUserLogMessage(jsonRep).getText());
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
    	
    	ClientHandler.releaseResource(jsonRep);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

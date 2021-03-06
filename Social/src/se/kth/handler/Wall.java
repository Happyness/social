package se.kth.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Transaction;

import se.kth.handler.security.TokenSession;
import se.kth.model.PrivateMessageHandler;
import se.kth.model.UserLogHandler;
import se.kth.model.bo.PrivateMessage;
import se.kth.model.bo.User;
import se.kth.model.bo.UserLogMessage;
import se.kth.model.bo.UserProfile;
import se.kth.model.dao.UserDao;
import se.kth.resource.HibernateUtil;

@ManagedBean
@SessionScoped
public class Wall implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String message;
	private String response;
	private List<UserLogMessage> messagesByUser;
	private int id;
	
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
    	if (id > 0) {
    		messagesByUser = new UserLogHandler().getMessagesByUser(id);
    	} else if (tokenSession.getProfile() != null) {
    		messagesByUser = new UserLogHandler().getMessagesByUser(tokenSession.getProfile().getUserProfileId());
    	} else {
    		messagesByUser = new ArrayList<UserLogMessage>();
    	}
    	
    	return messagesByUser;
    }
	
	public void save()
	{
		UserLogHandler ulh = new UserLogHandler();
		setResponse(ulh.createMessage(tokenSession.getProfile().getUserProfileId(), message));
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

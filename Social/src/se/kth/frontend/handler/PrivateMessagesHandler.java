package se.kth.frontend.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.hibernate.Transaction;

import se.kth.backend.model.bo.PrivateMessage;
import se.kth.backend.model.bo.User;
import se.kth.backend.model.bo.UserProfile;
import se.kth.backend.model.dao.UserDao;
import se.kth.backend.resource.HibernateUtil;
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
    
    public List<PrivateMessage> getMessagesToUser()
    {	
    	if (tokenSession.getProfile() != null) {
    		messagesToUser = new PrivateMessageService().getMessagesToUser(tokenSession.getProfile().getUserProfileId());
    		//response += tokenSession.getProfile().getUserProfileId();
    	} else {
    		messagesToUser = new ArrayList<PrivateMessage>();
    	}
    	
    	return messagesToUser;
    }
 
	public List<User> getToUser()
	{
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		List<User> users = new UserDao().getUsers();
		trans.commit();
		
		int id = -1;
		if (tokenSession.getProfile() != null) {
			id = tokenSession.getProfile().getUserProfileId();
		}
		
		if (users.size() > 1) {
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
	
	public void save()
	{
		PrivateMessageService pmh = new PrivateMessageService();
		UserProfile profile = tokenSession.getProfile();
		
		if (profile != null) {
			setResponse(pmh.createMessage(Integer.parseInt(toUserSelect), profile.getUserProfileId(), message));
		} else {
			setResponse("No profile available");
		}
	}

	public String getTo_id() {
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

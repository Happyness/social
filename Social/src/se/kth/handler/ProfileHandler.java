package se.kth.handler;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Transaction;

import se.kth.handler.security.TokenSession;
import se.kth.model.bo.User;
import se.kth.model.bo.UserProfile;
import se.kth.model.dao.UserDao;
import se.kth.resource.BaseResource;
import se.kth.resource.HibernateUtil;
import se.kth.resource.UserResource;
import se.kth.resource.UserServerResource;

@ManagedBean
@SessionScoped
public class ProfileHandler extends ClientHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String surName;
	private String email;
	private String dob;
	
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
	
	public String load()
	{
		Map<String, String> map = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		UserProfile profile = new UserProfile();
		String id = map.get("id");
		
	    if(id != null) {
			UserResource ur = ClientHandler.getObjectResource("/user/" + id, UserResource.class);
			
			if (ur != null) {
				System.out.println(ur.getUser().getUsername());
			}
			/*
	    	Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
	    	User user = new UserDao().getUser(Integer.parseInt(id));
	    	trans.commit();
	    	*/
	    	
	    	if (ur.getUser() != null) {
		    	UserProfile up = ur.getUser().getUserProfile();
		    	if (up != null)
		    		profile = up;
	    	}
	    } else if (tokenSession.getProfile() != null) {
	    	profile = tokenSession.getProfile();
	    }
	    
    	setSurName(profile.getSurname());
    	setFirstName(profile.getFirstName());
    	setEmail(profile.getEmail());
    	
    	if (profile.getDateOfBirth() != null)
    		setDob(profile.getDateOfBirth().toString());
    	
    	return "";
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}

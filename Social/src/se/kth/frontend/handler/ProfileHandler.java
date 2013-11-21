package se.kth.frontend.handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.restlet.ext.json.JsonRepresentation;

import se.kth.common.Converter;
import se.kth.common.ProfileResource;
import se.kth.common.model.bo.UserProfile;
import se.kth.frontend.handler.security.TokenSession;


/**
 * @author Mats Maatson and Joel Denke
 * @description Bean to handle profile page
 */
@ManagedBean
@SessionScoped
public class ProfileHandler extends ClientHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String surName;
	private String email;
	private Date dob;
	
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
			ProfileResource ur = ClientHandler.getObjectResource("/user/profile/" + id, ProfileResource.class);
	    	try {
	    		JsonRepresentation jsonRep = new JsonRepresentation(ur.getProfile()); 
				profile = Converter.fromJson(jsonRep.getText(), UserProfile.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    } else if (tokenSession.getProfile() != null) {
	    	profile = tokenSession.getProfile();
	    }
	    
    	setSurName(profile.getSurname());
    	setFirstName(profile.getFirstName());
    	setEmail(profile.getEmail());
    	
    	if (profile.getDateOfBirth() != null)
    		setDob(profile.getDateOfBirth());
    	
    	return "";
	}
	
	public UserProfile getProfile()
	{
		return this.profile;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
}

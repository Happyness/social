package se.kth.frontend.handler.security;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import se.kth.common.model.bo.UserProfile;

/**
 * @author Mats Maatson and Joel Denke
 * @description Token session bean to store profile, active session and if admin or not.
 */
@ManagedBean
@SessionScoped
public class TokenSession implements Serializable
{
	private static final long serialVersionUID = 2331221200478427979L;
	private boolean authorized = false;
	private boolean isAdmin = false;
	private UserProfile profile;
	
	public boolean getAuthorized() {
		return authorized;
	}
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	public UserProfile getProfile() {
		return profile;
	}
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}

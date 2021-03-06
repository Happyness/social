package se.kth.handler.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import se.kth.model.UserHandler;
import se.kth.resource.SecurityUtils;

@ManagedBean
@SessionScoped
public class Authentication implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String response;
	
    @ManagedProperty(value = "#{tokenSession}")
    private TokenSession tokenSession;
    
    public void setTokenSession(TokenSession tokenSession)
    {
    	this.tokenSession = tokenSession;
    }
    
    public TokenSession getTokenSession()
    {
    	return tokenSession;
    }
	
	public String getResponse()
	{
		return response;
	}
	
	public void setResponse(String response)
	{
		this.response = response;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String doLogin()
	{
		UserHandler uh = new UserHandler();
		
		if (username.equals("admin") && password.equals("admin"))
		{
			response = "You are now logged in as admin";
			tokenSession.setAuthorized(true);
			tokenSession.setIsAdmin(true);
			
			clearForm();
			return "/index?faces-redirect=true";
		}
		
		if (uh.login(username, password)) {
			response = "Valid username and password";
			tokenSession.setAuthorized(true);
			tokenSession.setProfile(uh.getUser().getUserProfile());
			tokenSession.setIsAdmin(false);
			
			clearForm();
			return "/index?faces-redirect=true";
		} else {
			response = "Invalid credentials provided! " + username + " : " + SecurityUtils.getHash(password);
			return "";
		}
	}
	
	public void clearForm()
	{
		username = null;
		password = null;
	}
	
	  public String doLogout()
	  {
		  	response = "";
		    tokenSession.setAuthorized(false);
		    return "/index";
	  }
}

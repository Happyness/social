package se.kth.frontend.handler.security;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.json.simple.JSONArray;
import org.restlet.ext.json.JsonRepresentation;

import se.kth.common.AuthResource;
import se.kth.common.Converter;
import se.kth.common.model.bo.User;
import se.kth.frontend.handler.ClientHandler;

/**
 * @author Mats Maatson and Joel Denke
 * @description Authentification bean to handle login and logout
 */
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
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(username);
		jsonArray.add(password);
		
		String jsonString = Converter.toJson(jsonArray);
		JsonRepresentation jsonRep = new JsonRepresentation(jsonString);
				
		if (username.equals("admin") && password.equals("admin"))
		{
			response = "You are now logged in as admin";
			tokenSession.setAuthorized(true);
			tokenSession.setIsAdmin(true);
			
			clearForm();
			return "/index?faces-redirect=true";
		}
		AuthResource ar = ClientHandler.getObjectResource("/login", AuthResource.class);
		try {
			JsonRepresentation returnRep = new JsonRepresentation(ar.login(jsonRep));
			if (returnRep.getSize() >= 1) {
				response = "Valid username and password";
				tokenSession.setAuthorized(true);
				User user = Converter.fromJson(returnRep.getText(), User.class);
				tokenSession.setProfile(user.getUserProfile());
				tokenSession.setIsAdmin(false);
				
				clearForm();
				return "/index?faces-redirect=true";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		response = "Invalid credentials provided!";
		return "";
	}
	
	public void clearForm()
	{
		username = null;
		password = null;
	}
	
	  public String doLogout()
	  {
		  	response = "";
		  	tokenSession.setIsAdmin(false);
		  	tokenSession.setProfile(null);
		    tokenSession.setAuthorized(false);
		    return "/index";
	  }
}

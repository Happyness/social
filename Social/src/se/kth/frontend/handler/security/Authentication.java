package se.kth.frontend.handler.security;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.restlet.ext.json.JsonRepresentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.kth.backend.resource.SecurityUtils;
import se.kth.common.AuthResource;
import se.kth.common.WallResource;
import se.kth.common.model.bo.User;
import se.kth.frontend.handler.ClientHandler;
import se.kth.frontend.handler.service.UserService;

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
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(username);
		jsonArray.add(password);
		
		String jsonString = gson.toJson(jsonArray);
		
		JsonRepresentation jsonRep = new JsonRepresentation(jsonString);
		
//		UserService uh = new UserService();
//		
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
				User user = gson.fromJson(returnRep.getText(), User.class);
				tokenSession.setProfile(user.getUserProfile());
				tokenSession.setIsAdmin(false);
				
				clearForm();
				return "/index?faces-redirect=true";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		if (uh.login(username, password)) {
//			response = "Valid username and password";
//			tokenSession.setAuthorized(true);
//			tokenSession.setProfile(uh.getUser().getUserProfile());
//			tokenSession.setIsAdmin(false);
//			
//			clearForm();
//			return "/index?faces-redirect=true";
//		} else {
//			response = "Invalid credentials provided! " + username + " : " + SecurityUtils.getHash(password);
//			return "";
//		}

		response = "Invalid credentials provided! " + username + " : " + SecurityUtils.getHash(password);
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
		    tokenSession.setAuthorized(false);
		    return "/index";
	  }
}

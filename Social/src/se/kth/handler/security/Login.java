package se.kth.handler.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import se.kth.handler.UserHandler;
import se.kth.resource.SecurityUtils;

@ManagedBean
@SessionScoped
public class Login implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String response;
	
    private String originalURL;
    
    public Login()
    {
    }
    
    @PostConstruct
    public void init() {
        ExternalContext    externalContext     = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request             = (HttpServletRequest) externalContext.getRequest();
        String             forwardedRequestURI = request.getRequestURI();
        
        if ((this.originalURL = request.getParameter("originalURL")) != null) {
            // If the user was redirected, retrieve the originalURL from the request's "originalURL" parameter
            return;
        
        } else if (forwardedRequestURI == null) { 
            // If the user logged in directly from the top bar, simply redirect to the originalURL recorded by UserSessionBean
            this.originalURL = tokenSession.getOriginalURL();
            if (originalURL == null) {
                // Redirect to home page in case the user didn't surf any pages before logging in
                this.originalURL = request.getContextPath() + "index.xhtml";
            }
            
        } else {
            // If the user was forwarded to the login page, re-build the orignal requestURL
            this.originalURL     = forwardedRequestURI;
            String originalQuery = request.getQueryString();
     
            if (originalQuery != null) {
                this.originalURL += "?" + originalQuery;
            }
        }
    }
	
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
		
		if (uh.login(username, password)) {
			response = "Valid username and password";
			tokenSession.setAuthorized(true);
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(originalURL);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "index";
		} else {
			response = "Invalid credentials provided! " + username + " : " + SecurityUtils.getHash(password);
			return "login";
		}
	}
}

package se.kth.handler.security;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TokenSession implements Serializable
{
	private static final long serialVersionUID = 2331221200478427979L;
	private boolean authorized = false;
    private String  originalURL;
    
    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
    
    public String getOriginalURL() {
        return originalURL;
    }
	
	public boolean getAuthorized() {
		return authorized;
	}
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
}

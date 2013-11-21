package se.kth.frontend.handler.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Mats Maatson and Joel Denke
 * @description Servlet filter which check if you are logged in or not and redirect if required
 */
public class UserAuthFilter implements Filter
{
	  private FilterConfig config;

	  public void doFilter(ServletRequest req, ServletResponse resp,
	      FilterChain chain) throws IOException, ServletException {
		  
		  HttpServletRequest request = (HttpServletRequest) req;
		  HttpSession session = ((HttpServletRequest) req).getSession(false);
		  TokenSession token = (session != null) ? (TokenSession) session.getAttribute("tokenSession") : null;
	    if (token != null && token.getAuthorized() == false) {
	    	if (!request.getRequestURI().contains("login")) {
	    		((HttpServletResponse) resp).sendRedirect("/Social/login.xhtml");
	    	}
	    } else {
	      chain.doFilter(req, resp);
	    }
	  }

	  public void init(FilterConfig config) throws ServletException {
	    this.setConfig(config);
	  }

	  public void destroy() {
	    setConfig(null);
	  }

		public FilterConfig getConfig() {
			return config;
		}
	
		public void setConfig(FilterConfig config) {
			this.config = config;
		}
	}

package se.kth.handler.security;

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

public class AdminAuthFilter implements Filter
{
	  private FilterConfig config;

	  public void doFilter(ServletRequest req, ServletResponse resp,
	      FilterChain chain) throws IOException, ServletException {
		  
		  HttpServletRequest request = (HttpServletRequest) req;
		  HttpSession session = ((HttpServletRequest) req).getSession(false);
		  TokenSession token = (session != null) ? (TokenSession) session.getAttribute("tokenSession") : null;
	    if (token != null && (token.getAuthorized() == false || token.getIsAdmin() == false)) {
	    	if (!request.getRequestURI().contains("index")) {
	    		((HttpServletResponse) resp).sendRedirect("/Social/index.xhtml");
	    	}
	    } else {
	      chain.doFilter(req, resp);
	    }
	  }

	  public void init(FilterConfig config) throws ServletException {
	    this.config = config;
	  }

	  public void destroy() {
	    config = null;
	  }
	}

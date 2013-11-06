package se.kth;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Session;

import se.kth.resource.HibernateUtil;

@ManagedBean
@SessionScoped
public class Application
{
	private Session session;
	
	public Application()
	{
	}
	
	public Session getSession()
	{
		return session;
	}
	
	public void closeSession()
	{
		session.close();
	}
}

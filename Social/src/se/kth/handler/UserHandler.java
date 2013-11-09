package se.kth.handler;

import java.io.Serializable;
import java.util.Date;

import se.kth.model.bo.User;
import se.kth.model.bo.UserProfile;
import se.kth.model.dao.UserDao;
import se.kth.resource.HibernateUtil;
import se.kth.resource.SecurityUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Transaction;

@ManagedBean
@SessionScoped
public class UserHandler implements Serializable
{
	private UserDao userDao;
	private User user;
	private static final long serialVersionUID = 1L;
	private String response;
	
	public UserHandler()
	{
		super();
		userDao = new UserDao();
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public String createUser(String username, String password, UserProfile profile)
	{
		User tmp = new User();
		tmp.setUsername(username);
		
		Transaction trans = null;
		
		try {
			trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			User user = userDao.getUser(tmp);
			
			if (user == null) {
				tmp.setPassword(SecurityUtils.getHash(password));
				tmp.setTimestamp(new Date());
				tmp.setUserProfile(profile);
				userDao.addUser(tmp);
				trans.commit();
				return "User successfully was created in database.";
			} else {
				trans.commit();
				return "User does already exist in database.";
			}
		} catch (RuntimeException e) {
			 HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			 return "User creation failed: " + e.getMessage();
		}
	}
	
	public boolean login(String username, String password)
	{
		User tmp = new User();
		tmp.setUsername(username);
		tmp.setPassword(SecurityUtils.getHash(password));
		
		Transaction trans = null;
				
		try {
			trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			
			User user = userDao.getUser(tmp);
			trans.commit();
		
			if (user != null) {
				setUser(user);
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			 return false;
		}
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}

package se.kth.handler;

import java.io.Serializable;
import java.util.Date;

import se.kth.Application;
import se.kth.resource.HibernateUtil;
import se.kth.resource.Security;
import se.kth.model.bo.User;
import se.kth.model.dao.UserDao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Transaction;

@ManagedBean
@SessionScoped
public class UserHandler extends Application implements Serializable
{
	private UserDao userDao;
	private User user;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserHandler()
	{
		super();
		userDao = new UserDao();
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(int id)
	{
		user = userDao.getUser(id);
	}
	
	public void createUser(String username, String password) throws Exception
	{
		User tmp = new User();
		tmp.setUsername(username);
		tmp.setPassword(password);
		tmp.setTimestamp(new Date());
		
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		User user = userDao.getUser(tmp);
		
		if (user == null) {
			userDao.addUser(user);
		} else {
			throw new Exception("User already exist!");
		}
		
		trans.commit();
	}
	
	public boolean login(String username, String password)
	{
		User tmp = new User();
		tmp.setUsername(username);
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		User user = userDao.getUser(tmp);
		trans.commit();
		
		if (user != null && user.getPassword() == Security.getHash(password)) {
			return true;
		} else {
			return false;
		}
	}
}

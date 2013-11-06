package se.kth.handler;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import se.kth.resource.HibernateUtil;
import se.kth.resource.Security;
import se.kth.model.bo.User;
import se.kth.model.dao.UserDao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Transaction;

@ManagedBean
@SessionScoped
public class UserHandler implements Serializable
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
	
	public void setUser(User user)
	{
		this.user = user;
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
		
		try {
			if (user != null && user.getPassword() == Security.getHash(password)) {
				setUser(user);
				return true;
			} else {
				return false;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
	}
}

package se.kth.handler;

import java.io.Serializable;
import java.util.Date;

import se.kth.model.bo.User;
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
	
	private String username;
	private String password;
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
	
	public void createUser()
	{
		User tmp = new User();
		tmp.setUsername(username);
		tmp.setPassword(SecurityUtils.getHash(password));
		tmp.setTimestamp(new Date());
		
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		User user = userDao.getUser(tmp);
		
		if (user == null) {
			userDao.addUser(tmp);
			response = "User is created!";
		} else {
			response = "User already exist!";
		}
		
		trans.commit();
	}
	
	public boolean login(String username, String password)
	{
		User tmp = new User();
		tmp.setUsername(username);
		tmp.setPassword(SecurityUtils.getHash(password));
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		User user = userDao.getUser(tmp);
		trans.commit();
		
			if (user != null) {
				setUser(user);
				return true;
			} else {
				return false;
			}
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

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}

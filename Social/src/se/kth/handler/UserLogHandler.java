package se.kth.handler;

import se.kth.model.bo.User;
import se.kth.model.dao.UserDao;

public class UserLogHandler {
	private UserLogDao userLogDao;
	private UserLog userLog;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserLogHandler()
	{
		super();
		userLogDao = new UserLogDao();
	}
	
	public UserLog getUserLog()
	{
		return userLog;
	}
	
	public void setUserLog(int id)
	{
		userLog = userLogDao.getLog(id);
	}
}

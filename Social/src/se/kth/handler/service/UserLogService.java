package se.kth.handler.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Transaction;

import se.kth.model.bo.PrivateMessage;
import se.kth.model.bo.User;
import se.kth.model.bo.UserLogMessage;
import se.kth.model.dao.UserDao;
import se.kth.model.dao.UserLogMessageDao;
import se.kth.resource.HibernateUtil;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;

public class UserLogService implements Serializable
{
	private UserLogMessageDao logMsgDao;
	private static final long serialVersionUID = 1L;
	private String response;
	
	public UserLogService()
	{
		logMsgDao = new UserLogMessageDao();
	}

	public List<UserLogMessage> getMessagesByUser(int id)
	{
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		List<UserLogMessage> messages = logMsgDao.getUserLogMessagesFrom(new UserDao().getUser(id));
		trans.commit();
		return messages;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String createMessage(int id, String message)
	{
		Transaction trans = null;
		
		try {
			trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			User user = new UserDao().getUser(id);
			UserLogMessage ulm = new UserLogMessage();
			ulm.setUser(user);
			ulm.setMessage(message);
			ulm.setMessageSent(new Date());
			
			logMsgDao.addUserLogMessage(ulm);
			trans.commit();
			return "Private message successfully sent";
		} catch (RuntimeException e) {
			 HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			 return "Send new private message failed: " + e.getMessage();
		}
	}
}

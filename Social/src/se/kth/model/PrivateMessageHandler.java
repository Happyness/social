package se.kth.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import se.kth.model.bo.PrivateMessage;
import se.kth.model.bo.User;
import se.kth.model.dao.PrivateMessageDao;
import se.kth.model.dao.UserDao;
import se.kth.resource.HibernateUtil;

import org.hibernate.Transaction;

public class PrivateMessageHandler implements Serializable
{
	private PrivateMessageDao privateMsgDao;
	private static final long serialVersionUID = 1L;
	private String response;
	
	public PrivateMessageHandler()
	{
		privateMsgDao = new PrivateMessageDao();
	}
	
	public List<PrivateMessage> getMessagesToUser(int to_id)
	{
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		List<PrivateMessage> messages = privateMsgDao.getPrivateMessagesTo(new UserDao().getUser(to_id));
		trans.commit();
		return messages;
	}
	
	public String createMessage(int to_id, int from_id, String message)
	{
		Transaction trans = null;
		
		try {
			trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			User to_user = new UserDao().getUser(to_id);
			User from_user = new UserDao().getUser(from_id);
			PrivateMessage msg = new PrivateMessage();
			msg.setUserByFromUser(from_user);
			msg.setUserByToUser(to_user);
			msg.setMessage(message);
			//msg.setTimestamp(new Date());
			
			privateMsgDao.addPrivateMessage(msg);
			trans.commit();
			return "Private message successfully sent";
		} catch (RuntimeException e) {
			 HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
			 return "Send new private message failed: " + e.getMessage();
		}
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}

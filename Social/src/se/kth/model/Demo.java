package se.kth.model;

import java.sql.Date;

import org.hibernate.Transaction;

import se.kth.model.bo.PrivateMessage;
import se.kth.model.bo.User;
import se.kth.model.dao.PrivateMessageDao;
import se.kth.model.dao.UserDao;
import se.kth.resource.HibernateUtil;

public class Demo {

	public static void main(String[] args)
	{

		UserDao userDao = new UserDao();
		PrivateMessageDao pmDao = new PrivateMessageDao();
		
		Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			User user = userDao.getUser(1);
			User user2 = userDao.getUser(3);
		tx.commit();
		PrivateMessage pm = new PrivateMessage();
		pm.setMessage("Test message 2");
		pm.setFromUser(user);
		pm.setToUser(user2);
		pm.setTimestamp(new Date(System.currentTimeMillis()));
		
		

		
		Transaction tx2 = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			pmDao.addPrivateMessage(pm);
			System.out.println("Message: " + pmDao.getPrivateMessagesFrom(user).get(0).getMessage());
			System.out.println("Message: " + pmDao.getPrivateMessagesFrom(user).get(1).getMessage());
		tx2.commit();


	}
}

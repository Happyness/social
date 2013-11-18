package se.kth.backend.model;

import java.util.Date;
import java.util.List;

import org.hibernate.Transaction;

import se.kth.backend.model.dao.PrivateMessageDao;
import se.kth.backend.model.dao.UserDao;
import se.kth.backend.resource.HibernateUtil;
import se.kth.common.model.bo.PrivateMessage;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserProfile;

public class Demo {

	public static void main(String[] args)
	{

		UserDao userDao = new UserDao();
		PrivateMessageDao pmDao = new PrivateMessageDao();

//		User user = new User();
//		user.setUsername("mats");
//		user.setPassword("pw");
//		
//		User user2 = new User();
//		user2.setUsername("joel");
//		user2.setPassword("pw");
//		
//		Transaction tx3 = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
//			userDao.addUser(user);
//			userDao.addUser(user2);
//		tx3.commit();
		
		Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			User user = userDao.getUser(5);
			User user2 = userDao.getUser(6);
		tx.commit();
		PrivateMessage pm = new PrivateMessage();
		pm.setMessage("Test2 PM!");
		pm.setFromUser(user);
		pm.setToUser(user2);
		

		Transaction tx2 = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			pmDao.addPrivateMessage(pm);
			List<PrivateMessage> pmList = pmDao.getPrivateMessagesFrom(user);
		tx2.commit();
		
		for (PrivateMessage item : pmList) {
			System.out.println("Message sent: " + item.getMessageSent().toString());
			System.out.println("Message to: " + item.getToUser().getUsername());
			System.out.println("Message from: " + item.getFromUser().getUsername());
			System.out.println("Message: " + item.getMessage());
		}

	}
}

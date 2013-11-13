package se.kth.model;

import java.util.Date;
import java.util.List;

import org.hibernate.Transaction;

import se.kth.model.bo.PrivateMessage;
import se.kth.model.bo.User;
import se.kth.model.bo.UserProfile;
import se.kth.model.dao.PrivateMessageDao;
import se.kth.model.dao.UserDao;
import se.kth.resource.HibernateUtil;

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
			User user = userDao.getUser(3);
			User user2 = userDao.getUser(4);
		tx.commit();
		PrivateMessage pm = new PrivateMessage();
		pm.setMessage("!!!!!TESTING JOIN FULL MESSAGE!!!!");
		pm.setUserByFromUser(user);
		pm.setUserByToUser(user2);
		

		Transaction tx2 = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			pmDao.addPrivateMessage(pm);
			List<PrivateMessage> pmList = pmDao.getPrivateMessagesFrom(user);
		tx2.commit();
		
		for (PrivateMessage item : pmList) {
			System.out.println("Message to: " + item.getUserByToUser().getUsername());
			System.out.println("Message from: " + item.getUserByFromUser().getUsername());
			System.out.println("Message to: " + item.getMessage());
		}

	}
}

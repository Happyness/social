package se.kth.model;

import java.sql.Date;
import java.util.List;

import org.hibernate.Transaction;

import se.kth.model.bo.FromUserToUserPrivateMessageJoin;
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
		FromUserToUserPrivateMessageJoin join = new FromUserToUserPrivateMessageJoin();
		pm.setMessage("TESTING JOIN");
		pm.setTimestamp(new Date(System.currentTimeMillis()));
		join.setFromUser(user);
		join.setToUser(user2);
		join.setPrivateMessage(pm);
		pm.setFromUserToUserPrivateMessageJoin(join);
		
		
		
		
		

		
		Transaction tx2 = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			pmDao.addPrivateMessage(pm);
			List<PrivateMessage> pmList = pmDao.getPrivateMessagesFrom(user);
		tx2.commit();
		
		for (PrivateMessage item : pmList) {
			System.out.println("Message: " + item.getMessage());
		}

	}
}

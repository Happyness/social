package se.kth.model;

import org.hibernate.Transaction;

import se.kth.model.bo.User;
import se.kth.model.dao.UserDao;
import se.kth.resource.HibernateUtil;

public class Demo {

	public static void main(String[] args)
	{

		UserDao userDao = new UserDao();
		
		Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			User user = userDao.getUser(1);
		tx.commit();
		System.out.println(user.getUsername());


	}
}

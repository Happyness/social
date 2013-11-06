package se.kth.model;

import org.hibernate.Transaction;

import se.kth.model.bo.User;
import se.kth.model.dao.UserDao;
import se.kth.resource.HibernateUtil;

public class Demo {

	public static void main(String[] args)
	{

		UserDao userDao = new UserDao();
		User tmp = new User();
		tmp.setUsername("jo");
		
		Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			User user = userDao.getUser(tmp);
		tx.commit();
		System.out.println(user.getUsername());


	}
}

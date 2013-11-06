package se.kth.model;

import java.util.Date;

import org.hibernate.Transaction;

import se.kth.model.bo.User;
import se.kth.model.bo.UserProfile;
import se.kth.model.dao.UserDao;
import se.kth.resource.HibernateUtil;

public class Demo {

	public static void main(String[] args)
	{

		UserDao userDao = new UserDao();
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setTimestamp(new Date());
		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName("Mats");
		userProfile.setSurname("Maatson");
		userProfile.setEmail("mats.maatson@gmail.com");
		userProfile.setDob(new Date(87, 2, 12));
		user.setUserProfile(userProfile);
		
		
		Transaction tx = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			userDao.addUser(user);
		tx.commit();


	}
}

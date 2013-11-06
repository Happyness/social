package se.kth.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import se.kth.model.bo.UserProfile;
import se.kth.resource.HibernateUtil;
import static org.hibernate.criterion.Example.create;

public class UserProfileDao {
	
	private final SessionFactory sessionFactory = this.getSessionFactory();
	
	protected SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
	
	public UserProfile getUserProfile(int id) {
		try {
			UserProfile userProfile = (UserProfile) sessionFactory.getCurrentSession().get(
					"se.kth.model.bo.User", id);
			if (userProfile == null) {
				// TODO: nothing found
			} 
			else {
				// TODO: instance found
			}
			return userProfile;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void addUserProfile(UserProfile userProfile) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(userProfile);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void deleteUserProfile(UserProfile userProfile) {
		try {
			sessionFactory.getCurrentSession().delete(userProfile);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserProfile> getUserProfiles() {
		try {
			@SuppressWarnings("unchecked")
			List<UserProfile> list = (List<UserProfile>) sessionFactory
					.getCurrentSession().createCriteria("se.kth.model.bo.UserProfile")
					.add(create(new UserProfile())).list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}

package se.kth.backend.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import se.kth.backend.resource.HibernateUtil;
import se.kth.common.model.bo.User;

public class UserDao {
	
	private final SessionFactory sessionFactory = this.getSessionFactory();
	
	protected SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
	
	public User getUser(int id) {
		try {
			User user = (User) sessionFactory.getCurrentSession().get(
					"se.kth.common.model.bo.User", id);
			if (user == null) {
				// TODO: nothing found
			} 
			else {
				// TODO: instance found
			}
			return user;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public User getUser(User user) {
		try {
			@SuppressWarnings("unchecked")
			List<User> results = (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).add(Example.create(user)).list();
			if (results.size() == 0) {
				return null;
			} 
			else {
				return results.get(0);
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void addUser(User user) {
		try {
			user.setUserAdded(new Date());
			sessionFactory.getCurrentSession().saveOrUpdate(user);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void deleteUser(User user) {
		try {
			sessionFactory.getCurrentSession().delete(user);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<User> getUsers() {
		try {
			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) sessionFactory
					.getCurrentSession().createCriteria(User.class)
					.add(Example.create(new User())).list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}

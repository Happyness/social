package se.kth.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import se.kth.model.bo.User;
import se.kth.resource.HibernateUtil;
import static org.hibernate.criterion.Example.create;

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
					"se.kth.model.bo.User", id);
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
	
	public void addUser(User user) {
		try {
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
					.getCurrentSession().createCriteria("se.kth.model.bo.User")
					.add(create(new User())).list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}

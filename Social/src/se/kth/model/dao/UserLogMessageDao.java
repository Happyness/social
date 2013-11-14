package se.kth.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;

import se.kth.model.bo.User;
import se.kth.model.bo.UserLogMessage;
import se.kth.resource.HibernateUtil;
import static org.hibernate.criterion.Example.create;

public class UserLogMessageDao {
	
	private final SessionFactory sessionFactory = this.getSessionFactory();
	
	protected SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
	
	public UserLogMessage getUserLogMessage(int id) {
		try {
			UserLogMessage ulm = (UserLogMessage) sessionFactory.getCurrentSession().get(
					"se.kth.model.bo.UserLogMessage", id);
			if (ulm == null) {
				// TODO: nothing found
			} 
			else {
				// TODO: instance found
			}
			return ulm;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings("null")
	public List<UserLogMessage> getUserLogMessagesFrom(User user) {
		try {
			List<UserLogMessage> results = null;
			List<UserLogMessage> tmp = this.getUserLogMessages();
			if (tmp.size() == 0) {
				return null;
			} 
			else {
				for (UserLogMessage item : tmp) {
					if (item.getUser().equals(user)) {
						results.add(item);
					}
				}
				return results;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void addUserLogMessage(UserLogMessage ulm) {
		try {
			ulm.setMessageSent(new Date());
			sessionFactory.getCurrentSession().saveOrUpdate(ulm);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void deleteUserLogMessage(UserLogMessage ulm) {
		try {
			sessionFactory.getCurrentSession().delete(ulm);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserLogMessage> getUserLogMessages() {
		try {
			@SuppressWarnings("unchecked")
			List<UserLogMessage> list = (List<UserLogMessage>) sessionFactory
					.getCurrentSession().createCriteria("se.kth.model.bo.UserLogMessage")
					.add(create(new UserLogMessage())).list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}

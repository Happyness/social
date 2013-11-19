package se.kth.backend.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import se.kth.backend.resource.HibernateUtil;
import se.kth.common.model.bo.PrivateMessage;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserLogMessage;
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
					"se.kth.common.model.bo.UserLogMessage", id);
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
	
	public List<UserLogMessage> getUserLogMessagesFrom(User user) {
		try {
			User u = new User();
			u.setUserId(user.getUserId());
			
			Session session = sessionFactory.getCurrentSession();
			return (List<UserLogMessage>) session.createCriteria(UserLogMessage.class)
			.add(Restrictions.eq("user", u)).list();
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
					.getCurrentSession().createCriteria("se.kth.common.model.bo.UserLogMessage")
					.add(create(new UserLogMessage())).list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}

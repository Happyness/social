package se.kth.model.dao;

import java.io.Console;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import se.kth.model.bo.PrivateMessage;
import se.kth.model.bo.User;
import se.kth.resource.HibernateUtil;
import static org.hibernate.criterion.Example.create;

public class PrivateMessageDao {
	
	private final SessionFactory sessionFactory = this.getSessionFactory();
	
	protected SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
	
	public PrivateMessage getPrivateMessage(int id) {
		try {
			PrivateMessage pm = (PrivateMessage) sessionFactory.getCurrentSession().get(
					"se.kth.model.bo.PrivateMessage", id);
			if (pm == null) {
				// TODO: nothing found
			} 
			else {
				// TODO: instance found
			}
			return pm;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	

	public List<PrivateMessage> getPrivateMessagesFrom(User user) {
		try {
			PrivateMessage pm = new PrivateMessage();
			User u = new User();
			u.setUserId(user.getUserId());
			pm.setFromUser(u);
			
			Session session = sessionFactory.getCurrentSession();
			List<PrivateMessage> results = (List<PrivateMessage>) session.createCriteria(PrivateMessage.class)
					.add(Restrictions.eq("fromUser", user)).list();
			
			if (results.size() == 0) {
				return null;
			} 
			else {
				return results;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<PrivateMessage> getPrivateMessagesTo(User user)
	{
		try {
			Session session = sessionFactory.getCurrentSession();
			
			List<PrivateMessage> results = (List<PrivateMessage>) session.createCriteria(PrivateMessage.class)
					.add(Restrictions.eq("toUser", user)).list();
			if (results.size() == 0) {
				return null;
			}
			else {
				return results;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void addPrivateMessage(PrivateMessage pm) {
		try {
			pm.setMessageSent(new Date());
			sessionFactory.getCurrentSession().saveOrUpdate(pm);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void deletePrivateMessage(PrivateMessage pm) {
		try {
			sessionFactory.getCurrentSession().delete(pm);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<PrivateMessage> getPrivateMessages() {
		try {
			@SuppressWarnings("unchecked")
			List<PrivateMessage> list = (List<PrivateMessage>) sessionFactory
					.getCurrentSession().createCriteria("se.kth.model.bo.PrivateMessage")
					.add(create(new PrivateMessage())).list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}

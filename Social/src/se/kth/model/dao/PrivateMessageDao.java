package se.kth.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

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
			pm.setFromUser(user);
			@SuppressWarnings("unchecked")
			List<PrivateMessage> results = (List<PrivateMessage>) sessionFactory.getCurrentSession().createCriteria(PrivateMessage.class).add(Example.create(pm)).list();
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
	
	public List<PrivateMessage> getPrivateMessagesTo(User user) {
		try {
			PrivateMessage pm = new PrivateMessage();
			pm.setToUser(user);
			@SuppressWarnings("unchecked")
			List<PrivateMessage> results = (List<PrivateMessage>) sessionFactory.getCurrentSession().createCriteria(PrivateMessage.class).add(Example.create(pm)).list();
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

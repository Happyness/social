package se.kth.backend.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import se.kth.backend.resource.HibernateUtil;
import se.kth.common.model.bo.PrivateMessage;
import se.kth.common.model.bo.User;
import static org.hibernate.criterion.Example.create;

/**
 * @author Mats Maatson and Joel Denke
 * @description Data access object for PrivateMessages uses Hibernate to communicate with database
 */
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
					"se.kth.common.model.bo.PrivateMessage", id);
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
			Session session = sessionFactory.getCurrentSession();
			return (List<PrivateMessage>) session.createCriteria(PrivateMessage.class)
					.add(Restrictions.eq("fromUser", user)).list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@SuppressWarnings("null")
	public List<PrivateMessage> getPrivateMessagesTo(User user)
	{
		try {
			Session session = sessionFactory.getCurrentSession();
			
			return (List<PrivateMessage>) session.createCriteria(PrivateMessage.class)
					.add(Restrictions.eq("toUser", user)).list();
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
					.getCurrentSession().createCriteria("se.kth.common.model.bo.PrivateMessage")
					.add(create(new PrivateMessage())).list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
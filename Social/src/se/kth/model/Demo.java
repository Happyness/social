package se.kth.model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Demo {

	public static void main(String[] args) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		User student = new User();
		student.setUsername("joel");
		student.setPassword("joel");
		
		session.save(student);
		session.getTransaction().commit();
		
		session.close();

	}
}

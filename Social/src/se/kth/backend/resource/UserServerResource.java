package se.kth.backend.resource;

import java.util.Date;

import org.hibernate.Transaction;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.dao.UserDao;
import se.kth.common.UserResource;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserProfile;

public class UserServerResource extends ServerResource implements UserResource
{
	String id;
	
	@Override
    public void doInit() throws ResourceException 
    {
		System.out.println("DEBUG: UsersServerResource.doInit()");
        this.id = (String) getRequestAttributes().get("userid");
    }
	
    @Override
    public User getUser()
    {
    	System.out.println("DEBUG: UserServerResource.getUser()");
    	User user = null;
    	
    	if (id != null) {
	    	Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
	    	user = new UserDao().getUser(Integer.parseInt(id));
	    	trans.commit();
    	}
    	
    	if (user instanceof User)
    		return user;
    	
        return new User();
    }

	@Override
	public Representation createUser(Representation entity)
	{	
		  Form form = new Form(entity);
		  String name = form.getFirstValue("name");
		  String surname = form.getFirstValue("surname");
		  String email = form.getFirstValue("email");
		  String username = form.getFirstValue("username");
		  String password = form.getFirstValue("password");
		  String dobYearSelect = form.getFirstValue("dobYearSelect");
		  String dobMonthSelect = form.getFirstValue("dobMonthSelect");
		  String dobDaySelect = form.getFirstValue("dobDaySelect");
		  String output = "";
		  
			UserProfile up = new UserProfile();   
			up.setFirstName(name);
			up.setSurname(surname);
			up.setEmail(email);
			
			String dobio = dobYearSelect + "-" + dobMonthSelect + "-" + dobDaySelect;
			up.setDateOfBirth(java.sql.Date.valueOf(dobio));
		  
			UserDao userDao = new UserDao();
			User tmp = new User();
			tmp.setUsername(username);
			
			Transaction trans = null;
			
			try {
				trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
				User user = userDao.getUser(tmp);
				
				if (user == null) {
					tmp.setPassword(SecurityUtils.getHash(password));
					tmp.setUserAdded(new Date());
					tmp.setUserProfile(up);
					up.setUser(tmp);
					userDao.addUser(tmp);
					trans.commit();
					output = "User successfully was created in database.";
				} else {
					trans.commit();
					output = "User does already exist in database.";
				}
			} catch (RuntimeException e) {
				 HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
				 output = "User creation failed: " + e.getMessage();
			}
		  
		  return new StringRepresentation(output, MediaType.TEXT_PLAIN);
	}
}

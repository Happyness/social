package se.kth.backend.resource;

import java.util.List;

import org.hibernate.Transaction;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.dao.UserDao;
import se.kth.common.Converter;
import se.kth.common.UserResource;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserProfile;

public class UserServerResource extends ServerResource implements UserResource
{
	String id;
	
	@Override
    public void doInit() throws ResourceException 
    {
        this.id = (String) getRequestAttributes().get("userid");
    }
	
	@Override
	public Representation get()
	{
		return new StringRepresentation("test");
	}
	
	/*
    @Override
    public User getUser()
    {
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
	public List<User> getUsers()
	{
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    	List<User> users = new UserDao().getUsers();
    	trans.commit();
    	
    	for (User u : users) {
    		System.out.println(u.getUsername());
    	}
    	
    	return users;
	}

	@Override
	public UserProfile getProfile() {
		User user = getUser();
		UserProfile profile = user.getUserProfile();
		
		if (profile != null) {
			return profile;
		}
		
		return new UserProfile();
	}*/
}

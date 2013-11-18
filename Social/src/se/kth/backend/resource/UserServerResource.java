package se.kth.backend.resource;

import org.hibernate.Transaction;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.bo.User;
import se.kth.backend.model.dao.UserDao;
import se.kth.common.UserResource;

public class UserServerResource extends ServerResource implements UserResource
{
	String id;
	
	@Override
    public void doInit() throws ResourceException 
    {
        this.id = (String) getRequestAttributes().get("userid");
    }

    @Get
    public String toString()
    {
    	return "";
    }
	
    @Get
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
}

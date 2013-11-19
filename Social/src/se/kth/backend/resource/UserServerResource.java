package se.kth.backend.resource;

import org.hibernate.Transaction;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.dao.UserDao;
import se.kth.common.UserResource;
import se.kth.common.model.bo.User;

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

}

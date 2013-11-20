package se.kth.backend.resource;


import org.hibernate.Transaction;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.dao.UserDao;
import se.kth.common.Converter;
import se.kth.common.ProfileResource;
import se.kth.common.model.bo.User;

public class ProfileServerResource extends ServerResource implements ProfileResource
{
	String id;
	
	@Override
    public void doInit() throws ResourceException 
    {
        this.id = (String) getRequestAttributes().get("userid");
    }

	@Override
	public Representation getProfile()
	{
    	User user = null;
    	
    	if (id != null) {
	    	Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
	    	user = new UserDao().getUser(Integer.parseInt(id));
	    	trans.commit();
    	}
    	
    	if (user instanceof User) {
    		String json = Converter.toJson(user.getUserProfile());
    		//System.out.println(json);
    		return new JsonRepresentation(json);
    	}
    	
        return new EmptyRepresentation();
	}
}

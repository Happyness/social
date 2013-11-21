package se.kth.backend.resource;

import java.util.List;

import org.hibernate.Transaction;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.dao.UserDao;
import se.kth.common.Converter;
import se.kth.common.UsersResource;
import se.kth.common.model.bo.User;

/**
 * @author Mats Maatson and Joel Denke
 * @description ServerResource taking care of getting list of User
 */
public class UsersServerResource extends ServerResource implements UsersResource {
	
	@Override
    public void doInit() throws ResourceException 
    {
		System.out.println("DEBUG: UserServerResource.doInit()");
    }
	
	@Override
	public Representation getUsers()
	{
		System.out.println("DEBUG: UsersServerResource.getUsers()");
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    	List<User> users = new UserDao().getUsers();
    	trans.commit();
    	
    	String jsonString = Converter.toJson(users);
    	return new JsonRepresentation(jsonString);
	}
}
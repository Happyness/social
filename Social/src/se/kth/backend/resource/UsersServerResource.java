package se.kth.backend.resource;

import java.util.List;

import org.hibernate.Transaction;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.kth.backend.model.dao.UserDao;
import se.kth.common.Converter;
import se.kth.common.UsersResource;
import se.kth.common.model.bo.User;

public class UsersServerResource extends ServerResource implements UsersResource {
	
	@Override
    public void doInit() throws ResourceException 
    {
		System.out.println("DEBUG: UserServerResource.doInit()");
    }
	
	@Override
	public Representation getUsers() {
		System.out.println("DEBUG: UsersServerResource.getUsers()");
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    	List<User> users = new UserDao().getUsers();
    	trans.commit();
    	
    	//Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    	String jsonString = Converter.toJson(users);
    	Representation jsonRep = new JsonRepresentation(jsonString);
    	
    	return jsonRep;
	}

}

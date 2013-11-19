package se.kth.backend.resource;

import java.io.IOException;

import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.kth.backend.model.dao.UserDao;
import se.kth.common.AuthResource;
import se.kth.common.model.bo.User;

public class AuthServerResource extends ServerResource implements AuthResource {
	
	UserDao ud;
	
	@Override
    public void doInit() throws ResourceException 
    {
        this.ud = new UserDao();
    }
	
	@Override
	@Post
	public Representation login(Representation entity) throws IOException {
		JsonRepresentation jsonRep = new JsonRepresentation(entity);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		
		JSONArray jsonArray = gson.fromJson(jsonRep.getText(), JSONArray.class);
		
		User tmp = new User();
		tmp.setUsername((String) jsonArray.get(0));
		//tmp.setPassword(SecurityUtils.getHash(password));
		
		Transaction trans = null;
				
		try {
			trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			
			User user = new UserDao().getUser(tmp);
			trans.commit();
		
			// Funkar inte med Hibernate att skicka med password, den lyckas inte matcha SHA256 av någon anledning
			if (user != null && user.getPassword().equals(SecurityUtils.getHash((String) jsonArray.get(1)))) {
				String jsonString = gson.toJson(user);
				Representation returnRep = new JsonRepresentation(jsonString);
				return returnRep;
			} else {
				return new EmptyRepresentation();
			}
		} catch (RuntimeException e) {
			 return new EmptyRepresentation();
		}
	}

}

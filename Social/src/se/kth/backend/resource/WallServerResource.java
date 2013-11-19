package se.kth.backend.resource;

import java.io.IOException;
import java.util.List;

import org.hibernate.Transaction;
import org.json.JSONException;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.kth.backend.model.dao.UserDao;
import se.kth.backend.model.dao.UserLogMessageDao;
import se.kth.common.WallResource;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserLogMessage;

public class WallServerResource extends ServerResource implements WallResource {
	
	String id;
	UserLogMessageDao ulmd;
	UserDao ud;
	
	@Override
    public void doInit() throws ResourceException {
		System.out.println("DEBUG: WallServerResource.doInit()");
        this.id = (String) getRequestAttributes().get("userid");
        ulmd = new UserLogMessageDao();
        ud = new UserDao();
    }

	@Override
	@Get
	public Representation getUserLogMessages() {
		System.out.println("DEBUG: WallServerResource.getUserLogMessages()");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		List<UserLogMessage> messages = null;
		String jsonString = "";
    	
    	if (id != null) {
	    	Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    		messages  = ulmd.getUserLogMessagesFrom(ud.getUser(Integer.parseInt(id)));
	    	trans.commit();
    	}
    	
    	if (messages == null) {
    		return new EmptyRepresentation();
    	}
    	
    	
    	jsonString = gson.toJson(messages);
    	
    	System.out.println(jsonString);

		Representation rep = new JsonRepresentation(jsonString);
        return rep;
	}

	@Override
	@Post
	public Representation postUserLogMessage(Representation entity) throws JSONException, IOException {

		JsonRepresentation jsonRep = new JsonRepresentation(entity);
		Gson gson = new Gson();
		
		String output = "";
		String msg = gson.fromJson(jsonRep.getText(), String.class);
		if (id != null) {
			Transaction tx = null;
			
			try {
				tx = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
				User user = ud.getUser(Integer.parseInt(id));
				UserLogMessage ulm = new UserLogMessage();
				ulm.setMessage(msg);
				ulm.setUser(user);
				ulmd.addUserLogMessage(ulm);
				tx.commit();
				
				output = "Wall message successfully sent";
			} catch (RuntimeException e) {
				HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
                output = "Sending wall message failed: " + e.getMessage();
			}
		}
		else {
			output = "Cannot send wall message without id";
		}
		
		return new StringRepresentation(output, MediaType.TEXT_PLAIN);
		
	}

}

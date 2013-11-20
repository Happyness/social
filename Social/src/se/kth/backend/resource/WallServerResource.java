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
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;


import se.kth.backend.model.dao.UserDao;
import se.kth.backend.model.dao.UserLogMessageDao;
import se.kth.common.Converter;
import se.kth.common.WallResource;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserLogMessage;

public class WallServerResource extends ServerResource implements WallResource {
	
	int id = -1;
	UserLogMessageDao ulmd;
	UserDao ud;
	
	@Override
    public void doInit() throws ResourceException
    {
		System.out.println("DEBUG: WallServerResource.doInit()");
        String data = (String) getRequestAttributes().get("userid");
        if (data != null) {
        	this.id = Integer.parseInt(data);
        }
        ulmd = new UserLogMessageDao();
        ud = new UserDao();
    }

	@Override
	public Representation getUserLogMessages() {
		System.out.println("DEBUG: WallServerResource.getUserLogMessages() with <userid> : " + id);
    	
    	if (id > 0) {
	    	Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
	    	User user = new UserDao().getUser(id);
	    	List<UserLogMessage> messages  = ulmd.getUserLogMessagesFrom(user);
	    	trans.commit();
	    	
	    	String jsonString = Converter.toJson(messages);
	    	System.out.println(jsonString);
	    	return new JsonRepresentation(jsonString);
    	}
    	
    	return new EmptyRepresentation();
	}

	@Override
	public Representation postUserLogMessage(Representation entity) throws JSONException, IOException {

		JsonRepresentation jsonRep = new JsonRepresentation(entity);
		
		String output = "";
		String msg = Converter.fromJson(jsonRep.getText(), String.class);
		if (id > 0) {
			Transaction tx = null;
			
			try {
				tx = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
				User user = ud.getUser(id);
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
package se.kth.backend.resource;

import java.util.List;

import org.hibernate.Transaction;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.dao.PrivateMessageDao;
import se.kth.backend.model.dao.UserDao;
import se.kth.common.Converter;
import se.kth.common.PrivateMessagesResource;
import se.kth.common.model.bo.PrivateMessage;
import se.kth.common.model.bo.User;

/**
 * @author Mats Maatson and Joel Denke
 * @description ServerResource taking care of getting list of PrivateMessage
 */
public class PrivateMessagesServerResource extends ServerResource implements PrivateMessagesResource
{
	String id;
	
	@Override
    public void doInit() throws ResourceException 
    {
        this.id = (String) getRequestAttributes().get("userid");
    }

	@Override
	public Representation getMessages()
	{
		List<PrivateMessage> messages = null;
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		User user = new UserDao().getUser(Integer.parseInt(id));
		
		if (user != null) {
			messages = new PrivateMessageDao().getPrivateMessagesTo(user);
		}
    	trans.commit();
    	
    	String jsonString = Converter.toJson(messages);
    	System.out.println(jsonString);
    	Representation jsonRep = new JsonRepresentation(jsonString);
    	
    	return jsonRep;
	}
}

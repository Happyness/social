package se.kth.backend.resource;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.dao.PrivateMessageDao;
import se.kth.backend.model.dao.UserDao;
import se.kth.common.Converter;
import se.kth.common.PrivateMessageResource;
import se.kth.common.PrivateMessagesResource;
import se.kth.common.model.bo.PrivateMessage;
import se.kth.common.model.bo.User;

public class PrivateMessagesServerResource extends ServerResource implements PrivateMessagesResource
{
	String id;
	
	@Override
    public void doInit() throws ResourceException 
    {
        this.id = (String) getRequestAttributes().get("userid");
    }

	@Override
	public String getMessages()
	{
		List<PrivateMessage> messages = null;
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		User user = new UserDao().getUser(Integer.parseInt(id));
		
		if (user != null) {
			messages = new PrivateMessageDao().getPrivateMessagesTo(user);
			//this.getLogger().info("testFromgetMessages");
		}
    	trans.commit();
    	
    	if (messages == null) {
    		messages = new ArrayList<PrivateMessage>();
    	}
    	return Converter.toJson(messages);
	}
}

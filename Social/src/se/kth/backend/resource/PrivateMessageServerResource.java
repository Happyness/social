package se.kth.backend.resource;

import java.util.List;

import org.hibernate.Transaction;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import se.kth.backend.model.dao.PrivateMessageDao;
import se.kth.backend.model.dao.UserDao;
import se.kth.common.PrivateMessageResource;
import se.kth.common.model.bo.PrivateMessage;
import se.kth.common.model.bo.User;

public class PrivateMessageServerResource extends ServerResource implements PrivateMessageResource
{
	String id;
	
	@Override
    public void doInit() throws ResourceException 
    {
        this.id = (String) getRequestAttributes().get("userid");
    }
	
    @Override
    public PrivateMessage getMessage()
    {
    	PrivateMessage message = null;
    	
    	if (id != null) {
	    	Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
	    	message = new PrivateMessageDao().getPrivateMessage(Integer.parseInt(id));
	    	trans.commit();
    	}
    	
    	if (message instanceof PrivateMessage)
    		return message;
    	
        return new PrivateMessage();
    }

	@Override
	public List<PrivateMessage> getMessages()
	{
		Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    	List<PrivateMessage> messages = new PrivateMessageDao().getPrivateMessages();
    	trans.commit();
    	
    	System.out.println("test");
    	
    	return messages;
	}
	
	@Override
	public Representation sendMessage(Representation entity)
	{
		  Form form = new Form(entity);  
		  int to_id = Integer.parseInt(form.getFirstValue("touser"));
		  String message = form.getFirstValue("message");
		  String output = "";
		  
		  if (id != null) {
			  int from_id = Integer.parseInt(id);
			  
			  Transaction trans = null;
				
				try {
					trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
					User to_user = new UserDao().getUser(to_id);
					User from_user = new UserDao().getUser(from_id);
					PrivateMessage msg = new PrivateMessage();
					msg.setFromUser(from_user);
					msg.setToUser(to_user);
					msg.setMessage(message);
					
					new PrivateMessageDao().addPrivateMessage(msg);
					trans.commit();
					output = "Private message successfully sent";
				} catch (RuntimeException e) {
					 HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
					 output = "Send new private message failed: " + e.getMessage();
				}
		  } else {
			  output = "Cannot send message without id";
		  }
		  
		  return new StringRepresentation(output, MediaType.TEXT_PLAIN);
	}
}

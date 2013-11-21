package se.kth.backend.resource;

import java.io.IOException;

import org.hibernate.Transaction;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import se.kth.backend.model.dao.PrivateMessageDao;
import se.kth.backend.model.dao.UserDao;
import se.kth.common.Converter;
import se.kth.common.PrivateMessageResource;
import se.kth.common.model.bo.PrivateMessage;

/**
 * @author Mats Maatson and Joel Denke
 * @description ServerResource taking care of getting and posting PrivateMessage
 */
public class PrivateMessageServerResource extends ServerResource implements
		PrivateMessageResource {
	String id;

	@Override
	public void doInit() throws ResourceException {
		System.out.println("DEBUG: PrivateMessageServerResource.doInit()");
		this.id = (String) getRequestAttributes().get("userid");
	}

	@Override
	public Representation getMessage() {
		System.out.println("DEBUG: PrivateMessageServerResource.getMessage()");
		PrivateMessage pm = null;

		if (id != null) {
			Transaction trans = HibernateUtil.getSessionFactory()
					.getCurrentSession().beginTransaction();
			pm = new PrivateMessageDao().getPrivateMessage(Integer
					.parseInt(id));
			trans.commit();
		}
		String jsonString = Converter.toJson(pm);
		Representation jsonRep = new JsonRepresentation(jsonString);

		return jsonRep;
	}

	@Override
	public Representation sendMessage(Representation entity) throws IOException
	{
		System.out.println("DEBUG: PrivateMessageServerResource.sendMessage()");
		Representation jsonRep = new JsonRepresentation(entity);
		PrivateMessage pm = Converter.fromJson(jsonRep.getText(), PrivateMessage.class);
		
		String output = "";

		try {
			Transaction trans = HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
			new PrivateMessageDao().addPrivateMessage(pm);
			trans.commit();
			output = "Private message successfully sent";
		} catch (RuntimeException e) {
			HibernateUtil.getSessionFactory().getCurrentSession()
					.getTransaction().rollback();
			output = "Send new private message failed: " + e.getMessage();
		}

		return new StringRepresentation(output, MediaType.TEXT_PLAIN);
	}
}

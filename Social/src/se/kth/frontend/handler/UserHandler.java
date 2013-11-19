package se.kth.frontend.handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import se.kth.common.UsersResource;
import se.kth.common.model.bo.User;
import se.kth.common.model.bo.UserLogMessage;
import org.restlet.ext.json.JsonRepresentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@ManagedBean
public class UserHandler implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public UserHandler() {

	}
	
	public List<User> getUsers() throws IOException
	{
		UsersResource ur = ClientHandler.getObjectResource("/users", UsersResource.class);
		JsonRepresentation jsonRep = new JsonRepresentation(ur.getUsers());
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		
		List<User> users = gson.fromJson(jsonRep.getText(), new TypeToken<List<User>>() {}.getType());
		
		return users;
		
		
	}
}

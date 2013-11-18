package se.kth.frontend.handler;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class ClientHandler
{
	public static final String server = "http://localhost:8182/social";
	
	public static <T> T getObjectResource(String uri, Class<T> type)
	{
		ClientResource resource = new ClientResource(ClientHandler.server + uri);
		//Client client = new Client( new Context(), Protocol.HTTPS);
		//resource.setNext(client);

    	try {
			return resource.wrap(type);
		} catch (ResourceException e) {
			e.printStackTrace();
			return null;
		}
	}
}

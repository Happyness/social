package se.kth.frontend.handler;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class ClientHandler
{
	public static final String server = "http://localhost:8182/social";
	
	private static ClientResource getResource(String uri)
	{
		return new ClientResource(ClientHandler.server + uri);
	}
	
	public static void releaseResource(Representation response)
	{
		if (response != null) {
		    try {
		        response.exhaust();
		    } catch (IOException e) {
		        // handle exception
		    }
		    response.release();
		}
	}
	
	public static Representation getNormalResource(String uri)
	{
		ClientResource resource = ClientHandler.getResource(uri);

    	try {
			return resource.get();
		} catch (ResourceException e) {
			return new EmptyRepresentation();
		}
	}
	
	public static Representation getNormalResource(String uri, MediaType type)
	{
		ClientResource resource = ClientHandler.getResource(uri);

    	try {
			return resource.get(type);
		} catch (ResourceException e) {
			return new EmptyRepresentation();
		}
	}
	
	public static <T> T getObjectResource(String uri, Class<T> type)
	{
		ClientResource resource = ClientHandler.getResource(uri);
		//resource.setNext(new Client(new Context(), Protocol.HTTP));

    	try {
			T test = resource.wrap(type);
			resource.release();
			return test;
		} catch (ResourceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Representation getJsonRequest(String uri)
	{
		ClientResource resource = ClientHandler.getResource(uri);
		return resource.get(MediaType.APPLICATION_JSON);
	}
}

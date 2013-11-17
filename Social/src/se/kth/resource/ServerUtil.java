package se.kth.resource;

import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

import se.kth.SocialApplication;

public class ServerUtil extends ServerResource {

	public static void main(String[] args)
	{
		try {
			Component component = new Component();
			component.getServers().add(Protocol.HTTP, 8182);
			component.getDefaultHost().attach("/social", new SocialApplication());
			component.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package se.kth.backend;

import java.net.BindException;

import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public class ServerUtil extends ServerResource {

	public static void main(String[] args)
	{
		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 8182);
		component.getDefaultHost().attach("/social", new SocialApplication());

		if (!component.isStarted()) {
			try {
				component.start();
			} catch (Exception ex) {
				if (ex instanceof BindException) {
					throw new RuntimeException("Server port already bound!");
				}
			}
		} else {
			// Already started?
		}
	}
}

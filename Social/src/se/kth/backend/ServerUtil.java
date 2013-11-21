package se.kth.backend;

import java.net.BindException;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;

/**
 * @author Mats Maatson and Joel Denke
 * @description Server for web services
 */
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
		}
	}
}

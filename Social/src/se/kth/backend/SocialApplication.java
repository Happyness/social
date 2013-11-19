package se.kth.backend;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import se.kth.backend.resource.AuthServerResource;
import se.kth.backend.resource.PrivateMessageServerResource;
import se.kth.backend.resource.UserServerResource;
import se.kth.backend.resource.UsersServerResource;
import se.kth.backend.resource.WallServerResource;

public class SocialApplication extends Application
{
	@Override
	public synchronized Restlet createInboundRoot()
	{
        Router router = new Router(getContext());
        router.attach("/users", UsersServerResource.class);
        router.attach("/user/{userid}", UserServerResource.class);
        router.attach("/messages/{userid}", PrivateMessageServerResource.class);
        router.attach("/message/{userid}", PrivateMessageServerResource.class);
        router.attach("/wall/{userid}", WallServerResource.class);
        router.attach("/login", AuthServerResource.class);
        return router;
    }
}

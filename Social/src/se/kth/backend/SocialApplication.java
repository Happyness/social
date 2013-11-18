package se.kth.backend;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import se.kth.backend.resource.UserServerResource;

public class SocialApplication extends Application
{
	@Override
	public synchronized Restlet createInboundRoot()
	{
        Router router = new Router(getContext());
        //router.attach("/users", UserResource.class);
        router.attach("/user/{userid}", UserServerResource.class);
        //router.attach("/user/profile/{id}", UserResource.class);
        return router;
    }
}

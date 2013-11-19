package se.kth.backend;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import se.kth.backend.resource.AuthServerResource;
import se.kth.backend.resource.PrivateMessageServerResource;
<<<<<<< HEAD
import se.kth.backend.resource.ProfileServerResource;
=======
import se.kth.backend.resource.PrivateMessagesServerResource;
>>>>>>> b9cbaa5e445276c8ef3cf8881d02f5d198e9c1a2
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
        router.attach("/user/store", UserServerResource.class);
        router.attach("/user/profile/{userid}", ProfileServerResource.class);
        router.attach("/messages/{userid}", PrivateMessageServerResource.class);
        router.attach("/message/{userid}", PrivateMessageServerResource.class);
        router.attach("/wall/{userid}", WallServerResource.class);
        router.attach("/login", AuthServerResource.class);
        return router;
    }
}

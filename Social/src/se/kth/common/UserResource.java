package se.kth.common;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface UserResource
{
    @Get
    public Representation getUser();

    @Post
    public Representation createUser(Representation entity);
}

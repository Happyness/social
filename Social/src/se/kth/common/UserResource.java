package se.kth.common;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import se.kth.common.model.bo.User;

public interface UserResource
{
    @Get
    public User getUser();

    @Post
    public Representation createUser(Representation entity);
}

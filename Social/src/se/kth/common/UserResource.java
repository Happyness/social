package se.kth.common;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * @author Mats Maatson and Joel Denke
 * @description Web service interface common for both Server and Client
 */
public interface UserResource
{
    @Get
    public Representation getUser();

    @Post
    public Representation createUser(Representation entity);
}

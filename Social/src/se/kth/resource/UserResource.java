package se.kth.resource;

import org.restlet.resource.Get;

import se.kth.model.bo.User;
public interface UserResource
{
    @Get
    public User getUser();
}

package se.kth.common;

import org.restlet.resource.Get;

import se.kth.backend.model.bo.User;
public interface UserResource
{
    @Get
    public User getUser();
}

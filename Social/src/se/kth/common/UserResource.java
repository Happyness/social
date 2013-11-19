package se.kth.common;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import se.kth.common.model.bo.User;

public interface UserResource
{
    @Get
    public User getUser();

}

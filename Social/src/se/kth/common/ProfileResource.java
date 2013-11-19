package se.kth.common;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import se.kth.common.model.bo.UserProfile;

public interface ProfileResource
{   
    @Get
    public Representation getProfile();
}

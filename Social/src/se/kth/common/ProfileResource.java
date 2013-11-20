package se.kth.common;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public interface ProfileResource
{   
    @Get
    public Representation getProfile();
}

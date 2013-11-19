package se.kth.common;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;


public interface PrivateMessageResource
{
    @Get
    public Representation getMessage();
	
	@Post
	public Representation sendMessage(Representation entity) throws IOException;
}

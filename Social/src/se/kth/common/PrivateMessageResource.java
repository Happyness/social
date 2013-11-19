package se.kth.common;

import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import se.kth.common.model.bo.PrivateMessage;

public interface PrivateMessageResource
{
    @Get
    public PrivateMessage getMessage();
	
	@Post
	public Representation sendMessage(Representation entity);
}

package se.kth.common;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public interface PrivateMessagesResource
{
	@Get
	public Representation getMessages();
}

package se.kth.common;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

public interface AuthResource {

	@Post
	public Representation login(Representation entity) throws IOException;
}

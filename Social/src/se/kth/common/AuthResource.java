package se.kth.common;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

/**
 * @author Mats Maatson and Joel Denke
 * @description Web service interface common for both Server and Client
 */
public interface AuthResource {

	@Post
	public Representation login(Representation entity) throws IOException;
}

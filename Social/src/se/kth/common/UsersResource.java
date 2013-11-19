package se.kth.common;


import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public interface UsersResource {

	@Get
    public Representation getUsers();
}

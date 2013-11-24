package se.kth.common;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

/**
 * @author Mats Maatson and Joel Denke
 * @description Web service interface common for both Server and Client
 */
public interface UsersResource {

	@Get
    public Representation getUsers();
}

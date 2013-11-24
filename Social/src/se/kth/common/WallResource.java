package se.kth.common;

import java.io.IOException;

import org.json.JSONException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * @author Mats Maatson and Joel Denke
 * @description Web service interface common for both Server and Client
 */
public interface WallResource
{
	@Get
    public Representation getUserLogMessages();
	
	@Post
	public Representation postUserLogMessage(Representation representation) throws JSONException, IOException;
}
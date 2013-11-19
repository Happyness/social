package se.kth.common;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;


public interface WallResource {

	@Get
    public Representation getUserLogMessages();
	
	@Post
	public Representation postUserLogMessage(Representation representation) throws JSONException, IOException;
}

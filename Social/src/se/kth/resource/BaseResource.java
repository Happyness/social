package se.kth.resource;

import org.restlet.resource.Get;

public interface BaseResource {
	@Get
	public <T> T getData();
}

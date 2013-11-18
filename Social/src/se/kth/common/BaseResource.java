package se.kth.common;

import org.restlet.resource.Get;

public interface BaseResource {
	@Get
	public <T> T getData();
}

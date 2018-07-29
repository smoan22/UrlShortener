package com.codeclobber.urlshortner;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

import com.codeclobber.urlshortner.services.UrlShortnerService;
 

public class UrlShortnerApplication  extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();
	 
	public UrlShortnerApplication() {
		singletons.add(new UrlShortnerService());
	}
 
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}

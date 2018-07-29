package com.codeclobber.urlshortner.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.validator.routines.UrlValidator;
import org.jboss.resteasy.plugins.providers.html.View;

import com.codeclobber.urlshortner.websocket.*;
import com.codeclobber.urlshortner.database.DBOperations;
import com.codeclobber.urlshortner.models.Response;
import com.codeclobber.urlshortner.models.ShortenedUrl;
import com.codeclobber.urlshortner.models.UrlClick;
import com.codeclobber.urlshortner.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
 
@Path("/")
public class UrlShortnerService {

	@Context
	private UriInfo uriInfo;
	
	@Path("/addUrl")  
	@POST
	// Produces JSON as response
	@Produces("application/json")	
	public Response addShortUrl(@Context HttpServletResponse resp, @Context HttpServletRequest req) {
		System.out.println("Inside addShortUrl of class "+this.toString());		
		ShortenedUrl shortenedUrl = new ShortenedUrl();
		shortenedUrl.setOrignalUrl(req.getParameter("url"));		
		Response response = new Response();	
			try {
				ShortenedUrl shortenedUrlOrig = DBOperations.getShortenedUrlByOrignalUrl(shortenedUrl.getOrignalUrl());
				DBOperations.updateShortenedUrl(shortenedUrlOrig);				
				response.setStatus("200");
				response.setData(shortenedUrlOrig.getShortUrl());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Inside addShortUrl catch  "+e.getMessage());
				if(e.getMessage().equals("NRFE")){
					try {
						String shortUrl = DBOperations.insertShortenedUrl(uriInfo, shortenedUrl);						
						response.setStatus("200");
						response.setData("Copy this: "+shortUrl);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						response.setStatus("500");
						response.setData("Some error occurred. Please try again");
					}
				}
			}		
				
		return response;	

	}
	
	@GET
	@Path("/shortUrl/get/{id}")
	@Produces("application/json")
	public ShortenedUrl getShortenedUrl(@PathParam("id") String id) throws Exception {
    	ShortenedUrl shortenedUrl = new ShortenedUrl();
		try {
			DBOperations.getShortenedUrlById(id);
		} catch (Exception se) {						
			se.printStackTrace();			
		}	     							
		return shortenedUrl; 
    }
	
	@GET
	@Path("/url/{shortUrl}")	
	public void redirectToOrignalUrl(@Context HttpServletResponse resp,@Context HttpServletRequest req, @PathParam("shortUrl") String shortUrl) throws Exception {
		String requestUri = uriInfo.getRequestUri().toString();
		requestUri = requestUri.substring(0, requestUri.lastIndexOf("/") + 1) + shortUrl;
		ShortenedUrl shortenedUrl = null;
		String url = "";
    	try {    		
    		shortenedUrl = DBOperations.getShortenedUrlByUrl(requestUri);
    		shortenedUrl = DBOperations.getShortenedUrlByUrl(requestUri);
    		Date expiryDate = DateUtil.getDateWithoutTime(shortenedUrl.getExpiryDate());
			Date today = DateUtil.getDateWithoutTime(new Date());
			if(expiryDate.getTime() >= today.getTime()){
				//Time hasn't expired yet
				url = shortenedUrl.getOrignalUrl();
				DBOperations.saveUrlClick(shortenedUrl.getId());				
			}else{
				url = "http://localhost:8080/UrlShortening/expired.jsp";
			}			
		} catch (Exception se) {
			url = "http://localhost:8080/UrlShortening/error.jsp";			
		}
    	
    	resp.sendRedirect(url);
    	try{
    		ServerEndpoint.handleMessage(shortenedUrl.getId());	
    	}catch(Exception e){
    		e.printStackTrace();
    	}    	
    }

	@GET
	@Path("/shortUrls/get")
	@Produces("text/html")
	public View getAllShortenedUrls(@Context HttpServletResponse resp,
			@Context HttpServletRequest req) throws Exception {    	
		List<ShortenedUrl> shortenedUrls = new ArrayList<ShortenedUrl>();
		try {
			shortenedUrls = DBOperations.getAllShortenedUrls();
			JsonArray shortendUrlsJson=new JsonArray();
			
			for(int j=0;j<shortenedUrls.size();j++){
				ShortenedUrl shortenedUrl = shortenedUrls.get(j);
				JsonObject jsonObject= new JsonParser().parse(new Gson().toJson(shortenedUrl)).getAsJsonObject();
				shortendUrlsJson.add(jsonObject);
			}

			req.setAttribute("shortUrls", shortendUrlsJson);
		} catch (Exception se) {
			se.printStackTrace();
		}	    
		
		return new View("/jsp/datatable.jsp");
    }
 
	@GET
	@Path("/urlClicks/get/{id}")
	@Produces("text/html")
	public View showShortUrlChart(@Context HttpServletResponse resp,
			@Context HttpServletRequest req, @PathParam("id") String id) throws Exception {    	
		List<UrlClick> urlClicks = new ArrayList<UrlClick>();
		try {
			urlClicks = DBOperations.getAllUrlClicksByShortenedUrlId(id);
			JsonArray urlClicksJson=new JsonArray();
			
			for(int j=0;j<urlClicks.size();j++){
				UrlClick urlClick = urlClicks.get(j);
				JsonObject jsonObject= new JsonParser().parse(new Gson().toJson(urlClick)).getAsJsonObject();
				urlClicksJson.add(jsonObject);
			}

			req.setAttribute("urlClicks", urlClicksJson);
		} catch (Exception se) {
			se.printStackTrace();
		}	    
		
		return new View("/jsp/dashboard.jsp");
    }
}

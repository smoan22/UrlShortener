package com.codeclobber.urlshortner.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import com.codeclobber.urlshortner.database.DBOperations;
import com.codeclobber.urlshortner.models.UrlClick;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@javax.websocket.server.ServerEndpoint(value = "/chartSocket")
public class ServerEndpoint {

	private static Set<Session> allSessions;
	
	@OnOpen
    public void handleOpen(Session session){
    	System.out.println("Client connected mann");
    	allSessions = session.getOpenSessions();   	 
    }

	@OnMessage
    public static void handleMessage(String id){
//		System.out.println("Got a message "+message);
//    	return message + " yeahhhhhhhhhh";
		List<UrlClick> urlClicks = new ArrayList<UrlClick>();
		try {
			urlClicks = DBOperations.getAllUrlClicksByShortenedUrlId(id);			
		} catch (Exception se) {
			se.printStackTrace();
		}
		for (Session sess: allSessions){          
	        try{   
	          sess.getBasicRemote().sendText(new Gson().toJson(urlClicks));
	          } catch (IOException ioe) {        
	              System.out.println(ioe.getMessage());         
	          }   
	     }   
//		return new Gson().toJson(urlClicks);
    }
    
	@OnClose
    public void handleClose(){
		System.out.println("Client disconnected mann");
    }
	
    @OnError
    public void handleError(Throwable t){
    	t.printStackTrace();
    }
    
    	 
    	
}

package com.codeclobber.urlshortner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.codeclobber.urlshortner.models.ShortenedUrl;
import com.codeclobber.urlshortner.models.UrlClick;
import com.codeclobber.urlshortner.utils.DateUtil;
 
public class DBOperations {

	public static String insertShortenedUrl(UriInfo uriInfo, ShortenedUrl shortenedUrl) throws Exception {
    	Connection connect = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {			
			connect = DBConnection.createConnection();
			connect.setAutoCommit(false); 			                        
			StringBuffer query = null;
			if (connect != null) {    			
				query = new StringBuffer("");
				query.append("SELECT ");
				query.append("MAX(ID) + 1 ID ");
				query.append("FROM ");
				query.append("SHORTENED_URL ");

				stmt = connect.prepareStatement(query.toString());
				rs = stmt.executeQuery();

				while (rs.next()) {
					if(rs.getString("ID") == null)
						shortenedUrl.setId("1");
					else
						shortenedUrl.setId(rs.getString("ID"));
				}
				query = null;
				query = new StringBuffer("");
				query.append("INSERT INTO SHORTENED_URL");
				query.append("(ID, ");    				
				query.append("ORIGNAL_URL, ");    				    				
				query.append("SHORT_URL, ");
				query.append("CREATION_DATE, ");				
				query.append("EXPIRY_DATE) values (");
				query.append("?,?,?,?,?)");			
				System.out.println(query.toString());
				Calendar cal = Calendar.getInstance();
				Timestamp creationDate = new Timestamp(cal.getTimeInMillis());
				cal.add(Calendar.DATE, 50);
				Timestamp expiryDate = new Timestamp(cal.getTimeInMillis());
				stmt = connect.prepareStatement(query.toString());
				String shortUrl = uriInfo.getBaseUri()+"url/"+shortenedUrl.getId() + System.currentTimeMillis();
	            shortenedUrl.setShortUrl(shortUrl);
				int i = 0;
				stmt.setString(++i, shortenedUrl.getId());
				stmt.setString(++i, shortenedUrl.getOrignalUrl());
				stmt.setString(++i, shortenedUrl.getShortUrl());
				stmt.setTimestamp(++i, creationDate);				
				stmt.setTimestamp(++i, expiryDate);
				stmt.executeUpdate();

				connect.commit();
				connect.setAutoCommit(true);			
			}
		} catch (Exception se) {
			if(connect != null){
				connect.rollback();
				connect.setAutoCommit(true);
			}
			System.out.println("Error in insertShortenedUrl method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {
				if(rs!=null){rs.close();}
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in insertShortenedUrl method");
				System.out.println(se.getMessage());
				se.printStackTrace();
			}
			if (connect != null) {
				connect.close();
			}
		}        
		
		return shortenedUrl.getShortUrl();
    }
	
	public static void updateShortenedUrl(ShortenedUrl shortenedUrl) throws Exception {
    	Connection connect = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {			
			connect = DBConnection.createConnection();
			connect.setAutoCommit(false); 			                        
			StringBuffer query = null;
			if (connect != null) {    							
				query = new StringBuffer("");
				query.append("UPDATE SHORTENED_URL ");
				query.append("SET EXPIRY_DATE = ? ");    				
				query.append("WHERE ");    				    				
				query.append("ID = ? ");
				Calendar cal = Calendar.getInstance();				
				cal.add(Calendar.DATE, 40);
				Timestamp expiryDate = new Timestamp(cal.getTimeInMillis());
				stmt = connect.prepareStatement(query.toString());
				int i = 0;
				stmt.setTimestamp(++i, expiryDate);
				stmt.setString(++i, shortenedUrl.getId());
				stmt.executeUpdate();

				connect.commit();
				connect.setAutoCommit(true);			
			}
		} catch (Exception se) {
			if(connect != null){
				connect.rollback();
				connect.setAutoCommit(true);
			}
			System.out.println("Error in updateShortenedUrl method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {
				if(rs!=null){rs.close();}
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in updateShortenedUrl method");
				System.out.println(se.getMessage());
				se.printStackTrace();				
			}
			if (connect != null) {
				connect.close();
			}
		}
    }
	
	public static void saveUrlClick(String shortenedUrlId) throws Exception {
    	Connection connect = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {			
			connect = DBConnection.createConnection();
			connect.setAutoCommit(false); 			                        
			StringBuffer query = null;
			if (connect != null) {    	
				query = new StringBuffer("");
				query.append("SELECT ");
				query.append("* ");
				query.append("FROM URL_CLICK WHERE ");
				query.append("SHORTENED_URL_ID = ? ");

				stmt = connect.prepareStatement(query.toString());
				int i = 0;
				stmt.setString(++i, shortenedUrlId);
				rs = stmt.executeQuery();
				
				boolean hasEqualClickDate = false;
				String urlClickId = "";
				Date clickDate = null;
				while (rs.next()) {
					clickDate = DateUtil.getDateWithoutTime(new Date(rs.getTimestamp("CLICK_DATE").getTime()));
					Date today = DateUtil.getDateWithoutTime(new Date());
					if(clickDate.getTime() == today.getTime()){
						hasEqualClickDate = true;
						urlClickId = rs.getString("ID");
						break;
					}
				}
				
				if(hasEqualClickDate){
					updateUrlClick(connect, rs.getString("ID"), clickDate);
				}else{
					insertUrlClick(connect, shortenedUrlId);
				}
								
				connect.commit();
				connect.setAutoCommit(true);			
			}
		} catch (Exception se) {
			if(connect != null){
				connect.rollback();
				connect.setAutoCommit(true);
			}
			System.out.println("Error in saveUrlClick method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {
				if(rs!=null){rs.close();}
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in saveUrlClick method");
				System.out.println(se.getMessage());
				se.printStackTrace();				
			}
			if (connect != null) {
				connect.close();
			}
		}
    }
	
	public static void insertUrlClick(Connection connect, String shortenedUrlId) throws Exception {    	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {			
			UrlClick urlClick = new UrlClick();			                        
			StringBuffer query = null;			    			
			query = new StringBuffer("");
			query.append("SELECT ");
			query.append("MAX(ID) + 1 ID ");
			query.append("FROM ");
			query.append("URL_CLICK ");

			stmt = connect.prepareStatement(query.toString());
			rs = stmt.executeQuery();

			if (rs.next()) {
				if(rs.getString("ID") == null)
					urlClick.setId("1");
				else
					urlClick.setId(rs.getString("ID"));
			}
			urlClick.setShortenedUrlId(shortenedUrlId);
			query = null;
			query = new StringBuffer("");
			query.append("INSERT INTO URL_CLICK");
			query.append("(ID, ");    				
			query.append("SHORTENED_URL_ID, ");    				    				
			query.append("TOTAL_CLICKS, ");
			query.append("CLICK_DATE ) values (");
			query.append("?,?,?,?)");			
			System.out.println(query.toString());				
			stmt = connect.prepareStatement(query.toString());
			int i = 0;
			stmt.setString(++i, urlClick.getId());
			stmt.setString(++i, urlClick.getShortenedUrlId());
			stmt.setInt(++i, 1);
			stmt.setTimestamp(++i, new Timestamp(System.currentTimeMillis()));
			stmt.executeUpdate();			
		} catch (Exception se) {			
			System.out.println("Error in insertUrlClick method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {				
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in insertUrlClick method");
				System.out.println(se.getMessage());
				se.printStackTrace();
			}
		}        
    }
	
	public static void updateUrlClick(Connection connect, String id, Date clickDate) throws Exception {
    	PreparedStatement stmt = null;
		try {			
			StringBuffer query = null;			 							
			query = new StringBuffer("");
			query.append("UPDATE URL_CLICK ");
			query.append("SET TOTAL_CLICKS = TOTAL_CLICKS + 1 ");    				
			query.append("WHERE ");    				    				
			query.append("ID = ? AND CLICK_DATE = ? ");								
			stmt = connect.prepareStatement(query.toString());
			int i = 0;
			stmt.setString(++i, id);
			stmt.setTimestamp(++i, new Timestamp(clickDate.getTime()));
			stmt.executeUpdate();
		} catch (Exception se) {
			System.out.println("Error in updateUrlClick method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {				
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in updateUrlClick method");
				System.out.println(se.getMessage());
				se.printStackTrace();				
			}			
		}
    }
	
	public static ShortenedUrl getShortenedUrlById(String id) throws Exception {
    	Connection connect = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ShortenedUrl shortenedUrl = new ShortenedUrl();
		try {
			connect = DBConnection.createConnection();
			connect.setAutoCommit(false);                        
			StringBuffer query = null;
			if (connect != null) {    			
				query = new StringBuffer("");

				query.append("SELECT ");
				query.append("A.*, IFNULL(SUM(B.TOTAL_CLICKS), 0) HIT_COUNT FROM ");
				query.append("SHORTENED_URL A LEFT JOIN URL_CLICK B ");
				query.append("ON A.ID = B.SHORTENED_URL_ID "
						+ "WHERE ID = ? GROUP BY A.ID; ");

				stmt = connect.prepareStatement(query.toString());
				stmt.setString(1, id); 
				rs = stmt.executeQuery();

				if (rs.next()) {						
					shortenedUrl.setId(rs.getString("ID"));
					shortenedUrl.setOrignalUrl(rs.getString("ORIGNAL_URL"));
					shortenedUrl.setShortUrl(rs.getString("SHORT_URL"));
					shortenedUrl.setCreationDate(rs.getTimestamp("CREATION_DATE"));
					shortenedUrl.setHitCount(rs.getInt("HIT_COUNT"));
					shortenedUrl.setExpiryDate(rs.getTimestamp("EXPIRY_DATE"));
				}

				connect.commit();
				connect.setAutoCommit(true);			
			}
		} catch (Exception se) {
			if(connect != null){
				connect.rollback();
				connect.setAutoCommit(true);
			}
			System.out.println("Error in getShortenedUrlById method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {
				if(rs!=null){rs.close();}
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in getShortenedUrlById method");
				System.out.println(se.getMessage());
				se.printStackTrace();				
			}
			if (connect != null) {
				connect.close();
			}
		}	     							
		return shortenedUrl; 
    }
	
	public static ShortenedUrl getShortenedUrlByOrignalUrl(String orgUrl) throws Exception {
    	Connection connect = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ShortenedUrl shortenedUrl = new ShortenedUrl();
		try {
			connect = DBConnection.createConnection();
			connect.setAutoCommit(false);                        
			StringBuffer query = null;
			if (connect != null) {    			
				query = new StringBuffer("");
				query.append("SELECT ");
				query.append("A.*, IFNULL(SUM(B.TOTAL_CLICKS), 0) HIT_COUNT FROM ");
				query.append("SHORTENED_URL A LEFT JOIN URL_CLICK B ");
				query.append("ON A.ID = B.SHORTENED_URL_ID "
						+ "WHERE ORIGNAL_URL = ? GROUP BY A.ID; ");
				
				stmt = connect.prepareStatement(query.toString());
				stmt.setString(1, orgUrl); 
				rs = stmt.executeQuery();
				String url = "";
				if (rs.next()) {						
					shortenedUrl.setId(rs.getString("ID"));
					shortenedUrl.setOrignalUrl(rs.getString("ORIGNAL_URL"));
					shortenedUrl.setShortUrl(rs.getString("SHORT_URL"));
					shortenedUrl.setCreationDate(rs.getTimestamp("CREATION_DATE"));
					shortenedUrl.setHitCount(rs.getInt("HIT_COUNT"));
					shortenedUrl.setExpiryDate(rs.getTimestamp("EXPIRY_DATE"));									
				}else {
					throw new Exception("NRFE");
				}

				connect.commit();
				connect.setAutoCommit(true);
			}
		} catch (Exception se) {
			if(connect != null){
				connect.rollback();
				connect.setAutoCommit(true);
			}
			System.out.println("Error in getShortenedUrlByUrl method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {
				if(rs!=null){rs.close();}
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in getShortenedUrlByUrl method");
				System.out.println(se.getMessage());
				se.printStackTrace();				
			}
			if (connect != null) {
				connect.close();
			}		
		}
		return shortenedUrl;
    }
	
	public static ShortenedUrl getShortenedUrlByUrl(String shortUrl) throws Exception {
    	Connection connect = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ShortenedUrl shortenedUrl = new ShortenedUrl();
		try {
			connect = DBConnection.createConnection();
			connect.setAutoCommit(false);                        
			StringBuffer query = null;
			if (connect != null) {    			
				query = new StringBuffer("");
				query.append("SELECT ");
				query.append("A.*, IFNULL(SUM(B.TOTAL_CLICKS), 0) HIT_COUNT FROM ");
				query.append("SHORTENED_URL A LEFT JOIN URL_CLICK B ");
				query.append("ON A.ID = B.SHORTENED_URL_ID "
						+ "WHERE SHORT_URL = ? GROUP BY A.ID; ");
				
				stmt = connect.prepareStatement(query.toString());
				stmt.setString(1, shortUrl); 
				rs = stmt.executeQuery();
				String url = "";
				if (rs.next()) {						
					shortenedUrl.setId(rs.getString("ID"));
					shortenedUrl.setOrignalUrl(rs.getString("ORIGNAL_URL"));
					shortenedUrl.setShortUrl(rs.getString("SHORT_URL"));
					shortenedUrl.setCreationDate(rs.getTimestamp("CREATION_DATE"));
					shortenedUrl.setHitCount(rs.getInt("HIT_COUNT"));
					shortenedUrl.setExpiryDate(rs.getTimestamp("EXPIRY_DATE"));									
				}else {
					throw new Exception("NRFE");
				}

				connect.commit();
				connect.setAutoCommit(true);
			}
		} catch (Exception se) {
			if(connect != null){
				connect.rollback();
				connect.setAutoCommit(true);
			}
			System.out.println("Error in getShortenedUrlByUrl method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {
				if(rs!=null){rs.close();}
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in getShortenedUrlByUrl method");
				System.out.println(se.getMessage());
				se.printStackTrace();				
			}
			if (connect != null) {
				connect.close();
			}		
		}
		return shortenedUrl;
    }

	public static List<ShortenedUrl> getAllShortenedUrls() throws Exception {
    	Connection connect = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ShortenedUrl> shortenedUrls = new ArrayList<ShortenedUrl>();
		try {
			connect = DBConnection.createConnection();
			connect.setAutoCommit(false);                        
			StringBuffer query = null;
			if (connect != null) {    			
				query = new StringBuffer("");
				query.append("SELECT ");
				query.append("A.*, IFNULL(SUM(B.TOTAL_CLICKS), 0) HIT_COUNT FROM ");
				query.append("SHORTENED_URL A LEFT JOIN URL_CLICK B ");
				query.append("ON A.ID = B.SHORTENED_URL_ID "
						+ "GROUP BY A.ID; ");

				stmt = connect.prepareStatement(query.toString());				
				rs = stmt.executeQuery();

				while (rs.next()) {
					ShortenedUrl shortenedUrl = new ShortenedUrl();
					shortenedUrl.setId(rs.getString("ID"));
					shortenedUrl.setOrignalUrl(rs.getString("ORIGNAL_URL"));
					shortenedUrl.setShortUrl(rs.getString("SHORT_URL"));
					shortenedUrl.setCreationDate(rs.getTimestamp("CREATION_DATE"));
					shortenedUrl.setHitCount(rs.getInt("HIT_COUNT"));
					shortenedUrl.setExpiryDate(rs.getTimestamp("EXPIRY_DATE"));
					shortenedUrls.add(shortenedUrl);
				}

				connect.commit();
				connect.setAutoCommit(true);			
			}
		} catch (Exception se) {
			if(connect != null){
				connect.rollback();
				connect.setAutoCommit(true);
			}
			System.out.println("Error in getShortenedUrl method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {
				if(rs!=null){rs.close();}
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in getShortenedUrl method");
				System.out.println(se.getMessage());
				se.printStackTrace();				
			}
			if (connect != null) {
				connect.close();
			}
		}	    
		
		return shortenedUrls;
    }
 
	public static List<UrlClick> getAllUrlClicksByShortenedUrlId(String shortenedUrlId) throws Exception {
		Connection connect = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<UrlClick> urlClicks = new ArrayList<UrlClick>();
		try {
			connect = DBConnection.createConnection();
			connect.setAutoCommit(false);                        
			StringBuffer query = null;
			if (connect != null) {    			
				query = new StringBuffer("");
				query.append("SELECT ");
				query.append("A.* FROM ");
				query.append("URL_CLICK A ");
				query.append("WHERE SHORTENED_URL_ID = ? ORDER BY CLICK_DATE");

				stmt = connect.prepareStatement(query.toString());
				stmt.setString(1, shortenedUrlId); 
				rs = stmt.executeQuery();

				while (rs.next()) {
					UrlClick urlClick = new UrlClick();
					urlClick.setId(rs.getString("ID"));
					urlClick.setShortenedUrlId(rs.getString("SHORTENED_URL_ID"));
					urlClick.setTotalClicks(rs.getInt("TOTAL_CLICKS"));
					urlClick.setClickDate(rs.getTimestamp("CLICK_DATE"));
					urlClicks.add(urlClick);
				}

				connect.commit();
				connect.setAutoCommit(true);			
			}
		} catch (Exception se) {
			if(connect != null){
				connect.rollback();
				connect.setAutoCommit(true);
			}
			System.out.println("Error in getAllUrlClicksByShortenedUrlId method");
			System.out.println(se.getMessage());
			se.printStackTrace();
			throw se;
		}finally {
			try {
				if(rs!=null){rs.close();}
				stmt.close();
			} catch (SQLException se) {
				System.out.println("Error in getAllUrlClicksByShortenedUrlId method");
				System.out.println(se.getMessage());
				se.printStackTrace();				
			}
			if (connect != null) {
				connect.close();
			}
		}	    
		
		return urlClicks; 
    }
	
}

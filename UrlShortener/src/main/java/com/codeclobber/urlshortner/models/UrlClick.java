package com.codeclobber.urlshortner.models;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by Muhammad Oan on 25/07/2018.
 */
@XmlRootElement
public class UrlClick {

    private String id;
    private String shortenedUrlId;    
    private int totalClicks;
    private Timestamp clickDate;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShortenedUrlId() {
		return shortenedUrlId;
	}
	public void setShortenedUrlId(String shortenedUrlId) {
		this.shortenedUrlId = shortenedUrlId;
	}
	public int getTotalClicks() {
		return totalClicks;
	}
	public void setTotalClicks(int totalClicks) {
		this.totalClicks = totalClicks;
	}
	public Timestamp getClickDate() {
		return clickDate;
	}
	public void setClickDate(Timestamp clickDate) {
		this.clickDate = clickDate;
	}
	    
}

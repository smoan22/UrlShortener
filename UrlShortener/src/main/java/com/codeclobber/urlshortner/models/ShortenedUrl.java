package com.codeclobber.urlshortner.models;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by Muhammad Oan on 25/07/2018.
 */
@XmlRootElement
public class ShortenedUrl {

    private String id;
    private String orignalUrl;
    private String shortUrl;
    private Timestamp creationDate;
    private Timestamp expiryDate;
    private int hitCount;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrignalUrl() {
		return orignalUrl;
	}
	public void setOrignalUrl(String orignalUrl) {
		this.orignalUrl = orignalUrl;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public Timestamp getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

    
}

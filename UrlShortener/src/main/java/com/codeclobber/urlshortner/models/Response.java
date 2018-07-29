package com.codeclobber.urlshortner.models;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by Muhammad Oan on 25/07/2018.
 */
@XmlRootElement
public class Response {

    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	private String status;
    private String data;    
    
    
}

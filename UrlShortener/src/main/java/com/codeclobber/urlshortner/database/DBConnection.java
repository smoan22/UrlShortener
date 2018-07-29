package com.codeclobber.urlshortner.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.codeclobber.urlshortner.constants.Constant;
import com.codeclobber.urlshortner.utils.FileUtil;

/**
 * Created by Muhammad Oan on 25/07/2018.
 */
public class DBConnection {

    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
        	Properties dbProps = FileUtil.getProperties();
	    	String connectionUrl = dbProps.getProperty("mysql.url");
	    	String userName = dbProps.getProperty("mysql.username");
	    	String password = dbProps.getProperty("mysql.password");
            Class.forName(Constant.DB_CLASS);
            con = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Inside createConnection catch  " + e.getMessage());
            throw e;
        }
        return con;
    }

}

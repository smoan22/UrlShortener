package com.codeclobber.urlshortner.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Muhammad Oan on 25/07/2018.
 */
public class FileUtil {

	public  static Properties getProperties(){
		FileInputStream fin;
		Properties dbProps = new Properties();
		try {
			File file = new File(
				"/configurations/database/applicationDb.conf");
			fin = new FileInputStream(file);
			dbProps.load(fin);

		}
		catch (FileNotFoundException ex) {
		    System.err.println(ex.getMessage());
			ex.printStackTrace();
			dbProps = null;
		}
		catch (IOException ie) {
			System.out.println(ie.getStackTrace());
			dbProps = null;
		}
		return dbProps;
	}

}

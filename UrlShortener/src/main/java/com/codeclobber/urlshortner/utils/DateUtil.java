package com.codeclobber.urlshortner.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Muhammad Oan on 25/07/2018.
 */
public class DateUtil {

    public static Date getDateWithoutTime(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	calendar.set(Calendar.SECOND, 0);
    	return calendar.getTime();
    }

}

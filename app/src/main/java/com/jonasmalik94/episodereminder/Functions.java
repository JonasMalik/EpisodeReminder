package com.jonasmalik94.episodereminder;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jonas on 2016-06-22.
 */
public class Functions{

    static public String getWeekday(){
        String weekday;
        Calendar calendar;

        calendar = Calendar.getInstance();
        weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        weekday = weekday.substring(0,1).toUpperCase() + weekday.substring(1) ;

        return weekday;
    }

    static public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        String date;

        date = sdf.format(new Date());

        return date;
    }

    static public String getYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String year;

        year = sdf.format(new Date());

        return year;
    }


}

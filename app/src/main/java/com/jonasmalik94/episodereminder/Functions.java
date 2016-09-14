package com.jonasmalik94.episodereminder;

import android.app.Activity;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
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

    static public String getLatestDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String latestDate;

        latestDate = sdf.format(new Date());

        return latestDate;
    }

    static public void exportDatabse(String databaseName, String packageName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+packageName+"//databases//"+databaseName+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


}

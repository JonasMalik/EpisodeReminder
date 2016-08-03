package com.jonasmalik94.episodereminder;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by jonas on 2016-06-28.
 */
public class SQL extends MainActivity{

    public static String createTable(String tableName, ArrayList<String> columns){

        int i = 0;
        String temp;
        String createTable;
        StringBuilder builder = new StringBuilder();

        // Set's a "," after each element in ArrayList
        while (i<(columns.size()-1)){
            temp = columns.get(i);
            columns.set(i,temp+",");
            i++;
        }

        // Converting ArrayList to a singel String
        for (String value : columns) {
            builder.append(value);
        }
        String text = builder.toString();
        createTable = "CREATE TABLE IF NOT EXISTS "+tableName+"("+text+");";

        return createTable;
    }

    public static String deleteTable(String tableName){

        String deleteTable;

        // Inserting tableName in SQL delete String
        deleteTable = "DROP TABLE "+tableName+";";

        return deleteTable;
    }

    public static String insertValues(String tableName, ArrayList<String> columns, ArrayList<String> values){

        int i = 0;
        String tempColumn;
        String tempValue;
        String insertvalues;
        StringBuilder builder = new StringBuilder();

        // Set's a "," after each element in ArrayList
        while (i<(columns.size()-1)){
            //Columns
            tempColumn = columns.get(i);
            columns.set(i,tempColumn+",");

            //Values
            tempValue = values.get(i);
            values.set(i,"'"+tempValue+"',");
            i++;
        }
        tempValue = values.get(i);
        values.set(i,"'"+tempValue+"'");

        // Converting ArrayList to a singel String
        for (String value : columns) {
            builder.append(value);
        }
        String newColumns = builder.toString();

        //Resets StringBuilder
        builder.setLength(0);

        // Converting ArrayList to a singel String
        for (String value : values) {
            builder.append(value);
        }
        String newValues = builder.toString();

        insertvalues = "INSERT INTO "+tableName+"("+newColumns+") VALUES("+newValues+");";

        return insertvalues;
    }

    public static String selectAll(String tableName){

        String selectAll;

        selectAll = "SELECT * FROM "+tableName+";";

        return selectAll;
    }

    public static String getLastRecord(String tableName){

        String lastRecord;

        lastRecord = "SELECT * FROM "+tableName+" WHERE Id = (SELECT MAX(Id) FROM "+tableName+");";

        return lastRecord;
    }

    public static String getRecordByID(String tableName, String id){

        String recordByID;

        recordByID = "SELECT * FROM "+tableName+" WHERE Id = '"+id+"';";

        return recordByID;
    }

    public static void updateValue(String tableName, String id, String colum, String value){

        final SQLiteDatabase db;


        db = SQLiteDatabase.openOrCreateDatabase(path, null);

        db.execSQL("UPDATE "+tableName+" SET "+colum+" = '" + value + "' WHERE Id = " + id + ";");

    }

    public static String selectFilter(String tableName, String filter, String sort){

        String selectFilter;

        if (sort.equals("title")) {
            selectFilter = "SELECT * FROM " + tableName + " WHERE title LIKE '%" + filter + "%' ORDER BY " + sort + " ;";
            return selectFilter;
        }
        else if (sort.equals("recent")){
            selectFilter = "SELECT * FROM " + tableName + " WHERE title LIKE '%" + filter + "%' ORDER BY date DESC ;";
            return selectFilter;
        }
        else if (sort.equals("favourites")){
            selectFilter = "SELECT * FROM " + tableName + " WHERE title LIKE '%" + filter + "%' AND rating = '1' ORDER BY title ;";
            return selectFilter;
        }

        return null;
    }

    public static void deleteRecord(String tableName, String id){

        final SQLiteDatabase db;


        db = SQLiteDatabase.openOrCreateDatabase(path, null);

        db.execSQL("DELETE FROM "+tableName+" WHERE Id = " + id + ";");

    }
}

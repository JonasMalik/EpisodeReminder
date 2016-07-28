package com.jonasmalik94.episodereminder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jonas on 2016-06-18.
 */
public class SubActivity extends AppCompatActivity {

    TextView text;
    ListView subList;
    ArrayAdapter<String> listAdapter ;
    SubListAdapter adapter;
    ArrayList<SubListRow> arrayOfRows = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sub);

        subList = (ListView) findViewById(R.id.subList);
        arrayOfRows = new ArrayList<>();

        Intent intent = getIntent();
        String myID = intent.getStringExtra("ID"); //if it's a string you stored.

        final SQLiteDatabase db;

        db=openOrCreateDatabase("EpisodeReminder", MODE_PRIVATE, null);
        Cursor c = db.rawQuery(SQL.getRecordByID("series",myID), null);
        c.moveToNext();
        String id          = c.getString(0);
        String title          = c.getString(1);
        String episode        = c.getString(2);
        String season         = c.getString(3);
        String is_over        = c.getString(4);
        String rating         = c.getString(5);

        SubListRow newRow = new SubListRow(title, season, episode, rating , id);
        arrayOfRows.add(newRow);

        adapter = new SubListAdapter(this, arrayOfRows);
        subList.setAdapter(adapter);




    }
}

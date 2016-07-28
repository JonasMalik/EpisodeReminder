package com.jonasmalik94.episodereminder;

import android.content.Intent;
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
        String myTitle = intent.getStringExtra("title"); //if it's a string you stored.
        String myRating = intent.getStringExtra("rating"); //if it's a string you stored.

        SubListRow newRow = new SubListRow(myRating, "-", "-", "1");
        arrayOfRows.add(newRow);

        adapter = new SubListAdapter(this, arrayOfRows);
        subList.setAdapter(adapter);




    }
}

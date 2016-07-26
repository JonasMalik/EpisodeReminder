package com.jonasmalik94.episodereminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jonas on 2016-06-18.
 */
public class ExerciseActivity extends AppCompatActivity {

    TextView text;
    ListView exerciseList;
    ArrayAdapter<String> listAdapter ;
    ArrayList<String> exerciseNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exercise);

        exerciseList = (ListView) findViewById(R.id.exerciseList);
        exerciseNumber = new ArrayList<>();

        exerciseNumber.add("Övning 1");
        exerciseNumber.add("Övning 2");
        exerciseNumber.add("Övning 3");

        listAdapter = new ArrayAdapter<String>(this,R.layout.row_exercise, R.id.exercise_number,exerciseNumber);
        exerciseList.setAdapter(listAdapter);



        //how to get var from another activity
        /*
        Intent intent = getIntent();
        String myIndex = intent.getStringExtra("index"); //if it's a string you stored.
        text = (TextView) findViewById(R.id.text);
        text.setText(myIndex);
        */


    }
}

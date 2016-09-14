package com.jonasmalik94.episodereminder;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by jonas on 2016-09-14.
 */
public class ExportActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        Button exportDB = (Button) findViewById(R.id.exportDB);
        Button importDB = (Button) findViewById(R.id.importDB);
        Button deleteDB = (Button) findViewById(R.id.deleteDB);

        exportDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDB();
            }
        });


    }

    private void exportDB(){
        ActivityCompat.requestPermissions(ExportActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        String SAMPLE_DB_NAME = "EpisodeReminder";
        File sd = Environment.getExternalStorageDirectory() ;
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.jonasmalik94.episodereminder" +"/databases/"+SAMPLE_DB_NAME;
        String backupDBPath = SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

package com.jonasmalik94.episodereminder;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView ;
    ArrayList<MainListRow> arrayOfRows = new ArrayList<>();
    ArrayList<String> columns = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();
    MainListAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DB setup
        final SQLiteDatabase db;
        db=openOrCreateDatabase("EpisodeReminder", MODE_PRIVATE, null);

        // Restore DB after restarting APP
        db.execSQL(SQL.deleteTable("series"));

        //Create table if not exist
        columns.clear();
        columns.add("Id INTEGER PRIMARY KEY  NOT NULL  UNIQUE");
        columns.add("title TEXT");
        columns.add("last_episode_watched TEXT");
        columns.add("last_season_watched TEXT");
        columns.add("is_over TEXT DEFAULT 0");
        columns.add("rating TEXT DEFAULT 0");
        db.execSQL(SQL.createTable("series", columns));

        // Inserting some values, only a TEST!
        int temp;
        int i = 0;

        while (i < 20){

            // Adding values to DB
            columns.clear();
            columns.add("title");
            columns.add("last_episode_watched");
            columns.add("last_season_watched");
            columns.add("is_over");
            columns.add("rating");
            values.clear();
            values.add("The walking dead"); // title
            values.add("13"); // last episode
            values.add("3"); // last season
            values.add("0"); // is over
            values.add("3"); // rating

            db.execSQL(SQL.insertValues("series", columns, values));
            i++;
        }
        //TEST ENDS HERE!

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = (int)(size.x*0.98);
                int height = (int)(size.y*0.4);
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                PopupWindow pw = new PopupWindow(
                        inflater.inflate(R.layout.popup, null, false),
                        width,
                        height,
                        true);
                // The code below assumes that the root container has an id called 'main'
                pw.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, -200);

                /*adapter = null;

                // Adding row to view
                MainListRow newRow = new MainListRow("games","2","5","8");
                arrayOfRows.add(0, newRow);

                // Adding values to DB
                columns.clear();
                columns.add("title");
                columns.add("last_episode_watched");
                columns.add("last_season_watched");
                columns.add("is_over");
                columns.add("rating");
                values.clear();
                values.add("games"); // title
                values.add("5"); // last episode
                values.add("2"); // last season
                values.add("0"); // is over
                values.add("8"); // rating

                db.execSQL(SQL.insertValues("series", columns, values));

                adapter = new MainListAdapter(getApplicationContext(), arrayOfRows);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Ny serie/film är tillagd", Toast.LENGTH_SHORT).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //==========================================================================================
        listView = (ListView) findViewById(R.id.list);

        Cursor c = db.rawQuery(SQL.selectAll("series"), null);
        while (c.moveToNext()) {
            String title          = c.getString(1);
            String episode        = c.getString(2);
            String season         = c.getString(3);
            String is_over        = c.getString(4);
            String rating         = c.getString(5);

            MainListRow newRow = new MainListRow(title, season, episode, rating);
            arrayOfRows.add(newRow);
        }

        adapter = new MainListAdapter(this, arrayOfRows);
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Opens a new view
                Intent myIntent = new Intent(MainActivity.this, ExerciseActivity.class);
                myIntent.putExtra("index", Integer.toString(position));
                MainActivity.this.startActivity(myIntent);
            }

        });

        //==========================================================================================


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



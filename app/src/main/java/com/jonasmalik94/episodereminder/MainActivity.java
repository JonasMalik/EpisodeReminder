package com.jonasmalik94.episodereminder;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    TextView blur;
    TextView tutorial_text;
    TextView tot;
    TextView watching;
    TextView done;
    Button tutorial_button;
    ImageView swipe;
    EditText popup_input;
    ArrayList<MainListRow> arrayOfRows = new ArrayList<>();
    ArrayList<String> columns = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();
    MainListAdapter adapter;
    static String path;
    static String filter = "recent";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // creating backup

        //Functions.exportDatabse("EpisodeReminder", getPackageName());

        //DB setup
        final SQLiteDatabase db;
        db = openOrCreateDatabase("EpisodeReminder", MODE_PRIVATE, null);
        path = db.getPath();

        tot      = (TextView) findViewById(R.id.tot);
        watching = (TextView) findViewById(R.id.watching);
        done     = (TextView) findViewById(R.id.done);

        // Restore DB after restarting APP
        //db.execSQL(SQL.deleteTable("series"));
        //db.execSQL(SQL.deleteTable("tutorial"));
        //db.execSQL(SQL.deleteTable("updates"));

        //Create table for series
        columns.clear();
        columns.add("Id INTEGER PRIMARY KEY  NOT NULL  UNIQUE");
        columns.add("title TEXT");
        columns.add("episode TEXT");
        columns.add("season TEXT");
        columns.add("is_over TEXT DEFAULT 0");
        columns.add("rating TEXT DEFAULT 0");
        columns.add("date TEXT DEFAULT 0 ");
        db.execSQL(SQL.createTable("series", columns));

        //Create table for tutorial
        columns.clear();
        columns.add("Id INTEGER PRIMARY KEY  NOT NULL  UNIQUE");
        columns.add("title TEXT");
        columns.add("show_at_start TEXT DEFAULT 1");// on
        db.execSQL(SQL.createTable("tutorial", columns));

        //Create table for updates
        columns.clear();
        columns.add("Id INTEGER PRIMARY KEY  NOT NULL  UNIQUE");
        columns.add("title TEXT UNIQUE");
        columns.add("is_updated TEXT DEFAULT 0");// false
        db.execSQL(SQL.createTable("updates", columns));

        // Adding values to DB
        columns.clear();
        columns.add("title");
        columns.add("is_updated");
        values.clear();
        values.add("is_movie_added_to_db"); // title
        values.add("0"); // false

        try {
            db.execSQL(SQL.insertValues("updates", columns, values));
        }catch (Exception e){}

        // Run update
        SQL.runUpdate(true);

        tot.setText(String.valueOf(SQL.CountRows("series")) + " serier");
        watching.setText(String.valueOf(SQL.CountStartedRows("series")) + " påbörjade");
        done.setText(String.valueOf(SQL.CountFinishedRows("series")) + " avslutade");

        String count = "SELECT count(*) FROM tutorial";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0) {
            //leave
        }
        else {
            // Adding values to DB
            columns.clear();
            columns.add("title");
            columns.add("show_at_start");
            values.clear();
            values.add("swipe"); // title
            values.add("1"); // on
            db.execSQL(SQL.insertValues("tutorial", columns, values));
        }
        swipe = (ImageView) findViewById(R.id.swipe);
        blur = (TextView) findViewById(R.id.blur);
        tutorial_button = (Button) findViewById(R.id.tutorial_button);
        tutorial_text = (TextView) findViewById(R.id.tutorial_text);

        tutorial_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blur.setVisibility(View.GONE);
                tutorial_text.setVisibility(View.GONE);
                tutorial_button.setVisibility(View.GONE);
                swipe.setVisibility(View.GONE);
            }
        });

        Cursor cursor = db.rawQuery(SQL.getRecordByID("tutorial", "1"), null);
        cursor.moveToFirst();
        String tutorial = cursor.getString(2);
        if (tutorial.equals("0")){
            blur.setVisibility(View.GONE);
            tutorial_text.setVisibility(View.GONE);
            tutorial_button.setVisibility(View.GONE);
            swipe.setVisibility(View.GONE);
        }else{
            swipe.setVisibility(View.VISIBLE);
            blur.setVisibility(View.VISIBLE);
            tutorial_text.setVisibility(View.VISIBLE);
            tutorial_button.setVisibility(View.VISIBLE);
            SQL.updateValue("tutorial","1","show_at_start","0");
        }



        // Inserting some values, only a TEST!
        int temp;
        int i = 0;

        while (i < 0){

            // Adding values to DB
            columns.clear();
            columns.add("title");
            columns.add("episode");
            columns.add("season");
            columns.add("is_over");
            columns.add("rating");
            columns.add("movie");
            values.clear();
            values.add("Exempel"); // title
            values.add("1"); // episode
            values.add("1"); // season
            values.add("0"); // is over
            values.add("1"); // rating
            values.add(Integer.toString(i)); // movie


            db.execSQL(SQL.insertValues("series", columns, values));
            i++;
        }
        //TEST ENDS HERE!

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // sets a blurry background
                blur = (TextView) findViewById(R.id.blur);
                blur.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);

                // getting screen size
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = (int)(size.x*0.98);
                int height = (int)(size.y*0.4);

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.popup, null, false),
                        width,
                        height,
                        true);

                // popup button listener
                Button add = (Button) pw.getContentView().findViewById(R.id.add);
                add.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text;
                        popup_input = (EditText) pw.getContentView().findViewById(R.id.popup_title_input);
                        text = popup_input.getText().toString();
                        pw.dismiss();

                        adapter = null;

                        // Adding values to DB
                        columns.clear();
                        columns.add("title");
                        columns.add("episode");
                        columns.add("season");
                        columns.add("is_over");
                        columns.add("rating");
                        values.clear();
                        values.add(text); // title
                        values.add("1"); // episode
                        values.add("1"); // season
                        values.add("0"); // is over
                        values.add("0"); // rating

                        db.execSQL(SQL.insertValues("series", columns, values));

                        Cursor c = db.rawQuery(SQL.getLastRecord("series"), null);
                        c.moveToNext();
                        String id             = c.getString(0);
                        String title          = c.getString(1);
                        String episode        = c.getString(2);
                        String season         = c.getString(3);
                        String is_over        = c.getString(4);
                        String rating         = c.getString(5);
                        String date           = c.getString(6);
                        String is_a_movie     = c.getString(7);

                        // Adding row to view
                        MainListRow newRow = new MainListRow(title,season,episode,rating,id,is_a_movie);
                        arrayOfRows.add(0, newRow);

                        adapter = new MainListAdapter(getApplicationContext(), arrayOfRows);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Ny serie/film är tillagd", Toast.LENGTH_SHORT).show();
                    }
                });

                // sets the position of the popup window
                pw.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, -200);

                // sets action when popup window is closed
                pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        blur.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.VISIBLE);
                    }
                });

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

        Cursor c = db.rawQuery(SQL.selectFilter("series","",filter), null);
        while (c.moveToNext()) {
            String id          = c.getString(0);
            String title          = c.getString(1);
            String episode        = c.getString(2);
            String season         = c.getString(3);
            String is_over        = c.getString(4);
            String rating         = c.getString(5);
            String date           = c.getString(6);
            String is_a_movie     = c.getString(7);

            MainListRow newRow = new MainListRow(title, season, episode, rating, id, is_a_movie);
            arrayOfRows.add(newRow);
        }

        adapter = new MainListAdapter(this, arrayOfRows);
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Getting the clicked list item by position
                final View view1 = listView.getAdapter().getView(position, null, listView);
                TextView myID = (TextView) view1.findViewById(R.id.myID);

                // Opens a new view
                Intent myIntent = new Intent(MainActivity.this, SubActivity.class);
                myIntent.putExtra("ID", myID.getText());
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                MainActivity.this.startActivity(myIntent);
                //overridePendingTransition(R.anim., R.anim.fade_out);
                finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // sets a blurry background
                blur = (TextView) findViewById(R.id.blur);
                blur.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);

                // getting screen size
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = (int)(size.x*0.98);
                int height = (int)(size.y*0.3);

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.delete_dialog, null, false),
                        width,
                        height,
                        true);

                // popup button listener
                Button add = (Button) pw.getContentView().findViewById(R.id.delete);
                add.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Logg raderad", Toast.LENGTH_LONG).show();
                        pw.dismiss();

                        // Getting the clicked list item by position
                        final View view1 = listView.getAdapter().getView(position, null, listView);
                        TextView myID = (TextView) view1.findViewById(R.id.myID);
                        SQL.deleteRecord("series",myID.getText().toString());

                        //resets adapter and array
                        listView.setAdapter(null);
                        arrayOfRows.clear();

                        //Reload List
                        Cursor c = db.rawQuery(SQL.selectAll("series"), null);
                        while (c.moveToNext()) {
                            String id          = c.getString(0);
                            String title          = c.getString(1);
                            String episode        = c.getString(2);
                            String season         = c.getString(3);
                            String is_over        = c.getString(4);
                            String rating         = c.getString(5);
                            String date           = c.getString(6);
                            String is_a_movie     = c.getString(7);

                            MainListRow newRow = new MainListRow(title, season, episode, rating, id,is_a_movie);
                            arrayOfRows.add(newRow);
                        }

                        adapter = new MainListAdapter(getApplicationContext(), arrayOfRows);
                        listView.setAdapter(adapter);
                    }
                });

                // sets the position of the popup window
                pw.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, 0);

                // sets action when popup window is closed
                pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        blur.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.VISIBLE);
                    }
                });

                return true;
            }
        });

        // Search Listeners
        final SearchView searchView;
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Do something
                listView.setAdapter(null);
                adapter.clear();
                arrayOfRows.clear();

                Cursor c = db.rawQuery(SQL.selectFilter("series", newText, filter), null);
                while (c.moveToNext()) {
                    String id          = c.getString(0);
                    String title          = c.getString(1);
                    String episode        = c.getString(2);
                    String season         = c.getString(3);
                    String is_over        = c.getString(4);
                    String rating         = c.getString(5);
                    String date           = c.getString(6);
                    String is_a_movie     = c.getString(7);

                    MainListRow newRow = new MainListRow(title, season, episode, rating, id, is_a_movie);
                    arrayOfRows.add(newRow);
                }

                adapter = new MainListAdapter(getApplicationContext(), arrayOfRows);
                listView.setAdapter(adapter);
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);



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
        final SQLiteDatabase db;


        if (id == R.id.recent) {
            filter = "recent";
        } else if (id == R.id.AtoZ) {
            filter = "title";
        }else if (id == R.id.movies) {
            filter = "movies";
        } else if (id == R.id.favourites) {
            filter = "favourites";
        } else if (id == R.id.inSleep) {
            filter = "inSleep";
        } else if (id == R.id.finished) {
            filter = "finished";
        } else if (id == R.id.nav_manage) {
            //exportDB();
            Intent myExportIntent = new Intent(MainActivity.this, ExportActivity.class);
            MainActivity.this.startActivity(myExportIntent);


        }

        listView.setAdapter(null);
        adapter.clear();
        arrayOfRows.clear();

        db=openOrCreateDatabase("EpisodeReminder", MODE_PRIVATE, null);

        Cursor c = db.rawQuery(SQL.selectFilter("series", "",filter), null);
        while (c.moveToNext()) {
            String myId          = c.getString(0);
            String title          = c.getString(1);
            String episode        = c.getString(2);
            String season         = c.getString(3);
            String is_over        = c.getString(4);
            String rating         = c.getString(5);
            String date           = c.getString(6);
            String is_a_movie     = c.getString(7);

            MainListRow newRow = new MainListRow(title, season, episode, rating, myId, is_a_movie);
            arrayOfRows.add(newRow);
        }

        adapter = new MainListAdapter(getApplicationContext(), arrayOfRows);
        listView.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void exportDB(){
        ActivityCompat.requestPermissions(MainActivity.this,
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




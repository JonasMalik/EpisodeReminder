package com.jonasmalik94.episodereminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jonas on 2016-06-22.
 */
public class MainListAdapter extends ArrayAdapter<MainListRow> {
    public MainListAdapter(Context context, ArrayList<MainListRow> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        MainListRow row = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_main, parent, false);
        }

        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.movie_title);
        TextView season = (TextView) convertView.findViewById(R.id.season);
        TextView episode = (TextView) convertView.findViewById(R.id.episode);
        final TextView myID = (TextView) convertView.findViewById(R.id.myID);
        final TextView star = (TextView) convertView.findViewById(R.id.star);
        final ImageView rating = (ImageView) convertView.findViewById(R.id.rating);



        // Populate the data into the template view using the data object
        title.setText(row.getTitle());
        season.setText(row.getSeason());
        episode.setText(row.getEpisode());
        myID.setText(row.getMyID());
        star.setText(row.getRating());
        if (row.getRating().equals("0")){
            rating.setImageResource(android.R.drawable.btn_star_big_off);
        }else {
            rating.setImageResource(android.R.drawable.btn_star_big_on);
        }

        // All handlers for click event
        final View finalConvertView = convertView;
        rating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView id = (TextView) finalConvertView.findViewById(R.id.myID);
                //Toast.makeText(getContext(), id.getText(), Toast.LENGTH_LONG).show();

                if (star.getText().equals("0")) {
                    rating.setImageResource(android.R.drawable.btn_star_big_on);
                    star.setText("1");
                    SQL.updateValue("series", id.getText().toString(), "rating", "1");
                }else{
                    rating.setImageResource(android.R.drawable.btn_star_big_off);
                    star.setText("0");
                    SQL.updateValue("series", id.getText().toString(), "rating", "0");
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
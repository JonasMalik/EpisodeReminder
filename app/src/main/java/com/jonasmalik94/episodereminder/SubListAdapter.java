package com.jonasmalik94.episodereminder;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jonas on 2016-06-22.
 */
public class SubListAdapter extends ArrayAdapter<SubListRow> {
    public SubListAdapter(Context context, ArrayList<SubListRow> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        SubListRow row = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_sub, parent, false);
        }

        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.sub_title);
        TextView season = (TextView) convertView.findViewById(R.id.season);
        TextView episode = (TextView) convertView.findViewById(R.id.episode);
        TextView myID = (TextView) convertView.findViewById(R.id.mySubID);
        ImageView rating = (ImageView) convertView.findViewById(R.id.sub_rating);
        Button newEpisode = (Button) convertView.findViewById(R.id.new_episode);
        Button newSeason = (Button) convertView.findViewById(R.id.new_season);

        // Populate the data into the template view using the data object
        title.setText(row.getTitle());
        season.setText(row.getSeason());
        episode.setText(row.getEpisode());
        myID.setText(row.getMySubID());
        if (row.getRating().equals("0")){
            rating.setImageResource(android.R.drawable.btn_star_big_off);
        }else {
            rating.setImageResource(android.R.drawable.btn_star_big_on);
        }

        // All handlers for click event
        final View finalConvertView = convertView;
        newEpisode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);
                Toast.makeText(getContext(), id.getText(), Toast.LENGTH_LONG).show();
            }
        });

        newSeason.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);
                Toast.makeText(getContext(), id.getText() + " s", Toast.LENGTH_LONG).show();
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);
                Toast.makeText(getContext(), id.getText() + " Rating", Toast.LENGTH_LONG).show();
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
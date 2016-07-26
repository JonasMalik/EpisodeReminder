package com.jonasmalik94.episodereminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView rating = (TextView) convertView.findViewById(R.id.rating);


        // Populate the data into the template view using the data object
        title.setText(row.getTitle());
        season.setText(row.getSeason());
        episode.setText(row.getEpisode());
        rating.setText(row.getRating());

        // Return the completed view to render on screen
        return convertView;
    }
}
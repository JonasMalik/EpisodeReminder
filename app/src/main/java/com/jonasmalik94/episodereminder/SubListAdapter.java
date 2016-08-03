package com.jonasmalik94.episodereminder;

import android.content.Context;
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
        final TextView season = (TextView) convertView.findViewById(R.id.season);
        final TextView episode = (TextView) convertView.findViewById(R.id.episode);
        TextView myID = (TextView) convertView.findViewById(R.id.mySubID);
        final TextView star = (TextView) convertView.findViewById(R.id.sub_star);
        final ImageView rating = (ImageView) convertView.findViewById(R.id.sub_rating);
        final Button newEpisode = (Button) convertView.findViewById(R.id.new_episode);
        Button newSeason = (Button) convertView.findViewById(R.id.new_season);

        // Populate the data into the template view using the data object
        title.setText(row.getTitle());
        season.setText(row.getSeason());
        episode.setText(row.getEpisode());
        myID.setText(row.getMySubID());
        star.setText(row.getRating());
        if (row.getRating().equals("0")){
            rating.setImageResource(android.R.drawable.btn_star_big_off);
        }else {
            rating.setImageResource(android.R.drawable.btn_star_big_on);
        }

        // All handlers for click event
        final View finalConvertView = convertView;
        newEpisode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int e;
                String epi;
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);

                e = Integer.parseInt(episode.getText().toString());
                e = e + 1;
                epi = Integer.toString(e);
                episode.setText(epi);
                SQL.updateValue("series", id.getText().toString(), "episode", epi);
                Toast.makeText(getContext(), id.getText(), Toast.LENGTH_LONG).show();
            }
        });

        newSeason.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int s;
                String seas;
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);

                s = Integer.parseInt(season.getText().toString());
                s = s + 1;
                seas = Integer.toString(s);
                season.setText(seas);
                episode.setText("1");
                SQL.updateValue("series", id.getText().toString(), "season", seas);
                SQL.updateValue("series", id.getText().toString(), "episode", "1");
                Toast.makeText(getContext(), id.getText() + " s", Toast.LENGTH_LONG).show();
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);
                Toast.makeText(getContext(), id.getText() + " Rating", Toast.LENGTH_LONG).show();

                if (star.getText().equals("0")) {
                    rating.setImageResource(android.R.drawable.btn_star_big_on);
                    star.setText("1");
                    SQL.updateValue("series", id.getText().toString(), "rating", "1");
                } else {
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
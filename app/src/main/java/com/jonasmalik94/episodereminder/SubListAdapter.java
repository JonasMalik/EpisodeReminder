package com.jonasmalik94.episodereminder;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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
        final SubListRow row = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_sub, parent, false);
        }

        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.sub_title);
        TextView myID  = (TextView) convertView.findViewById(R.id.mySubID);
        final TextView season    = (TextView) convertView.findViewById(R.id.season);
        final TextView episode   = (TextView) convertView.findViewById(R.id.episode);
        final TextView star      = (TextView) convertView.findViewById(R.id.sub_star);
        final ImageView rating   = (ImageView) convertView.findViewById(R.id.sub_rating);
        final Button newEpisode  = (Button) convertView.findViewById(R.id.new_episode);
        final Button newSeason   = (Button) convertView.findViewById(R.id.new_season);
        final Button serieIsDone = (Button) convertView.findViewById(R.id.serie_is_done);
        final ImageView checkbox = (ImageView) convertView.findViewById(R.id.checkbox);

        // Populate the data into the template view using the data object
        title.setText(row.getTitle());
        season.setText(row.getSeason());
        episode.setText(row.getEpisode());
        myID.setText(row.getMySubID());
        star.setText(row.getRating());
        newSeason.setText("Klar med säsong "+row.getSeason());
        newEpisode.setText("Klar med avsnitt " + row.getEpisode());
        if (row.getRating().equals("0")){
            rating.setImageResource(android.R.drawable.btn_star_big_off);
        }else {
            rating.setImageResource(android.R.drawable.btn_star_big_on);
        }

        if (row.getIsOver().equals("1")){
            serieIsDone.setBackgroundResource(R.color.green);
            newEpisode.setVisibility(View.INVISIBLE);
            newSeason.setVisibility(View.INVISIBLE);
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
                SQL.updateValue("series", id.getText().toString(), "date", Functions.getLatestDate());
                newEpisode.setText("Klar med avsnitt " + e);
            }
        });
        newEpisode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int e;
                String epi;
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);

                e = Integer.parseInt(episode.getText().toString());
                e = e - 1;
                epi = Integer.toString(e);
                episode.setText(epi);
                SQL.updateValue("series", id.getText().toString(), "episode", epi);
                SQL.updateValue("series", id.getText().toString(), "date", Functions.getLatestDate());
                newEpisode.setText("Klar med avsnitt " + e);
                return true;
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
                SQL.updateValue("series", id.getText().toString(), "date", Functions.getLatestDate());
                newSeason.setText("Klar med säsong " + s);
                newEpisode.setText("Klar med avsnitt 1");
            }
        });

        newSeason.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int s;
                String seas;
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);

                s = Integer.parseInt(season.getText().toString());
                s = s - 1;
                seas = Integer.toString(s);
                season.setText(seas);
                SQL.updateValue("series", id.getText().toString(), "season", seas);
                SQL.updateValue("series", id.getText().toString(), "date", Functions.getLatestDate());
                newSeason.setText("Klar med säsong " + s);
                newEpisode.setText("Klar med avsnitt 1");
                return true;
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);
                //Toast.makeText(getContext(), id.getText() + " Rating", Toast.LENGTH_LONG).show();

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

        serieIsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Du släppte för tidigt", Toast.LENGTH_SHORT).show();
            }
        });
        serieIsDone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (newEpisode.getVisibility() == View.VISIBLE) {
                    TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);
                    serieIsDone.setText("klar med hela serien");
                    newEpisode.setVisibility(View.INVISIBLE);
                    newSeason.setVisibility(View.INVISIBLE);
                    checkbox.setImageResource(android.R.drawable.checkbox_on_background);
                    SQL.updateValue("series", id.getText().toString(), "is_over", "1");
                }else {
                    TextView id = (TextView) finalConvertView.findViewById(R.id.mySubID);
                    serieIsDone.setText("klar med hela serien");
                    newEpisode.setVisibility(View.VISIBLE);
                    newSeason.setVisibility(View.VISIBLE);
                    checkbox.setImageResource(android.R.drawable.checkbox_off_background);
                    SQL.updateValue("series", id.getText().toString(), "is_over", "0");
                }
                return true;
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }
}
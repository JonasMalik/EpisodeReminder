package com.jonasmalik94.episodereminder;

/**
 * Created by jonas on 2016-06-22.
 */
public class MainListRow {
    private String title;
    private String season;
    private String episode;
    private String rating;
    private String myID;



    private String is_a_movie;

    public MainListRow(String title,
                       String season,
                       String episode,
                       String rating,
                       String myID,
                       String is_a_movie) {

        this.title          = title;
        this.season         = season;
        this.episode        = episode;
        this.rating         = rating;
        this.myID           = myID;
        this.is_a_movie     = is_a_movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMyID() {
        return myID;
    }

    public void setMyID(String myID) {
        this.myID = myID;
    }

    public String getIs_a_movie() {
        return is_a_movie;
    }

    public void setIs_a_movie(String is_a_movie) {
        this.is_a_movie = is_a_movie;
    }


}

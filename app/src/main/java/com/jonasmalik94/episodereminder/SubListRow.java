package com.jonasmalik94.episodereminder;

/**
 * Created by jonas on 2016-06-22.
 */
public class SubListRow {
    private String title;
    private String season;
    private String episode;
    private String rating;
    private String mySubID;
    private String isOver;

    public SubListRow(String title,
                      String season,
                      String episode,
                      String rating,
                      String mySubID,
                      String isOver) {

        this.title = title;
        this.season = season;
        this.episode = episode;
        this.rating = rating;
        this.mySubID = mySubID;
        this.isOver = isOver;
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

    public String getMySubID() {
        return mySubID;
    }

    public void setMySubID(String mySubID) {
        this.mySubID = mySubID;
    }

    public String getIsOver() {
        return isOver;
    }

    public void setIsOver(String isOver) {
        this.isOver = isOver;
    }
}

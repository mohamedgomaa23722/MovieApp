package com.mg.movie.model.PersonMovieCredits;

import java.util.ArrayList;

public class CastMovies {
   private String release_date;
   private boolean adult;
   private String backdrop_path;
   private ArrayList<Integer> genre_ids;
   private int vote_count;
   private String original_language;
   private String original_title;
   private String poster_path;
   private float vote_average;
   private boolean video;
   private int id;
   private String title;
   private String overview;
   private float popularity;
   private String character;
   private String credit_id;
   private int order;

    public CastMovies(String release_date, boolean adult, String backdrop_path, ArrayList<Integer> genre_ids, int vote_count, String original_language, String original_title, String poster_path, float vote_average, boolean video, int id, String title, String overview, float popularity, String character, String credit_id, int order) {
        this.release_date = release_date;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.genre_ids = genre_ids;
        this.vote_count = vote_count;
        this.original_language = original_language;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.video = video;
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.character = character;
        this.credit_id = credit_id;
        this.order = order;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

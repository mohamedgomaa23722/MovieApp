package com.mg.movie.model.personData;

import java.util.ArrayList;

public class Person {
    private String profile_path;
    private boolean adult;
    private int id;
    private ArrayList<PersonDetailsForMovie> known_for;
    private String name;
    private float popularity;

    public Person(String profile_path, boolean adult, int id, ArrayList<PersonDetailsForMovie> known_for, String name, float popularity) {
        this.profile_path = profile_path;
        this.adult = adult;
        this.id = id;
        this.known_for = known_for;
        this.name = name;
        this.popularity = popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<PersonDetailsForMovie> getKnown_for() {
        return known_for;
    }

    public void setKnown_for(ArrayList<PersonDetailsForMovie> known_for) {
        this.known_for = known_for;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }
}

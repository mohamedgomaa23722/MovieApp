package com.mg.movie.model.castData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CastResponse implements Serializable {
    private int id;
    private List<cast> cast;
    private List<crew> crew;

    public CastResponse(int id, List<cast> cast, List<crew> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<cast> getCast() {
        return cast;
    }

    public void setCast(List<cast> cast) {
        this.cast = cast;
    }

    public List<crew> getCrew() {
        return crew;
    }

    public void setCrew(List<crew> crew) {
        this.crew = crew;
    }
}

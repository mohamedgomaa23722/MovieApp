package com.mg.movie.model.trialer;

import java.util.List;

public class videoResponse {
    private int id;
    private List<movieTrailer> results;

    public videoResponse(int id, List<movieTrailer> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<movieTrailer> getResults() {
        return results;
    }

    public void setResults(List<movieTrailer> results) {
        this.results = results;
    }
}

package com.mg.movie.model.PersonMovieCredits;

import com.mg.movie.model.MovieData.movie;
import com.mg.movie.model.castData.crew;

import java.util.List;

public class CastMoviesResponse {
    private List<CastMovies> cast;
    private List<CrewMovies> crew;
    private int id;

    public CastMoviesResponse(int id, List<CastMovies> cast, List<CrewMovies> crew) {
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

    public List<CastMovies> getCast() {
        return cast;
    }

    public void setCast(List<CastMovies> cast) {
        this.cast = cast;
    }

    public List<CrewMovies> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewMovies> crew) {
        this.crew = crew;
    }
}

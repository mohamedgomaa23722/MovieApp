package com.mg.movie.repository;

import com.mg.movie.model.MovieResponse;
import com.mg.movie.network.movieApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private movieApiService movieApiService=null;

    @Inject
    public Repository(movieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    public Observable<MovieResponse> getMovies(String query, String api_key, int page) {
        return movieApiService.getMovies(query,api_key,page);
    }
}

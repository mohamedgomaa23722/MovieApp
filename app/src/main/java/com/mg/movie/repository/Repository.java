package com.mg.movie.repository;

import static com.mg.movie.utils.constantVariables.TMDB_API_KEY;

import android.graphics.Movie;

import com.mg.movie.model.MovieResponse;
import com.mg.movie.model.castData.CastDetails;
import com.mg.movie.model.castData.CastResponse;
import com.mg.movie.model.trialer.videoResponse;
import com.mg.movie.network.movieApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repository {

    private movieApiService movieApiService = null;

    @Inject
    public Repository(movieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    public Observable<MovieResponse> getMovies(String query, String api_key, int page) {
        return movieApiService.getMovies(query, api_key, page);
    }

    public Observable<CastResponse> getCast(int Movie_ID) {
        return movieApiService.getCast(Movie_ID, TMDB_API_KEY, "en-US");
    }

    public Observable<videoResponse> getTrailer(int Movie_ID) {
        return movieApiService.getTrailer(Movie_ID, TMDB_API_KEY);
    }

    public Observable<MovieResponse> getSpecificMovies(int movie_id, String Query) {
        return movieApiService.getSpecificMovies(movie_id,Query,TMDB_API_KEY);
    }

    public Single<CastDetails> getCastDetails(int person_id){
        return movieApiService.getCastDetails(person_id, TMDB_API_KEY);
    }
}

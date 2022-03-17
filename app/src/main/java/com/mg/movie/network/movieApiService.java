package com.mg.movie.network;

import com.mg.movie.model.MovieResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface movieApiService {

    @GET("movie/{query}")
    Observable<MovieResponse> getMovies( @Path("query") String query,
                                                 @Query("api_key") String apiKey,
                                                 @Query("page") int page);
}

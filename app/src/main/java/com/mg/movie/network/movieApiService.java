package com.mg.movie.network;

import com.mg.movie.model.MovieData.MovieResponse;
import com.mg.movie.model.castData.CastDetails;
import com.mg.movie.model.castData.CastResponse;
import com.mg.movie.model.personData.PersonResponse;
import com.mg.movie.model.trialer.videoResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface movieApiService {

    @GET("movie/{query}")
    Observable<MovieResponse> getMovies(@Path("query") String query,
                                        @Query("api_key") String apiKey,
                                        @Query("page") int page);

    @GET("movie/{movie_id}/credits")
    Observable<CastResponse> getCast(@Path("movie_id") int movie_id,
                                     @Query("api_key") String apiKe,
                                     @Query("language") String language);

    @GET("movie/{movie_id}/videos")
    Observable<videoResponse> getTrailer(@Path("movie_id") int movie_id,
                                         @Query("api_key") String apiKe);

    @GET("movie/{movie_id}/{query}")
    Observable<MovieResponse> getSpecificMovies(@Path("movie_id") int movie_id
            , @Path("query") String query
            , @Query("api_key") String apiKe);

    @GET("person/{person_id}")
    Single<CastDetails> getCastDetails(@Path("person_id") int person_id,
                                       @Query("api_key") String apiKe);

    @GET("search/movie")
    Observable<MovieResponse> SearchForMovie(
            @Query("query") String MovieName,
            @Query("api_key") String apiKe,
            @Query("page") int page
    );

    @GET("search/person")
    Observable<PersonResponse> SearchForActor(
            @Query("query") String ActorName,
            @Query("api_key") String apiKe,
            @Query("page") int page
    );

}

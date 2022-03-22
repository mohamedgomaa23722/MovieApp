package com.mg.movie.viewModel;

import static com.mg.movie.utils.constantVariables.POPULAR;
import static com.mg.movie.utils.constantVariables.TMDB_API_KEY;
import static com.mg.movie.utils.constantVariables.TOP_RATED;
import static com.mg.movie.utils.constantVariables.UPCOMING;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mg.movie.model.castData.CastDetails;
import com.mg.movie.model.castData.cast;
import com.mg.movie.model.movie;
import com.mg.movie.model.trialer.movieTrailer;
import com.mg.movie.repository.Repository;

import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class movieViewModel extends ViewModel {
    private static final String TAG = "movieViewModel";
    private final Repository repository;
    private final MutableLiveData<List<movie>> moviesData = new MutableLiveData<>();
    private final MutableLiveData<List<movie>> topRatedMoviesData = new MutableLiveData<>();
    private final MutableLiveData<List<movie>> upComingMoviesData = new MutableLiveData<>();
    private final MutableLiveData<List<cast>> movieCastData = new MutableLiveData<>();
    private final MutableLiveData<List<movieTrailer>> movieTrailerData = new MutableLiveData<>();
    private final MutableLiveData<List<movie>> RecommendedMoviesData = new MutableLiveData<>();
    private final MutableLiveData<CastDetails> CastDetailsData = new MutableLiveData<>();

    @ViewModelInject
    public movieViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getPopularMovies() {
        repository.getMovies(POPULAR, TMDB_API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> moviesData.setValue(movieResponse.getResults())
                        , error -> Log.e("viewModel", "", error)
                        , () -> Log.d(TAG, "Completed: "));
    }


    public void getTopRatedMovies() {
        repository.getMovies(TOP_RATED, TMDB_API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> topRatedMoviesData.setValue(movieResponse.getResults())
                        , error -> Log.e("viewModel", "", error)
                        , () -> Log.d(TAG, "Completed: "));
    }

    public void getUpcomingMovies() {
        repository.getMovies(UPCOMING, TMDB_API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> upComingMoviesData.setValue(movieResponse.getResults())
                        , error -> Log.e("viewModel", "", error)
                        , () -> Log.d(TAG, "Completed: "));
    }

    public void getMovieCast(int movie_id) {
        repository.getCast(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(castResponse -> movieCastData.setValue(castResponse.getCast())
                        , error -> Log.e("viewModel", "", error)
                        , () -> Log.d(TAG, "Completed: "));
    }

    public void getMovieTrailer(int movie_id) {
        repository.getTrailer(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(TrailerResponse -> movieTrailerData.setValue(TrailerResponse.getResults())
                        , error -> Log.e("viewModel", "", error)
                        , () -> Log.d(TAG, "Completed: "));
    }

    public void getRecommendedMovies(int movie_id){
        repository.getSpecificMovies(movie_id,"recommendations")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RecommendedResponse -> RecommendedMoviesData.setValue(RecommendedResponse.getResults())
                        , error -> Log.e("viewModel", "", error)
                        , () -> Log.d(TAG, "Completed: "));
    }

    public void getCastDetails(int Person_Id){
        repository.getCastDetails(Person_Id)
                   .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(CastDetailsData::setValue);
    }

    public MutableLiveData<List<movie>> getMoviesData() {
        return moviesData;
    }

    public MutableLiveData<List<movie>> getTopRatedMoviesData() {
        return topRatedMoviesData;
    }

    public MutableLiveData<List<movie>> getUpComingMoviesData() {
        return upComingMoviesData;
    }

    public MutableLiveData<List<cast>> getMovieCastData() {
        return movieCastData;
    }

    public MutableLiveData<List<movieTrailer>> getMovieTrailerData() {
        return movieTrailerData;
    }

    public MutableLiveData<List<movie>> getRecommendedMoviesData() {
        return RecommendedMoviesData;
    }

    public MutableLiveData<CastDetails> getCastDetailsData() {
        return CastDetailsData;
    }
}

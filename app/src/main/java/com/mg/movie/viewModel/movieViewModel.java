package com.mg.movie.viewModel;

import static com.mg.movie.utils.constantVariables.POPULAR;
import static com.mg.movie.utils.constantVariables.TMDB_API_KEY;
import static com.mg.movie.utils.constantVariables.TOP_RATED;
import static com.mg.movie.utils.constantVariables.UPCOMING;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mg.movie.model.movie;
import com.mg.movie.repository.Repository;

import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class movieViewModel extends ViewModel {
    private static final String TAG = "movieViewModel";
    private final Repository repository;
    private final MutableLiveData<List<movie>> moviesData=new MutableLiveData<>();
    private final MutableLiveData<List<movie>> topRatedMoviesData=new MutableLiveData<>();
    private final MutableLiveData<List<movie>> upComingMoviesData=new MutableLiveData<>();

    @ViewModelInject
    public movieViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getPopularMovies(){
        repository.getMovies(POPULAR,TMDB_API_KEY,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> moviesData.setValue(movieResponse.getResults())
                 , error -> Log.e("viewModel", "",error )
                ,() ->Log.d(TAG, "Completed: "));
    }


    public void getTopRatedMovies(){
        repository.getMovies(TOP_RATED,TMDB_API_KEY,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> topRatedMoviesData.setValue(movieResponse.getResults())
                        , error -> Log.e("viewModel", "",error )
                        ,() ->Log.d(TAG, "Completed: "));
    }

    public void getUpcomingMovies(){
        repository.getMovies(UPCOMING,TMDB_API_KEY,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> upComingMoviesData.setValue(movieResponse.getResults())
                        , error -> Log.e("viewModel", "",error )
                        ,() ->Log.d(TAG, "Completed: "));
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
}

package com.mg.movie.viewModel;

import static com.mg.movie.utils.constantVariables.TMDB_API_KEY;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mg.movie.model.movie;
import com.mg.movie.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class movieViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<movie>> moviesData=new MutableLiveData<>();

    @ViewModelInject
    public movieViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getMovies(){
        repository.getMovies("popular",TMDB_API_KEY,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> moviesData.setValue(movieResponse.getResults())
                 , error -> Log.e("viewModel", "",error ));
    }

    public MutableLiveData<List<movie>> getMoviesData() {
        return moviesData;
    }
}

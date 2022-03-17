package com.mg.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.mg.movie.adapter.movieAdapter;
import com.mg.movie.model.movie;
import com.mg.movie.viewModel.movieViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private movieViewModel viewModel;
    private movieAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.movie_recyclerview);
        adapter=new movieAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(movieViewModel.class);
        viewModel.getMovies();
        viewModel.getMoviesData().observe(this, new Observer<List<movie>>() {
            @Override
            public void onChanged(List<movie> movies) {
                ArrayList<movie> list=new ArrayList<>(movies);
                adapter.setList(list);
            }
        });

    }
}
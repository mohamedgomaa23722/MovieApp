package com.mg.movie.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mg.movie.adapter.PopularAdapter;
import com.mg.movie.adapter.TopRatedAdapter;
import com.mg.movie.databinding.ActivityMainBinding;
import com.mg.movie.model.movie;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.viewModel.movieViewModel;


import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements OnItemClicked {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private movieViewModel viewModel;
    @Inject
    PopularAdapter popularAdapter;
    @Inject
    TopRatedAdapter topRatedAdapter;
    @Inject
    TopRatedAdapter upcomingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(movieViewModel.class);
        InitializeAdapters();
        InitializePopularMovies();
        InitializeTopRatedMovies();
        InitializeUpcomingMovies();
    }

    private void InitializeAdapters(){
        binding.PopularRecycler.setAdapter(popularAdapter);
        binding.ratedRecycler.setAdapter(topRatedAdapter);
        binding.UpcomingRecycler.setAdapter(upcomingAdapter);
        //Handle onItemClicked
        popularAdapter.setOnItemClicked(this);
        topRatedAdapter.setOnItemClicked(this);
        upcomingAdapter.setOnItemClicked(this);
    }

    private void InitializePopularMovies() {
        viewModel.getPopularMovies();
        viewModel.getMoviesData().observe(this, PopularMovies -> {
            popularAdapter.setList(PopularMovies);
            AnimateView(binding.PopularRecycler, binding.shimmerLayout);
        });
    }

    private void InitializeTopRatedMovies() {
        viewModel.getTopRatedMovies();
        viewModel.getTopRatedMoviesData().observe(this, TopRatedMovies -> {
            topRatedAdapter.setList(TopRatedMovies);
            AnimateView(binding.ratedRecycler, binding.topRatedShimmer);
        });
    }

    private void InitializeUpcomingMovies() {
        viewModel.getUpcomingMovies();
        viewModel.getUpComingMoviesData().observe(this, upcomingMovies -> {
            upcomingAdapter.setList(upcomingMovies);
            AnimateView(binding.UpcomingRecycler, binding.upcomingShimmer);
        });
    }

    private void AnimateView(RecyclerView recyclerView, ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        binding.shimmerLayout.startShimmer();
        binding.topRatedShimmer.startShimmer();
        binding.upcomingShimmer.startShimmer();
        super.onResume();
    }

    @Override
    protected void onPause() {
        binding.shimmerLayout.stopShimmer();
        binding.topRatedShimmer.stopShimmer();
        binding.upcomingShimmer.stopShimmer();
        super.onPause();
    }

    @Override
    public void onClickListener(movie movie) {
        Toast.makeText(this, "Item Clicked" + movie.getOriginal_title(), Toast.LENGTH_SHORT).show();
    }
}
package com.mg.movie.ui.Fragments;

import static com.mg.movie.utils.constantVariables.MOVIE_DATA;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mg.movie.R;
import com.mg.movie.adapter.PopularAdapter;
import com.mg.movie.adapter.TopRatedAdapter;
import com.mg.movie.databinding.FragmentMainBinding;
import com.mg.movie.model.MovieData.movie;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.viewModel.movieViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment implements OnItemClicked, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private FragmentMainBinding binding;
    private movieViewModel viewModel;
    @Inject
    PopularAdapter popularAdapter;
    @Inject
    TopRatedAdapter topRatedAdapter;
    @Inject
    TopRatedAdapter upcomingAdapter;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(movieViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());

        InitializeAdapters();
        InitializePopularMovies();
        InitializeTopRatedMovies();
        InitializeUpcomingMovies();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "gogo onCreate: ");
    }

    private void InitializeAdapters() {
        binding.PopularRecycler.setAdapter(popularAdapter);
        binding.ratedRecycler.setAdapter(topRatedAdapter);
        binding.UpcomingRecycler.setAdapter(upcomingAdapter);
        //Handle onItemClicked
        popularAdapter.setOnItemClicked(this);
        topRatedAdapter.setOnItemClicked(this);
        upcomingAdapter.setOnItemClicked(this);
        binding.SearchViewContainer.setOnClickListener(this);
    }

    private void InitializePopularMovies() {
        viewModel.getPopularMovies();
        viewModel.getMoviesData().observe(getViewLifecycleOwner(), PopularMovies -> {
            popularAdapter.setList(PopularMovies);
            AnimateView(binding.PopularRecycler, binding.shimmerLayout);
        });
    }

    private void InitializeTopRatedMovies() {
        viewModel.getTopRatedMovies();
        viewModel.getTopRatedMoviesData().observe(getViewLifecycleOwner(), TopRatedMovies -> {
            topRatedAdapter.setList(TopRatedMovies);
            AnimateView(binding.ratedRecycler, binding.topRatedShimmer);
        });
    }

    private void InitializeUpcomingMovies() {
        viewModel.getUpcomingMovies();
        viewModel.getUpComingMoviesData().observe(getViewLifecycleOwner(), upcomingMovies -> {
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
    public void onResume() {
        binding.shimmerLayout.startShimmer();
        binding.topRatedShimmer.startShimmer();
        binding.upcomingShimmer.startShimmer();
        super.onResume();
    }

    @Override
    public void onPause() {
        binding.shimmerLayout.stopShimmer();
        binding.topRatedShimmer.stopShimmer();
        binding.upcomingShimmer.stopShimmer();
        super.onPause();
    }

    @Override
    public void onClickListener(movie movie) {
        Bundle SelectedMovie = new Bundle();
        SelectedMovie.putSerializable(MOVIE_DATA, movie);
        MoveUp(R.id.action_mainFragment_to_detailsFragment2, SelectedMovie);
    }

    @Override
    public void onClick(View view) {
        MoveUp(R.id.action_mainFragment_to_searchFragment, null);
    }

    private void MoveUp(int Action, Bundle Data) {
        if (Data == null)
            navController.navigate(Action);
        else
            navController.navigate(Action, Data);
    }
}
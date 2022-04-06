package com.mg.movie.ui.Fragments;

import static com.mg.movie.utils.constantVariables.MOVIE_DATA;
import static com.mg.movie.utils.constantVariables.MOVIE_TYPE;
import static com.mg.movie.utils.constantVariables.NOW_PLAYING;
import static com.mg.movie.utils.constantVariables.POPULAR;
import static com.mg.movie.utils.constantVariables.TOP_RATED;
import static com.mg.movie.utils.constantVariables.UPCOMING;

import android.annotation.SuppressLint;
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

import java.io.Serializable;

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
    @Inject
    TopRatedAdapter NowPLayingAdapter;
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
        InitializeNowPlayingMovies();
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
        binding.NowPlayingRecycler.setAdapter(NowPLayingAdapter);
        //Handle onItemClicked
        popularAdapter.setOnItemClicked(this);
        topRatedAdapter.setOnItemClicked(this);
        upcomingAdapter.setOnItemClicked(this);
        NowPLayingAdapter.setOnItemClicked(this);
        binding.ViewAllMostPopular.setOnClickListener(this);
        binding.ViewAllTopRated.setOnClickListener(this);
        binding.ViewAllNowPlaying.setOnClickListener(this);
        binding.ViewAllupcoming.setOnClickListener(this);
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

    private void InitializeNowPlayingMovies() {
        viewModel.getNowPlayingMovies();
        viewModel.getNowPlayingMoviesData().observe(getViewLifecycleOwner(), NowPlayingMovies -> {
            NowPLayingAdapter.setList(NowPlayingMovies);
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
    public void onClickListener(Object movie) {
        Bundle SelectedMovie = new Bundle();
        SelectedMovie.putSerializable(MOVIE_DATA, (movie) movie);
        MoveUp(R.id.action_mainFragment_to_detailsFragment2, SelectedMovie);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.SearchViewContainer) {
            MoveUp(R.id.action_mainFragment_to_searchFragment, null);
        } else {
            Bundle SelectedType = new Bundle();
            switch (view.getId()){
                case R.id.ViewAllMostPopular:
                    SelectedType.putString(MOVIE_TYPE,POPULAR);
                    break;
                case R.id.ViewAllNowPlaying:
                    SelectedType.putString(MOVIE_TYPE,NOW_PLAYING);
                    break;
                case R.id.ViewAllTopRated:
                    SelectedType.putString(MOVIE_TYPE,TOP_RATED);
                    break;
                case R.id.ViewAllupcoming:
                    SelectedType.putString(MOVIE_TYPE,UPCOMING);
                    break;
            }
            MoveUp(R.id.action_mainFragment_to_viewAllMoviesFragment,SelectedType);
        }
    }

    private void MoveUp(int Action, Bundle Data) {
        if (Data == null)
            navController.navigate(Action);
        else
            navController.navigate(Action, Data);
    }
}
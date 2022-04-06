package com.mg.movie.ui.Fragments;

import static com.mg.movie.utils.constantVariables.MINUS;
import static com.mg.movie.utils.constantVariables.MOVIE_DATA;
import static com.mg.movie.utils.constantVariables.MOVIE_TYPE;
import static com.mg.movie.utils.constantVariables.PLUS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mg.movie.R;
import com.mg.movie.adapter.SearchAdapter;
import com.mg.movie.databinding.FragmentViewAllMoviesBinding;
import com.mg.movie.model.MovieData.movie;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.viewModel.movieViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewAllMoviesFragment extends Fragment implements View.OnClickListener, OnItemClicked {

    private movieViewModel viewModel;
    private NavController navController;
    private FragmentViewAllMoviesBinding binding;
    private int PageNumber = 1;
    private String MovieType;
    @Inject
    SearchAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewAllMoviesBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(movieViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        SetUpViews();
    }

    private void SetUpViews() {
        //Setup Recycler view
        binding.AllMoviesRecycler.setAdapter(adapter);
        adapter.setOnItemClicked(this);
        // Handle Onclick Listener
        binding.MinusPage.setOnClickListener(this);
        binding.PlusPage.setOnClickListener(this);
        // then we Need to get the kind of movies which related to the response
        // we received from main fragment : Popular, TopRated, upComing, nowPlaying
        MovieType = getArguments().getString(MOVIE_TYPE);
        SetupMoviesTypes(MovieType,PageNumber);
    }

    private void SetupMoviesTypes(String Query, int pageNumber) {
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.AllMoviesRecycler.setVisibility(View.INVISIBLE);
        viewModel.getAllMoviesByType(Query,pageNumber);
        viewModel.getAllMoviesByType().observe(getViewLifecycleOwner(), AllMoviesData -> {
            adapter.setList(AllMoviesData);
            AnimateView(binding.AllMoviesRecycler,binding.shimmerLayout);
        });

    }

    private void AnimateView(RecyclerView recyclerView, ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.PlusPage) {
            SetupPageNumber(PLUS);
        } else {
            SetupPageNumber(MINUS);
        }
    }

    private void SetupPageNumber(String Operation) {
        if (Operation.equals(MINUS))
            PageNumber--;
        else
            PageNumber++;
        binding.PageNumber.setText(String.valueOf(PageNumber));
        SetupMoviesTypes(MovieType,PageNumber);
    }

    @Override
    public void onClickListener(Object movie) {
        Bundle SelectedMovie = new Bundle();
        SelectedMovie.putSerializable(MOVIE_DATA, (movie) movie);
        navController.navigate(R.id.action_viewAllMoviesFragment_to_detailsFragment, SelectedMovie);
    }
}
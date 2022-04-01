package com.mg.movie.ui.Fragments;

import static com.mg.movie.utils.constantVariables.ACTOR;
import static com.mg.movie.utils.constantVariables.MOVIE;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mg.movie.R;
import com.mg.movie.adapter.ActorAdapter;
import com.mg.movie.adapter.SearchAdapter;
import com.mg.movie.databinding.FragmentSearchBinding;
import com.mg.movie.viewModel.movieViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private movieViewModel viewModel;
    @Inject
    SearchAdapter searchAdapter;
    @Inject
    ActorAdapter actorAdapter;
    private FragmentSearchBinding binding;
    private String SelectedSearchType = MOVIE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(movieViewModel.class);
        binding.SearchEdx.addTextChangedListener(this);
        binding.SearchRecyclerView.setAdapter(searchAdapter);
        SetUpSelectedSearch();
    }

    private void SetUpSelectedSearch() {
        binding.SearchByActor.setOnClickListener(this);
        binding.SearchByMovie.setOnClickListener(this);
    }

    private void AnimateView(RecyclerView recyclerView, ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onPause() {
        super.onPause();
        binding.shimmerLayout.stopShimmer();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (SelectedSearchType == MOVIE) {
            viewModel.SearchForSomeMovie(String.valueOf(charSequence));
            viewModel.getSearchedResultData().observe(this, SearchedResultData -> {
                searchAdapter.setList(SearchedResultData);
                AnimateView(binding.SearchRecyclerView, binding.shimmerLayout);
            });
        } else {
            viewModel.SearchForSomeActor(String.valueOf(charSequence));
            viewModel.getSearchedActorResultData().observe(this, SearchedActorResultsData -> {
                actorAdapter.setList(SearchedActorResultsData);
                AnimateView(binding.SearchRecyclerView, binding.shimmerLayout);
            });
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NonConstantResourceId"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Search_By_Actor:
                SetupSelectedSearchType(binding.SearchByMovie, binding.SearchByActor);
                SelectedSearchType = ACTOR;
                binding.SearchRecyclerView.setAdapter(actorAdapter);
                break;
            case R.id.Search_By_Movie:
                SetupSelectedSearchType(binding.SearchByActor, binding.SearchByMovie);
                SelectedSearchType = MOVIE;
                binding.SearchRecyclerView.setAdapter(searchAdapter);
                break;
        }
    }

    /*
     Type one : for the item which will be unselected
     type two : for the item which will be selected
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void SetupSelectedSearchType(TextView Type_one, TextView Type_two) {
        Type_one.setBackgroundColor(getResources().getColor(R.color.unselectedColor));
        Type_two.setBackground(getResources().getDrawable(R.drawable.ic_selected_search));
        binding.SearchEdx.setText("");
    }
}
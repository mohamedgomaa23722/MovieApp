package com.mg.movie.ui.Fragments;

import static com.mg.movie.utils.constantVariables.ACTOR;
import static com.mg.movie.utils.constantVariables.CAST_ID;
import static com.mg.movie.utils.constantVariables.MINUS;
import static com.mg.movie.utils.constantVariables.MOVIE;
import static com.mg.movie.utils.constantVariables.MOVIE_DATA;
import static com.mg.movie.utils.constantVariables.PLUS;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import com.mg.movie.model.MovieData.movie;
import com.mg.movie.model.castData.cast;
import com.mg.movie.model.personData.Person;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.viewModel.movieViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment implements TextWatcher, View.OnClickListener, OnItemClicked {

    private movieViewModel viewModel;
    @Inject
    SearchAdapter searchAdapter;
    @Inject
    ActorAdapter actorAdapter;
    private FragmentSearchBinding binding;
    private String SelectedSearchType = MOVIE;
    private NavController navController;
    private int PageNumber = 1;
    private String Query;

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
        navController = Navigation.findNavController(binding.getRoot());
        binding.SearchEdx.addTextChangedListener(this);
        binding.SearchRecyclerView.setAdapter(searchAdapter);
        SetUpSelectedSearch();
    }

    private void SetUpSelectedSearch() {
        binding.SearchByActor.setOnClickListener(this);
        binding.SearchByMovie.setOnClickListener(this);
        actorAdapter.setOnItemClicked(this);
        searchAdapter.setOnItemClicked(this);
        binding.SearchMinusPage.setOnClickListener(this);
        binding.SearchPlusPage.setOnClickListener(this);
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
        PageNumber = 1;
        if (String.valueOf(charSequence).length() > 0) {
            binding.ResultMessage.setVisibility(View.INVISIBLE);
            binding.shimmerLayout.setVisibility(View.VISIBLE);
        } else {
            binding.ResultMessage.setVisibility(View.VISIBLE);
            binding.shimmerLayout.setVisibility(View.INVISIBLE);
            binding.SearchRecyclerView.setVisibility(View.INVISIBLE);
        }
        Query = String.valueOf(charSequence);
        SetUpData(Query);
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
                binding.SearchpageView.setVisibility(View.INVISIBLE);
                break;
            case R.id.Search_By_Movie:
                SetupSelectedSearchType(binding.SearchByActor, binding.SearchByMovie);
                SelectedSearchType = MOVIE;
                binding.SearchRecyclerView.setAdapter(searchAdapter);
                break;

            case R.id.Search_MinusPage:
                SetupPage(MINUS);
                break;
            case R.id.Search_PlusPage:
                SetupPage(PLUS);
                break;
        }
    }

    private void SetupPage(String Action) {
        if (Action.equals(MINUS))
            PageNumber--;
        else
            PageNumber++;
        binding.SearchPageNumber.setText(String.valueOf(PageNumber));
        SetUpData(Query);
    }

    private void SetUpData(String Query) {
        if (SelectedSearchType.equals(MOVIE)) {
            viewModel.SearchForSomeMovie(Query, PageNumber);
            viewModel.getSearchedResultData().observe(this, SearchedResultData -> {
                if (SearchedResultData.isEmpty()) {
                    binding.ResultMessage.setVisibility(View.VISIBLE);
                    binding.SearchpageView.setVisibility(View.INVISIBLE);
                    binding.ResultMessage.setText("No Movie Found");
                } else {
                    binding.ResultMessage.setVisibility(View.INVISIBLE);
                    binding.SearchpageView.setVisibility(View.VISIBLE);
                }
                searchAdapter.setList(SearchedResultData);
                AnimateView(binding.SearchRecyclerView, binding.shimmerLayout);
            });
        } else {
            viewModel.SearchForSomeActor(Query);
            viewModel.getSearchedActorResultData().observe(this, SearchedActorResultsData -> {
                if (SearchedActorResultsData.isEmpty()) {
                    binding.ResultMessage.setVisibility(View.VISIBLE);
                    binding.ResultMessage.setText("No Actor Found");
                } else {
                    binding.ResultMessage.setVisibility(View.INVISIBLE);
                }
                actorAdapter.setList(SearchedActorResultsData);
                AnimateView(binding.SearchRecyclerView, binding.shimmerLayout);
            });
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

    @Override
    public void onClickListener(Object object) {
        Bundle SelectedMovie = new Bundle();
        if (SelectedSearchType.equals(MOVIE)) {
            SelectedMovie.putSerializable(MOVIE_DATA, (movie) object);
            navController.navigate(R.id.action_searchFragment_to_detailsFragment, SelectedMovie);
        } else {
            Person cast = (Person) object;
            SelectedMovie.putInt(CAST_ID, cast.getId());
            navController.navigate(R.id.action_searchFragment_to_castFragment2, SelectedMovie);
        }
    }
}
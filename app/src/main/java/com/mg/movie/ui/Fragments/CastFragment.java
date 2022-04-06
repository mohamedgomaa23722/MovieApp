package com.mg.movie.ui.Fragments;

import static com.mg.movie.utils.constantVariables.CAST_ID;
import static com.mg.movie.utils.constantVariables.IMAGE_URL;
import static com.mg.movie.utils.constantVariables.MOVIE_DATA;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mg.movie.R;
import com.mg.movie.adapter.ActorMoviesAdapter;
import com.mg.movie.adapter.TopRatedAdapter;
import com.mg.movie.databinding.FragmentCastBinding;
import com.mg.movie.model.MovieData.movie;
import com.mg.movie.model.PersonMovieCredits.CastMovies;
import com.mg.movie.model.castData.cast;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.viewModel.movieViewModel;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CastFragment extends Fragment implements View.OnClickListener, OnItemClicked {
    private FragmentCastBinding binding;
    private movieViewModel viewModel;
    private NavController navController;
    @Inject
    ActorMoviesAdapter adapter;
    private static final String TAG = "CastFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCastBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(movieViewModel.class);
        navController= Navigation.findNavController(binding.getRoot());
        //Setup Views
        int CastID = getArguments().getInt(CAST_ID);
        SetupViews(CastID);
    }

    private void SetupViews(int CastId) {
        viewModel.getCastDetails(CastId);
        viewModel.getCastDetailsData().observe(getViewLifecycleOwner(), CastDetailsResponse -> {
            Glide.with(this).load(IMAGE_URL + CastDetailsResponse.getProfile_path())
                    .into(binding.CastImage);
            float CastRate = (float) (CastDetailsResponse.getPopularity() / 10);
            binding.castRate.setRating(CastRate);
            binding.castRateText.setText(String.valueOf(new DecimalFormat("#.##").format(CastRate)));
            binding.CastName.setText(CastDetailsResponse.getName());
            binding.CastDetails.setText(CastDetailsResponse.getBiography());
        });
        //setup best movies list
        binding.CastBestMovies.setAdapter(adapter);
        viewModel.GetSomeActorMovies(CastId);
        viewModel.getActorMoviesData().observe(getViewLifecycleOwner(),ActorMovies -> {
            adapter.setList(ActorMovies);
        });
        adapter.setOnItemClicked(this);
        //Handle onclick items
        binding.BackToDetailsFragment.setOnClickListener(this);
    }

    private void SetUpBio(cast cast){

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.BackToDetailsFragment)
        navController.popBackStack(R.id.action_castFragment_to_detailsFragment,true);
    }

    @Override
    public void onClickListener(Object object) {
        CastMovies castMovies=(CastMovies) object;
        movie movie=new movie(castMovies.getId(),castMovies.getOriginal_title(),castMovies.getOverview(),castMovies.getPopularity(),castMovies.getPoster_path()
        ,castMovies.getBackdrop_path(),castMovies.getRelease_date(),castMovies.getVote_average(),castMovies.getVote_count(),castMovies.getGenre_ids(),castMovies.isAdult()
        ,castMovies.isVideo(),castMovies.getOriginal_language());

        Bundle SelectedMovie = new Bundle();
        SelectedMovie.putSerializable(MOVIE_DATA,movie);
        navController.navigate(R.id.action_castFragment_to_detailsFragment,SelectedMovie);

    }
}
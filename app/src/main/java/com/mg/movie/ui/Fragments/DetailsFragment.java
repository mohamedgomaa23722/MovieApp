package com.mg.movie.ui.Fragments;

import static com.mg.movie.utils.constantVariables.CAST_ID;
import static com.mg.movie.utils.constantVariables.IMAGE_URL;
import static com.mg.movie.utils.constantVariables.MOVIE_DATA;
import static com.mg.movie.utils.constantVariables.YOUTUBE_KEY_VALUE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import com.mg.movie.R;
import com.mg.movie.adapter.CastAdapter;
import com.mg.movie.adapter.TopRatedAdapter;
import com.mg.movie.databinding.FragmentDetailsBinding;
import com.mg.movie.model.castData.cast;
import com.mg.movie.model.movie;
import com.mg.movie.network.OnCastClicked;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.ui.YoutubeActivity;
import com.mg.movie.viewModel.movieViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsFragment extends Fragment implements View.OnClickListener, OnItemClicked, OnCastClicked {
    private static final String TAG = "DetailsFragment";
    private FragmentDetailsBinding binding;
    private movieViewModel viewModel;
    private String yKey;
    @Inject
    CastAdapter adapter;
    @Inject
    TopRatedAdapter recommendationsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(movieViewModel.class);
        movie movie = (movie) getArguments().get(MOVIE_DATA);
        Log.d(TAG, "onViewCreated: " + movie.getOriginal_title());
        SetupViews(movie);

    }

    private void SetupViews(movie movie) {
        // Set movie name
        binding.contentMovieName.setText(movie.getOriginal_title());
        //set movie date
        binding.contentMovieDate.setText(movie.getRelease_date());
        //set movie rate
        float movieRate = (float) (movie.getVote_average() / 2.00);
        binding.contentMovieRate.setRating(movieRate);
        //set movie Image to each one youtube and main image
        Glide.with(this).load(IMAGE_URL + movie.getPoster_path())
                .into(binding.contentMovieImage);

        Glide.with(this).load(IMAGE_URL + movie.getBackdrop_path())
                .into(binding.MovieVideoImage);
        //set movie overview
        binding.contentMovieOverview.setText(movie.getOverview());
        // setup cast Data
        SetupCastAdapter(movie.getId());
        //Setup youtube
        SetupYoutubeVideo(movie.getId());
        //Setup Recommendation Data
        SetupRecommendationsList(movie.getId());
    }

    private void SetupCastAdapter(int MovieID) {
        binding.contentMovieCastRecyclerView.setAdapter(adapter);
        viewModel.getMovieCast(MovieID);
        viewModel.getMovieCastData().observe(getViewLifecycleOwner(), new Observer<List<cast>>() {
            @Override
            public void onChanged(List<cast> casts) {
                Log.d(TAG, "ggonChanged: " + casts.get(1).getCharacter());
                adapter.setList(casts);
            }
        });
        adapter.setOnItemClicked(this);
    }

    private void SetupYoutubeVideo(int movie_id) {
        viewModel.getMovieTrailer(movie_id);
        viewModel.getMovieTrailerData().observe(getViewLifecycleOwner(), movieTrailers -> {
            yKey = movieTrailers.get(0).getKey();
        });
        binding.ContentPlayYoutubeVideo.setOnClickListener(this);
    }

    private void SetupRecommendationsList(int movie_id) {
        binding.ContentRecommendationRecycler.setAdapter(recommendationsAdapter);
        viewModel.getRecommendedMovies(movie_id);
        viewModel.getRecommendedMoviesData().observe(getViewLifecycleOwner(), RecommendationData -> {
            if (!RecommendationData.isEmpty())
                recommendationsAdapter.setList(RecommendationData);
            else
                binding.recomendedTitle.setVisibility(View.INVISIBLE);
        });
        recommendationsAdapter.setOnItemClicked(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), YoutubeActivity.class);
        intent.putExtra(YOUTUBE_KEY_VALUE, yKey);
        startActivity(intent);
    }

    @Override
    public void onClickListener(movie movie) {
        NavController navController= Navigation.findNavController(binding.getRoot());
        Bundle SelectedMovie=new Bundle();
        SelectedMovie.putSerializable(MOVIE_DATA,movie);
        navController.navigate(R.id.action_detailsFragment_self,SelectedMovie);
    }

    @Override
    public void CastClicked(cast cast) {
        NavController navController= Navigation.findNavController(binding.getRoot());
        Bundle SelectedCast=new Bundle();
        SelectedCast.putInt(CAST_ID,cast.getId());
        navController.navigate(R.id.action_detailsFragment_to_castFragment,SelectedCast);
    }
}
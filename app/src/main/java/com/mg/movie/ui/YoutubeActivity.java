package com.mg.movie.ui;

import static com.mg.movie.utils.constantVariables.YOUTUBE_API_KEY;
import static com.mg.movie.utils.constantVariables.YOUTUBE_KEY_VALUE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.mg.movie.R;
import com.mg.movie.databinding.ActivityYoutubeBinding;
import com.mg.movie.databinding.FragmentDetailsBinding;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private ActivityYoutubeBinding binding;
    private String Key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYoutubeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Key = getIntent().getStringExtra(YOUTUBE_KEY_VALUE);
        binding.ContentYoutubeView.initialize(YOUTUBE_API_KEY,this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
       youTubePlayer.loadVideo(Key);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
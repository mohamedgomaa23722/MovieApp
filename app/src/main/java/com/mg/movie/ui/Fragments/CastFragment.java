package com.mg.movie.ui.Fragments;

import static com.mg.movie.utils.constantVariables.CAST_ID;
import static com.mg.movie.utils.constantVariables.IMAGE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mg.movie.R;
import com.mg.movie.databinding.FragmentCastBinding;
import com.mg.movie.viewModel.movieViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CastFragment extends Fragment {
    private FragmentCastBinding binding;
    private movieViewModel viewModel;
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
        //Setup Views
        int CastID = getArguments().getInt(CAST_ID);
        SetupViews(CastID);
    }

    private void SetupViews(int CastId) {
        viewModel.getCastDetails(CastId);
        viewModel.getCastDetailsData().observe(getViewLifecycleOwner(), CastDetailsResponse -> {
            Log.d(TAG, "SetupViews: "+CastDetailsResponse.getBiography());
            Glide.with(this).load(IMAGE_URL + CastDetailsResponse.getProfile_path())
                    .into(binding.CastImage);
            binding.CastName.setText(CastDetailsResponse.getName());
            binding.CastDepartment.setText(CastDetailsResponse.getKnown_for_department());
            binding.CastAbout.setText(CastDetailsResponse.getBiography());
            binding.CastPopularity.setText(String.valueOf(CastDetailsResponse.getPopularity()));
        });
    }
}
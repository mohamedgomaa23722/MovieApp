package com.mg.movie.ui.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mg.movie.R;

public class PopularViewHolder extends RecyclerView.ViewHolder {
    public ImageView movieImage;
    public RatingBar ratingBar;
    public TextView ratingNumber;
    public PopularViewHolder(@NonNull View itemView) {
        super(itemView);
        movieImage = itemView.findViewById(R.id.movie_image);
        ratingBar = itemView.findViewById(R.id.rating_bar);
        ratingNumber = itemView.findViewById(R.id.rate_number);
    }
}

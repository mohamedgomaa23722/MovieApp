package com.mg.movie.ui.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mg.movie.R;

public class TopRatedViewHolder extends RecyclerView.ViewHolder {
    public ImageView topRatedMovieImage;
    public TextView topRatedMovieName, topRatedMovieDate;

    public TopRatedViewHolder(@NonNull View itemView) {
        super(itemView);
        topRatedMovieImage = itemView.findViewById(R.id.top_rated_movie_image);
        topRatedMovieName = itemView.findViewById(R.id.top_rated_movie_name);
        topRatedMovieDate = itemView.findViewById(R.id.top_rated_movie_date);
    }
}

package com.mg.movie.ui.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mg.movie.R;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    public TextView Searched_Movie_Name, Searched_Movie_Overview, SearchMovieRated;
    public ImageView Searched_Movie_Image;
    public RatingBar SearchMovieRatingView;
    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        Searched_Movie_Image = itemView.findViewById(R.id.Searched_Movie_image);
        Searched_Movie_Name = itemView.findViewById(R.id.Searched_Movie_name);
        Searched_Movie_Overview = itemView.findViewById(R.id.Searched_Movie_overview);
        SearchMovieRated = itemView.findViewById(R.id.Search_rate_number);
        SearchMovieRatingView = itemView.findViewById(R.id.search_rating_bar);
    }
}

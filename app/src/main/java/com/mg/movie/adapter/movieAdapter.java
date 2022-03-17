package com.mg.movie.adapter;

import static com.mg.movie.utils.constantVariables.IMAGE_URL;
import static com.mg.movie.utils.constantVariables.TMDB_API_KEY;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mg.movie.R;
import com.mg.movie.model.movie;
import com.mg.movie.network.OnItemClicked;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class movieAdapter  extends RecyclerView.Adapter<movieAdapter.movieViewHolder> {
    private List<movie> mList = new ArrayList<>();
    private Context mContext;
    private  OnItemClicked onItemClicked;

    @Inject
    public movieAdapter(@ApplicationContext Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new movieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, int position) {
        Glide.with(mContext).load(IMAGE_URL+mList.get(position).getPoster_path())
                .into(holder.movieImage);
        float movie_rate= (float) (mList.get(position).getVote_average()/ 2.00);
        holder.ratingBar.setRating(movie_rate);
        holder.ratingNumber.setText(String.valueOf(mList.get(position).getVote_average()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked.onClickListener(mList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<movie> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public movie getMovieAt(int position){
        return mList.get(position);
    }

    public class movieViewHolder extends RecyclerView.ViewHolder {
        private ImageView movieImage;
        private RatingBar ratingBar;
        private TextView ratingNumber;
        public movieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            ratingNumber = itemView.findViewById(R.id.rate_number);
        }
    }
}


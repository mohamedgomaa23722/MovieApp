package com.mg.movie.adapter;

import static com.mg.movie.utils.constantVariables.IMAGE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mg.movie.R;
import com.mg.movie.model.MovieData.movie;
import com.mg.movie.model.PersonMovieCredits.CastMovies;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.ui.ViewHolder.TopRatedViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedViewHolder> {
    private List<movie> mList = new ArrayList<>();
    private List<CastMovies> castMoviesList = new ArrayList<>();
    private boolean isActorMovies = false;
    private final Context mContext;
    private OnItemClicked onItemClicked;

    @Inject
    public TopRatedAdapter(@ApplicationContext Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public TopRatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopRatedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rateditem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedViewHolder holder, int position) {
        if (isActorMovies) {
            Glide.with(mContext).load(IMAGE_URL + castMoviesList.get(position).getPoster_path())
                    .into(holder.topRatedMovieImage);
            holder.topRatedMovieName.setText(castMoviesList.get(position).getOriginal_title());
            holder.topRatedMovieDate.setText(castMoviesList.get(position).getCharacter());
        } else {
            Glide.with(mContext).load(IMAGE_URL + mList.get(position).getPoster_path())
                    .into(holder.topRatedMovieImage);
            holder.topRatedMovieName.setText(mList.get(position).getOriginal_title());
            holder.topRatedMovieDate.setText(mList.get(position).getRelease_date());
            holder.itemView.setOnClickListener(view -> onItemClicked.onClickListener(mList.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        if (isActorMovies)
            return castMoviesList.size();
        else
            return mList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<movie> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCastMoviesList(List<CastMovies> castMoviesList) {
        this.castMoviesList = castMoviesList;
        notifyDataSetChanged();
    }

    public void setActorMovies(boolean actorMovies) {
        this.isActorMovies = isActorMovies;
    }
}
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
import com.mg.movie.model.movie;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.ui.ViewHolder.PopularViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import dagger.hilt.android.qualifiers.ApplicationContext;
public class PopularAdapter extends RecyclerView.Adapter<PopularViewHolder> {
    private List<movie> mList = new ArrayList<>();
    private final Context mContext;
    private  OnItemClicked onItemClicked;

    @Inject
    public PopularAdapter(@ApplicationContext Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Glide.with(mContext).load(IMAGE_URL+mList.get(position).getPoster_path())
                .into(holder.movieImage);
        float movie_rate= (float) (mList.get(position).getVote_average()/ 2.00);
        holder.ratingBar.setRating(movie_rate);
        holder.ratingNumber.setText(String.valueOf(mList.get(position).getVote_average()));
        holder.itemView.setOnClickListener(view -> onItemClicked.onClickListener(mList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<movie> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}


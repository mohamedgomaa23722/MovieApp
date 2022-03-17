package com.mg.movie.adapter;

import static com.mg.movie.utils.constantVariables.IMAGE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.topRatedViewHolder>{
    private List<movie> mList = new ArrayList<>();
    private Context mContext;
    private static final String TAG = "PokemonAdapter";
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
    public TopRatedAdapter.topRatedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopRatedAdapter.topRatedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rateditem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedAdapter.topRatedViewHolder holder, int position) {
        Glide.with(mContext).load(IMAGE_URL+mList.get(position).getPoster_path())
                .into(holder.topRatedMovieImage);
        holder.topRatedMovieName.setText(mList.get(position).getOriginal_title());
        holder.topRatedMovieDate.setText(mList.get(position).getRelease_date());
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

    public movie getMovieAt(int position){
        return mList.get(position);
    }

    public class topRatedViewHolder extends RecyclerView.ViewHolder {
        private ImageView topRatedMovieImage;
        private TextView topRatedMovieName, topRatedMovieDate;

        public topRatedViewHolder(@NonNull View itemView) {
            super(itemView);
            topRatedMovieImage = itemView.findViewById(R.id.top_rated_movie_image);
            topRatedMovieName = itemView.findViewById(R.id.top_rated_movie_name);
            topRatedMovieDate = itemView.findViewById(R.id.top_rated_movie_date);
        }
    }

}

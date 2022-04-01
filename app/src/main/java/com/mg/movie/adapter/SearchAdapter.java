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
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.ui.ViewHolder.SearchViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{
    private List<movie> mList = new ArrayList<>();
    private final Context mContext;
    private OnItemClicked onItemClicked;

    @Inject
    public SearchAdapter(@ApplicationContext Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClicked(OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Glide.with(mContext).load(IMAGE_URL+mList.get(position).getPoster_path())
                .into(holder.Searched_Movie_Image);
        holder.Searched_Movie_Name.setText(mList.get(position).getOriginal_title());
        holder.Searched_Movie_Overview.setText(mList.get(position).getOverview());
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
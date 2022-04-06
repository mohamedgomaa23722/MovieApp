package com.mg.movie.adapter;

import static com.mg.movie.utils.constantVariables.IMAGE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mg.movie.R;
import com.mg.movie.model.MovieData.movie;
import com.mg.movie.model.personData.Person;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.ui.ViewHolder.SearchViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class ActorAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private List<Person> mList = new ArrayList<>();
    private final Context mContext;
    private OnItemClicked onItemClicked;

    @Inject
    public ActorAdapter(@ApplicationContext Context mContext) {
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
        Glide.with(mContext).load(IMAGE_URL + mList.get(position).getProfile_path())
                .into(holder.Searched_Movie_Image);
        holder.Searched_Movie_Name.setText(mList.get(position).getName());
        if (mList.get(position).getKnown_for() !=null) {
            if (mList.get(position).getKnown_for().get(0) != null)
                holder.Searched_Movie_Overview.setText(mList.get(position).getKnown_for().get(0).getOverview());
            else
                holder.Searched_Movie_Overview.setText(mList.get(position).getKnown_for().get(1).getOverview());
        }
        holder.itemView.setOnClickListener(view -> onItemClicked.onClickListener(mList.get(position)));

        float popularity = (float) (mList.get(position).getPopularity() /10);
        holder.SearchMovieRated.setText(new DecimalFormat("#.##").format(popularity));
        holder.SearchMovieRatingView.setRating(popularity);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Person> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}
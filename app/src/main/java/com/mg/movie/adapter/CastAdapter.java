package com.mg.movie.adapter;

import static com.mg.movie.utils.constantVariables.IMAGE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mg.movie.R;
import com.mg.movie.model.castData.cast;
import com.mg.movie.network.OnCastClicked;
import com.mg.movie.network.OnItemClicked;
import com.mg.movie.ui.ViewHolder.CastViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {
    private List<cast> mList = new ArrayList<>();
    private final Context mContext;
    private OnCastClicked onItemClicked;
    private static final String TAG = "CastAdapter";

    @Inject
    public CastAdapter(@ApplicationContext Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClicked(OnCastClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rateditem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        cast cast = mList.get(position);

        Glide.with(mContext).load(IMAGE_URL+cast.getProfile_path())
                .into(holder.cast_Image);
        if (cast.getProfile_path() ==null)
            Glide.with(mContext).load(R.drawable.loading)
            .centerInside().into(holder.cast_Image);

        holder.cast_Name.setText(cast.getName());
        holder.cast_rule_name.setText(cast.getCharacter());
        holder.itemView.setOnClickListener(view -> onItemClicked.CastClicked(cast));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<cast> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}

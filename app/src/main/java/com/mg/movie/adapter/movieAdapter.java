package com.mg.movie.adapter;

import static com.mg.movie.utils.constantVariables.TMDB_API_KEY;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mg.movie.R;
import com.mg.movie.model.movie;

import java.util.ArrayList;

public class movieAdapter  extends RecyclerView.Adapter<movieAdapter.movieViewHolder> {
    private ArrayList<movie> mList = new ArrayList<>();
    private Context mContext;
    private static final String TAG = "PokemonAdapter";
    public movieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new movieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, int position) {
        holder.movieName.setText(mList.get(position).getOriginal_title());
        Log.d(TAG, "onBindViewHolder: "+"https://image.tmdb.org/t/p/w500/"+mList.get(position).getPoster_path());
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/"+mList.get(position).getPoster_path())
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<movie> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public movie getMovieAt(int position){
        return mList.get(position);
    }

    public class movieViewHolder extends RecyclerView.ViewHolder {
        private ImageView movieImage;
        private TextView movieName;
        public movieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieName = itemView.findViewById(R.id.movie_name);
        }
    }
}


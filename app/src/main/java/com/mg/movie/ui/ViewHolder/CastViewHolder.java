package com.mg.movie.ui.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mg.movie.R;

public class CastViewHolder extends RecyclerView.ViewHolder {
    public ImageView cast_Image;
    public TextView cast_Name, cast_rule_name,Image_Validation_Message;
    public CastViewHolder(@NonNull View itemView) {
        super(itemView);
        cast_Image=itemView.findViewById(R.id.top_rated_movie_image);
        cast_rule_name=itemView.findViewById(R.id.top_rated_movie_name);
        cast_Name=itemView.findViewById(R.id.top_rated_movie_date);
        Image_Validation_Message=itemView.findViewById(R.id.Image_Validation_Message);
    }
}

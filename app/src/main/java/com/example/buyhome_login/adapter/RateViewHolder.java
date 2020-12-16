package com.example.buyhome_login.adapter;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.buyhome_login.R;

public class RateViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView productImage;
    public TextView productTitle;
    public TextView text_describe;
    public RatingBar ratingBar_rate;



    public RateViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.productImage);
        productTitle = itemView.findViewById(R.id.productTitle);
        text_describe=itemView.findViewById(R.id.text_describe);
        ratingBar_rate=itemView.findViewById(R.id.ratingBar_rate);
    }
}

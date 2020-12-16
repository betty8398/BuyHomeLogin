package com.example.buyhome_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyhome_login.R;
import com.example.buyhome_login.network.ImageRequester;

/**
 * Adapter used to show a simple grid of products.
 */
public class RateRecyclerViewAdapter extends RecyclerView.Adapter<RateViewHolder> {

    private ImageRequester imageRequester;
    private OnItemClickListener mOnItemClickListener=null;
    private float rate;
    private String describe;
    private String name;
    private String url;

    public static RateRecyclerViewAdapter getInstance(Context context,float rate,String describe,String name,String url){

        return new RateRecyclerViewAdapter(context,rate,describe,name,url);
    }

    public RateRecyclerViewAdapter(Context context, float rate, String describe, String name, String url) {
        imageRequester = ImageRequester.getInstance(context);
        this.rate = rate;
        this.describe = describe;
        this.name = name;
        this.url = url;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate_card, parent, false);
        return new RateViewHolder(layoutView);
    }


    @Override
    public void onBindViewHolder(@NonNull final RateViewHolder holder, final int position) {

            holder.productTitle.setText(name);
            holder.text_describe.setText(describe);
            holder.ratingBar_rate.setRating(rate);
            imageRequester.setImageFromUrl(holder.productImage, url);
            if(null != mOnItemClickListener) {
                holder.productTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(holder.productTitle, position);
                    }
                });
                holder.productImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(holder.productImage, position);
                    }
                });
            }


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public interface OnItemClickListener {
        void onClick(View parent, int position);
    }

    // 設置點擊事件
    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }
}

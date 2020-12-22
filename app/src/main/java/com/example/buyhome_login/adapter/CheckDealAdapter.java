package com.example.buyhome_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.buyhome_login.R;
import com.example.buyhome_login.network.ImageRequester;

import java.util.List;

public class CheckDealAdapter extends RecyclerView.Adapter<com.example.buyhome_login.adapter.CheckDealAdapter.ViewHolder> {
    private final Context context;

    private final List<String> nameString;
    private final List<Integer> priceList;
    private final List<String> pictureId;
    private final List<Integer> amount;

    private final LayoutInflater mLayoutInflater;
    private final ImageRequester mImageRequester;

    //4-1.建構子
    //取得context與資料，並設定一個Inflater填充於傳來的context中
    public CheckDealAdapter(Context context, List<String> nameString, List<Integer> priceList, List<String> pictureId, List<Integer> amount) {
        this.context = context;

        this.nameString = nameString;
        this.priceList = priceList;
        this.pictureId = pictureId;
        this.amount = amount;

        mImageRequester= ImageRequester.getInstance(context);
        mLayoutInflater = LayoutInflater.from(context);
    }

    //4-2.取得項目數量
    @Override
    public int getItemCount() {
        //回傳資料數
        return nameString.size();
    }

    //4-3.建立ViewHolder內部類別，必須繼承RecyclerView.ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final NetworkImageView img_data;
        private final TextView tv_name_data;
        private final TextView tv_price_data;
        private final TextView tv_item_amount;

        //取得項目視圖中的ViewID
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_data = itemView.findViewById(R.id.img_item_picture);
            tv_name_data = itemView.findViewById(R.id.tv_item_name);
            tv_price_data = itemView.findViewById(R.id.tv_item_price);
            tv_item_amount = itemView.findViewById(R.id.tv_item_amount);

            //監聽器
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, tv_name_data.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //4-4.建立ViewHolder
    @NonNull
    @Override
    public com.example.buyhome_login.adapter.CheckDealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //填充項目View
        View view = mLayoutInflater.inflate(R.layout.item_checkdeal, null);
        //建立ViewHolder實體
        //此處會把已填充的view給viewHolder，由viewHolder把內部的viewID取出
        ViewHolder viewHolder = new ViewHolder(view);
        //回傳viewHolder
        return viewHolder;
    }

    //4-5.將資料連接到ViewHolder
    @Override
    public void onBindViewHolder(@NonNull com.example.buyhome_login.adapter.CheckDealAdapter.ViewHolder holder, int position) {
        //TODO
        mImageRequester.setImageFromUrl(holder.img_data,pictureId.get(position));
        holder.tv_name_data.setText(nameString.get(position));
        holder.tv_price_data.setText("$" + priceList.get(position));
        holder.tv_item_amount.setText("數量：" + amount.get(position).toString());
    }
}
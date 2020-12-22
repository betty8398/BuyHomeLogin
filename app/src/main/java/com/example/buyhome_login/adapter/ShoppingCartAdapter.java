package com.example.buyhome_login.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.buyhome_login.R;
import com.example.buyhome_login.data.ShoppingCartViewModel;
import com.example.buyhome_login.network.ImageRequester;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<com.example.buyhome_login.adapter.ShoppingCartAdapter.ViewHolder> {
    private final Context context;
    private final ImageRequester mImageRequester;

    //ViewModel
    private ShoppingCartViewModel viewModel;

    private final List<String> nameString;
    private final List<Integer> priceList;
    private final List<String> pictureId;
    private final List<Integer> amount;

    private final LayoutInflater mLayoutInflater;

    //建構子
    //取得context與資料，並設定一個Inflater填充於傳來的context中
    public ShoppingCartAdapter(Context context, List<String> nameString, List<Integer> priceList, List<String> pictureId, List<Integer> amount) {
        this.context = context;

        //取得自定義 ViewModel
        viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ShoppingCartViewModel.class);

        this.nameString = nameString;
        this.priceList = priceList;
        this.pictureId = pictureId;
        this.amount = amount;
        mImageRequester=ImageRequester.getInstance(context);
        mLayoutInflater = LayoutInflater.from(context);
    }

    //取得項目數量
    @Override
    public int getItemCount() {
        //回傳資料數
        return nameString.size();
    }

    //建立ViewHolder內部類別
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final NetworkImageView img_data;
        private final TextView tv_name_data;
        private final TextView tv_price_data;
        private final TextView tv_item_amount;
        private final Button btnSub, btnAdd;
        private final ImageButton btnDeleteItem;

        //取得項目視圖中的ViewID
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            img_data = itemView.findViewById(R.id.img_item_picture);
            tv_name_data = itemView.findViewById(R.id.tv_item_name);
            tv_price_data = itemView.findViewById(R.id.tv_item_price);
            tv_item_amount = itemView.findViewById(R.id.tv_item_amount);
            btnDeleteItem = itemView.findViewById(R.id.btn_delete_item);
            btnSub = itemView.findViewById(R.id.btn_sub);
            btnAdd = itemView.findViewById(R.id.btn_add);

            //[按鈕] 刪除項目
            btnDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Toast.makeText(context, viewModel.nameList.get(getAdapterPosition()) + "\n已刪除", Toast.LENGTH_SHORT).show();
                        viewModel.deleteProduct(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos,viewModel.nameList.size());
                    }
                }
            });

            //[按鈕] 數量減一
            btnSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //數量減一
                    viewModel.onSubAmount(getAdapterPosition());
                    //計算總價
                    viewModel.setPureTotalPrice();

                    notifyItemChanged(getAdapterPosition());
//                    //建立 LiveData 觀察者
//                    final Observer<List<Integer>> observer = new Observer<List<Integer>>() {
//                        @Override
//                        public void onChanged(@Nullable final List<Integer> newValue) {
//                            //若觀測到資料變化則做
//                            String newAmount = newValue.get(getAdapterPosition()).toString();
//                            tv_item_amount.setText(newAmount);
//                            Log.d("myTest", "Observer觀測到資料變化。");
//                        }
//                    };
//                    //連結 LiveData 與觀察者
//                    viewModel._amountList.observe((LifecycleOwner) context, observer);
                }
            });

            //[按鈕] 數量加一
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //數量加一
                    viewModel.onAddAmount(getAdapterPosition());
                    //計算總價
                    viewModel.setPureTotalPrice();

                    notifyItemChanged(getAdapterPosition());
//                    //建立 LiveData 觀察者
//                    final Observer<List<Integer>> observer = new Observer<List<Integer>>() {
//                        @Override
//                        public void onChanged(@Nullable final List<Integer> newValue) {
//                            //若觀測到資料變化則做
//                            int pos = getAdapterPosition();
//                            if (pos != RecyclerView.NO_POSITION) {
//                                String newAmount = newValue.get(pos).toString();
//                                tv_item_amount.setText(newAmount);
//                                Toast.makeText(context,pos, Toast.LENGTH_SHORT).show();
//                                Log.d("myTest", "Observer觀測到資料變化。");
//                            }
//                        }
//                    };
//                    //連結 LiveData 與觀察者
//                    viewModel._amountList.observe((LifecycleOwner) context, observer);
                }
            });
        }
    }

    //建立ViewHolder
    @NonNull
    @Override
    public com.example.buyhome_login.adapter.ShoppingCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //填充項目View
        View view = mLayoutInflater.inflate(R.layout.item_shoppingcart, null);
        //建立ViewHolder實體
        //此處會把已填充的view給viewHolder，由viewHolder把內部的viewID取出
        ViewHolder viewHolder = new ViewHolder(view);
        //回傳viewHolder
        return viewHolder;
    }

    //將資料連接到ViewHolder
    @Override
    public void onBindViewHolder(@NonNull com.example.buyhome_login.adapter.ShoppingCartAdapter.ViewHolder holder, int position) {
        mImageRequester.setImageFromUrl(holder.img_data,pictureId.get(position));
        holder.tv_name_data.setText(nameString.get(position));
        holder.tv_price_data.setText("$" + priceList.get(position));
        holder.tv_item_amount.setText(amount.get(position).toString());
    }
}
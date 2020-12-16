package com.example.buyhome_login.fragment_cart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.buyhome_login.R;
import com.example.buyhome_login.adapter.ShoppingCartAdapter;
import com.example.buyhome_login.data.ShoppingCartViewModel;

public class ShoppingCart extends Fragment {
    Context context;
    View view;

    //ViewModel
    private ShoppingCartViewModel viewModel;
    //RecyclerView
    private RecyclerView rvShoppingCart;
    private ShoppingCartAdapter adapter;
    private TextView tvPriceDisplay;

    Button btnGoCheckDeal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        context = requireActivity();

        //開啟 ActionBar
        setHasOptionsMenu(true);

        //取得自定義 ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingCartViewModel.class);

        //[小記金額]
        //取得小記金額之TextView元件
        tvPriceDisplay = view.findViewById(R.id.tv_price_display);
        //建立 LiveData 觀察者
        final Observer<Integer> observerPureTotalPrice = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newValue) {
                //若觀測到資料變化則做
                tvPriceDisplay.setText("$" + viewModel.getPureTotalPrice());
            }
        };
        //連結 LiveData 與觀察者
        viewModel._pureTotalPrice.observe((LifecycleOwner) context, observerPureTotalPrice);


        //[清單] 呈現商品資料
        rvShoppingCart = view.findViewById(R.id.rv_shoppingcart);
        StaggeredGridLayoutManager mLayoutManager_stagger = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvShoppingCart.setLayoutManager(mLayoutManager_stagger);
        adapter = new ShoppingCartAdapter(context, viewModel.nameList, viewModel.priceList, viewModel.pictureList, viewModel._amountList.getValue());
        rvShoppingCart.setAdapter(adapter);

        //[按鈕]  前往"確認付款"頁面
        btnGoCheckDeal = view.findViewById(R.id.btn_go_checkdeal);
        btnGoCheckDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_shoppingCart_to_checkDeal);
            }
        });

        return view;
    }

    //設定返回鍵功能
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //取得被點擊的物件編號
        switch (item.getItemId()){
            //若編號為返回鍵則做
            case android.R.id.home:
                //結束購物車
                requireActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
package com.example.buyhome_login.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.buyhome_login.R;
import com.example.buyhome_login.activity.ProductActivity;
import com.example.buyhome_login.activity.ProductDetailActivity;
import com.example.buyhome_login.adapter.ProductCardHAdapter;
import com.example.buyhome_login.adapter.ProductCardRecyclerViewAdapter;
import com.example.buyhome_login.adapter.ViewPagerAdapter;
import com.example.buyhome_login.network.ProductEntry;

import java.util.List;

public class HomeFragment extends Fragment implements ProductCardRecyclerViewAdapter.OnItemClickListener, ProductCardHAdapter.OnItemClickListener {

    private HomeViewModel homeViewModel;
    private View root;
    private static final String LOG_TAG = "MainActivity";
    public ViewPager viewPager;
    public List<ProductEntry> productList;
    private ProductCardRecyclerViewAdapter adapter_v;
    private RecyclerView recyclerview_h,recyclerview_v,recyclerview_buy;
    private ProductCardHAdapter adapter_h;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        dataIni();
        findView();
        setAdapter();
        setListener();
        return root;
    }

    private void dataIni() {
        ProductActivity activity=(ProductActivity)getActivity();
        productList=activity.getProductList();
    }

    private void setAdapter() {
        recyclerview_v.setHasFixedSize(true);
        System.out.println(getContext().getResources());
        adapter_v=new ProductCardRecyclerViewAdapter(getContext(),productList);
        adapter_h=new ProductCardHAdapter(getContext(),productList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager_buy = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerview_v.setAdapter(adapter_v);
        recyclerview_v.setLayoutManager(gridLayoutManager);
        recyclerview_h.setAdapter(adapter_h);
        recyclerview_h.setLayoutManager(gridLayoutManager2);
        recyclerview_buy.setAdapter(adapter_h);
        recyclerview_buy.setLayoutManager(gridLayoutManager_buy);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext(),productList);
        viewPager.setAdapter(viewPagerAdapter);

    }

    private void setListener() {
        adapter_v.setOnItemClickListener(this);
        adapter_h.setOnItemClickListener(this);
    }

    private void findView() {
        recyclerview_h=(RecyclerView)root.findViewById(R.id.recyclerview_fes);
        recyclerview_v=(RecyclerView)root.findViewById(R.id.recycler_view_v);
        recyclerview_buy=(RecyclerView)root.findViewById(R.id.recyclerView_buy);
        viewPager = (ViewPager) root.findViewById(R.id.main_viewpager);
    }

    @Override
    public void onClick(View parent, int position) {
        Intent intent=new Intent(getActivity(),ProductDetailActivity.class);
        intent.putExtra("id",productList.get(position).id);
        intent.putExtra("sellerId",productList.get(position).sellerId);
        intent.putExtra("title",productList.get(position).title);
        intent.putExtra("price",productList.get(position).price);
        intent.putExtra("url",productList.get(position).url);
        intent.putExtra("description",productList.get(position).description);
        intent.putExtra("rate",productList.get(position).buyerRating.rate);
        intent.putExtra("buyerId",productList.get(position).buyerRating.buyerId);
        intent.putExtra("describe",productList.get(position).buyerRating.describe);
        intent.putExtra("lat",productList.get(position).location.lat);
        intent.putExtra("lng",productList.get(position).location.lng);
        startActivityForResult(intent,200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("result : "+data);
    }
}
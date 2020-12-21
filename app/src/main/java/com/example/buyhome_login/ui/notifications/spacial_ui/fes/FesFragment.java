package com.example.buyhome_login.ui.notifications.spacial_ui.fes;

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
import com.example.buyhome_login.activity.ProductDetailActivity;
import com.example.buyhome_login.adapter.ProductCardRecyclerViewAdapter;
import com.example.buyhome_login.adapter.ViewPagerAdapter;
import com.example.buyhome_login.network.ProductEntry;

import java.util.ArrayList;
import java.util.List;

public class FesFragment extends Fragment implements ProductCardRecyclerViewAdapter.OnItemClickListener {

    private FesViewModel mViewModel;
    private View root;
    private ViewPager main_viewpager;
    private RecyclerView recyclerview_fes;
    private ProductCardRecyclerViewAdapter adapter_v;
    private List<ProductEntry> productList_t;
    private List<ProductEntry> productList;

    public static FesFragment newInstance() {
        return new FesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fes_fragment, container, false);
        setData();
        findView();
        setAdapter();
        return root;
    }

    private void setData() {
        productList_t= ProductEntry.initProductEntryList(getResources());
        productList=new ArrayList<>();
        for(ProductEntry p:productList_t){
            if(p.id>1038){
                productList.add(p);
            }
        }
    }

    private void setAdapter() {
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext(),productList);
        main_viewpager.setAdapter(viewPagerAdapter);
        adapter_v=new ProductCardRecyclerViewAdapter(getContext(),productList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerview_fes.setAdapter(adapter_v);
        recyclerview_fes.setLayoutManager(gridLayoutManager);
        adapter_v.setOnItemClickListener(this);
    }

    private void findView() {
        main_viewpager=(ViewPager)root.findViewById(R.id.main_viewpager);
        recyclerview_fes=(RecyclerView)root.findViewById(R.id.recyclerview_fes);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FesViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View parent, int position) {
        Intent intent=new Intent(getActivity(), ProductDetailActivity.class);
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
}
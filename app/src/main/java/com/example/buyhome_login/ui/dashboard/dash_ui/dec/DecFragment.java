package com.example.buyhome_login.ui.dashboard.dash_ui.dec;

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

import com.example.buyhome_login.R;
import com.example.buyhome_login.activity.ProductDetailActivity;
import com.example.buyhome_login.adapter.DashCardRecyclerViewAdapter;
import com.example.buyhome_login.network.ProductEntry;
import com.example.buyhome_login.ui.dashboard.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class DecFragment extends Fragment implements DashCardRecyclerViewAdapter.OnItemClickListener {

    private DashboardViewModel mViewModel;
    private View rootview;
    private RecyclerView recyclerView;
    private List<ProductEntry> result;
    private DashCardRecyclerViewAdapter adapter;

    public static DecFragment newInstance() {
        return new DecFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootview= inflater.inflate(R.layout.dec_fragment, container, false);
        findView();
        setData();
        setAdapter();
        setListener();
        return rootview;
    }

    private void setListener() {
        adapter.setOnItemClickListener(this);
    }

    private void setAdapter() {
        adapter=new DashCardRecyclerViewAdapter(getContext(),result);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setData() {
        result=new ArrayList<>();
        List<ProductEntry> productList=  mViewModel.getProductList();
        for(ProductEntry productEntry:productList){
            if(productEntry.tag.equals("dec")){
                result.add(productEntry);
            }
        }
    }

    private void findView() {
        recyclerView=rootview.findViewById(R.id.recyclerView);
        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View parent, int position) {
        Intent intent=new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("id",result.get(position).id);
        intent.putExtra("sellerId",result.get(position).sellerId);
        intent.putExtra("title",result.get(position).title);
        intent.putExtra("price",result.get(position).price);
        intent.putExtra("url",result.get(position).url);
        intent.putExtra("description",result.get(position).description);
        intent.putExtra("rate",result.get(position).buyerRating.rate);
        intent.putExtra("buyerId",result.get(position).buyerRating.buyerId);
        intent.putExtra("describe",result.get(position).buyerRating.describe);
        intent.putExtra("lat",result.get(position).location.lat);
        intent.putExtra("lng",result.get(position).location.lng);
        startActivityForResult(intent,200);
    }
}
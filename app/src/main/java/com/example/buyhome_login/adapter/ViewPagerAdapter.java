package com.example.buyhome_login.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.example.buyhome_login.network.ImageRequester;
import com.example.buyhome_login.network.ProductEntry;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<NetworkImageView> mImageList=new ArrayList<>();
    private List<ProductEntry> productList;
    private ImageRequester imageRequester;

    public ViewPagerAdapter(Context context,List<ProductEntry> productList) {
        this.productList = productList;
        imageRequester = ImageRequester.getInstance(context);
        for (int i = 0; i < productList.size(); i++) {
            NetworkImageView networkImageView = new NetworkImageView(context);
            networkImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageRequester.setImageFromUrl(networkImageView,productList.get(i).url);
            mImageList.add(networkImageView);
        }
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImageList.get(position));
        return mImageList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageList.get(position));
    }
}

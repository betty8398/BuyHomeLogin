package com.example.buyhome_login.ui.notifications.spacial_ui.fes;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.toolbox.NetworkImageView;
import com.example.buyhome_login.FlickerTextView;
import com.example.buyhome_login.R;
import com.example.buyhome_login.network.ImageRequester;

public class FesFragment extends Fragment {

    private FesViewModel mViewModel;
    private View root;
    private NetworkImageView networkImageView1,networkImageView2,networkImageView3,networkImageView4;
    private ImageRequester imageRequester;
    private FlickerTextView flickerTextView;

    public static FesFragment newInstance() {
        return new FesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fes_fragment, container, false);
        findView();
        setImage();
        return root;
    }

    private void setImage() {
        imageRequester=ImageRequester.getInstance(getContext());
        imageRequester.setImageFromUrl(networkImageView1,"https://christmasland.ntpc.gov.tw/img/zone/zone_1.jpg?V=4.81");
        imageRequester.setImageFromUrl(networkImageView2,"https://christmasland.ntpc.gov.tw/img/zone/zone_5.jpg?V=4.81");
        imageRequester.setImageFromUrl(networkImageView3,"https://christmasland.ntpc.gov.tw/img/zone/zone_8.jpg?V=4.81");
        imageRequester.setImageFromUrl(networkImageView4,"https://christmasland.ntpc.gov.tw/img/zone/zone_40.jpg?V=4.81");
        flickerTextView.setGrad(new int[]{Color.RED,0xffffff,Color.GREEN});
    }

    private void findView() {
        networkImageView1=(NetworkImageView)root.findViewById(R.id.networkImageView1);
        networkImageView2=(NetworkImageView)root.findViewById(R.id.networkImageView2);
        networkImageView3=(NetworkImageView)root.findViewById(R.id.networkImageView3);
        networkImageView4=(NetworkImageView)root.findViewById(R.id.networkImageView4);
        flickerTextView=(FlickerTextView)root.findViewById(R.id.flickerTextView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FesViewModel.class);
        // TODO: Use the ViewModel
    }

}
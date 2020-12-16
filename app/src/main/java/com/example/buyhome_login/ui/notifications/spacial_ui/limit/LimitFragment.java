package com.example.buyhome_login.ui.notifications.spacial_ui.limit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.toolbox.NetworkImageView;
import com.example.buyhome_login.R;
import com.example.buyhome_login.TimerTextView;
import com.example.buyhome_login.network.ImageRequester;

public class LimitFragment extends Fragment {

    private LimitViewModel mViewModel;
    private View root;
    private com.example.buyhome_login.TimerTextView timer1,timer2,timer3;
    private NetworkImageView network1,network2,network3;
    private ImageRequester imageRequester;

    public static LimitFragment newInstance() {
        return new LimitFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.limit_fragment, container, false);
        findView();
        setData();

        return root;
    }

    private void setData() {
        imageRequester= ImageRequester.getInstance(getContext());
        imageRequester.setImageFromUrl(network1,"https://img.ruten.com.tw/s5/34c/59b/kaizong17/4/f9/ee/22043735353838_920.jpg");
        imageRequester.setImageFromUrl(network2,"https://s.newtalk.tw/album/news/345/5e01ce8a40325.jpg");
        imageRequester.setImageFromUrl(network3,"https://cdn.statically.io/img/techmoon.xyz/wp-content/uploads/2019/03/im-free.png?quality=80&f=auto1");
        timer1.setDrutime(5);
        timer2.setDrutime(5);
        timer3.setDrutime(5);
    }

    private void findView() {
        timer1=root.findViewById(R.id.timer1);
        timer2=root.findViewById(R.id.timer2);
        timer3=root.findViewById(R.id.timer3);
        network1=root.findViewById(R.id.network1);
        network2=root.findViewById(R.id.network2);
        network3=root.findViewById(R.id.network3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LimitViewModel.class);
        // TODO: Use the ViewModel
    }

}
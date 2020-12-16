package com.example.buyhome_login.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.buyhome_login.R;
import com.example.buyhome_login.activity.ProductActivity;
import com.example.buyhome_login.network.ProductEntry;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private ProductActivity productActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        productActivity=(ProductActivity)getActivity();

        NavigationView navView = root.findViewById(R.id.navigation);
        FragmentManager fm = getChildFragmentManager();

        NavHostFragment navHostFragment= (NavHostFragment) fm.findFragmentById( R.id.nav_host_fragment3);
        //System.out.println(navHostFragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);
        return root;
    }

    public List<ProductEntry> getProductList() {
        return productActivity.getProductList();
    }
}
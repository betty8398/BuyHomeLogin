package com.example.buyhome_login.ui.dashboard;

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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.buyhome_login.R;
import com.example.buyhome_login.activity.ProductActivity;
import com.example.buyhome_login.network.ProductEntry;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ProductActivity productActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        productActivity=(ProductActivity)getActivity();
        dashboardViewModel.setProductList(productActivity.getProductList());
        System.out.println("df"+dashboardViewModel.getProductList().get(0));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavigationView navView = root.findViewById(R.id.navigation);

        // Passing each menu_product_detail ID as a set of Ids because each
        // menu_product_detail should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_food, R.id.nav_clothing,R.id.nav_3c,R.id.nav_dec,R.id.nav_big)
                .build();
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left);
        NavHostFragment navHostFragment= (NavHostFragment) fm.findFragmentById( R.id.nav_host_fragment2);
        System.out.println(navHostFragment);
        NavController navController = navHostFragment.getNavController();

        //NavigationUI.setupActionBarWithNavController(productActivity, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        return root;
    }

    public List<ProductEntry> getProductList() {
        return productActivity.getProductList();
    }
}
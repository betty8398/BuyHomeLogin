package com.example.buyhome_login.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.buyhome_login.Login.MainActivity;
import com.example.buyhome_login.R;
import com.example.buyhome_login.activity.ProductActivity;

public class UserFragment extends Fragment {

    private UserViewModel mViewModel;
    private View root;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.user_fragment, container, false);
        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        ProductActivity productActivity=(ProductActivity)getActivity();
        NavController navController=productActivity.getNavController();
        navController.navigate(R.id.action_navigation_user_to_navigation_home);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // TODO: Use the ViewModel

    }

}
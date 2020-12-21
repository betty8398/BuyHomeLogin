package com.example.buyhome_login.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.buyhome_login.Login.MainActivity;
import com.example.buyhome_login.MainActivity_shopping_cart;
import com.example.buyhome_login.MemberAreaActivity;
import com.example.buyhome_login.R;
import com.example.buyhome_login.activity.ProductActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CartFragment extends Fragment {

    private CartViewModel mViewModel;
    private View root;
    private FirebaseAuth authControl;
    private FirebaseUser currentUser;
    private GoogleSignInAccount account;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.cart_fragment, container, false);
        //最近登入的google帳號
        account = GoogleSignIn.getLastSignedInAccount(getActivity());
        //最近登入的Firebase帳號
        authControl = FirebaseAuth.getInstance();
        currentUser = authControl.getCurrentUser();
        if(account==null && currentUser==null){
            //都沒登入過 前往登入頁面
            Intent intent=new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }else {
            //有登入過 前往購物車頁面
            Intent intent=new Intent(getActivity(), MainActivity_shopping_cart.class);
            startActivity(intent);
        }

        ProductActivity productActivity=(ProductActivity)getActivity();
        NavController navController=productActivity.getNavController();
        navController.navigate(R.id.action_navigation_cart_to_navigation_home);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        // TODO: Use the ViewModel
    }

}
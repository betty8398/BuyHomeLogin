package com.example.buyhome_login.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.buyhome_login.Login.MainActivity;
import com.example.buyhome_login.MainActivity_shopping_cart;
import com.example.buyhome_login.R;

public class CartFragment extends Fragment {

    private CartViewModel mViewModel;
    private View root;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.cart_fragment, container, false);
        Intent intent=new Intent(getActivity(), MainActivity_shopping_cart.class);
        startActivity(intent);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        // TODO: Use the ViewModel
    }

}
package com.example.buyhome_login.ui.notifications.spacial_ui.only;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.buyhome_login.R;

public class OnlyFragment extends Fragment {

    private OnlyViewModel mViewModel;
    private View root;
    private TextView trick_text;

    public static OnlyFragment newInstance() {
        return new OnlyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.only_fragment, container, false);
        trick_text=root.findViewById(R.id.trick_text);
        trick_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trick_text.setText("就說你不是了吼");
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OnlyViewModel.class);
        // TODO: Use the ViewModel
    }

}
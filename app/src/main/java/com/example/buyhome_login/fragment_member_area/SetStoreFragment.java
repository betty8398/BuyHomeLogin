package com.example.buyhome_login.fragment_member_area;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.buyhome_login.R;
import com.example.buyhome_login.data.MemberAreaViewModel;


public class SetStoreFragment extends Fragment {
    Context context;
    View view;
    //ViewModel
    private MemberAreaViewModel viewModel;

    TextView tvOldValue;
    EditText etNewValue;
    Button btnConfirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_set_store, container, false);
        context = requireActivity();
        //取得自定義 ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(MemberAreaViewModel.class);

        //開啟 ActionBar
        setHasOptionsMenu(true);

        tvOldValue = view.findViewById(R.id.tv_old_value);
        tvOldValue.setText(viewModel.getStore());

        etNewValue = view.findViewById(R.id.et_new_value);


        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                viewModel.setStore(etNewValue.getText().toString());
                //退出BackStack，此處便不會記錄到堆疊中。否則之後按返回鍵會回到這頁。
                Navigation.findNavController(view).popBackStack();
            }
        });

        return view;
    }

    //設定返回鍵功能
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //取得被點擊的物件編號
        switch (item.getItemId()){
            //若編號為返回鍵則做
            case android.R.id.home:
                //返回前頁
                Navigation.findNavController(view).popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
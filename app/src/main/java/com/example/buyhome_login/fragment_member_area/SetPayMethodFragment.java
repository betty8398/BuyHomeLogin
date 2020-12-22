package com.example.buyhome_login.fragment_member_area;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.buyhome_login.R;
import com.example.buyhome_login.data.MemberAreaViewModel;

public class SetPayMethodFragment extends Fragment {
    Context context;
    View view;
    //ViewModel
    private MemberAreaViewModel viewModel;

    RadioGroup rg;
    Button btnConfirm;

    String payMethod = "宅配";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_set_pay_method, container, false);
        context = requireActivity();
        //取得自定義 ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(MemberAreaViewModel.class);

        //開啟 ActionBar
        setHasOptionsMenu(true);

        rg = view.findViewById(R.id.rg);
        //設置RadioGroup監聽器
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbtn_address:
                        payMethod = "宅配";
                        break;
                    case R.id.rbtn_store:
                        payMethod = "超商取貨";
                        break;
                }
            }
        });

        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                viewModel.setPayMethod(payMethod);

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
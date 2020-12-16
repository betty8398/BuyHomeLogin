package com.example.buyhome_login.fragment_cart;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.buyhome_login.R;
import com.example.buyhome_login.data.ShoppingCartViewModel;

public class ChooseAddress extends Fragment {
    Context context;
    View view;

    //ViewModel
    private ShoppingCartViewModel viewModel;

    ListView llAddress;
    ArrayAdapter adapter;

    Button btnShowAddAddress, btnChooseAddressToCheckDelivery;

    View includeAddAddress;
    EditText etCity, etDistrict, etAddressDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = requireActivity();

        //開啟 ActionBar
        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.fragment_choose_address, container, false);

        //取得自定義 ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingCartViewModel.class);

        //TODO [清單] ListView
        llAddress = view.findViewById(R.id.ll_address);

        //設定Adapter
        //引數一：context
        //引數二：[view]
        //引數三：[資料]資料陣列
        //這邊的view使用Android預設的
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, viewModel.getAddressList());

        //連結Adapter
        llAddress.setAdapter(adapter);

        //設定監聽器
        llAddress.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            //參數三為項目編號，可依此決定相應的處理
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
                Toast.makeText(context, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
                //設定預設宅配地址
                viewModel.defaultAddress = viewModel.addressList.get(i);
                Log.d("myTest", "defaultAddress: " + viewModel.defaultAddress);

                //退出BackStack，此處便不會記錄到堆疊中。否則之後按返回建會回到這頁。
                Navigation.findNavController(view).popBackStack();
            }
        });

        //[按鈕] 輸入新地址
        //處理畫面顯示
        btnShowAddAddress = view.findViewById(R.id.btn_show_addaddress);
        includeAddAddress = view.findViewById(R.id.include_add_address);
        btnShowAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnShowAddAddress.setVisibility(View.GONE);
                includeAddAddress.setVisibility(View.VISIBLE);
            }
        });

        //處理按鈕
        etCity =view.findViewById(R.id.et_city);
        etDistrict =view.findViewById(R.id.et_district);
        etAddressDetail =view.findViewById(R.id.et_address_detail);

        btnChooseAddressToCheckDelivery = view.findViewById(R.id.btn_chooseaddress_to_checkdelivery);
        btnChooseAddressToCheckDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etCity.length() == 0 || etDistrict.length() == 0 || etAddressDetail.length() == 0){
                    new AlertDialog.Builder(context).setMessage("請輸入完整地址").create().show();
                }else{
                    //存入新地址
                    String newAddress;
                    newAddress = "#" + etCity.getText().toString() + "#" + etDistrict.getText().toString() + "#" + etAddressDetail.getText().toString();
                    viewModel.addAddress(newAddress);

                    //刷新ListView
                    adapter.clear();
                    adapter.addAll(viewModel.getAddressList());
                }
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
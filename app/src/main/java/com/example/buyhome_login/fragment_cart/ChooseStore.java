package com.example.buyhome_login.fragment_cart;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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

import com.example.buyhome_login.R;
import com.example.buyhome_login.data.ShoppingCartViewModel;

public class ChooseStore extends Fragment {
    Context context;
    View view;

    //ViewModel
    private ShoppingCartViewModel viewModel;

    ListView llStore;
    ArrayAdapter adapter;

    Button btnShowAddStore, btnChooseStoreToCheckDelivery;

    View includeAddStore;
    EditText etCVS, etBranch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = requireActivity();
        //開啟 ActionBar
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_choose_store, container, false);

        //取得自定義 ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingCartViewModel.class);

        //TODO [清單] ListView
        llStore = view.findViewById(R.id.ll_store);

        //設定Adapter
        //引數一：context
        //引數二：[view]
        //引數三：[資料]資料陣列
        //這邊的view使用Android預設的
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, viewModel.getStoreList());

        //連結Adapter
        llStore.setAdapter(adapter);

        //設定監聽器
        llStore.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            //參數三為項目編號，可依此決定相應的處理
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
                Toast.makeText(context, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
                //TODO 依照參數三決定相應的處理
                viewModel.defaultStore = viewModel.storeList.get(i);

                //退出BackStack，此處便不會記錄到堆疊中。否則之後按返回建會回到這頁。
                Navigation.findNavController(view).popBackStack();
            }
        });

        //[按鈕] 輸入新地址
        //處理畫面顯示
        btnShowAddStore = view.findViewById(R.id.btn_show_store);
        includeAddStore = view.findViewById(R.id.include_add_store);
        btnShowAddStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnShowAddStore.setVisibility(View.GONE);
                includeAddStore.setVisibility(View.VISIBLE);
            }
        });

        //處理按鈕
        etCVS =view.findViewById(R.id.et_cvs);
        etBranch =view.findViewById(R.id.et_branch);

        btnChooseStoreToCheckDelivery = view.findViewById(R.id.btn_choosestore_to_checkdelivery);
        btnChooseStoreToCheckDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etCVS.length() == 0 || etBranch.length() == 0){
                    new AlertDialog.Builder(context).setMessage("請輸入完整資訊").create().show();
                }else{
                    //存入新門市
                    String newStore;
                    newStore = "#" + etCVS.getText().toString() + "#" + etBranch.getText().toString();
                    viewModel.addStore(newStore);

                    //刷新ListView
                    adapter.clear();
                    adapter.addAll(viewModel.getStoreList());
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
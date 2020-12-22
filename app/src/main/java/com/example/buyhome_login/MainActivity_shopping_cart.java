package com.example.buyhome_login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.buyhome_login.data.ShoppingCartViewModel;
import com.example.buyhome_login.network.ProductEntry;

import java.util.List;

public class MainActivity_shopping_cart extends AppCompatActivity {
    Context context;
    private Toolbar toolbar;
    private long product_id;
    private List<ProductEntry> productList;
    private ProductEntry target_product;
    private ShoppingCartViewModel shoppingCartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shopping_cart);
        context = this;
        toolbar = findViewById(R.id.app_bar);
        //創建ActionBar物件
        setSupportActionBar(toolbar);
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        getData();
        setData();
    }

    private void setData() {
        shoppingCartViewModel=new ViewModelProvider((ViewModelStoreOwner) context).get(ShoppingCartViewModel.class);
        if(target_product!=null){
            shoppingCartViewModel.addProduct(target_product.title,Integer.valueOf(target_product.price.split("[$]")[1]),target_product.url);
        }
    }

    private void getData() {
        Intent intent=getIntent();
        product_id=intent.getLongExtra("id",0);
        productList= ProductEntry.initProductEntryList(getResources());
        for(ProductEntry productEntry:productList){
            if(productEntry.id==product_id){
                target_product=productEntry;
            }
        }
    }

    //9-6.將 request 傳給 Fragment
    //當使用 Navigation + Fragment 時需採用此方式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //將 Activity 接收到的資料傳到 Fragment
        for (Fragment fragment : getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager().getFragments())
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
package com.example.buyhome_login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buyhome_login.activity.ProductDetailActivity;
import com.example.buyhome_login.network.ProductEntry;

import java.util.List;

public class DeepLinkActivity extends AppCompatActivity {

    private List<ProductEntry> productList;
    private ProductEntry product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent()!=null){
            Uri data = getIntent().getData();
            try {
                String scheme = data.getScheme();
                String host = data.getHost();
                List<String> params = data.getPathSegments();
                // 從網頁傳過來的資料
                long Id = Long.parseLong(params.get(0));
                String text = "Scheme: " + scheme + "\n" + "host: " + host + "\n" + "params: " + Id;
                Log.e("ScrollingActivity", text);
                productList= ProductEntry.initProductEntryList(getResources());
                for(int i=0;i<productList.size();i++){
                    if(productList.get(i).id==Id){
                        product=productList.get(i);
                        break;
                    }
                }
                if(product!=null){
                    Intent intent=new Intent(com.example.buyhome_login.DeepLinkActivity.this, ProductDetailActivity.class);
                    intent.putExtra("id",product.id);
                    intent.putExtra("title",product.title);
                    intent.putExtra("price",product.price);
                    intent.putExtra("url",product.url);
                    intent.putExtra("description",product.description);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"沒有此項商品",Toast.LENGTH_SHORT).show();
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {

        }

    }
}
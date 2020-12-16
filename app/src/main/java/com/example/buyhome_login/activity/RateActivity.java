package com.example.buyhome_login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyhome_login.R;
import com.example.buyhome_login.adapter.RateRecyclerViewAdapter;
import com.example.buyhome_login.network.UserList;

import java.util.List;

public class RateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView_rate;
    private Button add_rate;
    private LinearLayout rate_all,rate_one,rate_two,rate_three,rate_four,rate_five;
    private List<UserList> userList;
    private float rate;
    private String buyerId,describe,name,url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        findView();
        setData();
        setAdapter();
        setListener();
        setSupportActionBar(toolbar);
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
    }

    private void setListener() {

    }

    private void setAdapter() {
        RateRecyclerViewAdapter adapter=RateRecyclerViewAdapter.getInstance(RateActivity.this,
                rate,describe,name,url);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(RateActivity.this, 1, GridLayoutManager.VERTICAL, false);
        recyclerView_rate.setAdapter(adapter);
        recyclerView_rate.setLayoutManager(gridLayoutManager2);
    }

    private void setData() {
        Intent intent=getIntent();
        rate=intent.getFloatExtra("rate",0);
        buyerId=intent.getStringExtra("buyerId");
        describe=intent.getStringExtra("describe");
        userList=UserList.initUserList(getResources());
        System.out.println(userList);
        for(UserList u:userList){
            if(u.id.equals(buyerId)){
                name=u.name;
                url=u.imageURL;
            }
        }
    }

    private void findView() {
        toolbar = findViewById(R.id.app_bar);
        recyclerView_rate=(RecyclerView)findViewById(R.id.recyclerView_rate);
        add_rate=(Button)findViewById(R.id.add_rate);
        rate_all=(LinearLayout)findViewById(R.id.rate_all);
        rate_one=(LinearLayout)findViewById(R.id.rate_one);
        rate_two=(LinearLayout)findViewById(R.id.rate_two);
        rate_three=(LinearLayout)findViewById(R.id.rate_three);
        rate_four=(LinearLayout)findViewById(R.id.rate_four);
        rate_five=(LinearLayout)findViewById(R.id.rate_five);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
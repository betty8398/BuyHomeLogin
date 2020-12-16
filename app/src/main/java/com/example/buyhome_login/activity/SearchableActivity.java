package com.example.buyhome_login.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.buyhome_login.R;
import com.example.buyhome_login.network.ProductEntry;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends AppCompatActivity {
    private List<ProductEntry> productList;
    private List<ProductEntry> resultList;
    private List<String> result_title_list;
    private ListView listView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        findView();
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println(query);
            doMySearch(query);
            toolbar.setTitle(query);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        setAdapter();
        setListener();
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SearchableActivity.this,ProductDetailActivity.class);
                intent.putExtra("id",resultList.get(position).id);
                intent.putExtra("title",resultList.get(position).title);
                intent.putExtra("price",resultList.get(position).price);
                intent.putExtra("url",resultList.get(position).url);
                intent.putExtra("description",resultList.get(position).description);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,result_title_list);
        listView.setAdapter(adapter);
    }

    private void findView() {
        toolbar = findViewById(R.id.app_bar);
        listView=findViewById(R.id.listView);
    }

    private void doMySearch(String query) {
        productList=ProductEntry.initProductEntryList(getResources());
        result_title_list=new ArrayList<>();
        resultList=new ArrayList<>();
        for(int i=0;i<productList.size();i++){
            if(productList.get(i).title.contains(query)){
                resultList.add(productList.get(i));
                result_title_list.add(productList.get(i).title);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
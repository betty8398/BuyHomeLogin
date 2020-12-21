package com.example.buyhome_login.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.buyhome_login.R;
import com.example.buyhome_login.network.ProductEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private Handler handler= new Handler();
    private NavController navController;
    private List<ProductEntry> productList;
    private ProductEntry product;
    private long firstTime;

    // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_product);
        createData();
        getOrder();
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu_product_detail ID as a set of Ids because each
        // menu_product_detail should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_cart,
                R.id.navigation_notifications,R.id.navigation_user)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void getOrder() {
        Intent intent_from=getIntent();
        long Id=intent_from.getLongExtra("id",0);
        if(Id!=0){
            for(int i=0;i<productList.size();i++){
                if(productList.get(i).id==Id){
                    product=productList.get(i);
                    break;
                }
            }
            if(product!=null){
                Intent intent=new Intent(ProductActivity.this,ProductDetailActivity.class);
                intent.putExtra("id",product.id);
                intent.putExtra("title",product.title);
                intent.putExtra("price",product.price);
                intent.putExtra("url",product.url);
                intent.putExtra("description",product.description);
                startActivity(intent);
            }else {
                Toast.makeText(this,"沒有此項商品",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createData() {
        productList= ProductEntry.initProductEntryList(getResources());
    }

    public List<ProductEntry> getProductList() {
        return productList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_show,menu);
        MenuItem menuSearchItem = menu.findItem(R.id.app_bar_search);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuSearchItem.getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // 這邊讓icon可以還原到搜尋的icon
        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:

                break;
        }
        return true;
    }

    public NavController getNavController() {
        return navController;
    }

    protected Handler getHandler(){
        return handler;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstTime > 2000) {
            Toast.makeText(ProductActivity.this, "真的都買好了嗎？再按一次離開", Toast.LENGTH_SHORT)
                    .show();
            firstTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
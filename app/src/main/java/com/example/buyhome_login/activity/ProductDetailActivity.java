package com.example.buyhome_login.activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.buyhome_login.Login.MainActivity;
import com.example.buyhome_login.MainActivity_shopping_cart;
import com.example.buyhome_login.MemberAreaActivity;
import com.example.buyhome_login.R;
import com.example.buyhome_login.adapter.ViewPagerAdapter;
import com.example.buyhome_login.network.ProductEntry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewpager;
    public List<ProductEntry> productList;
    private TextView text_title,text_price,text_product_detail;
    private String URL;
    private long product_id;
    private RatingBar ratingBar;
    private LinearLayout goto_rating,goto_map;
    private Button add_to_car;
    private ImageView image_love;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        findView();
        setData();
        setAdapter();
        setListener();
        setSupportActionBar(toolbar);
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
    }

    private void setData() {
        Intent intent=getIntent();
        product_id=intent.getLongExtra("id",0);
        toolbar.setTitle(intent.getStringExtra("title"));
        text_title.setText(intent.getStringExtra("title"));
        text_price.setText(intent.getStringExtra("price"));
        text_product_detail.setText(intent.getStringExtra("description"));
        URL=intent.getStringExtra("url");
        ratingBar.setRating((float)intent.getIntExtra("rate",0));
    }

    private void findView() {
        toolbar = findViewById(R.id.app_bar);
        viewpager=(ViewPager)findViewById(R.id.viewpager);
        text_title=(TextView)findViewById(R.id.text_title);
        text_price=(TextView)findViewById(R.id.text_price);
        text_product_detail=(TextView)findViewById(R.id.text_product_detail);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        goto_rating=(LinearLayout)findViewById(R.id.goto_rating);
        goto_map=(LinearLayout)findViewById(R.id.goto_map);
        add_to_car=(Button)findViewById(R.id.add_rate);
        image_love=(ImageView)findViewById(R.id.image_love);
        context=this;
    }

    private void setAdapter() {
        ProductEntry productEntry=new ProductEntry(0, null, null,URL,URL,null,null, null, 0, 0, null,null);
        productList=new ArrayList<>();
        productList.add(productEntry);
        viewpager.setAdapter(new ViewPagerAdapter(this,productList));
    }

    private void setListener() {
        final float rate=(float)getIntent().getIntExtra("rate",0);
        final String buyerId=getIntent().getStringExtra("buyerId");
        final String describe=getIntent().getStringExtra("describe");
        add_to_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最近登入的google帳號
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
                //最近登入的Firebase帳號
                FirebaseAuth authControl = FirebaseAuth.getInstance();
                FirebaseUser currentUser = authControl.getCurrentUser();

                if(account==null && currentUser==null){
                    //都沒登入過 前往登入頁面
                    Intent intent=new Intent(context, MainActivity.class);
                    startActivity(intent);
                }else {
                    //有登入過 把商品加入購物車 並傳入商品id
                    Intent intent=new Intent(ProductDetailActivity.this, MainActivity_shopping_cart.class);
                    intent.putExtra("id",product_id);
                    startActivity(intent);
                }


            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ProductDetailActivity.this);
                builder.setTitle("導向評論")
                        .setMessage("想告訴大家您的心得嗎？")
                        .setPositiveButton("當然！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(ProductDetailActivity.this,RateActivity.class);
                                intent.putExtra("rate",rate);
                                intent.putExtra("buyerId",buyerId);
                                intent.putExtra("describe",describe);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("下次！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        goto_rating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    goto_rating.setBackgroundColor(Color.parseColor("#34B403F4"));
                    Intent intent=new Intent(ProductDetailActivity.this,RateActivity.class);
                    intent.putExtra("rate",rate);
                    intent.putExtra("buyerId",buyerId);
                    intent.putExtra("describe",describe);
                    startActivity(intent);
                }else{
                    goto_rating.setBackgroundResource(R.drawable.frame_01);
                }
                return true;
            }
        });
        goto_map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    goto_map.setBackgroundColor(Color.parseColor("#34B403F4"));
//                    Uri gmmIntentUri = Uri.parse("geo:"+getIntent().getFloatExtra("lat",0)+","+getIntent().getFloatExtra("lng",0));
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
////                    mapIntent.setPackage("com.google.android.apps.maps");
////                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(mapIntent);
//                    }
                    Intent intent=new Intent(ProductDetailActivity.this,MapsActivity.class);
                    intent.putExtra("lat",getIntent().getFloatExtra("lat",0));
                    intent.putExtra("lng",getIntent().getFloatExtra("lng",0));
                    intent.putExtra("title",getIntent().getStringExtra("title"));
                    startActivity(intent);

                }else{
                    goto_map.setBackgroundResource(R.drawable.frame_01);
                }
                return true;
            }
        });
        image_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductDetailActivity.this,"喜歡不如買下來吧~",Toast.LENGTH_SHORT).show();
                image_love.setImageResource(R.drawable.ic_love);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_detail,menu);
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
            case android.R.id.home:
                finish();
                break;
            case R.id.goto_car:
                Intent intent=new Intent(ProductDetailActivity.this, MainActivity_shopping_cart.class);
                startActivity(intent);
                break;
            case R.id.share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://buyhome/"+product_id);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
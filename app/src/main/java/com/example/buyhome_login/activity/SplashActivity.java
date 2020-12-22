package com.example.buyhome_login.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.buyhome_login.R;
import com.example.buyhome_login.network.ProductEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    /**
     * 闪屏页
     */
    private ConstraintLayout rlSplash;
    private boolean end;
    AlphaAnimation alpha = new AlphaAnimation(0, 1);
    private List<ProductEntry> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); // 確認取消半透明設置。
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 全螢幕顯示，status bar 不隱藏，activity 上方 layout 會被 status bar 覆蓋。
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); // 配合其他 flag 使用，防止 system bar 改變後 layout 的變動。
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); // 跟系統表示要渲染 system bar 背景。
            window.setStatusBarColor(Color.TRANSPARENT);//將statusBar背景設為透明色
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 表示我們的 UI 是 LIGHT 的 style，icon 就會呈現深色系。
            );
        }

        setContentView(R.layout.activity_splash);

        rlSplash = findViewById(R.id.rl_splash);

        startAnim();
        //firebase setup
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productRef = database.getReference("product");
        DatabaseReference userListRef = database.getReference("user");
        //DatabaseReference userdataRef = database.getReference("product");

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        userListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    /**
     * 启动动画
     */
    private void startAnim() {
        // 渐变动画,从完全透明到完全不透明

        // 持续时间 1 秒
        alpha.setDuration(1000);
        // 动画结束后，保持动画状态
        alpha.setFillAfter(true);

        // 设置动画监听器
        alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画结束时回调此方法
            @Override
            public void onAnimationEnd(Animation animation) {
                // 跳转到下一个页面

                jumpNextPage();

            }
        });

        // 启动动画
        rlSplash.startAnimation(alpha);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!end) {
            alpha.setDuration(100);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 跳转到下一个页面
     */
    private void jumpNextPage() {
        Intent intent=new Intent(SplashActivity.this, ProductActivity.class);
        if(getIntent()!=null) {
            Uri data = getIntent().getData();
            try {
                String scheme = data.getScheme();
                String host = data.getHost();
                List<String> params = data.getPathSegments();
                // 從網頁傳過來的資料
                long Id = Long.parseLong(params.get(0));
                String text = "Scheme: " + scheme + "\n" + "host: " + host + "\n" + "params: " + Id;
                Log.e("SplashActivity", text);
                intent.putExtra("id",Id);
            } catch (Exception e) {

            }
        }
        startActivity(intent);
        finish();
    }
}


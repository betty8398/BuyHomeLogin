package com.example.buyhome_login.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.buyhome_login.R;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    /**
     * 闪屏页
     */
    private ConstraintLayout rlSplash;
    private boolean end;
    AlphaAnimation alpha = new AlphaAnimation(0, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rlSplash = findViewById(R.id.rl_splash);

        startAnim();
    }

    /**
     * 启动动画
     */
    private void startAnim() {
        // 渐变动画,从完全透明到完全不透明

        // 持续时间 2 秒
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
            alpha.setDuration(2000);
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


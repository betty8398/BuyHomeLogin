package com.example.buyhome_login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.buyhome_login.data.MemberAreaViewModel;

import java.io.IOException;

public class MemberAreaActivity extends AppCompatActivity {
    Context context;

    //ViewModel
    private MemberAreaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_area);
        context = this;

        //取得自定義 ViewModel
        viewModel = new ViewModelProvider(this).get(MemberAreaViewModel.class);

        //接收意圖
        Intent intent = getIntent();
        //透過key值取得字串資料
        String userid = intent.getStringExtra("userid");
        String useremail = intent.getStringExtra("useremail");
        String username = intent.getStringExtra("username");
        String userphotourl = intent.getStringExtra("userphotourl");

        Log.d("myTest", "userphotourl: " + userphotourl);

        if(userphotourl != null){
            try {
                Uri userphotourlOK = Uri.parse(userphotourl);
                Bitmap photoBitmap = null;
                photoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),userphotourlOK);
                viewModel.setUserPhotoBitmap(photoBitmap);
                viewModel.setHasPhoto(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("myTest", "useremail: " + useremail);
        Log.d("myTest", "username: " + username);
        viewModel.setEmail(useremail);
        viewModel.setNickname(username);

//        //創建ActionBar物件
//        ActionBar bar = getSupportActionBar();
//        //設定ActionBar顯示返回鍵
//        bar.setDisplayHomeAsUpEnabled(true);
    }

    //將 request 傳給 Fragment
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //將 Activity 接收到的資料傳到 Fragment
        for (Fragment fragment : getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
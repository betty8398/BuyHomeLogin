package com.example.buyhome_login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.buyhome_login.activity.ProductDetailActivity;
import com.example.buyhome_login.data.MemberAreaViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MemberAreaActivity extends AppCompatActivity {
    Context context;

    //ViewModel
    private MemberAreaViewModel viewModel;
    private DatabaseReference classDB;
    private FirebaseDatabase fbControl;
    private GoogleSignInAccount account;
    private FirebaseAuth authControl;
    private FirebaseUser user;
    public String userid,useremail,username,userphotourl;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_area);
        context = this;

        //取得自定義 ViewModel
        viewModel = new ViewModelProvider(this).get(MemberAreaViewModel.class);

        Log.d("myTest", "MemberAreaActivity ");



        //google 登入實體 (最近登入用戶)
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            //google 會員資料取
            userid = account.getId();
            useremail = account.getEmail();
            username = account.getDisplayName();

            try {
                userphotourl = account.getPhotoUrl().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //FirebaseAuth 實體
        authControl = FirebaseAuth.getInstance();
        user = authControl.getCurrentUser();//(最近登入用戶)
        if(user!=null){
            userid = user.getUid();
            useremail = user.getEmail();
            username = user.getDisplayName();
            userphotourl = "https://lh3.googleusercontent.com/a-/AOh14GjGp02JIlI42UYlBGh-D_NPsJYeN7pROOrmJpoNkw";

        }


        Log.d("myTest", "userphotourl: " + userphotourl);

        if(userphotourl.length() != 0){
            new MyPhotoAsyncTask().execute(userphotourl);
            Log.d("myTest", "載入圖片 OK");
        }

        Log.d("myTest", "useremail: " + useremail);
        Log.d("myTest", "username: " + username);
        viewModel.setEmail(useremail);
        viewModel.setNickname(username);

//        //創建ActionBar物件
//        ActionBar bar = getSupportActionBar();
//        //設定ActionBar顯示返回鍵
//        bar.setDisplayHomeAsUpEnabled(true);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
    }

    //取得網路圖片的執行緒
    class MyPhotoAsyncTask extends AsyncTask<String, Integer, byte[]> {
        @Override
        protected byte[] doInBackground(String... strings) {
            return getPhotoData(strings[0]);
        }

        @Override
        protected void onPostExecute(byte[] data) {
            //取得點陣圖
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            viewModel.setUserPhotoBitmap(bitmap);
            viewModel.setHasPhoto(true);

            Log.d("myTest", "圖片已存入: " + viewModel.getUserPhotoBitmap());
        }
    }

    //取得網路圖片
    private byte[] getPhotoData(String urlStr) {
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try{
            //取得url
            URL url = new URL(urlStr);
            //取得網路連接
            URLConnection conn = url.openConnection();
            //設定輸入串流來自網路連接
            is = conn.getInputStream();

            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            //若讀取串流有內容則做
            int len = 0;
            //讀取網路串流
            while((len = is.read(buffer)) > 0){
                //把buffer內容寫入ByteArrayOutputStream
                baos.write(buffer, 0, len);
            }
            //把全部資料存起來
            byte[] data = baos.toByteArray();

            //關閉串流
            is.close();
            baos.close();

            //回傳資料
            return data;
        }catch (IOException e){
            if(is != null){
                try{
                    is.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }

            if(baos != null){
                try{
                    baos.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return null;
        }
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
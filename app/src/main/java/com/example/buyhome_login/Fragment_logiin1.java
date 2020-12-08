package com.example.buyhome_login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Fragment_logiin1 extends Fragment {
    private Button button_signIn;
    private SignInButton button_gLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN=100;//當作確認有登入的碼
    private String TAG="main";

    public Fragment_logiin1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //填充畫面
        View view =inflater.inflate(R.layout.fragment_logiin1, container, false);
        //取得元件
        button_signIn=view.findViewById(R.id.button_signIn);
        button_gLogin=view.findViewById(R.id.button_gLogin);

        //TODO:轉接到成功登入畫面
        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_signIn:
                        Navigation.findNavController(view).navigate(R.id.action_fragment_logiin1_to_fragment_login_regester);
                        break;
                }
            }
        });


        //TODO:按下google登入按鈕 轉接到Google登入API
        button_gLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn();
            }
        });

        return view;
    }


    private void singIn(){
        //TODO:google登入
        // GoogleSignInClient 是用來確定是否從google登入

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        //用Builder建立Option物件

        //TODO:找到Token
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);


        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        //RC_SIGN_IN是自定義的int常數 用來在onActivityResult()檢查是不是用Google的管道登入
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.d(TAG, "onActivityResult: 偵測到用google登入");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    //TODO:取得使用者資料
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d(TAG, "handleSignInResult: getDisplayName："+account.getDisplayName());
            Log.d(TAG, "handleSignInResult: getGivenName："+account.getGivenName());
            Log.d(TAG, "handleSignInResult: getFamilyName："+account.getFamilyName());


            Log.d(TAG, "handleSignInResult: getEmail:"+account.getEmail());
            Log.d(TAG, "handleSignInResult: getID:"+account.getId());
            Log.d(TAG, "handleSignInResult: getPhotoUrl：:"+account.getPhotoUrl());

            Log.d(TAG, "handleSignInResult: getIDToken:"+account.getIdToken());
            Log.d(TAG, "handleSignInResult: getAccount"+account.getAccount());
            Log.d(TAG, "handleSignInResult: getServerAuthCode"+account.getServerAuthCode());

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            //TODO:目前都是登入失敗

            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //TODO:
        // 確認有沒有登入過的帳號 如果有 GoogleSignInAccount物件!=null
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        updateUI(account);

    }
    private void updateUI(GoogleSignInAccount account){
        if(account==null){//如果抓取不到使用者最近登入 物件 則跳轉到登入畫面
            //TODO:要轉跳的畫面  (這個應該做在購物車或用戶資料登入)
        }
    }

}
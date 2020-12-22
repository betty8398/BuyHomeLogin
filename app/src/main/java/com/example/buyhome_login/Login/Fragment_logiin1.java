package com.example.buyhome_login.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyhome_login.MemberAreaActivity;
import com.example.buyhome_login.R;
import com.example.buyhome_login.activity.ProductActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Fragment_logiin1 extends Fragment {
    private Button button_signIn;
    private SignInButton button_gLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 100;//當作確認有登入的碼
    private String TAG = "main";
    private EditText editText_email, editText_password;
    private TextView textView_toRegister, textView_toForgotPassword;
    private View email_frame_red;
    private String email, password;
    private FirebaseAuth authControl;
    private FirebaseUser currentUser;
    //取得使用者資訊
    private GoogleSignInAccount account;
    private String username,useremail,userid;
    private Uri userphotourl;
    //建立回傳端編號常數
    private final int RETURN_DATA = 10;
    private String Fname,Femail,FUID;
    private int index;
    private DatabaseReference classDB;
    private FirebaseDatabase fbControl;

    public Fragment_logiin1() {
        // Required empty public constructor
    }

    //TODO:檢查是否登入
    @Override
    public void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(getActivity());


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //填充畫面
        View view = inflater.inflate(R.layout.fragment_logiin1, container, false);
        //取得元件
        button_signIn = view.findViewById(R.id.button_signIn);
        button_gLogin = view.findViewById(R.id.button_gLogin);
        editText_email = view.findViewById(R.id.editText_email);
        editText_password = view.findViewById(R.id.editText_password);
        textView_toRegister = view.findViewById(R.id.textView_toRegister);
        textView_toForgotPassword = view.findViewById(R.id.textView_toForgotPassword);
        email_frame_red = view.findViewById(R.id.email_frame_red);

        //FirebaseAuth 實體
        authControl = FirebaseAuth.getInstance();
        Log.d(TAG, "authControl = " + authControl);

        //按下Register 轉向 Register頁面
        textView_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_logiin1_to_fragment_login_regester);
            }
        });

        //按下忘記密碼 前往忘記密碼頁面
        textView_toForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Sorry we can't help you~", Toast.LENGTH_SHORT).show();
            }
        });


        //取得FirebaseDatabase物件(用來儲存資料)
        fbControl = FirebaseDatabase.getInstance();
        classDB = fbControl.getReference("user");
        //取得user資料筆數
        classDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int dataCount = (int) snapshot.getChildrenCount();//有幾筆資料
                index = dataCount;
            }

            //監聽"資料更新失敗"事件
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //TODO:按下google登入按鈕 轉接到Google登入API
        button_gLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn();
            }
        });



        //填寫email及時顯示反應
        editText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: editable = " + s);
                String p = s.toString();
                if (p.indexOf("@") > -1) {
                    Log.d(TAG, "email has @");
                    email_frame_red.setVisibility(View.INVISIBLE);
                } else {
                    email_frame_red.setVisibility(View.VISIBLE);
                }
            }
        });

        //TODO:按下LOGIN 傳送資料到RealTime Database 比對帳號密碼
        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editText_email.getText().toString();
                password = editText_password.getText().toString();
                if (email.length() == 0 || password.length() == 0) {
                    Toast.makeText(getActivity(), "請輸入帳號密碼", Toast.LENGTH_SHORT).show();
                } else {
                    //檢查有沒有user在線上 有的話登出 (一次只能一個人登入firebase)
                    currentUser = authControl.getCurrentUser();
                    if (currentUser != null) {
                        authControl.signOut();
                    }
                    //登入firebase 參數帶入帳號密碼
                    authControl.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() { //設立登入完成的監聽器
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "login ok");
                                        Toast.makeText(getActivity(), "登入成功", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = authControl.getCurrentUser();
                                        DisplayUser(user);
                                        //TODO:firebase
                                        //設定回傳用的intent
                                        Intent intent = new Intent(getActivity(),MemberAreaActivity.class);
                                        //放入資料，引數一為key，引數二為value
                                        intent.putExtra("userid", FUID);
                                        intent.putExtra("useremail", Femail);
                                        intent.putExtra("username", Fname);
                                        //傳送資料到
                                        requireActivity().startActivity(intent);
                                    } else {
                                        Log.d(TAG, "login fail");
                                        Toast.makeText(getActivity(), "登入失敗", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        return view;
    }




    private void singIn() {
        //TODO:google登入
        // GoogleSignInClient 是用來確定是否從google登入

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        //用Builder建立Option物件

        //TODO:找到Token
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

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

    //TODO:取得Google使用者資料
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            account = completedTask.getResult(ApiException.class);
            username = account.getDisplayName();
            useremail=account.getEmail();
            userid=account.getId();
            userphotourl=account.getPhotoUrl();

            //手動新增Google資料庫會員資料
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("id", userid);
            data.put("name", username);
            data.put("email", useremail);
            data.put("password", "null");
            //以下為預留給使用者填寫詳情資料
            data.put("birth", "null");
            data.put("gender", "null");
            data.put("imageURL",userphotourl.toString());
            data.put("location", "null");
            data.put("shoppingCart", "null");

            String at = index + "";
            Log.d(TAG, "at=" + at);
            Task<Void> result = classDB.child(at).setValue(data);

            // Signed in successfully, show authenticated UI.
            updateUI(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(TAG, "signInResult:failed code=" + e.getStatusCode());
            switch (e.getStatusCode()) {
                case 7:
                    Toast.makeText(requireActivity(), "NETWORK_ERROR", Toast.LENGTH_SHORT).show();
                case 10:
                    Toast.makeText(requireActivity(), "Login API未授權", Toast.LENGTH_SHORT).show();
            }
            updateUI(null);
        }
    }
    private void DisplayUser(FirebaseUser user) {
         Fname = user.getDisplayName();
         Femail = user.getEmail();
         FUID = user.getUid();

    }


    private void updateUI(GoogleSignInAccount account) {
        //TODO:傳遞user資料到 MemberAreaActivity
        if (account == null && authControl.getCurrentUser() == null) {//如果抓取不到使用者最近登入 物件 則跳轉到登入畫面
            Toast.makeText(getActivity(), "google 和 firebase 尚未當入", Toast.LENGTH_SHORT).show();

        } else if (account != null && authControl.getCurrentUser() == null) {
            Toast.makeText(getActivity(), "google 登入成功", Toast.LENGTH_SHORT).show();

            //設定回傳用的intent
            Intent intent = new Intent(getActivity(),MemberAreaActivity.class);
            //放入資料，引數一為key，引數二為value
            intent.putExtra("userid", userid);
            intent.putExtra("useremail", useremail);
            intent.putExtra("username", username);
            intent.putExtra("userphotourl", userphotourl);
            //傳送資料到
            requireActivity().startActivity(intent);

        } else if (account == null && authControl.getCurrentUser() != null) {
            Toast.makeText(getActivity(), "firebase 登入成功", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getActivity(), "google 和 firebase 都登入成功", Toast.LENGTH_SHORT).show();
        }
    }



}
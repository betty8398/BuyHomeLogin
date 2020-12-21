package com.example.buyhome_login.Login;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyhome_login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_login_regester extends Fragment {
    private Button button_singUP;
    private EditText editText_name, editText_Remail, editText_Rpassword, editText_Rpassword2;
    private TextView textView_toSingIn;
    private View view_name, view_email, view_Rpassword2, view_Rpassword;
    private String TAG = "main";
    private FirebaseAuth authControl;
    private DatabaseReference classDB;
    private FirebaseDatabase fbControl;
    private String name, email, password;
    private long index;
    private boolean userExist;

    public Fragment_login_regester() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_regester, container, false);

        button_singUP = view.findViewById(R.id.button_singUP);
        textView_toSingIn = view.findViewById(R.id.textView_toSingIn);

        editText_name = view.findViewById(R.id.editText_name);
        editText_Remail = view.findViewById(R.id.editText_Remail);
        editText_Rpassword = view.findViewById(R.id.editText_Rpassword);
        editText_Rpassword2 = view.findViewById(R.id.editText_Rpassword2);

        view_name = view.findViewById(R.id.name_frame_red);
        view_email = view.findViewById(R.id.email_fram_red);
        view_Rpassword = view.findViewById(R.id.password_fram_red);
        view_Rpassword2 = view.findViewById(R.id.password2_fram_red);

        //監聽editText是否有值 如果點選後沒填寫過 就換成紅色
        editText_name.addTextChangedListener(new MyEditTextFrame(view_name));
        editText_Remail.addTextChangedListener(new MyEditTextFrame(view_email));
        editText_Rpassword.addTextChangedListener(new MyEditTextFrame(view_Rpassword));
        editText_Rpassword2.addTextChangedListener(new MyEditTextFrame(view_Rpassword2));

        //FirebaseAuth 實體 (用來認證會員登入)
        authControl = FirebaseAuth.getInstance();
        Log.d(TAG, "authControl = " + authControl);

        //1-1取得FirebaseDatabase物件(用來儲存資料)
        fbControl = FirebaseDatabase.getInstance();
        Log.d(TAG, "onCreate: fbControl=" + fbControl);
        //1-2指定到參考資料路徑class資料夾
        classDB = fbControl.getReference("user");
        Log.d(TAG, "onCreate: classDB = " + classDB);


        //TODO:會員註冊 先判斷資料填寫正確
        button_singUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果未輸入內容 則紅框 吐司提示
                if (editText_name.length() == 0) {
                    Toast.makeText(getActivity(), "請輸入名字", Toast.LENGTH_SHORT).show();
                    view_name.setVisibility(View.VISIBLE);
                } else if (editText_Remail.length() == 0) {
                    Toast.makeText(getActivity(), "請輸入email", Toast.LENGTH_SHORT).show();
                    view_email.setVisibility(View.VISIBLE);
                } else if (editText_Rpassword.length() < 5) {
                    Toast.makeText(getActivity(), "密碼長度不小於5", Toast.LENGTH_SHORT).show();
                    view_Rpassword.setVisibility(View.VISIBLE);
                } else if (!editText_Rpassword.getText().toString().equals(editText_Rpassword2.getText().toString())) {
                    Log.d(TAG, "onClick: " + editText_Rpassword.getText().toString());
                    Toast.makeText(getActivity(), "密碼確認不符合", Toast.LENGTH_SHORT).show();
                    view_Rpassword2.setVisibility(View.VISIBLE);
                } else {
                    //隱藏紅色框框
                    view_Rpassword2.setVisibility(View.INVISIBLE);
                    view_email.setVisibility(View.INVISIBLE);
                    view_name.setVisibility(View.INVISIBLE);
                    //取得註冊會員資料
                    name = editText_name.getText().toString();
                    email = editText_Remail.getText().toString();
                    password = editText_Rpassword.getText().toString();

                    //TODO:註冊firebase會員
                        authControl.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //TODO:如果成功在firebase-auth建立帳號 更新資料庫會員資訊
                                if (task.isSuccessful()) { //firebase會把關 不讓註冊過的email進來
                                    Log.d(TAG, "註冊成功");
                                    Toast.makeText(getActivity(), "註冊成功", Toast.LENGTH_SHORT).show();
                                    //取得最近登入的user(就是剛註冊成功的會員)
                                    FirebaseUser user = authControl.getCurrentUser();

                                    //手動新增資料庫會員資料
                                    Map<String, Object> data = new HashMap<String, Object>();
                                    data.put("id", authControl.getUid());
                                    data.put("name", name);
                                    data.put("email", email);
                                    data.put("password", password);
                                    //以下為預留給使用者填寫詳情資料
                                    data.put("birth", "null");
                                    data.put("gender", "null");
                                    data.put("imageURL", "null");
                                    data.put("location", "null");
                                    data.put("shoppingCart", "null");


                                    String at = index + "";
                                    Log.d(TAG, "at=" + at);
                                    Task<Void> result = classDB.child(at).setValue(data);

                                    //classDB.child("4").child("name").setValue("Richard");


                                    //2.監聽"新增成功"事件
                                    result.addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "更新firebase json會員資料 成功");
                                        }
                                    });

                                    //3.監聽"新增失敗"事件
                                    result.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "更新firebase json會員資料 失敗");

                                        }
                                    });
                                    //列印出用戶訊息
                                    DisplayUser(user);

                                } else {
                                    Log.d(TAG, "Register fail");
                                    Toast.makeText(getActivity(), "註冊失敗", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });//註冊會員 End


                    ArrayList<Map<String, String>> dataList = new ArrayList<>();
                    dataList.clear();

                    //TODO:監聽 線上的資料庫 資料更新時 就讓App也更新
                    classDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            dataList.clear(); //清空List 等下裝新資料
                            int dataCount = (int) snapshot.getChildrenCount();//有幾筆資料
                            index = dataCount;
                            Log.d(TAG, " dataCount=" + dataCount);

                            for (DataSnapshot ds : snapshot.getChildren()) { //從0~n把snapshot的資料讀完
                                Map<String, String> mapData = new HashMap<String, String>();//建立Map儲存多筆資料的Key和Value(一位使用者的多個屬性資料)

                                //取得snapshot KEY="name"的資料 存到Map裡面
                                String nameValue = (String) ds.child("name").getValue();
                                if (nameValue == null) {
                                    mapData.put("name", "no name");//如果沒有填入名字 則輸入no name
                                } else {
                                    mapData.put("name", nameValue);
                                }
                                //取得snapshot KEY="phone"的資料 存到Map裡面
                                String phoneValue = (String) ds.child("phone").getValue();
                                if (nameValue == null) {
                                    mapData.put("phone", "no phone");
                                } else {
                                    mapData.put("phone", phoneValue);
                                }
                                dataList.add(mapData);//更新資料到List 所以一次for迴圈 增加一個人的name和phone
                            }
                        }

                        //監聽"資料更新失敗"事件
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


        //按下sing in 前往登入頁面
        textView_toSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_login_regester_to_fragment_logiin1);
            }
        });
        return view;
    }

    private class MyEditTextFrame implements TextWatcher {
        View viewfram;

        public MyEditTextFrame(View v) {
            viewfram = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() != 0) {
                viewfram.setVisibility(View.INVISIBLE);
            }
        }
    }


    private void DisplayUser(FirebaseUser user) {
        //更新用戶資料
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
        String name = user.getDisplayName();
        String email = user.getEmail();
        String UID = user.getUid();
        Uri photoUrl = user.getPhotoUrl();
        boolean emailVerified = user.isEmailVerified();
        Log.d(TAG, "DisplayUser: name = " + name);
        Log.d(TAG, "DisplayUser: email = " + email);
        Log.d(TAG, "DisplayUser: UID = " + UID);
        Log.d(TAG, "DisplayUser: photoUrl = " + photoUrl);
        Log.d(TAG, "DisplayUser: emailVerified = " + emailVerified);
    }
}
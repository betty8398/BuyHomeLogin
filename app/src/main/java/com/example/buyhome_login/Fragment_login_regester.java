package com.example.buyhome_login;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_login_regester#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_login_regester extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button button_singUP;
    private EditText editText_name,editText_Remail,editText_Rpassword,editText_Rpassword2;
    private TextView textView_toSingIn;
    private View view_name, view_email, view_Rpassword2, view_Rpassword;
    private String TAG="main";
    private FirebaseAuth authControl;

    public Fragment_login_regester() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login_regester, container, false);

        button_singUP=view.findViewById(R.id.button_singUP);
        textView_toSingIn=view.findViewById(R.id.textView_toSingIn);

        editText_name=view.findViewById(R.id.editText_name);
        editText_Remail=view.findViewById(R.id.editText_Remail);
        editText_Rpassword=view.findViewById(R.id.editText_Rpassword);
        editText_Rpassword2=view.findViewById(R.id.editText_Rpassword2);

        view_name =view.findViewById(R.id.name_frame_red);
        view_email =view.findViewById(R.id.email_fram_red);
        view_Rpassword =view.findViewById(R.id.password_fram_red);
        view_Rpassword2 =view.findViewById(R.id. password2_fram_red);

        //監聽editText是否有值 如果點選後沒填寫過 就換成紅色
        editText_name.addTextChangedListener(new MyEditTextFrame(view_name));
        editText_Remail.addTextChangedListener(new MyEditTextFrame(view_email));
        editText_Rpassword.addTextChangedListener(new MyEditTextFrame(view_Rpassword));
        editText_Rpassword2.addTextChangedListener(new MyEditTextFrame(view_Rpassword2));

        //FirebaseAuth 實體
        authControl = FirebaseAuth.getInstance();
        Log.d(TAG, "authControl = " + authControl);


        //TODO:會員註冊 先判斷資料填寫正確
        button_singUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果未輸入內容 則紅框 吐司提示
                if(editText_name.length()==0 ){
                    Toast.makeText(getActivity(), "請輸入名字", Toast.LENGTH_SHORT).show();
                    view_name.setVisibility(View.VISIBLE);
                }else if(editText_Remail.length()==0){
                    Toast.makeText(getActivity(), "請輸入email", Toast.LENGTH_SHORT).show();
                    view_email.setVisibility(View.VISIBLE);
                }else if(editText_Rpassword.length()<5){
                    Toast.makeText(getActivity(), "密碼長度不小於5", Toast.LENGTH_SHORT).show();
                    view_Rpassword.setVisibility(View.VISIBLE);
                }
                else if(!editText_Rpassword.getText().toString().equals(editText_Rpassword2.getText().toString())){
                    Log.d(TAG, "onClick: "+editText_Rpassword.getText().toString());
                    Toast.makeText(getActivity(), "密碼確認不符合", Toast.LENGTH_SHORT).show();
                    view_Rpassword2.setVisibility(View.VISIBLE);
                }else {
                    //隱藏紅色框框
                    view_Rpassword2.setVisibility(View.INVISIBLE);
                    view_email.setVisibility(View.INVISIBLE);
                    view_name.setVisibility(View.INVISIBLE);
                    //取得註冊會員資料
                    String name = editText_name.getText().toString();
                    String email = editText_Remail.getText().toString();
                    String password = editText_Rpassword.getText().toString();
                    //TODO:必須檢查會員存不存在

                    //TODO:註冊firebase會員
                    authControl.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Register ok");
                                FirebaseUser user = authControl.getCurrentUser();
                                DisplayUser(user);
                            } else {
                                Log.d(TAG, "Register fail");
                                Toast.makeText(getActivity(), "註冊失敗", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });//註冊會end
                    //TODO:Firebase會員存資料
                    //取得FirebaseDatabase物件
                    FirebaseDatabase fbControl = FirebaseDatabase.getInstance();
                    Log.d(TAG, "onCreate: fbControl="+fbControl);
                    //參考資料到class資料夾
                    Query classDB = fbControl.getReference("user");
                    Log.d(TAG, "目前所在資料夾"+classDB);

                    ArrayList<Map<String, String>> dataList = new ArrayList<>();
                    dataList.clear();

                    //TODO:監聽 線上的資料庫 資料更新時 就讓App也更新
                    classDB.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            dataList.clear(); //清空List 等下裝新資料
                            int dataCount = (int) snapshot.getChildrenCount();//有幾筆資料
                            Log.d(TAG, " dataCount="+dataCount);

                            for(DataSnapshot ds: snapshot.getChildren()){ //從0~n把snapshot的資料讀完
                                Map<String,String> mapData = new HashMap<String,String>();//建立Map儲存多筆資料的Key和Value(一位使用者的多個屬性資料)

                                //取得snapshot KEY="name"的資料 存到Map裡面
                                String nameValue = (String)ds.child("name").getValue();
                                if(nameValue == null){
                                    mapData.put("name","no name");
                                }else {
                                    mapData.put("name",nameValue);
                                }
                                //取得snapshot KEY="phone"的資料 存到Map裡面
                                String phoneValue = (String)ds.child("phone").getValue();
                                if(nameValue == null){
                                    mapData.put("phone","no phone");
                                }else {
                                    mapData.put("phone",phoneValue);
                                }
                                dataList.add(mapData);//更新資料到List 所以一次for迴圈 增加一個人的name和phone
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    //TODO:Google會員存資料

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
            viewfram=v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()!=0){
                viewfram.setVisibility(View.INVISIBLE);
            }
        }
    }


    private void DisplayUser(FirebaseUser user) {
        String name = user.getDisplayName();
        String email = user.getEmail();
        String UID = user.getUid();
        Log.d(TAG, "DisplayUser: name = "+name);
        Log.d(TAG, "DisplayUser: email = "+email);
        Log.d(TAG, "DisplayUser: UID = " + UID);
    }
}
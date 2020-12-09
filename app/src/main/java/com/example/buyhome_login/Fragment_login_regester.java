package com.example.buyhome_login;

import android.os.Bundle;

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

    public Fragment_login_regester() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_login_regester.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_login_regester newInstance(String param1, String param2) {
        Fragment_login_regester fragment = new Fragment_login_regester();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        //TODO:監聽editText是否有值 如果點選後沒填寫過 就換成紅色
        editText_name.addTextChangedListener(new MyEditTextFrame(view_name));
        editText_Remail.addTextChangedListener(new MyEditTextFrame(view_email));
        editText_Rpassword.addTextChangedListener(new MyEditTextFrame(view_Rpassword));
        editText_Rpassword2.addTextChangedListener(new MyEditTextFrame(view_Rpassword2));


        //當按下註冊按鈕
        button_singUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_name.length()==0 ){
                    Toast.makeText(getActivity(), "請輸入名字", Toast.LENGTH_SHORT).show();
                    view_name.setVisibility(View.VISIBLE);
                }else if(editText_Remail.length()==0){
                    Toast.makeText(getActivity(), "請輸入email", Toast.LENGTH_SHORT).show();
                    view_email.setVisibility(View.VISIBLE);
                }else if(editText_Rpassword.length()==0){
                    Toast.makeText(getActivity(), "請輸入密碼", Toast.LENGTH_SHORT).show();
                    view_Rpassword.setVisibility(View.VISIBLE);
                }
                //else if(!editText_Rpassword.equals(editText_Rpassword2)){
                else if(!editText_Rpassword.getText().toString().equals(editText_Rpassword2.getText().toString())){
                    Log.d(TAG, "onClick: "+editText_Rpassword.getText().toString());
                    Toast.makeText(getActivity(), "密碼確認不符合", Toast.LENGTH_SHORT).show();
                    view_Rpassword2.setVisibility(View.VISIBLE);
                    //TODO:做成紅框框
                }else {
                    String name = editText_name.getText().toString();
                    String email = editText_Remail.getText().toString();
                    String password = editText_Rpassword.getText().toString();
                    view_Rpassword2.setVisibility(View.INVISIBLE);
                    view_email.setVisibility(View.INVISIBLE);
                    view_name.setVisibility(View.INVISIBLE);
                    //TODO:將資料傳到雲端
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
}
package com.example.buyhome_login.network;

import android.content.res.Resources;
import android.util.Log;

import com.example.buyhome_login.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserList {
    private static final String TAG = UserList.class.getSimpleName();

    public final String birth;
    public final String email;
    public final int gender;
    public final String id;
    public final String imageURL;
    public final String name;
    public final String phone;
    public final Location location;

    public UserList(String birth, String email, int gender, String id, String imageURL, String name, String phone, Location location) {
        this.birth = birth;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.imageURL = imageURL;
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public class Location{
        public final String productDelivery;
        public final String receiver;

        public Location(String productDelivery, String receiver) {
            this.productDelivery = productDelivery;
            this.receiver = receiver;
        }
    }


    public static List<UserList> initUserList(Resources resources) {
        JSONObject jsonObject = null;
        InputStream inputStream = resources.openRawResource(R.raw.test20201202);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        try{
            jsonObject=new JSONObject(jsonProductsString);
            System.out.println(jsonObject.get("user").toString());
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<UserList>>() {
            }.getType();
            return gson.fromJson(jsonObject.get("user").toString(), userListType);
        }catch (Exception e){
            System.out.println(e);
        }

        return null;
    }
}

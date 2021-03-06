package com.example.buyhome_login.data;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class MemberAreaViewModel extends ViewModel {
    //帳戶資訊
    public String address;
    public String store;
    public String payMethod;

    //使用者基本資訊
    public static Boolean hasPhoto;
    public static Bitmap userPhotoBitmap;
    public String nickname;
    public String email;

    //使用者資訊
    public String password;
    public int gender;
    private final int GENDER_UNKNOWN = 0;
    private final int GENDER_FEMALE = 1;
    private final int GENDER_MALE = 2;
    public String birthday;
    public String phone;

    /**
     * 初始化
     */
    public MemberAreaViewModel() {
        //初始化帳號資訊
        address = "";
        store = "";
        payMethod = "";

        //初始化頭像、暱稱、信箱
        hasPhoto = false;
        nickname = "";
        email = "";

        //初始化基本資訊
        password = "";
        gender = GENDER_UNKNOWN;
        birthday = "";
        phone = "";

        //TODO 假資料要換真資料
        initMemberInfo(
                "台北市信義區\n信義路五段7號89樓",
                "全家 楊梅幼獅店",
                "宅配");

        initUserBasicInfo(
                hasPhoto,
                "Mr. Hello World",
                "myaccount@gmail.com");

        initUserInfo(
                "123123",
                GENDER_FEMALE,
                "20201212",
                "0911222333");
    }

    private void initWithHardcode(){

    }

    /**
     * 初始化帳號資訊
     */
    private void initMemberInfo(String address, String store, String payMethod ){
        this.address = address;
        this.store = store;
        this.payMethod = payMethod;
    }

    /**
     * 初始化頭像、暱稱、信箱
     */
    private void initUserBasicInfo(Boolean hasPhoto, String nickname, String email){
        this.hasPhoto = hasPhoto;
        this.nickname = nickname;
        this.email = email;
    }

    /**
     * 初始化基本資訊
     */
    private void initUserInfo(String password, int gender, String birthday, String phone){
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
    }


    public Bitmap getUserPhotoBitmap() {
        return userPhotoBitmap;
    }

    public void setUserPhotoBitmap(Bitmap userPhotoBitmap) {
        this.userPhotoBitmap = userPhotoBitmap;
    }

    public Boolean getHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(Boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHided() {
        int length = password.length();
        StringBuilder hidedPWD = new StringBuilder();
        for(int i = 0 ; i < length ; i++){
            hidedPWD.append("*");
        }
        return hidedPWD.toString();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        switch (gender){
            case 0:
                return "Unknown";
            case 1:
                return "Female";
            case 2:
                return "Male";
            default:
                return "Unknown";
        }
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

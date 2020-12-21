package com.example.buyhome_login.data;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class MemberAreaViewModel extends ViewModel {
    //帳戶資訊
    public String address;
    public String store;
    public String payMethod;

    //使用者資訊
    public Boolean hasPhoto;
    public Bitmap userPhotoBitmap;
    public String nickname;
    public String account_id;
    public String password;
    public int gender;
    private final int GENDER_UNKNOWN = 0;
    private final int GENDER_FEMALE = 1;
    private final int GENDER_MALE = 2;
    public int birthday;
    public String phone;
    public String email;

    /**
     * 初始化
     */
    public MemberAreaViewModel() {
        //TODO 假資料要換真資料
        address = "台北市信義區信義路五段7號89樓";
        store = "全家 楊梅幼獅店";
        payMethod = "宅配";


        hasPhoto = false;
        account_id = "MyAccount";
        nickname = "Mr. Hello World";
        password = "123123";
        gender = GENDER_FEMALE;
        birthday = 20201212;
        phone = "0911222333";
        email = "myaccount@gmail.com";
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

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getPassword() {
        return password;
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

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
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

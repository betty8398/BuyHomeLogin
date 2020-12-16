package com.example.buyhome_login.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.buyhome_login.network.ProductEntry;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private static List<ProductEntry> productList;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setProductList(List<ProductEntry> productList) {
        this.productList = productList;
    }

    public List<ProductEntry> getProductList() {
        return productList;
    }
}
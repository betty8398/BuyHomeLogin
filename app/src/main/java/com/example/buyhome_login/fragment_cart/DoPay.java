package com.example.buyhome_login.fragment_cart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.buyhome_login.R;
import com.example.buyhome_login.util.PaymentsUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;


public class DoPay extends Fragment {
    Context context;
    View view;

    //6-1-1.用戶端，用於與 Google Pay API 互動
    private PaymentsClient paymentsClient;

    //8-1.宣告 googlePay 按鈕
    View googlePayButton;

    //8-6.交易序號，用於追蹤該筆交易
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = requireActivity();
        view = inflater.inflate(R.layout.fragment_do_pay, container, false);

        //開啟 ActionBar
        setHasOptionsMenu(true);

        //6-1-2.初始化 Google Pay API 用戶端
        //此處使用的 PaymentsUtil.createPaymentsClient(this) 方法解釋見6-2
        paymentsClient = PaymentsUtil.createPaymentsClient((Activity) context);

        //8-2.[按鈕] 取得按鈕
        googlePayButton = view.findViewById(R.id.googlePayButton);
        googlePayButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestPayment(view);
                        Log.d("myTest", "按按鈕");
                    }
                });

        //8-3.確認使用者能夠使用 Google Pay 並顯示按鈕
        possiblyShowGooglePayButton();

        return view;
    }

    //設定返回鍵功能
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //取得被點擊的物件編號
        switch (item.getItemId()){
            //若編號為返回鍵則做
            case android.R.id.home:
                //返回前頁
                Navigation.findNavController(view).popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //8-4.確認使用者能夠使用 Google Pay ，若可以則顯示按鈕
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }

        //非同步執行，確認是否可付款
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        //若可付款，則顯示付款按鈕
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener((Activity)context,
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            setGooglePayAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }
    //8-4-2.若可付款，則顯示付款按鈕
    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(context, R.string.googlepay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }

    //8-5-1.顯示 Google Pay 頁面
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestPayment(View view) {

        //隱藏按鈕，避免多次點擊
        googlePayButton.setClickable(false);

        //[設定價格]
        long testPrice = 31;

        //存入價格
        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(testPrice);
        if (!paymentDataRequestJson.isPresent()) {
            return;
        }

        //設定付款 Request
        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

        //8-5-2.此處會跳出一個視窗詢問使用者付款方式。
        //並使用 AutoResolveHelper 來等待使用者點擊按鈕。一旦點擊後，系統會呼叫 onActivityResult 發出 request。
        if (request != null) {
            //這是一個 com.google.android.gms.wallet 中的函數
            AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request),
                    (Activity) context, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }
    }

    //9-2.處理 request 回傳的結果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //依照回應碼做相應處理
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        //利用付款信息執行實際交易
                        handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }
                //關閉按鈕，避免連續點擊
                googlePayButton.setClickable(true);
        }
    }

    //9-3.實際進行交易
    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String tokenizationType = tokenizationData.getString("type");
            final String token = tokenizationData.getString("token");

            if ("PAYMENT_GATEWAY".equals(tokenizationType) && "examplePaymentMethodToken".equals(token)) {
                new AlertDialog.Builder(context)
                        .setTitle("Warning")
                        .setMessage(getString(R.string.gateway_replace_name_example))
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }

            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    context, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }
    //9-4.處理錯誤訊息
    private void handleError(int statusCode) {
        Log.e("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }
}
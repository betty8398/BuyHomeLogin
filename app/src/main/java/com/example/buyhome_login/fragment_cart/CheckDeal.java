package com.example.buyhome_login.fragment_cart;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.buyhome_login.R;
import com.example.buyhome_login.adapter.CheckDealAdapter;
import com.example.buyhome_login.data.ShoppingCartViewModel;

public class CheckDeal extends Fragment {
    Context context;
    View view;

    //ViewModel
    private ShoppingCartViewModel viewModel;
    //RecyclerView
    private RecyclerView rvCheckdeal;
    private CheckDealAdapter adapter;

    ImageButton imgBtnDelivery;
    EditText etDiscountCode;
    TextView tvDiscountCodeTip, tvPurePriceDisplay, tvDiscountDisplay, tvDeliveryFeeDisplay, tvTotalDisplay, tvDeliveryDisplay;
    Button btnGoDoPay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_check_deal, container, false);
        context = requireActivity();

        //開啟 ActionBar
        setHasOptionsMenu(true);

        //取得自定義 ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingCartViewModel.class);

        //[清單] 呈現商品資料
        rvCheckdeal = view.findViewById(R.id.rv_checkdeal);
        StaggeredGridLayoutManager mLayoutManager_stagger = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvCheckdeal.setLayoutManager(mLayoutManager_stagger);
        adapter = new CheckDealAdapter(context, viewModel.getCheckedNameList(), viewModel.getCheckedPriceList(),viewModel.getCheckedPictureList(), viewModel.getCheckedAmountList());
        rvCheckdeal.setAdapter(adapter);

        //顯示寄送方式
        tvDeliveryDisplay = view.findViewById(R.id.tv_delivery_display);
        switch (viewModel.deliveryMethod){
            case 0:
                tvDeliveryDisplay.setText("");
                break;
            case 1:
                tvDeliveryDisplay.setText("宅配");
                break;
            case 2:
                tvDeliveryDisplay.setText("超商取貨");
                break;
        }

        //[按鈕]  前往"設定寄送方式"
        imgBtnDelivery = view.findViewById(R.id.imgbtn_delivery);
        imgBtnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 隱藏鍵盤
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                Navigation.findNavController(view).navigate(R.id.action_checkDeal_to_checkDelivery);
            }
        });

        //[輸入]  折扣碼
        //初始化折扣碼提示
        tvDiscountCodeTip = view.findViewById(R.id.tv_discount_code_tip);
        tvDiscountCodeTip.setVisibility(View.INVISIBLE);

        etDiscountCode = view.findViewById(R.id.et_discount_code);
        etDiscountCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                    checkDiscountCode(editable);
            }
        });

        //[金額呈現]
        //取得元件
        tvPurePriceDisplay = view.findViewById(R.id.tv_pure_price_display);
        tvDiscountDisplay = view.findViewById(R.id.tv_discount_display);
        tvTotalDisplay = view.findViewById(R.id.tv_total_display);
        //設定值
        tvPurePriceDisplay.setText("$" + viewModel.getPureTotalPrice());
        tvDiscountDisplay.setText("$" + viewModel._discount.getValue());
        viewModel.setTotalPrice();
        tvTotalDisplay.setText("$" + viewModel.getTotalPrice());

        //[按鈕]  前往付款
        btnGoDoPay = view.findViewById(R.id.btn_go_dopay);
        btnGoDoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_checkDeal_to_doPay);
            }
        });

        //[LiveData]
        //建立Discount的觀察者
        final Observer<Integer> observerDiscount = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newValue) {
                //若觀測到資料變化則做
                tvDiscountDisplay.setText("-$" + viewModel.getDiscount());
            }
        };
        //連結 LiveData 與觀察者
        viewModel._discount.observe((LifecycleOwner) context, observerDiscount);

        //建立TotalPrice的觀察者
        final Observer<Integer> observerTotalPrice = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer newValue) {
                //若觀測到資料變化則做
                tvTotalDisplay.setText("$" + viewModel.getTotalPrice());
            }
        };
        //連結 LiveData 與觀察者
        viewModel._totalPrice.observe((LifecycleOwner) context, observerTotalPrice);

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

    /**
     * 檢查折扣碼
     * @param editable
     */
    private void checkDiscountCode(Editable editable) {
        //圖示符合折扣碼字數才可見
        if(etDiscountCode.getText().toString().length() == 4){
            tvDiscountCodeTip.setVisibility(View.VISIBLE);

            //依折扣碼對錯選擇相應圖示
            if(editable.toString().equals("1234")){
                tvDiscountCodeTip.setText("OK");
                tvDiscountCodeTip.setBackgroundResource(R.drawable.frame_03);
                etDiscountCode.setBackground(getResources().getDrawable(R.drawable.frame_greenline));
                viewModel.setDiscount(100);
            }else {
                tvDiscountCodeTip.setText("－");
                tvDiscountCodeTip.setBackgroundResource(R.drawable.frame_04);
                etDiscountCode.setBackground(getResources().getDrawable(R.drawable.frame_redline));
                viewModel.setDiscount(0);
            }

        }else {
            tvDiscountCodeTip.setVisibility(View.INVISIBLE);
            etDiscountCode.setBackground(getResources().getDrawable(R.drawable.frame_redline));
            viewModel.setDiscount(0);
        }
    }


}
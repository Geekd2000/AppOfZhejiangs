package com.example.appofzhejiang.pay;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.R;

public class OrderDialog extends Dialog implements View.OnClickListener {

    private Button mBtnReturn;
    private Button mUse;
    private Button buy;
    private TextView mPay1, mPay2;//两个实付
    private TextView mGoodsName1, mGoodsName2;//两盒商品名称
    private TextView mGoodsType;//商品种类
    private TextView mAmount;//商品数量
    private TextView mGoodsUnitPrice;//商品单价
    private TextView mUsername;//收货姓名
    private TextView mTelephone;//收货电话
    private TextView mAddress;//收货地址
    private ImageView imageGoods;//商品图片
    private TextView time;//订单时间
    private TextView number;//订单号

    private IOnConfirmListener mPay;
    private IOnUseListener use;
    private IOnGotoListener mReturn;

    private String pay1;
    private String pay2;
    private String goodsName1;
    private String goodsName2;
    private String goodsType;
    private String amount;
    private String goodsUnitPrice;
    private String username;
    private String telephone;
    private String address;
    private String use1, confirm, goto1;
    private String image;
    private Context context;
    private String timeNow;
    private String mNumber;


    public void setNumber(String number){
        this.mNumber = number;
    }
    public void setTimeNow(String time){
        this.timeNow = time;
    }
    public void setPay1(String pay1) {
        this.pay1 = pay1;
    }

    public void setPay2(String pay2) {
        this.pay2 = pay2;
    }

    public void setGoodsName1(String goodsName1) {
        this.goodsName1 = goodsName1;
    }

    public void setGoodsName2(String goodsName2) {
        this.goodsName2 = goodsName2;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setGoodsUnitPrice(String goodsUnitPrice) {
        this.goodsUnitPrice = goodsUnitPrice;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image,Context context) {
        this.image = image;
        this.context = context;
    }

    public void setUse(String use, IOnUseListener useListener) {
        this.use1 = use;
        this.use = useListener;
    }

    public void setConfirm(String confirm, IOnConfirmListener confirmListener) {
        this.confirm = confirm;
        this.mPay = confirmListener;
    }

    public void setGoto(String Goto,IOnGotoListener gotoListener){
        this.goto1 = Goto;
        this.mReturn = gotoListener;
    }


    //构造方法
    public OrderDialog(@NonNull Context context) {
        super(context);
    }

    public OrderDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_return:
                mReturn.onGoto(this);
                dismiss();
                break;
            case R.id.order_use:
                use.onUse(this);
                dismiss();
                break;
            case R.id.order_buy:
                mPay.onConfirm(this);
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_1);


        //设置弹窗的宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * 0.9);//是dialog的宽度为app界面的80%
        getWindow().setAttributes(p);

        init();

        //设置点击事件
        buy.setOnClickListener(this);
        mUse.setOnClickListener(this);
        mBtnReturn.setOnClickListener(this);

        if(!TextUtils.isEmpty(mNumber)){
            number.setText(mNumber);
        }
        if(!TextUtils.isEmpty(timeNow)){
            time.setText(timeNow);
        }
        if (!TextUtils.isEmpty(pay1)) {
            mPay1.setText(pay1);
        }
        if (!TextUtils.isEmpty(pay2)) {
            mPay2.setText(pay2);
        }
        if (!TextUtils.isEmpty(goodsName1)) {
            mGoodsName1.setText(goodsName1);
        }
        if (!TextUtils.isEmpty(goodsName2)) {
            mGoodsName2.setText(goodsName2);
        }
        if (!TextUtils.isEmpty(goodsType)) {
            mGoodsType.setText(goodsType);
        }
        if (!TextUtils.isEmpty(goodsUnitPrice)) {
            mGoodsUnitPrice.setText(goodsUnitPrice);
        }
        if (!TextUtils.isEmpty(amount)) {
            mAmount.setText(amount);
        }
        if (!TextUtils.isEmpty(address)) {
            mAddress.setText(address);
        }
        if (!TextUtils.isEmpty(pay1)) {
            mPay1.setText(pay1);
        }
        if (!TextUtils.isEmpty(telephone)) {
            mTelephone.setText(telephone);
        }
        if (!TextUtils.isEmpty(username)) {
            mUsername.setText(username);
        }
        if (!TextUtils.isEmpty(image)) {
            Glide.with(context).load(image).into(imageGoods);
        }
    }

    //初始化控件 找到控件
    public void init() {
        mBtnReturn = findViewById(R.id.order_return);
        mGoodsName1 = findViewById(R.id.goods_name1);
        mGoodsName2 = findViewById(R.id.goods_name2);
        mPay1 = findViewById(R.id.pay1);
        mPay2 = findViewById(R.id.pay2);
        mGoodsType = findViewById(R.id.goods_type);
        mAmount = findViewById(R.id.order_amount);
        mGoodsUnitPrice = findViewById(R.id.order_goods_unitPrice);
        mUsername = findViewById(R.id.username);
        mTelephone = findViewById(R.id.telephone);
        mAddress = findViewById(R.id.address);
        imageGoods = findViewById(R.id.imageGoods);
        mBtnReturn = findViewById(R.id.order_return);
        mUse = findViewById(R.id.order_use);
        buy = findViewById(R.id.order_buy);
        time = findViewById(R.id.order_time);
        number = findViewById(R.id.order_number);
    }

    public interface IOnUseListener {
        void onUse(OrderDialog dialog);
    }

    public interface IOnConfirmListener {
        void onConfirm(OrderDialog dialog);
    }

    public interface IOnGotoListener {
        void onGoto(OrderDialog dialog);
    }
}

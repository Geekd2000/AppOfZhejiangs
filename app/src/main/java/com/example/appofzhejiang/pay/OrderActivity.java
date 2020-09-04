package com.example.appofzhejiang.pay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.ToastUtils;
import com.example.appofzhejiang.fragment3.TicketActivity;
import com.example.appofzhejiang.xihu.more;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity {

    private Button mBtnReturn;
    private Toolbar mBtnBack;
    private TextView mPay1, mPay2;//两个实付
    private TextView mGoodsName1, mGoodsName2;//两盒商品名称
    private TextView mGoodsType;//商品种类
    private TextView mAmount;//商品数量
    private TextView mGoodsUnitPrice;//商品单价
    private TextView mUsername;//收货姓名
    private TextView mTelephone;//收货电话
    private TextView mAddress;//收货地址
    private ImageView imageGoods;//商品图片
    private TextView orderNumber;//订单编号
    private Button orderBuy;//去付款
    private Button orderUse;//去使用
    private TextView orderTime;//下单时间
    //悬浮按钮
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //初始化控件
        init();
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);
        //返回我的
        mBtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                intent.putExtra("numb", 3);
                startActivity(intent);
                OrderActivity.this.finish();
            }
        });//返回上一页按钮
        mBtnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//标题栏返回按钮

        //初始化悬浮按钮
        initFloatActionButton();

        //获得传递过来的参数
        Intent intent = getIntent();
        final String order_id = intent.getStringExtra("order_id");
        //通过订单ID获取服务器数据
        final OrderBean orderBean = new OrderBeanUtil(order_id).getOrderBean();
        //单价获取
        double unitPrice = orderBean.getMoney() / orderBean.getNum();
        mGoodsName1.setText(orderBean.getShop_name());
        mGoodsName2.setText(orderBean.getShop_name());
        mPay1.setText(String.valueOf(orderBean.getMoney()));
        mPay2.setText(String.valueOf(orderBean.getMoney()));
        mGoodsType.setText(orderBean.getParam());
        mAmount.setText(String.valueOf(orderBean.getNum()));
        mGoodsUnitPrice.setText(String.valueOf(unitPrice));
        mUsername.setText(orderBean.getName());
        mTelephone.setText(orderBean.getTel());
        mAddress.setText(orderBean.getDelivery_way());
        orderNumber.setText(orderBean.getOrder_no());
        orderTime.setText(orderBean.getDatetime());
        Glide.with(this).load(orderBean.getRemarks()).into(imageGoods);

        if (orderBean.getPay()) {
            orderBuy.setVisibility(View.GONE);
        } else {
            //跳转至支付成功页面
            orderBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        runPaySet(orderBean.getDelivery_way(), orderBean.getOrder_no());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(OrderActivity.this, PaySuccessActivity.class);
                    startActivity(intent);
                    OrderActivity.this.finish();
                }
            });
        }

        if (orderBean.getPin()) {
            orderUse.setVisibility(View.GONE);
        } else if (!orderBean.getPin() && !orderBean.getPay()) {
            orderUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.show(OrderActivity.this, "请先付款");
                }
            });
        } else if (!orderBean.getPin() && orderBean.getPay()) {
            //使用成功界面
            orderUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    runUseSet(String.valueOf(orderBean.getOrder_id()), String.valueOf(orderBean.getProduct_id()));
                    Intent intent = new Intent(OrderActivity.this, UseSuccessActivity.class);
                    startActivity(intent);
                    OrderActivity.this.finish();
                }
            });
        }
    }

    //向服务器发送put请求,处理支付
    private void runPaySet(String delivery_way, String order_no) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("_pay", true);
        map.put("delivery_way", delivery_way);
        map.put("order_no", order_no);
        String params = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, params);
        final Request request = new Request.Builder()
                .url("http://120.26.172.104:9002/wx/updateOrder")
                .put(requestBody)
                .build();
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t1.join();
    }

    //向服务器发送put请求,处理使用
    private void runUseSet(String order_id, String product_id) {
        String url = "http://120.26.172.104:9002/wx/destructionOrder";
        RequestBody formBody = new FormBody.Builder()
                .add("order_id", order_id)
                .add("product_id", product_id)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .put(formBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d("TAG", headers.name(i) + ":" + headers.value(i));
                }
                Log.d("TAG", "onResponse: " + response.body().string());
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }

    //悬浮按钮配置
    public void initFloatActionButton() {
        floatingActionButton1 = findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);

        floatingActionButton1.setLabelText("首页");
        floatingActionButton2.setLabelText("景点");
        floatingActionButton3.setLabelText("商城");
        floatingActionButton1.setImageResource(R.drawable.shouye);
        floatingActionButton2.setImageResource(R.drawable.jingdian);
        floatingActionButton3.setImageResource(R.drawable.shangcheng);

        //跳转至首页页面
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                intent.putExtra("numb", 0);
                startActivity(intent);
                OrderActivity.this.finish();
            }
        });

        //跳转至更多热门景点列表页面（暂无跳转 更多景点页面未写）
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                intent.putExtra("numb", 1);
                startActivity(intent);
                OrderActivity.this.finish();
            }
        });

        //跳转至商品列表页面(默认进入商品列表的门票页面)
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                intent.putExtra("numb", 2);
                startActivity(intent);
                OrderActivity.this.finish();
            }
        });
    }

    //初始化控件 找到控件
    public void init() {
        mBtnBack = findViewById(R.id.order_toolbar_back);
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
        orderNumber = findViewById(R.id.order_number);
        orderBuy = findViewById(R.id.order_buy);
        orderTime = findViewById(R.id.order_time);
        orderUse = findViewById(R.id.order_use);
    }
}
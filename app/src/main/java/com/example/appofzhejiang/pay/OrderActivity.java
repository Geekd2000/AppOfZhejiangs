package com.example.appofzhejiang.pay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.SubmitOrderActivity;
import com.example.appofzhejiang.fragment3.TicketActivity;
import com.github.clans.fab.FloatingActionButton;

public class OrderActivity extends AppCompatActivity {

    private Button mBtnReturn;
    private Toolbar mBtnBack;
    private TextView mPay1,mPay2;//两个实付
    private TextView mGoodsName1,mGoodsName2;//两盒商品名称
    private TextView mGoodsType;//商品种类
    private TextView mAmount;//商品数量
    private TextView mGoodsUnitPrice;//商品单价
    private TextView mUsername;//收货姓名
    private TextView mTelephone;//收货电话
    private TextView mAddress;//收货地址
    private ImageView imageGoods;//商品图片
    //悬浮按钮
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        //初始化控件
        init();

        mBtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//返回我的按钮
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
        String name = intent.getStringExtra("goodsName");
        String type = intent.getStringExtra("goodsType");
        String unitPrice = intent.getStringExtra("goodsUnitPrice");
        String amount = intent.getStringExtra("goodsAmount");
        String pay = intent.getStringExtra("goodsPay");
        String username = intent.getStringExtra("username");
        String telephone = intent.getStringExtra("telephone");
        String address = intent.getStringExtra("address");
        String image =intent.getStringExtra("goodsImage");
        mGoodsName1.setText(name);
        mGoodsName2.setText(name);
        mPay1.setText(pay);
        mPay2.setText(pay);
        mGoodsType.setText(type);
        mAmount.setText(amount);
        mGoodsUnitPrice.setText(unitPrice);
        mUsername.setText(username);
        mTelephone.setText(telephone);
        mAddress.setText(address);
        imageGoods.setImageResource(Integer.parseInt(image));
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
                startActivity(intent);
            }
        });

        //跳转至更多热门景点列表页面（暂无跳转 更多景点页面未写）
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, RecyclerPageActivity.class);
//                startActivity(intent);
            }
        });

        //跳转至商品列表页面(默认进入商品列表的门票页面)
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, TicketActivity.class);
                intent.putExtra("num", "0");
                startActivity(intent);
            }
        });
    }

    //初始化控件 找到控件
    public void init() {
        mBtnBack = findViewById(R.id.order_toolbar_back);
        mBtnReturn = findViewById(R.id.order_return);
        mGoodsName1=findViewById(R.id.goods_name1);
        mGoodsName2=findViewById(R.id.goods_name2);
        mPay1=findViewById(R.id.pay1);
        mPay2=findViewById(R.id.pay2);
        mGoodsType=findViewById(R.id.goods_type);
        mAmount=findViewById(R.id.order_amount);
        mGoodsUnitPrice=findViewById(R.id.order_goods_unitPrice);
        mUsername=findViewById(R.id.username);
        mTelephone=findViewById(R.id.telephone);
        mAddress=findViewById(R.id.address);
        imageGoods=findViewById(R.id.imageGoods);
    }
}
package com.example.appofzhejiang.pay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.SubmitOrderActivity;
import com.example.appofzhejiang.fragment3.TicketActivity;
import com.github.clans.fab.FloatingActionButton;

public class OrderActivity extends AppCompatActivity {

    private Button mBtnReturn;
    private Toolbar mBtnBack;
    //悬浮按钮
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
//        order.findViewById(R.id.order_buy1);
        mBtnBack = findViewById(R.id.order_toolbar_back);
        mBtnReturn = findViewById(R.id.order_buy1);

        mBtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        mBtnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //初始化悬浮按钮
        initFloatActionButton();
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
}
package com.example.appofzhejiang.pay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.fragment3.TicketActivity;

public class PaySuccessActivity extends AppCompatActivity {

    private TextView continueBuy,backMain,toUse;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        //返回上一页
        toolbar=findViewById(R.id.success_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //跳转至商城
        continueBuy=findViewById(R.id.continue_buy);
        continueBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PaySuccessActivity.this, MainActivity.class);
                intent.putExtra("numb",2);
                startActivity(intent);
                PaySuccessActivity.this.finish();
            }
        });

        //跳转至首页
        backMain=findViewById(R.id.back_main);
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PaySuccessActivity.this, MainActivity.class);
                startActivity(intent);
                PaySuccessActivity.this.finish();
            }
        });

        //跳转至订单管理
        toUse=findViewById(R.id.toUse);
        toUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PaySuccessActivity.this, PayActivity.class);
                intent.putExtra("id",2);
                startActivity(intent);
                PaySuccessActivity.this.finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }
}
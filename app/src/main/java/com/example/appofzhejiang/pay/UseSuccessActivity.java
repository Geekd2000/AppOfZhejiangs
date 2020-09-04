package com.example.appofzhejiang.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.fragment3.TicketActivity;

public class UseSuccessActivity extends AppCompatActivity {

    private TextView continueUse,backMain;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_success);
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

        //跳转至订单管理页面
        continueUse=findViewById(R.id.continue_use);
        continueUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UseSuccessActivity.this.finish();
            }
        });

        //跳转至我的页面
        backMain=findViewById(R.id.back_main);
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UseSuccessActivity.this, MainActivity.class);
                startActivity(intent);
                UseSuccessActivity.this.finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }
}
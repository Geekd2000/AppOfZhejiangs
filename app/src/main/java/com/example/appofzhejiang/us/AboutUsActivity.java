package com.example.appofzhejiang.us;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.appofzhejiang.Login.LoginActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

public class AboutUsActivity extends AppCompatActivity {

    private Toolbar mBack;
    private TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        mBack = findViewById(R.id.setting_toolbar);
        tv1 = findViewById(R.id.txt_setting_1);
        tv2 = findViewById(R.id.txt_setting_2);
        tv3 = findViewById(R.id.txt_setting_3);
        tv4 = findViewById(R.id.txt_setting_4);

        mBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutUsActivity.this, "服务协议暂无", Toast.LENGTH_SHORT).show();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutUsActivity.this, "没有隐私政策", Toast.LENGTH_SHORT).show();
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutUsActivity.this, "无版权", Toast.LENGTH_SHORT).show();
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutUsActivity.this, "自己体验", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }
}
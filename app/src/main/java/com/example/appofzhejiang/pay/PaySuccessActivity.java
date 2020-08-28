package com.example.appofzhejiang.pay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.TicketActivity;

public class PaySuccessActivity extends AppCompatActivity {

    private TextView continueBuy,backMain;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);

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
                Intent intent=new Intent(PaySuccessActivity.this, TicketActivity.class);
                intent.putExtra("num", "0");
                startActivity(intent);
            }
        });

        //跳转至首页
        backMain=findViewById(R.id.back_main);
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PaySuccessActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }
}
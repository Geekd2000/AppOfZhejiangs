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

public class OrderActivity extends AppCompatActivity {

    private Button mBtnReturn;
    private Toolbar mBtnBack;

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
    }
}
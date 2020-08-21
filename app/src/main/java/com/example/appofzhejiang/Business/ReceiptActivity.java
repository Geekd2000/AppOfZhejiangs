package com.example.appofzhejiang.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appofzhejiang.R;

import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private Button mBtnNew;
    private Toolbar mBack;
    private RecyclerView mRyAddress;
    private List<AddressList> date = new ArrayList<AddressList>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        mBtnNew = findViewById(R.id.btn_new);
        mBack = findViewById(R.id.receipt_toolbar);
        mRyAddress = findViewById(R.id.address_message);
        mRyAddress.setLayoutManager(new LinearLayoutManager(ReceiptActivity.this));
        mRyAddress.setAdapter(new ReceiptAdapter(ReceiptActivity.this,date));

        mBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(ReceiptActivity.this,AddressActivity.class);
                startActivity(intent);
            }
        });
    }
}
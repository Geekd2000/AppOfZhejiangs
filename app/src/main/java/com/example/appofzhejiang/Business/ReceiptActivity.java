package com.example.appofzhejiang.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private TextView mTxtNew;
    private Toolbar mBack;
    private RecyclerView mRyAddress;
    private ReceiptAdapter adapter;
    private List<AddressList> data = new ArrayList<AddressList>();//收货信息
    private AddressList addressList = new AddressList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        initView();
        //初始化RecyclerView
        initRecycleView();

        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        mBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTxtNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceiptActivity.this, AddressActivity.class);
                intent.putExtra("size", Integer.toString(data.size()));
                startActivityForResult(intent, 101);
            }
        });

        Intent in = getIntent();
        int code = Integer.parseInt(in.getStringExtra("code"));
        if (code == 1) {
            adapter.setOnItemClickListener(new ReceiptAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, AddressList addressList) {
                    Intent intent = new Intent();
                    intent.putExtra("name", addressList.getUsername());
                    intent.putExtra("phone", addressList.getPhone());
                    intent.putExtra("address", addressList.getAddress());
                    setResult(2, intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent != null) {
            String receiveName = intent.getStringExtra("name");
            String telephone = intent.getStringExtra("phone");
            String address = intent.getStringExtra("address");
            String select = intent.getStringExtra("select");
            addressList.setUsername(receiveName);
            addressList.setPhone(telephone);
            addressList.setAddress(address);
            addressList.setSelect(select);
            //添加自带默认动画
            adapter.addData(data.size());
        }
    }


    //初始化RecyclerView
    private void initRecycleView() {
        mRyAddress.setLayoutManager(new LinearLayoutManager(ReceiptActivity.this));
        //获取数据,向适配器传数据,绑定适配器
        adapter = new ReceiptAdapter(ReceiptActivity.this, data, addressList);
        mRyAddress.setAdapter(adapter);
        //添加动画
        mRyAddress.setItemAnimator(new DefaultItemAnimator());
    }

    //初始化控件
    private void initView() {
        mTxtNew = findViewById(R.id.txt_new);
        mBack = findViewById(R.id.receipt_toolbar);
        mRyAddress = findViewById(R.id.address_message);
    }
}
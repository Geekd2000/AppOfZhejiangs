package com.example.appofzhejiang.Business;

import androidx.annotation.NonNull;
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
import android.widget.TextView;

import com.example.appofzhejiang.Login.LoginUtil;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private TextView mTxtNew;
    private Toolbar mBack;
    private RecyclerView mRyAddress;
    private ReceiptAdapter adapter;
    private List<AddressBean> data = new ArrayList<AddressBean>();//收货信息
    private SmartRefreshLayout refreshLayout;//刷新
    private int userID;//用户ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        initView();

        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        //获取用户id
        SharedPreferences sp = ReceiptActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginUserName = sp.getString("loginUserName", null);
        userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
        //初始化RecyclerView
        initRecycleView();

        mBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //添加新地址
        mTxtNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceiptActivity.this, AddressActivity.class);
                intent.putExtra("num",0);
                startActivity(intent);
            }
        });

        Intent in = getIntent();
        int code = Integer.parseInt(in.getStringExtra("code"));
        if (code == 1) {
            adapter.setOnItemClickListener(new ReceiptAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, AddressBean addressBean) {
                    Intent intent = new Intent();
                    intent.putExtra("name", addressBean.getName());
                    intent.putExtra("phone", addressBean.getMobile());
                    intent.putExtra("address", addressBean.getAddress());
                    setResult(2, intent);
                    finish();
                }
            });
        }

        refreshLayout = findViewById(R.id.refreshLayout);//SmartRefreshLayout控件
        refreshLayout.setRefreshHeader(new ClassicsHeader(ReceiptActivity.this));//设置Header样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(ReceiptActivity.this));//设置Footer样式
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                adapter.refresh(new AddressBeanList(String.valueOf(userID)).getAddressBeanList());
                refreshLayout.finishRefresh();//延迟2000毫秒后结束刷新  传入false表示刷新失败
                //不传时间则立即停止刷新
            }
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);//延迟2000毫秒后结束加载  传入false表示刷新失败
                refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.refresh(new AddressBeanList(String.valueOf(userID)).getAddressBeanList());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }


    //初始化RecyclerView
    private void initRecycleView() {
        mRyAddress.setLayoutManager(new LinearLayoutManager(ReceiptActivity.this));
        //获取数据,向适配器传数据,绑定适配器
        adapter = new ReceiptAdapter(new AddressBeanList(String.valueOf(userID)).getAddressBeanList(),ReceiptActivity.this);
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
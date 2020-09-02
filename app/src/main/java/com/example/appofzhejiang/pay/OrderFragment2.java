package com.example.appofzhejiang.pay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appofzhejiang.Login.LoginUtil;
import com.example.appofzhejiang.R;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment2#} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment2 extends Fragment {

    private View view;
    public RecyclerView recyclerView;
    private PayAdapter payAdapter;
    private SmartRefreshLayout refreshLayout;
    private String content;
    private int userID;//用户ID

    public OrderFragment2() {
    }

    public OrderFragment2(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order2, container, false);
        //获取用户id
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginUserName = sp.getString("loginUserName", null);
        userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();

        initRecyclerView();

        //刷新加载
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));//设置Header样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));//设置Footer样式
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                payAdapter.refresh(new OrderBeanListUtil(2,String.valueOf(userID)).getOrderBeanList());
                refreshLayout.finishRefresh();//结束刷新  传入false表示刷新失败
                //不传时间则立即停止刷新
            }
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                //模拟网络请求到的数据
                refreshLayout.finishLoadMore(1000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        payAdapter.refresh(new OrderBeanListUtil(2,String.valueOf(userID)).getOrderBeanList());
    }

    private void initRecyclerView() {
        //获取recyclerview
        recyclerView = view.findViewById(R.id.fragment_order2);
        //创建Adapter
        payAdapter = new PayAdapter(new OrderBeanListUtil(2,String.valueOf(userID)).getOrderBeanList(),getActivity());
        recyclerView.setAdapter(payAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
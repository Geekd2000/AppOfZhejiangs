package com.example.appofzhejiang.pay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appofzhejiang.R;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment4 extends Fragment {
    private View view;
    public RecyclerView recyclerView;
    private PayAdapter payAdapter;
    private SmartRefreshLayout refreshLayout;
    private List<FileList> fileLists = new ArrayList<FileList>();
    private String content;

    public OrderFragment4() {
    }

    public OrderFragment4(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order4, container, false);
        initData();
        initList();
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
                refreshLayout.autoRefresh();
                refreshLayout.finishRefresh(2000);//结束刷新  传入false表示刷新失败
                //不传时间则立即停止刷新
            }
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                //模拟网络请求到的数据
                refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
            }
        });
        return view;
    }

    private void initData() {
//        for(int i = 0; i<fileLists.size(); i++){
//            FileList fileList = new FileList();
//            fileLists.add(fileList);
//        }
    }

    private void initRecyclerView() {
        //获取recyclerview
        recyclerView = view.findViewById(R.id.fragment_order4);
        //创建Adapter
        payAdapter = new PayAdapter(getActivity(), fileLists);
        recyclerView.setAdapter(payAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initList() {
        for (int i = 0; i < 10; i++) {
            FileList fileList = new FileList(R.drawable.picturezhejiang, "订单号" + i, "西湖游船外事船舶成人票", "小船", "3", "35", "105", "已完成");
            fileLists.add(fileList);
        }
    }
}
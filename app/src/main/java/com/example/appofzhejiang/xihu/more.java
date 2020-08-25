package com.example.appofzhejiang.xihu;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class more extends AppCompatActivity {

    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //景区攻略的recelyeView
        RecycleViewAdepter_more recycleViewAdepter_more = new RecycleViewAdepter_more(this);
        RecyclerView recyclerView = findViewById(R.id.jq_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        recyclerView.setAdapter(recycleViewAdepter_more);

        refreshLayout = findViewById(R.id.refreshLayout);//SmartRefreshLayout控件
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));//设置Header样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));//设置Footer样式
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                //模拟网络请求到的数据


                refreshLayout.finishRefresh(2000/*false*/);//延迟2000毫秒后结束刷新  传入false表示刷新失败
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
    }
}
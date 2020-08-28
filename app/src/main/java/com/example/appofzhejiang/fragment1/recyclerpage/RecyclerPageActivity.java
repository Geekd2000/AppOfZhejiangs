package com.example.appofzhejiang.fragment1.recyclerpage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;


import java.util.List;

public class RecyclerPageActivity extends AppCompatActivity {
    private List<RecyclerBean> recyclerBeanList; // 数据List
    private Toolbar backButton; // 标题栏的返回按钮
    private String currentCity; // 当前城市
    private String type; // 类型
    private TextView tourismPublicityTitle; // 标题栏的标题
    private SwipeRefreshLayout swipeRefresh; // 下拉刷新
    private RecyclerPageAdapter adapter; // RecyclerView的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_page);
        Intent intent = getIntent();
        this.currentCity = intent.getStringExtra("currentCity");
        this.type = intent.getStringExtra("type");
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);
        // 隐藏系统标题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 设置标题
        this.tourismPublicityTitle = (TextView) findViewById(R.id.tourism_publicity_title);
        setRecyclerPageTitle(this.type);

        // 初始化
        recyclerBeanList = new RecyclerBeanListUtil(currentCity, type).getRecyclerBeanList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerPageAdapter(recyclerBeanList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // 设置标题栏返回按钮点击事件
        backButton = findViewById(R.id.back_toolbar);
        backButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 实现下拉刷新 start
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.recycler_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBeanList();
            }
        });

        // 实现下拉刷新 end

        // 判断recyclerView是否已经滑到底部了
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        Toast.makeText(RecyclerPageActivity.this, "已经显示全部数据", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dx >= 0) {
                    //大于0表示正在向右滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0表示停止或向左滚动
                    isSlidingToLast = false;
                }
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }

    private void refreshBeanList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerBeanList = new RecyclerBeanListUtil(currentCity, type).getRecyclerBeanList();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false); // 刷新事件结束，并隐藏刷新进度条。
                    }
                });
            }
        }).start();
    }


    /**
     * 设置标题
     *
     * @param location
     */
    private void setRecyclerPageTitle(String location) {
        if (location == null) {
            this.tourismPublicityTitle.setText("详情信息");
            return;
        }
        if (RecyclerType.NEWS.equals(location)) {
            this.tourismPublicityTitle.setText("专题新闻");
        } else if (RecyclerType.STRATEGY.equals(location)) {
            this.tourismPublicityTitle.setText("攻略详情");
        } else {
            this.tourismPublicityTitle.setText("详情信息");
        }

    }
}
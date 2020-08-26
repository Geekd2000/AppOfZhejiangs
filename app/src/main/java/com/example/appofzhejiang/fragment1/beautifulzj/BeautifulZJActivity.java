package com.example.appofzhejiang.fragment1.beautifulzj;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.example.appofzhejiang.R;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BeautifulZJActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefresh;
    private List<BeautifulPicture> bpList = new LinkedList<>();
    Future<List<BeautifulPicture>> result;
    private BeautifulPictureAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beautiful_zj);
        // 设置标题
        TextView tourismPublicityTitle = (TextView) findViewById(R.id.tourism_publicity_title);
        tourismPublicityTitle.setText("最美浙江，最美杭州－精美图片展");
        tourismPublicityTitle.setTextColor(Color.BLACK);
        // 设置标题栏返回按钮点击事件
        Toolbar backButton = findViewById(R.id.back_toolbar);
        backButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // RecyclerView start
        initBPList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        adapter = new BeautifulPictureAdapter(bpList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        // RecyclerView end

        // 实现下拉刷新 start
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBPs();
            }
        });

        // 实现下拉刷新 end
    }

    private void initBPList() {
        bpList.clear();
        for (int i = 0; i < 100; i++) {
            BeautifulPicture bp = new BeautifulPicture();
            bp.setUrl("http://120.26.172.104:8080/picture/des-picture/hz-pic/hz_pic1.png");
            bp.setIntroduction("漂亮极了");
            bpList.add(bp);
        }
//        bpList = getPictures("");
    }

    private List<BeautifulPicture> getPictures(String url) {
        sendRequest(url);
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<BeautifulPicture>>() {
            @Override
            public List<BeautifulPicture> call() throws Exception {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(address)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    return parseJSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    private List<BeautifulPicture> parseJSON(String responseData) {
        return JSON.parseArray(responseData, BeautifulPicture.class);
    }

    private void refreshBPs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initBPList();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false); // 刷新事件结束，并隐藏刷新进度条。
                    }
                });
            }
        }).start();
    }
}
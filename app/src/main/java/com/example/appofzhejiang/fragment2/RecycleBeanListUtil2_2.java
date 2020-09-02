package com.example.appofzhejiang.fragment2;

import com.alibaba.fastjson.JSON;
import com.example.appofzhejiang.xihu.RecyclerBeanJingqu;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecycleBeanListUtil2_2 {
    Future<List<RecycleBean2_2>> result;



    public RecycleBeanListUtil2_2() {
        this.initRecyclerBeanJingquLists();
    }

    private void initRecyclerBeanJingquLists() {
        sendRequest("http://120.26.172.104:9002//wx/searchPoi?search=");
//        sendRequest("http://120.26.172.104:9002//wx/findPoiById");
    }

    private void sendRequest(final String s) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<RecycleBean2_2>>() {
            @Override
            public List<RecycleBean2_2> call() throws Exception {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(s)
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

    private List<RecycleBean2_2> parseJSON(String responseData) {
        return JSON.parseArray(responseData, RecycleBean2_2.class);
    }

    public List<RecycleBean2_2> getList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

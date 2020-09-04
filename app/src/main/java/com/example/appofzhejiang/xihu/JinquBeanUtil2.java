package com.example.appofzhejiang.xihu;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JinquBeanUtil2 {
    private String id;
    Future<JinquBean2> result;


    public JinquBeanUtil2(String id) {
        this.id = id;
        this.initDetailBeanLists();
    }

    private void initDetailBeanLists() {
        sendRequest("http://120.26.172.104:9002//wx/findPoiById?place_id="+id);
    }

    private void sendRequest(final String s) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<JinquBean2>() {
            @Override
            public JinquBean2 call() throws Exception {
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

    private JinquBean2 parseJSON(String responseData) {
        return JSON.parseObject(responseData, JinquBean2.class);
    }


    /**
     * 得到TicketList
     */
    public JinquBean2 getDetailBeanList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

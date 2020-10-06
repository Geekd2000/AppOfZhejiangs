package com.example.appofzhejiang.fragment3.TicketDetail;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import java.util.concurrent.Callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailBeanUtil {
    private String id;
    Future<DetailBean> result;

    public DetailBeanUtil (String id) {
        this.id = id;
        this.initDetailBeanLists();
    }

    /**
     * 初始化TicketLists
     */
    private void initDetailBeanLists() {
        sendRequest("http://47.97.10.218:9002//wx/productById?product_id="+id);
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<DetailBean>() {
            @Override
            public DetailBean call() throws Exception {
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

    /**
     * 解析json
     *
     * @param responseData
     * @return
     */
    private DetailBean parseJSON(String responseData) {
        return JSON.parseObject(responseData, DetailBean.class);
    }

    /**
     * 得到TicketList
     */
    public DetailBean getDetailBeanList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
package com.example.appofzhejiang.xihu;

import com.alibaba.fastjson.JSON;
import com.example.appofzhejiang.fragment3.TicketDetail.DetailBean;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JinquBeanUtil {
    private String id;
    Future<JinquBean> result;


    public JinquBeanUtil(String id) {
        this.id = id;
        this.initDetailBeanLists();
    }

    /**
     * 初始化TicketLists
     */
    private void initDetailBeanLists() {
        sendRequest("http://120.26.172.104:9002//wx/productById?product_id="+id);
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<JinquBean>() {
            @Override
            public JinquBean call() throws Exception {
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
    private JinquBean parseJSON(String responseData) {
        return JSON.parseObject(responseData, JinquBean.class);
    }

    /**
     * 得到TicketList
     */
    public JinquBean getDetailBeanList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
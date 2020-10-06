package com.example.appofzhejiang.xihu;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerBeanJingquUtil {
    private String currentCity;
//    private String type;
    Future<List<RecyclerBeanJingqu>> result;

    public RecyclerBeanJingquUtil() {
        this.currentCity = currentCity;
//        this.type = type;
        this.initRecyclerBeanJingquLists();
    }

    /**
     * 初始化TicketLists
     */
    private void initRecyclerBeanJingquLists(){
        sendRequest("http://47.97.10.218:9002//wx/productBySalesDown?search=&type=门票");
    }

//    private void initTicketLists() {
//        if (TicketType.TICKET.equals(this.type)) {
//            sendRequest("http://47.97.10.218:9002//wx/productBySalesDown?search=&type=门票");
//        }
//    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<RecyclerBeanJingqu>>() {
            @Override
            public List<RecyclerBeanJingqu> call() throws Exception {
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
    private List<RecyclerBeanJingqu> parseJSON(String responseData) {
        return JSON.parseArray(responseData, RecyclerBeanJingqu.class);
    }

    /**
     * 得到TicketList
     */
    public List<RecyclerBeanJingqu> getList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

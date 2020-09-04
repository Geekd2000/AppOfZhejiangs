package com.example.appofzhejiang.fragment3;

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

public class TicketUtil {
    private String currentCity;
    private String type;
    Future<List<Ticket>> result;

    public TicketUtil(String currentCity, String type) {
        this.currentCity = currentCity;
        this.type = type;
        this.initTicketLists();
    }

    /**
     * 初始化TicketLists
     */
    private void initTicketLists() {
        if (TicketType.TICKET.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productBySalesDown?search=&type=门票");
        }else if(TicketType.SCENIC.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productBySalesUp?search=&type=门票");
        }
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<Ticket>>() {
            @Override
            public List<Ticket> call() throws Exception {
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
    private List<Ticket> parseJSON(String responseData) {
        return JSON.parseArray(responseData, Ticket.class);
    }

    /**
     * 得到TicketList
     */
    public List<Ticket> getTicketList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

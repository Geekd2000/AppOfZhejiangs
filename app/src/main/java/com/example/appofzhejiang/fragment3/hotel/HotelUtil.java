package com.example.appofzhejiang.fragment3.hotel;

import com.alibaba.fastjson.JSON;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketType;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.Callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HotelUtil {
    private String currentCity;
    private String type;
    Future<List<Hotel>> result;

    public HotelUtil(String currentCity, String type) {
        this.currentCity = currentCity;
        this.type = type;
        this.initHotelLists();

    }

    /**
     * 初始化TicketLists
     */
    private void initHotelLists() {
        if (TicketType.TICKET.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productByPriceUp?search=&type=门票");
        } else if (TicketType.HOTEL.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productBySalesDown?search=&type=酒店");
        } else if (TicketType.TAXI.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productBySalesDown?search=&type=租车");
        } else if (TicketType.GUIDER.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productBySalesDown?search=&type=导游");
        } else if (TicketType.FARM.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productBySalesDown?search=&type=农家乐");
        } else if (TicketType.FOOD.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productBySalesDown?search=&type=美食");
        } else if (TicketType.PRODUCT.equals(this.type)) {
            sendRequest("http://120.26.172.104:9002//wx/productBySalesDown?search=&type=特产");
        }
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<Hotel>>() {
            @Override
            public List<Hotel> call() throws Exception {
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
    private List<Hotel> parseJSON(String responseData) {
        return JSON.parseArray(responseData, Hotel.class);
    }

    /**
     * 得到TicketList
     */
    public List<Hotel> getHotelList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

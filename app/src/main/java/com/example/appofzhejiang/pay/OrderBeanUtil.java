package com.example.appofzhejiang.pay;

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

public class OrderBeanUtil {
    private String order_id;
    Future<OrderBean> result;

    public OrderBeanUtil (String order_id) {
        this.order_id = order_id;
        this.initOrderBean();
    }

    /**
     * 初始化OrderBean
     */
    private void initOrderBean() {
        sendRequest("http://120.26.172.104:9002/wx/findOrderByOrderId?order_id="+order_id);
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<OrderBean>() {
            @Override
            public OrderBean call() throws Exception {
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
    private OrderBean parseJSON(String responseData) {
        return JSON.parseObject(responseData, OrderBean.class);
    }

    /**
     * 得到OrderBean
     */
    public OrderBean getOrderBean() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
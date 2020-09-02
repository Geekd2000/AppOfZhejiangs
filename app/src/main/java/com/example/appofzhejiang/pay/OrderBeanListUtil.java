package com.example.appofzhejiang.pay;

import com.alibaba.fastjson.JSON;
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

public class OrderBeanListUtil {

    private String user_id;
    private int index;
    Future<List<OrderBean>> result;

    //登陆时获取密码的构造函数
    public OrderBeanListUtil(int index, String user_id) {
        this.index = index;
        this.user_id = user_id;
        initOrderBeanList();
    }

    /**
     * 初始化OrderBeanList
     */
    private void initOrderBeanList() {
        if (index == 1) {
            sendGetRequest("http://120.26.172.104:9002/wx/findAllOrderById?user_id="+user_id);
        } else if (index == 2) {
            sendGetRequest("http://120.26.172.104:9002/wx/findNoPayById?user_id="+user_id);
        } else if (index == 3) {
            sendGetRequest("http://120.26.172.104:9002/wx/findPayById?user_id="+user_id);
        } else if (index == 4) {
            sendGetRequest("http://120.26.172.104:9002/wx/findFinOrderById?user_id="+user_id);
        }
    }

    /**
     * 使用GET访问网络
     *
     * @param address
     * @return 服务器返回的结果
     */
    private void sendGetRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<List<OrderBean>>() {
            @Override
            public List<OrderBean> call() throws Exception {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(address)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    return parseGetJSON(responseData);
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
    private List<OrderBean> parseGetJSON(String responseData) {
        return JSON.parseArray(responseData, OrderBean.class);
    }

    /**
     * 得到OrderBeanList
     */
    public List<OrderBean> getOrderBeanList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
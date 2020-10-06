package com.example.appofzhejiang.Business;

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

public class AddressBeanList {

    private String user_id;
    Future<List<AddressBean>> result;

    public AddressBeanList(String user_id) {
        this.user_id = user_id;
        this.initReceiptBeanList();
    }

    /**
     * 初始化AddressBeanList
     */
    private void initReceiptBeanList() {
        sendRequest("http://47.97.10.218:9002//wx/getAddress?user_id="+user_id);
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<AddressBean>>() {
            @Override
            public List<AddressBean> call() throws Exception {
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
    private List<AddressBean> parseJSON(String responseData) {
        return JSON.parseArray(responseData, AddressBean.class);
    }

    /**
     * 得到AddressBeanList
     */
    public List<AddressBean> getAddressBeanList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
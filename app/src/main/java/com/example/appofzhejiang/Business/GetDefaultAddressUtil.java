package com.example.appofzhejiang.Business;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetDefaultAddressUtil {
    private String user_id;
    Future<AddressBean> result;

    public GetDefaultAddressUtil (String user_id) {
        this.user_id = user_id;
        this.initAddressBean();
    }

    /**
     * 初始化AddressBean
     */
    private void initAddressBean() {
        sendRequest("http://120.26.172.104:9002//wx/getDefaultAddress?user_id="+user_id);
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<AddressBean>() {
            @Override
            public AddressBean call() throws Exception {
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
    private AddressBean parseJSON(String responseData) {
        return JSON.parseObject(responseData, AddressBean.class);
    }

    /**
     * 得到AddressBean
     */
    public AddressBean getAddressBean() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
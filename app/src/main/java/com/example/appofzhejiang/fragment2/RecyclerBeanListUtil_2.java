package com.example.appofzhejiang.fragment2;

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

public class RecyclerBeanListUtil_2 {
    private String currentCity;
    Future<List<RecyclerBean_2>> result;

    public RecyclerBeanListUtil_2(String currentCity) {
        this.currentCity = currentCity;
        this.initRecyclerBeanLists();
    }

    /**
     * 初始化recyclerBeanList1
     */
    private void initRecyclerBeanLists() {
            sendRequest("http://47.97.10.218:9002/web/findDestinationByType?type=旅游攻略");
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<RecyclerBean_2>>() {
            @Override
            public List<RecyclerBean_2> call() throws Exception {
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
    private List<RecyclerBean_2> parseJSON(String responseData) {
        return JSON.parseArray(responseData, RecyclerBean_2.class);
    }


    /**
     * 得到BeanList
     */
    public List<RecyclerBean_2> getRecyclerBeanList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

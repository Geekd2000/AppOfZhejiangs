package com.example.appofzhejiang.Setting;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class userUtil {
    Future<userBean> result;

    //登陆时获取密码的构造函数
    public userUtil(String name){
        sendGetRequest(name);
    }

    /**
     * 使用GET访问网络
     *
     * @param name
     * @return 服务器返回的结果
     */
    private void sendGetRequest(final String name) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<userBean>() {
            @Override
            public userBean call() throws Exception {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://120.26.172.104:9002//wx/findUser?name="+name)
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
    private userBean parseGetJSON(String responseData) {
        return JSON.parseObject(responseData, userBean.class);
    }

    /**
     * 得到LoginRegisterBean
     */
    public userBean getUserBean() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

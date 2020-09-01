package com.example.appofzhejiang.Login;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginUtil {

    Future<LoginBean> result;

    //登陆时获取密码的构造函数
    public LoginUtil(String name){
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
        result = pool.submit(new Callable<LoginBean>() {
            @Override
            public LoginBean call() throws Exception {
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
    private LoginBean parseGetJSON(String responseData) {
        return JSON.parseObject(responseData, LoginBean.class);
    }

    /**
     * 得到LoginRegisterBean
     */
    public LoginBean getLoginRegisterBean() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
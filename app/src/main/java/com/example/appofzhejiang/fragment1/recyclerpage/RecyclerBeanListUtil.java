package com.example.appofzhejiang.fragment1.recyclerpage;


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

public class RecyclerBeanListUtil {
    private String currentCity;
    private String type;
    Future<List<RecyclerBean>> result;


    public RecyclerBeanListUtil(String currentCity, String type) {
        this.currentCity = currentCity;
        this.type = type;
        this.initRecyclerBeanLists();

    }


    /**
     * 初始化recyclerBeanList1
     */
    private void initRecyclerBeanLists() {
        if (RecyclerType.STRATEGY.equals(this.type)) {
            sendRequest("http://47.97.10.218:9002/web/findDestinationByType?type=旅游攻略");
        } else if (RecyclerType.NEWS.equals(this.type)) {
            sendRequest("http://47.97.10.218:9002/web/findDestinationByType?type=专题新闻");
        } else {
            sendRequest("http://47.97.10.218:9002/web/findAllDestination");
        }
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<RecyclerBean>>() {
            @Override
            public List<RecyclerBean> call() throws Exception {
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
    private List<RecyclerBean> parseJSON(String responseData) {
        return JSON.parseArray(responseData, RecyclerBean.class);
    }


    /**
     * 得到BeanList
     */
    public List<RecyclerBean> getRecyclerBeanList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

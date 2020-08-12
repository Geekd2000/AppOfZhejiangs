package com.example.appofzhejiang.recyclerpage;

import com.example.appofzhejiang.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerBeanListUtil {
    private String currentCity;
    private String type;
    private List<RecyclerBean> recyclerBeanList;

    public RecyclerBeanListUtil(String currentCity, String type) {
        this.currentCity = currentCity;
        this.type = type;
        this.initRecyclerBeanLists();
    }

    /**
     * 初始化recyclerBeanList
     */
    private void initRecyclerBeanLists() {
        recyclerBeanList = new ArrayList<>();
        String typeContent = "旅游攻略";
        if(RecyclerType.STRATEGY.equals(this.type)) {
            typeContent = "旅游攻略";
        } else if(RecyclerType.NEWS.equals(this.type)) {
            typeContent = "专题新闻";
        }

        // 初始化
        for (int i = 0; i < 10; i++) {
            RecyclerBean recyclerBean = new RecyclerBean(R.drawable.tourism_strategy_logo, this.currentCity + "实用交通指南", typeContent,"2020-08-08", "484");
            recyclerBeanList.add(recyclerBean);
        }
    }

    /**
     * 得到BeanList
     */
    public List<RecyclerBean> getRecyclerBeanList() {
        return recyclerBeanList;
    }
}

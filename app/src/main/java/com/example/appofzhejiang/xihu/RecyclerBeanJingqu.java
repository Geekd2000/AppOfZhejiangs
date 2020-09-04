package com.example.appofzhejiang.xihu;

import java.io.Serializable;

public class RecyclerBeanJingqu implements Serializable {
    private String path;
    private String name;
    private int product_id;

    public RecyclerBeanJingqu(){}

    public RecyclerBeanJingqu(int product_id,String path, String name) {
        this.path = path;
        this.name = name;
        this.product_id = product_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}

package com.example.appofzhejiang.fragment3;

import java.io.Serializable;

public class Ticket implements Serializable {
    private String path;
    private String name;
    private String prices;
    private String sales;
    private int product_id;

    public Ticket() {
    }

    public Ticket(String path, String name, String prices, String sales,int product_id) {
        this.path = path;
        this.name = name;
        this.prices = prices;
        this.sales = sales;
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

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}

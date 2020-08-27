package com.example.appofzhejiang.fragment3.TicketDetail;

import java.io.Serializable;

public class DetailBean implements Serializable {

    private String params;
    private String name;
    private String prices;
    private String sales;
    private String pictures;
    private String introduct;
    private int inventory;

    public DetailBean() {
    }

    public DetailBean(String params, String name, String prices, String sales, String pictures, String introduct,int inventory) {
        this.params = params;
        this.name = name;
        this.prices = prices;
        this.sales = sales;
        this.pictures = pictures;
        this.introduct = introduct;
        this.inventory = inventory;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}

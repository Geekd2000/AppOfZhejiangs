package com.example.appofzhejiang.TicketDetail;

import java.io.Serializable;

public class DetailBean implements Serializable {

    private String params;
    private String name;
    private String prices;
    private String sales;
    private String pictures;
    private String introduct;

    public DetailBean() {
    }

    public DetailBean(String params, String name, String prices, String sales, String pictures, String introduct) {
        this.params = params;
        this.name = name;
        this.prices = prices;
        this.sales = sales;
        this.pictures = pictures;
        this.introduct = introduct;
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
}

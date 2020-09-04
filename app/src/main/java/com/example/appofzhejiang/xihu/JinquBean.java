package com.example.appofzhejiang.xihu;

import java.io.Serializable;

public class JinquBean implements Serializable {

    private String name;
    private String pictures;
    private String introduct;
    private String params;
    private String prices;

    public JinquBean() {
    }

    public JinquBean( String name, String pictures, String introduct,String params,String prices) {
        this.name = name;
        this.pictures = pictures;
        this.introduct = introduct;
        this.params = params;
        this.prices = prices;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

package com.example.appofzhejiang.recyclerpage;

import java.io.Serializable;


public class RecyclerBean implements Serializable {
    private String pictures;
    private String title;
    private String type;
    private String time;
    private String dread;

    public RecyclerBean() {
    }

    public RecyclerBean(String pictures, String title, String type, String time, String dread) {
        this.pictures = pictures;
        this.title = title;
        this.type = type;
        this.time = time;
        this.dread = dread;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }
    public String getPictures() {
        return pictures;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDread() {
        return dread;
    }

    public void setDread(String dread) {
        this.dread = dread;
    }

}
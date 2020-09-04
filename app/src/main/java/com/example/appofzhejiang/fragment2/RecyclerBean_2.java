package com.example.appofzhejiang.fragment2;

import java.io.Serializable;

public class RecyclerBean_2 implements Serializable {
    private String pictures;
    private String picture;
    private String title;
    private String time;
    private String dread;

    public RecyclerBean_2() {
    }

    public RecyclerBean_2(String paicture,String pictures, String title, String time, String dread) {
        this.picture = picture;
        this.pictures = pictures;
        this.title = title;
        this.time = time;
        this.dread = dread;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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


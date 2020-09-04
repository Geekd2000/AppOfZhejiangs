package com.example.appofzhejiang.fragment2;

import android.graphics.Region;

import java.io.Serializable;

public class RecycleBean2_2 implements Serializable {
    private String name;
    private String time;
    private String level;
    private int place_id;
    private String city;
    private String address;

    public RecycleBean2_2(){}

    public RecycleBean2_2(String time,String imagePath, String name, String level, int place_id, String city, String address) {
        this.name = name;
        this.time = time;
        this.level = level;
        this.place_id = place_id;
        this.city = city;
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}

package com.example.appofzhejiang.xihu;

import java.io.Serializable;

public class JinquBean2 implements Serializable {

    private String time;
    private String address;
    private String level;

    private JinquBean2(){};

    public JinquBean2(String time, String address,String level) {
        this.time = time;
        this.address = address;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

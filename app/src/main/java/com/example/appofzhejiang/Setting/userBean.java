package com.example.appofzhejiang.Setting;

import java.io.Serializable;

public class userBean implements Serializable {

    private String username;//昵称
    private String tel;//密码
    private String tell;
    private String name;//用户名
    private int user_id;

    public userBean() {
    }


    public userBean(String username, String tel, int user_id) {
        this.username = username;
        this.tel = tel;
        this.user_id = user_id;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

package com.example.appofzhejiang.Login;

import java.io.Serializable;

public class LoginBean implements Serializable {

    private String username;//用户名
    private String tel;//密码
    private int user_id;

    public LoginBean() {
    }

    public LoginBean(String username, String tel, int user_id) {
        this.username = username;
        this.tel = tel;
        this.user_id = user_id;
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
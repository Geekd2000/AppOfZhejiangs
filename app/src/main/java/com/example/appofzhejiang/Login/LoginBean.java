package com.example.appofzhejiang.Login;

import java.io.Serializable;

public class LoginBean implements Serializable {

    private String username;//用户名
    private String tel;//密码

    public LoginBean() {
    }

    public LoginBean(String username, String tel) {
        this.username = username;
        this.tel = tel;
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

}
package com.example.appofzhejiang.Login;

import java.io.Serializable;

public class RegisterBean implements Serializable {

    private String username;//用户名
    private String tel;//密码
    private String tell;//确认密码

    public RegisterBean() {
    }

    public RegisterBean(String username, String tel, String tell) {
        this.username = username;
        this.tel = tel;
        this.tell = tell;
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

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

}
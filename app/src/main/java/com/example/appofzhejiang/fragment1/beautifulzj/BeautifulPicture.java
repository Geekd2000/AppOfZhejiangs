package com.example.appofzhejiang.fragment1.beautifulzj;

public class BeautifulPicture {
    private String picture;
    private String introduction;

    public BeautifulPicture() {
    }

    public BeautifulPicture(String picture, String introduction) {
        this.picture = picture;
        this.introduction = introduction;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}

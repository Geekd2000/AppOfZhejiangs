package com.example.appofzhejiang.fragment1.beautifulzj;

public class BeautifulPicture {
    private String url;
    private String introduction;

    public BeautifulPicture() {
    }

    public BeautifulPicture(String url, String introduction) {
        this.url = url;
        this.introduction = introduction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}

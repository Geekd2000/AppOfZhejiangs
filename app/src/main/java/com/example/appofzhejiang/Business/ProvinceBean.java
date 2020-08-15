package com.example.appofzhejiang.Business;

public class ProvinceBean {
    private String name;


    public ProvinceBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*这个用来显示在PickerView上面的字符串,PickerView会通过反射获
    取getPickerViewText方法显示出来。*/
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return this.name;
    }
}

package com.example.u.okhttptest.Bean;

/**
 * Created by u on 2017/1/27.
 */

public class WeixinBean {
    private String title;
    private String picUrl;
    private String url;
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "WeixinBean{" +
                "title='" + title + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

package com.example.u.okhttptest.Bean;

import java.util.List;

/**
 * Created by u on 2017/1/27.
 */

public class ZhihuBean {

    public String images;
    public int type;
    public int id;
    public String ga_prefix;
    public String title;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    @Override
    public String toString() {
        return "ZhihuBean{" +
                "images='" + images + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

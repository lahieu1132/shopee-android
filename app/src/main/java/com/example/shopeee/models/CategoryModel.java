package com.example.shopeee.models;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String img_url;
    private String name;
    private String type;

    public CategoryModel(String img_url, String name, String type) {
        this.img_url = img_url;
        this.name = name;
        this.type = type;
    }

    public CategoryModel() {
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

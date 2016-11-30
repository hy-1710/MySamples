package com.example.serpentcs.recycdemo.model;

/**
 * Created by serpentcs on 17/11/16.
 */

public class Model {

    private String name;
    private String image;

    public Model() {
    }

    public Model(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

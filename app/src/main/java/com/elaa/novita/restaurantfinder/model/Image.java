package com.elaa.novita.restaurantfinder.model;

import java.io.Serializable;

/**
 * Created by elaa on 23/04/18.
 */

public class Image implements Serializable {
    String name;
    String image;

    public Image() {
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

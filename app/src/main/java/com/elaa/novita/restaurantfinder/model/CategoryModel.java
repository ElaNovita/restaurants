package com.elaa.novita.restaurantfinder.model;

import java.io.Serializable;

/**
 * Created by elaa on 22/04/18.
 */

public class CategoryModel implements Serializable {
    int id;
    String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

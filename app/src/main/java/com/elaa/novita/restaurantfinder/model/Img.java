package com.elaa.novita.restaurantfinder.model;

import java.io.Serializable;

/**
 * Created by elaa on 29/04/18.
 */

public class Img implements Serializable{
    private String small, thumbnail, full;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }
}

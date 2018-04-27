package com.elaa.novita.restaurantfinder.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by elaa on 26/04/18.
 */

public class Rating {
    @SerializedName("restaurant_rate")
    private float averageRate;
    private float rate;
    private String status;

    public float getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(float averageRate) {
        this.averageRate = averageRate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

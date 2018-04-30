package com.elaa.novita.restaurantfinder.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elaa on 21/04/18.
 */

public class RestaurantModel implements Serializable{
    private int id;
    private List<CategoryModel> category;
    private List<Menu> menus;
    private List<Menu> photos;
    private String title, description, address, city, phone;
    private Float rate;
    private int rated;
    private Img img;

    @SerializedName("is_rated")
    boolean isRated;

    @SerializedName("open_time")
    private String openTime;

    @SerializedName("close_time")
    private String closeTime;

    @SerializedName("location_lat")
    private String locationLat;

    @SerializedName("location_lng")
    private String locationLng;

    @SerializedName("is_bookmarked")
    boolean isBookmarked;

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CategoryModel> getCategories() {
        return category;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.category = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }

    public List<CategoryModel> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryModel> category) {
        this.category = category;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }


    public List<Menu> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Menu> photos) {
        this.photos = photos;
    }

    public int getRated() {
        return rated;
    }

    public void setRated(int rated) {
        this.rated = rated;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setToRated(boolean rated) {
        isRated = rated;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}

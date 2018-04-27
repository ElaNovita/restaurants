package com.elaa.novita.restaurantfinder.helper;

import com.elaa.novita.restaurantfinder.model.Login;
import com.elaa.novita.restaurantfinder.model.Rating;
import com.elaa.novita.restaurantfinder.model.RestaurantModel;
import com.elaa.novita.restaurantfinder.model.Review;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by elaa on 22/04/18.
 */

public interface MyInterface {

    @GET("restaurants/")
    Call<List<RestaurantModel>> getRestaurants();

    @FormUrlEncoded
    @POST("auth/")
    Call<Login> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/register/")
    Call<Login> register(@Field("email") String email,
                         @Field("username") String username,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("restaurants/{id}/rate/")
    Call<Rating> rating(@Path("id") int id, @Field("rate") int rate);

    @Multipart
    @POST("restaurants/{id}/reviews/")
    Call<Review> postReview(@Path("id") int id,
                            @Part("text") RequestBody text,
                            @Part MultipartBody.Part img);

}

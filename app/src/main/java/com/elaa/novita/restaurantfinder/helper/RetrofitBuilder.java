package com.elaa.novita.restaurantfinder.helper;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elaa on 03/04/18.
 */

public class RetrofitBuilder {
    public static final String BaseUrl = "http://d1313a7d.ngrok.io/api/";

    private Context _context;

    public RetrofitBuilder(Context context) {
        this._context = context;
    }

    private OkHttpClient getClient() {
        final String token = new MySharedPreferences(this._context).getToken();

//        final String token = "216f18a7c9e81b5e6bfdd3ec17911d59a281f417";
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization",
                        "Token " + token).build();
                return chain.proceed(request);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);

        return builder.build();
    }

    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
    }
}

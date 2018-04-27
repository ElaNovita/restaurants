package com.elaa.novita.restaurantfinder.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by elaa on 03/04/18.
 */

public class MySharedPreferences extends Application {
    private final String myPref = "myPrefs";
    private SharedPreferences sp;
    private Context _context;
    public SharedPreferences.Editor editor;

    public MySharedPreferences(Context context) {
        this._context = context;
        this.sp = _context.getSharedPreferences(myPref, MODE_PRIVATE);
        this.editor = sp.edit();
    }


    public int getUserId() {
        return sp.getInt("id", 0);
    }

    public void setid(int id) {
        editor.putInt("id",  id);
        editor.commit();
    }

    public void setToken(String token) {
        editor.putString("t", token);
        editor.commit();
    }

    public String getToken() {
        return sp.getString("t", null);
    }

    public void setUsername(String username) {
        editor.putString("username", username);
        editor.commit();
    }

    public String getUsername() {
        return sp.getString("username", "Username");
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail() {
        return sp.getString("email", "email");
    }

    public boolean getLogin() {
        return sp.getBoolean("login", false);
    }

    public void setLogin(boolean login) {
        editor.putBoolean("login",  login);
        editor.commit();
    }

    public void deleteToken() {
        editor.clear();
        editor.commit();
    }

    public void setFirebaseToken(String token) {
        editor.putString("f", token);
        editor.commit();
    }

    public String getFirebaseToken() {
        return sp.getString("f", null);
    }
}

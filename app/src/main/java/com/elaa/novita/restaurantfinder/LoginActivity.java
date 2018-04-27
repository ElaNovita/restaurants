package com.elaa.novita.restaurantfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elaa.novita.restaurantfinder.helper.MyInterface;
import com.elaa.novita.restaurantfinder.helper.MySharedPreferences;
import com.elaa.novita.restaurantfinder.helper.RetrofitBuilder;
import com.elaa.novita.restaurantfinder.model.Login;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final String TAG = "login";
    LoginButton loginButton;
    EditText username, password;
    Button login;
    TextView toRegister;
    Login model;
    MySharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        sharedPreferences = new MySharedPreferences(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        toRegister = findViewById(R.id.to_register);
        model = new Login();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: " + loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _username = username.getText().toString();
                String _password = password.getText().toString();

                setLogin(_username, _password);
            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setLogin(String username, String password) {
        MyInterface service = new RetrofitBuilder(this).getRetrofit().create(MyInterface.class);
        Call<Login> call = service.login(username, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                    finish();
                    sharedPreferences.setToken(login.getToken());
                    sharedPreferences.setUsername(login.getUsername());
                    sharedPreferences.setEmail(login.getEmail());
                    sharedPreferences.setid(login.getId());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
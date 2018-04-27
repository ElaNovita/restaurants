package com.elaa.novita.restaurantfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elaa.novita.restaurantfinder.helper.MyInterface;
import com.elaa.novita.restaurantfinder.helper.MySharedPreferences;
import com.elaa.novita.restaurantfinder.helper.RetrofitBuilder;
import com.elaa.novita.restaurantfinder.model.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText name, username, password, email;
    Button register;
    MySharedPreferences sharedPreferences;
    final static String TAG = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        sharedPreferences = new MySharedPreferences(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _username = username.getText().toString();
                String _name = name.getText().toString();
                String _email = email.getText().toString();
                String _password = password.getText().toString();

                setRegister(_username, _password, _name, _email);
            }
        });

    }

    private void setRegister(String u, String p, String n, String e) {
        MyInterface service = new RetrofitBuilder(this).getRetrofit().create(MyInterface.class);
        Call<Login> call = service.register(e, u, p);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Login login = response.body();
                    Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_LONG).show();
                    finish();
                    sharedPreferences.setToken(login.getToken());
                    sharedPreferences.setUsername(login.getUsername());
                    sharedPreferences.setEmail(login.getEmail());
                    sharedPreferences.setid(login.getId());
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}

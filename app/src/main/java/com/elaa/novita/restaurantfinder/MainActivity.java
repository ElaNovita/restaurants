package com.elaa.novita.restaurantfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.elaa.novita.restaurantfinder.adapter.HomeRestaurantAdapter;
import com.elaa.novita.restaurantfinder.helper.MyInterface;
import com.elaa.novita.restaurantfinder.helper.MySharedPreferences;
import com.elaa.novita.restaurantfinder.helper.OnItemClickListener;
import com.elaa.novita.restaurantfinder.helper.RetrofitBuilder;
import com.elaa.novita.restaurantfinder.model.RestaurantModel;
import com.facebook.login.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView nearYouRv;
    HomeRestaurantAdapter adapter;
    final String TAG = "mainactivity";
    MySharedPreferences sf ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        sf = new MySharedPreferences(this);

        nearYouRv = findViewById(R.id.nearYouRv);
        adapter = new HomeRestaurantAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        nearYouRv.setLayoutManager(layoutManager);
        nearYouRv.setAdapter(adapter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        reqJson();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.login) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            sf.deleteToken();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void reqJson() {
        MyInterface service = new RetrofitBuilder(this).getRetrofit().create(MyInterface.class);
        Call<List<RestaurantModel>> call = service.getRestaurants();
        call.enqueue(new Callback<List<RestaurantModel>>() {
            @Override
            public void onResponse(Call<List<RestaurantModel>> call, Response<List<RestaurantModel>> response) {
                final List<RestaurantModel> models = response.body();
                adapter.setData(models);
                final RestaurantModel model = new RestaurantModel();

                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position, boolean isLongClick) {
                        RestaurantModel i = models.get(position);
                        model.setId(i.getId());
                        model.setTitle(i.getTitle());
                        model.setAddress(i.getAddress());
                        model.setCity(i.getCity());
                        model.setImg(i.getImg());
                        model.setRate(i.getRate());
                        model.setMenus(i.getMenus());
                        model.setCategories(i.getCategories());
                        model.setOpenTime(i.getOpenTime());
                        model.setCloseTime(i.getCloseTime());
                        model.setPhotos(i.getPhotos());
                        model.setPhone(i.getPhone());
                        model.setLocationLat(i.getLocationLat());
                        model.setLocationLng(i.getLocationLng());
                        model.setRated(i.getRated());
                        model.setToRated(i.isRated());
                        Log.d(TAG, "onItemClick: " + i.isRated());

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("restaurant", model);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<RestaurantModel>> call, Throwable t) {

            }
        });
    }
}

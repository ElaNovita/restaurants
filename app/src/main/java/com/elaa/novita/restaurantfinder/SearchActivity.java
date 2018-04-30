package com.elaa.novita.restaurantfinder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elaa.novita.restaurantfinder.adapter.SearchAdapter;
import com.elaa.novita.restaurantfinder.helper.MyInterface;
import com.elaa.novita.restaurantfinder.helper.OnItemClickListener;
import com.elaa.novita.restaurantfinder.helper.RetrofitBuilder;
import com.elaa.novita.restaurantfinder.model.RestaurantModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchAdapter adapter;
    TextView empty;
    List<RestaurantModel> models;
    RestaurantModel model;
    ProgressBar progressBar;
    MaterialSearchView searchView;
    private static String TAG = "searchactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.searchRv);
        empty = findViewById(R.id.empty);
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchView);
        adapter = new SearchAdapter(this);
        model = new RestaurantModel();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setIconified(false);
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void search(String query) {
        progressBar.setVisibility(View.GONE);
        MyInterface service = new RetrofitBuilder(this).getRetrofit().create(MyInterface.class);
        Call<List<RestaurantModel>> call = service.search(query);
        call.enqueue(new Callback<List<RestaurantModel>>() {
            @Override
            public void onResponse(Call<List<RestaurantModel>> call, Response<List<RestaurantModel>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                models = response.body();
                adapter.setData(models);

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

                        Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
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
}

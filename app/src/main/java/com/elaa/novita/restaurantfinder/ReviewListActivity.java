package com.elaa.novita.restaurantfinder;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.elaa.novita.restaurantfinder.adapter.ReviewAdapter;
import com.elaa.novita.restaurantfinder.helper.MyInterface;
import com.elaa.novita.restaurantfinder.helper.RetrofitBuilder;
import com.elaa.novita.restaurantfinder.model.Image;
import com.elaa.novita.restaurantfinder.model.RestaurantModel;
import com.elaa.novita.restaurantfinder.model.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewListActivity extends AppCompatActivity {
    FloatingActionButton addReview;
    RestaurantModel model;
    RecyclerView recyclerView;
    ReviewAdapter adapter;
    List<Review> models;
    private String TAG = "reviewlist";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);

        model = (RestaurantModel) getIntent().getSerializableExtra("restaurant");
        addReview = findViewById(R.id.add_review);
        recyclerView = findViewById(R.id.reviewRv);
        adapter = new ReviewAdapter(this);

        getSupportActionBar().setTitle(model.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getReviews(model.getId());

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewListActivity.this, AddReviewActivity.class);
                intent.putExtra("restaurant", model);
                startActivity(intent);
            }
        });


    }

    private void getReviews(int id) {
        MyInterface service = new RetrofitBuilder(this).getRetrofit().create(MyInterface.class);
        Call<List<Review>> call = service.getReviews(id);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                Log.d(TAG, "onResponse: " + response.code());
                adapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

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

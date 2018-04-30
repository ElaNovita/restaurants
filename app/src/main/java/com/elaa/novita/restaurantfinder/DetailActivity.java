package com.elaa.novita.restaurantfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elaa.novita.restaurantfinder.adapter.GalleryAdapter;
import com.elaa.novita.restaurantfinder.adapter.MenuAdapter;
import com.elaa.novita.restaurantfinder.helper.MyInterface;
import com.elaa.novita.restaurantfinder.helper.MySharedPreferences;
import com.elaa.novita.restaurantfinder.helper.OnItemClickListener;
import com.elaa.novita.restaurantfinder.helper.RetrofitBuilder;
import com.elaa.novita.restaurantfinder.model.CategoryModel;
import com.elaa.novita.restaurantfinder.model.Image;
import com.elaa.novita.restaurantfinder.model.Menu;
import com.elaa.novita.restaurantfinder.model.Rating;
import com.elaa.novita.restaurantfinder.model.RestaurantModel;
import com.elaa.novita.restaurantfinder.model.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    final String TAG = "detail";
    ImageView backdrop;
    Toolbar toolbar;
    int id, menuState;
    android.view.Menu bookmarkMenu;
    RestaurantModel model;
    TextView titles, city, rating, openHour;
    TextView category, phone, openMap, address;
    List<Menu> menus;
    List<Menu> gallerys;
    ArrayList<Image> menuImages = new ArrayList<>();
    ArrayList<Image> galleryImages = new ArrayList<>();
    List<CategoryModel> categories;
    RecyclerView recyclerViewMenu, recyclerviewGallery;
    MenuAdapter menuAdapter;
    GalleryAdapter galleryAdapter;
    LinearLayout photoWrapper;
    RelativeLayout cardViewMenu;
    TextView rate, review;
    MySharedPreferences sf;
    List<String> categoryList = new ArrayList<>();
    boolean isRated, isBookmarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        backdrop = findViewById(R.id.backdrop);
        toolbar = findViewById(R.id.toolbar);
        sf = new MySharedPreferences(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titles = findViewById(R.id.restaurant_title);
        city = findViewById(R.id.city);
        rating = findViewById(R.id.rating);
        cardViewMenu = findViewById(R.id.menuCv);
        recyclerViewMenu = findViewById(R.id.menuRv);
        openHour = findViewById(R.id.open_hour);
        category = findViewById(R.id.category);
        recyclerviewGallery = findViewById(R.id.galleryRv);
        photoWrapper = findViewById(R.id.img_wrapper);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        openMap = findViewById(R.id.open_map);
        rate = findViewById(R.id.rate);
        review = findViewById(R.id.review);

        model = (RestaurantModel) getIntent().getSerializableExtra("restaurant");
        Glide.with(this).load(model.getImg().getFull()).into(backdrop);

        recyclerViewMenu.setLayoutManager(new GridLayoutManager(this, 5));
        menuAdapter = new MenuAdapter(this);
        recyclerViewMenu.setAdapter(menuAdapter);

        recyclerviewGallery.setLayoutManager(new GridLayoutManager(this, 4));
        galleryAdapter = new GalleryAdapter(this);
        recyclerviewGallery.setAdapter(galleryAdapter);

        categories = model.getCategory();
        for (int i = 0; i < categories.size(); i++) {
            categoryList.add(categories.get(i).getTitle());
        }

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Log.d(TAG, "onCreate: " + sf.getUsername());

        String join = TextUtils.join(", ", categoryList);

        titles.setText(model.getTitle());
        city.setText(model.getCity());
        rating.setText(Float.toString(model.getRate()));
        openHour.setText(model.getOpenTime() + " - " + model.getCloseTime());
        phone.setText(model.getPhone());
        address.setText(model.getAddress());
        category.setText(join);
        menus = model.getMenus();
        gallerys = model.getPhotos();
        menuAdapter.setData(menus);
        galleryAdapter.setData(gallerys);
        id = model.getId();
        isRated = model.isRated();

        Log.d(TAG, "onCreate: " + model.getRate());
        Log.d(TAG, "onCreate: " + isRated);

        if (gallerys.size() > 4) {
            photoWrapper.setVisibility(View.VISIBLE);
        }

        if (menus.size() == 0) {
            cardViewMenu.setVisibility(View.GONE);
        }

        if (isRated) {
            rate.setText(getResources().getString(R.string.reset_rating));
        }

        initMenu();
        initPhotos();
        setImages();

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double parsedLat, parsedLng;
                try {
                    parsedLat = Double.parseDouble(model.getLocationLat());
                    parsedLng = Double.parseDouble(model.getLocationLng());
                } catch (NumberFormatException e) {
                    parsedLat = 0.00;
                    parsedLng = 0.00;
                }
                openMaps(parsedLat, parsedLng);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReview();
            }
        });

    }

    private void menuClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("images", menuImages);
        bundle.putInt("position", position);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SlideshowDialogFragment dialogFragment = SlideshowDialogFragment.newInstance();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, "slideshow");
    }

    private void galleryClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("images", galleryImages);
        bundle.putInt("position", position);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SlideshowDialogFragment dialogFragment = SlideshowDialogFragment.newInstance();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, "slideshow");
    }

    private void setImages() {
        for (int i = 0; i < gallerys.size() ; i++) {
            Image image = new Image();
            image.setImage(gallerys.get(i).getImg());
            galleryImages.add(image);
        }

        for (int i = 0; i < menus.size() ; i++) {
            Image image = new Image();
            image.setImage(menus.get(i).getImg());
            menuImages.add(image);
        }
    }

    private void initMenu() {
        menuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, boolean isLongClick) {
                menuClick(position);
            }
        });
    }

    private void initPhotos() {
        galleryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, boolean isLongClick) {
                galleryClick(position);
            }
        });
    }

    private void openMaps(double lat, double lng) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private void showDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LinearLayout linearLayout = new LinearLayout(this);
        final RatingBar rating = new RatingBar(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);

        //add ratingBar to linearLayout
        linearLayout.addView(rating);

        popDialog.setTitle("Add Rating: ");

        //add linearLayout to dailog
        popDialog.setView(linearLayout);



        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rated val:"+v);
            }
        });



        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: " + String.valueOf(rating.getProgress()));
                        setRate(rating.getProgress());
                        dialog.dismiss();
                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();
    }

    private void setRate(int rate) {
        MyInterface service = new RetrofitBuilder(this).getRetrofit().create(MyInterface.class);
        Call<Rating> call = service.rating(id, rate);
        call.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Toast.makeText(DetailActivity.this, "Rating Success", Toast.LENGTH_LONG).show();
                    rating.setText(Float.toString(response.body().getAverageRate()));
                }
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {

            }
        });
    }

    private void addReview() {
        Intent intent = new Intent(DetailActivity.this, ReviewListActivity.class);
        intent.putExtra("restaurant", model);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.bookmark:
                setBookmark();
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookmarks, menu);
        this.bookmarkMenu = menu;
        cekStatus();

        return true;
    }

    private void reqBookmark() {
        MyInterface service = new RetrofitBuilder(this).getRetrofit().create(MyInterface.class);
        Call<Status> call = service.bookmark(id);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Log.d(TAG, "onResponse: " + response.code());

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });
    }

    private void cekStatus() {
        Log.d(TAG, "cekStatus: " + isBookmarked);
        if (isBookmarked) {
            bookmarkMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_32dp));
            menuState = 0;
        } else {
            bookmarkMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_border_32dp));
            menuState = 1;
        }
    }

    private void setBookmark() {
        reqBookmark();
        if (menuState == 0) {
            bookmarkMenu.findItem(R.id.bookmark).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_border_32dp));
            menuState = 1;
        } else {
            bookmarkMenu.findItem(R.id.bookmark).setIcon(getResources().getDrawable(R.drawable.ic_bookmark_32dp));
            menuState = 0;
        }
    }

}

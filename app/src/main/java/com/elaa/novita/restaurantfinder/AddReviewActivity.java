package com.elaa.novita.restaurantfinder;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elaa.novita.restaurantfinder.helper.MyInterface;
import com.elaa.novita.restaurantfinder.helper.RealPathUtil;
import com.elaa.novita.restaurantfinder.helper.RetrofitBuilder;
import com.elaa.novita.restaurantfinder.model.Review;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReviewActivity extends AppCompatActivity {
    TextView restaurantTitle, city;
    EditText review;
    ImageView addPhoto, preview;
    Button submit;
    int id = 1;
    ProgressDialog progressDialog;
    RequestBody reviewText;
    MultipartBody.Part requestFileBody;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "addreview";
    boolean islogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        restaurantTitle = findViewById(R.id.restaurant_title);
        city = findViewById(R.id.city);
        review = findViewById(R.id.review_txt);
        addPhoto = findViewById(R.id.add_photo);
        submit = findViewById(R.id.submit);
        preview = findViewById(R.id.photo_result);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text = review.getText().toString();
                if (_text.matches("")) {
                    Toast.makeText(AddReviewActivity.this, "Review Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    reviewText = RequestBody.create(MediaType.parse("multipart/form-data"), _text);
                    addReview();
                }
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhoto();
            }
        });

    }

    private void addReview() {
        MyInterface service = new RetrofitBuilder(this).getRetrofit().create(MyInterface.class);
        Call<Review> call = service.postReview(id, reviewText, requestFileBody);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Log.d(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });
    }

    private void getPhoto() {
        if (islogin) {

            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(AddReviewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddReviewActivity.this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        ActivityCompat.requestPermissions(AddReviewActivity.this,
                                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                PICK_IMAGE_REQUEST);

                    }
                } else {
                    ActivityCompat.requestPermissions(AddReviewActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
                    Intent intent = new Intent();
                    intent.setType("image/* video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);
                }
            } else {
                Intent intent = new Intent();
                intent.setType("image/* video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);
            }
        } else {
            Intent intent = new Intent(AddReviewActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            String realPath = RealPathUtil.getPath(this, data.getData());
            File file = new File(realPath);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            requestFileBody = MultipartBody.Part.createFormData("img", file.getName(), requestFile);

            Log.d(TAG, "onActivityResult: " + file.getName());
        }
    }
}

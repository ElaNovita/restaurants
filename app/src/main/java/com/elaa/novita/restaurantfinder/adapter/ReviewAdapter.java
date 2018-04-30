package com.elaa.novita.restaurantfinder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elaa.novita.restaurantfinder.FullSreenActivity;
import com.elaa.novita.restaurantfinder.R;
import com.elaa.novita.restaurantfinder.ReviewListActivity;
import com.elaa.novita.restaurantfinder.SlideshowDialogFragment;
import com.elaa.novita.restaurantfinder.helper.CustomDateFormatter;
import com.elaa.novita.restaurantfinder.model.Image;
import com.elaa.novita.restaurantfinder.model.Review;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elaa on 29/04/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context context;
    List<Review> models;
    CustomDateFormatter customDateFormatter;
    ArrayList<Image> menuImages = new ArrayList<>();

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Review> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review review = models.get(position);
        holder.username.setText(review.getUser().getUsername());
        holder.review.setText(review.getText());
        final int  i =position;
        if (review.getImg().isEmpty()) {
            holder.userImage.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(review.getImg()).into(holder.reviewImage);
        }

        try {
            customDateFormatter = new CustomDateFormatter();
            holder.posted.setText(customDateFormatter.getTimeAgo(review.getCreated()));
        } catch (ParseException e) {
            //
        }

        holder.reviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullSreenActivity.class);
                intent.putExtra("image", review.getImg());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (models == null) return 0;
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView reviewImage, userImage;
        TextView username, posted, review;

        public ViewHolder(View itemView) {
            super(itemView);

            reviewImage = itemView.findViewById(R.id.review_image);
            userImage = itemView.findViewById(R.id.user_image);
            username = itemView.findViewById(R.id.username);
            posted = itemView.findViewById(R.id.time);
            review = itemView.findViewById(R.id.review);
        }
    }

}

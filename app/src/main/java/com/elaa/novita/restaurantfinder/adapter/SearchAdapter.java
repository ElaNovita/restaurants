package com.elaa.novita.restaurantfinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elaa.novita.restaurantfinder.R;
import com.elaa.novita.restaurantfinder.helper.OnItemClickListener;
import com.elaa.novita.restaurantfinder.model.CategoryModel;
import com.elaa.novita.restaurantfinder.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elaa on 29/04/18.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    List<RestaurantModel> models;
    public static OnItemClickListener listener;

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setData(List<RestaurantModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RestaurantModel model = models.get(position);
        holder.titles.setText(model.getTitle());
        holder.city.setText(model.getCity());
        holder.rating.setText(Float.toString(model.getRate()));
        Glide.with(context).load(model.getImg().getThumbnail()).into(holder.restaurantImage);

        List<String> categoryList = new ArrayList<>();
        List<CategoryModel> categories;
        categories = model.getCategory();
        for (int i = 0; i < categories.size(); i++) {
            categoryList.add(categories.get(i).getTitle());
        }

        String joined = TextUtils.join(", ", categoryList);
        holder.category.setText(joined);
    }

    @Override
    public int getItemCount() {
        if (models == null) return 0;
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView restaurantImage;
        TextView titles, rating, city, category;

        public ViewHolder(View itemView) {
            super(itemView);

            restaurantImage = itemView.findViewById(R.id.restaurant_image);
            titles = itemView.findViewById(R.id.restaurant_title);
            rating = itemView.findViewById(R.id.rating);
            city = itemView.findViewById(R.id.city);
            category = itemView.findViewById(R.id.category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, getLayoutPosition(), false);
                }
            });

        }
    }

}

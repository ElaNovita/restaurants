package com.elaa.novita.restaurantfinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elaa.novita.restaurantfinder.R;
import com.elaa.novita.restaurantfinder.helper.OnItemClickListener;
import com.elaa.novita.restaurantfinder.model.RestaurantModel;

import java.util.List;

/**
 * Created by elaa on 21/04/18.
 */

public class HomeRestaurantAdapter extends RecyclerView.Adapter<HomeRestaurantAdapter.ViewHolder> {
    Context context;
    List<RestaurantModel> models;
    public static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public HomeRestaurantAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RestaurantModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titles.setText(models.get(position).getTitle());
        holder.address.setText(models.get(position).getCity());
        Glide.with(context).load(models.get(position).getImg()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (models == null) return 0;
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView titles, address;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.restaurant_image);
            titles = itemView.findViewById(R.id.restaurant_title);
            address = itemView.findViewById(R.id.restaurant_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, getLayoutPosition(), false);
                }
            });

        }
    }
}


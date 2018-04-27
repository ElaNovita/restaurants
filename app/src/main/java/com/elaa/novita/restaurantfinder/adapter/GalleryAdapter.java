package com.elaa.novita.restaurantfinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.elaa.novita.restaurantfinder.R;
import com.elaa.novita.restaurantfinder.helper.OnItemClickListener;
import com.elaa.novita.restaurantfinder.model.Menu;

import java.util.List;

/**
 * Created by elaa on 24/04/18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    Context context;
    List<Menu> models;
    public static OnItemClickListener listener;

    public void setData(List<Menu> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(models.get(position).getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (models == null){
            return 0;
        } else if (models.size() > 4) {
            return 4;
        } else {
            return models.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.gallery_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, getLayoutPosition(), false);
                }
            });
        }
    }
}


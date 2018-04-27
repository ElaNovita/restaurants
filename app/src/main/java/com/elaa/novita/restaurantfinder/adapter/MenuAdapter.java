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
 * Created by elaa on 23/04/18.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    Context context;
    List<Menu> menus;
    public static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MenuAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Menu> menus) {
        this.menus = menus;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(menus.get(position).getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (menus == null){
            return 0;
        } else if (menus.size() > 5) {
            return 5;
        } else {
            return menus.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.menu_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, getLayoutPosition(), false);
                }
            });
        }
    }


}


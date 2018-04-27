package com.elaa.novita.restaurantfinder;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.elaa.novita.restaurantfinder.model.Image;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlideshowDialogFragment extends DialogFragment {
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    int selectedPosition = 0;
    TextView count;
    ArrayList<Image> images;
    final String TAG = "slideshow";

    public SlideshowDialogFragment() {
        // Required empty public constructor
    }

    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment fragment = new SlideshowDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_slider, container, false);

        viewPager = view.findViewById(R.id.viewpager);
        count = view.findViewById(R.id.lbl_count);

        images = (ArrayList<Image>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerChangeListener);

        setCurrentItem(selectedPosition);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    private void setCurrentItem(int i) {
        viewPager.setCurrentItem(i, false);
        displayMetaInfo(selectedPosition);
    }

    private void displayMetaInfo(int position) {
        count.setText(position+1+ " of " + images.size());
    }

    ViewPager.OnPageChangeListener viewPagerChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class ViewPagerAdapter extends PagerAdapter {
        LayoutInflater inflater;

        public ViewPagerAdapter() {
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_fullscreen, container, false);

            ImageView imageView = view.findViewById(R.id.image_preview);
            Image image = images.get(position);

            Glide.with(getActivity()).load(image.getImage())
                    .thumbnail(0.5f)
                    .into(imageView);

            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}

package com.sendpost.dreamsoft.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.sendpost.dreamsoft.databinding.ItemSliderLayoutBinding;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.model.SliderModel;

import java.util.List;


public class SlidingAdapter extends PagerAdapter {


    private List<SliderModel> imageList;
    private LayoutInflater inflater;
    private Context context;

    AdapterClickListener adapterClickListener;

    public SlidingAdapter(Context context, List<SliderModel> IMAGES, AdapterClickListener click_listener) {
        this.context = context;
        this.imageList = IMAGES;
        this.adapterClickListener = click_listener;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        ItemSliderLayoutBinding binding = ItemSliderLayoutBinding.inflate(LayoutInflater.from(context), view, false);
        binding.setSlider(imageList.get(position));
        binding.getRoot().setOnClickListener(v -> {
            adapterClickListener.onItemClick(v, position, imageList.get(position));
        });
        view.addView(binding.getRoot(), 0);
        return binding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}

package com.sendpost.dreamsoft.ImageEditor.Frame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sendpost.dreamsoft.model.FrameCategoryModel;
import com.sendpost.dreamsoft.model.FrameModel;

import java.util.ArrayList;
import java.util.List;

public class FramePagerAdapter extends FragmentPagerAdapter {

    List<FrameCategoryModel> list = new ArrayList<>();
    FrameListener frameListener;

    public FramePagerAdapter(@NonNull FragmentManager fm, List<FrameCategoryModel> list, FrameListener listener) {
        super(fm);
        this.list = list;
        this.frameListener = listener;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        if (position == 0){
//            return new PersonalFragment(new ArrayList<FrameModel>(), new FrameListener() {
//                @Override
//                public void onFrameSelected(@Nullable FrameModel name) {
//                    frameListener.onFrameSelected(name);
//                }
//            });
//        }else{
            return new FrameFragment(list.get(position).getFrames(), new FrameListener() {
                @Override
                public void onFrameSelected(@Nullable FrameModel name) {
                    frameListener.onFrameSelected(name);
                }
            });
//        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}

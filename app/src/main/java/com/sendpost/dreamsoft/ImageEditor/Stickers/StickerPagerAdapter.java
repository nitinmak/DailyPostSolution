package com.sendpost.dreamsoft.ImageEditor.Stickers;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StickerPagerAdapter extends FragmentPagerAdapter {

    List<StickerModelCategory> list = new ArrayList<>();
    StickerBSFragment.StickerListener stickerListener;

    public StickerPagerAdapter(@NonNull FragmentManager fm, List<StickerModelCategory> list, StickerBSFragment.StickerListener listener) {
        super(fm);
        this.list = list;
        this.stickerListener = listener;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new StickerFragment(list.get(position).getStickers(), new StickerBSFragment.StickerListener() {
            @Override
            public void onStickerClick(@Nullable Bitmap bitmap) {
                stickerListener.onStickerClick(bitmap);
            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).name;
    }
}

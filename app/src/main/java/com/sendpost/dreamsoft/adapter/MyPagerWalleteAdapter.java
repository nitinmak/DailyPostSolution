package com.sendpost.dreamsoft.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sendpost.dreamsoft.NavFragment.PinFragment;
import com.sendpost.dreamsoft.NavFragment.PointFragment;

public class MyPagerWalleteAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;
    public MyPagerWalleteAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                PointFragment page2 = new PointFragment();
                return page2;

            case 1:
                PinFragment page1 = new PinFragment();
                return page1;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}

package com.sendpost.dreamsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rd.PageIndicatorView;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.ImageEditor.filters.Fragments.IntroFragment;

public class IntroActivity extends AppCompatActivity {

    ViewPager viewPager;
    PageIndicatorView pageIndicatorView;
    TextView next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager = findViewById(R.id.viewPager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        next_btn = findViewById(R.id.next_btn);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        pageIndicatorView.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2){
                    next_btn.setText("Start");
                }else {
                    next_btn.setText("Next");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == 2){
                    Functions.getSharedPreference(IntroActivity.this).edit().putBoolean(Variables.IS_FIRST_TIME,false).apply();
                    startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                    finish();
                }else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });
        findViewById(R.id.skip_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.getSharedPreference(IntroActivity.this).edit().putBoolean(Variables.IS_FIRST_TIME,false).apply();
                startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
    static class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            final Fragment result;
            switch (position) {
                case 0:
                    result = new IntroFragment("1");
                    break;
                case 1:
                    result = new IntroFragment("2");
                    break;
                case 2:
                    result = new IntroFragment("3");
                    break;
                default:
                    return null;
            }
            return result;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
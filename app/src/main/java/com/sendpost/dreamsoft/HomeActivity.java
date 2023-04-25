package com.sendpost.dreamsoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.NavFragment.DailyFragment;
import com.sendpost.dreamsoft.NavFragment.DailyNewFragment;
import com.sendpost.dreamsoft.NavFragment.ImageFragment;
import com.sendpost.dreamsoft.NavFragment.CreateFragment;
import com.sendpost.dreamsoft.NavFragment.HomeFragment;
import com.sendpost.dreamsoft.NavFragment.VideoFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ImageView home_btn,image_btns,images_daily_routing,search_btn,create_btn,profile_btn;
    //  premium_btn,

    public static Fragment active;
    public static FragmentManager fragmentManager;
    boolean homeClicked = true, imageClicked = false,dailyClicked = false,videoClicked = false,createClicked = false,premiumClicked = false,profileClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Functions.setLocale(Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), this, HomeActivity.class,false);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();

        context = this;
        setupTabIcons();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    Fragment homeFragment = new HomeFragment();
    Fragment dailyFragment = new DailyFragment();
    Fragment imageFragment = new ImageFragment();
    Fragment dailynewfragment = new DailyNewFragment();
    Fragment createFragment = new CreateFragment();
    Fragment searchFragment = new VideoFragment();
//  Fragment premiumFragment = new PremiumFragment();
//  Fragment profileFragment = new ProfileFragment();

    private void setupTabIcons() {
        home_btn = findViewById(R.id.home_btn);
        image_btns = findViewById(R.id.images_btn);
        images_daily_routing = findViewById(R.id.images_daily_routing);
        search_btn = findViewById(R.id.search_btn);
        create_btn = findViewById(R.id.create_btn);
//      premium_btn = findViewById(R.id.premium_btn);
//      profile_btn = findViewById(R.id.profile_btn);

        home_btn.setOnClickListener(this);
        image_btns.setOnClickListener(this);
        images_daily_routing.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        create_btn.setOnClickListener(this);
//      premium_btn.setOnClickListener(this);
//      profile_btn.setOnClickListener(this);

        active = homeFragment;
        fragmentManager.beginTransaction().add(R.id.main_framelayout, homeFragment).commit();
    }


    boolean isHome = true;
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }else{
            if (isHome){
                super.onBackPressed();
            }else {
                home_btn.performClick();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_btn:
                isHome = true;
                home_btn.setImageDrawable(getDrawable(R.drawable.home_activity));
                images_daily_routing.setImageDrawable(getDrawable(R.drawable.daily_page_icon));
                image_btns.setImageDrawable(getDrawable(R.drawable._business_page_icon));
                create_btn.setImageDrawable(getDrawable(R.drawable.frame_page_icon));
                search_btn.setImageDrawable(getDrawable(R.drawable.video_page_icon));
//              premium_btn.setImageDrawable(getDrawable(R.drawable.nav_premium));
//              profile_btn.setImageDrawable(getDrawable(R.drawable.nav_profile));
            
                home_btn.getBackground().setTintList(null);
                home_btn.getDrawable().setTint(getColor(R.color.white));
                images_daily_routing.getDrawable().setTint(getColor(R.color.black));
                image_btns.getDrawable().setTint(getColor(R.color.black));
                create_btn.getDrawable().setTint(getColor(R.color.black));
                search_btn.getDrawable().setTint(getColor(R.color.black));
                image_btns.getBackground().setTint(getResources().getColor(R.color.transparent));
                images_daily_routing.getBackground().setTint(getResources().getColor(R.color.transparent));
                search_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
                create_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//              premium_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//              profile_btn.getBackground().setTint(getResources().getColor(R.color.transparent));

                if (active != homeFragment) {
                    if (!homeClicked) {
                        fragmentManager.beginTransaction().add(R.id.main_framelayout, homeFragment).show(homeFragment).hide(active).commit();
                        homeClicked = true;
                    } else {
                        fragmentManager.beginTransaction().show(homeFragment).hide(active).commit();
                    }
                }
                showFragmentOnFrame(homeFragment);

                break;
            case R.id.images_btn:
                isHome = false;
                home_btn.setImageDrawable(getDrawable(R.drawable.home_activity));
                images_daily_routing.setImageDrawable(getDrawable(R.drawable.daily_page_icon));
                image_btns.setImageDrawable(getDrawable(R.drawable._business_page_icon));
                create_btn.setImageDrawable(getDrawable(R.drawable.frame_page_icon));
                search_btn.setImageDrawable(getDrawable(R.drawable.video_page_icon));
//              premium_btn.setImageDrawable(getDrawable(R.drawable.nav_premium));
//              profile_btn.setImageDrawable(getDrawable(R.drawable.nav_profile));

                home_btn.getDrawable().setTint(getColor(R.color.black));
                images_daily_routing.getDrawable().setTint(getColor(R.color.black));
                image_btns.getDrawable().setTint(getColor(R.color.white));
                create_btn.getDrawable().setTint(getColor(R.color.black));
                search_btn.getDrawable().setTint(getColor(R.color.black));


                home_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
                images_daily_routing.getBackground().setTint(getResources().getColor(R.color.transparent));
                image_btns.getBackground().setTintList(null);

                search_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
                create_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//              premium_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//              profile_btn.getBackground().setTint(getResources().getColor(R.color.transparent));

                if (active != imageFragment) {
                    if (!imageClicked) {
                        fragmentManager.beginTransaction().add(R.id.main_framelayout, imageFragment).show(imageFragment).hide(active).commit();
                        imageClicked = true;
                    } else {
                        fragmentManager.beginTransaction().show(imageFragment).hide(active).commit();
                    }
                }
                showFragmentOnFrame(imageFragment);

                break;
            case R.id.images_daily_routing:
                isHome = false;
                home_btn.setImageDrawable(getDrawable(R.drawable.home_activity));
                images_daily_routing.setImageDrawable(getDrawable(R.drawable.daily_page_icon));
                image_btns.setImageDrawable(getDrawable(R.drawable._business_page_icon));
                create_btn.setImageDrawable(getDrawable(R.drawable.frame_page_icon));
                search_btn.setImageDrawable(getDrawable(R.drawable.video_page_icon));
//              premium_btn.setImageDrawable(getDrawable(R.drawable.nav_premium));
//              profile_btn.setImageDrawable(getDrawable(R.drawable.nav_profile));

                home_btn.getDrawable().setTint(getColor(R.color.black));
                image_btns.getDrawable().setTint(getColor(R.color.black));
                images_daily_routing.getDrawable().setTint(getColor(R.color.white));
                create_btn.getDrawable().setTint(getColor(R.color.black));
                search_btn.getDrawable().setTint(getColor(R.color.black));


                home_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
                image_btns.getBackground().setTint(getResources().getColor(R.color.transparent));
                images_daily_routing.getBackground().setTintList(null);

                search_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
                create_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//              premium_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//              profile_btn.getBackground().setTint(getResources().getColor(R.color.transparent));

                if (active != dailyFragment) {
                    if (!dailyClicked) {
                        fragmentManager.beginTransaction().add(R.id.main_framelayout, dailyFragment).show(dailyFragment).hide(active).commit();
                        dailyClicked = true;
                    } else {
                        fragmentManager.beginTransaction().show(dailyFragment).hide(active).commit();
                    }
                }
                showFragmentOnFrame(dailyFragment);
                break;
            case R.id.search_btn:
                isHome = false;
                home_btn.setImageDrawable(getDrawable(R.drawable.home_activity));
                images_daily_routing.setImageDrawable(getDrawable(R.drawable.daily_page_icon ));
                image_btns.setImageDrawable(getDrawable(R.drawable._business_page_icon));
                create_btn.setImageDrawable(getDrawable(R.drawable.frame_page_icon));
                search_btn.setImageDrawable(getDrawable(R.drawable.video_page_icon));
//              premium_btn.setImageDrawable(getDrawable(R.drawable.nav_premium));
//              profile_btn.setImageDrawable(getDrawable(R.drawable.nav_profile));


                home_btn.getDrawable().setTint(getColor(R.color.black));
                images_daily_routing.getDrawable().setTint(getColor(R.color.black));
                image_btns.getDrawable().setTint(getColor(R.color.black));
                search_btn.getDrawable().setTint(getColor(R.color.white));
                create_btn.getDrawable().setTint(getColor(R.color.black));



                home_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
                image_btns.getBackground().setTint(getResources().getColor(R.color.transparent));
                images_daily_routing.getBackground().setTint(getResources().getColor(R.color.transparent));
                search_btn.getBackground().setTintList(null);

                create_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                premium_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                profile_btn.getBackground().setTint(getResources().getColor(R.color.transparent));

                if (active != searchFragment) {
                    if (!videoClicked) {
                        fragmentManager.beginTransaction().add(R.id.main_framelayout, searchFragment).show(searchFragment).hide(active).commit();
                        videoClicked = true;
                    } else {
                        fragmentManager.beginTransaction().show(searchFragment).hide(active).commit();
                    }
                }
                showFragmentOnFrame(searchFragment);
                break;
            case R.id.create_btn:
                isHome = false;
                home_btn.setImageDrawable(getDrawable(R.drawable.home_activity));
                images_daily_routing.setImageDrawable(getDrawable(R.drawable.daily_page_icon));
                image_btns.setImageDrawable(getDrawable(R.drawable._business_page_icon));
                create_btn.setImageDrawable(getDrawable(R.drawable.frame_page_icon));
                search_btn.setImageDrawable(getDrawable(R.drawable.video_page_icon));
//                premium_btn.setImageDrawable(getDrawable(R.drawable.nav_premium));
//                profile_btn.setImageDrawable(getDrawable(R.drawable.nav_profile));

                home_btn.getDrawable().setTint(getColor(R.color.black));
                images_daily_routing.getDrawable().setTint(getColor(R.color.black));
                image_btns.getDrawable().setTint(getColor(R.color.black));
                search_btn.getDrawable().setTint(getColor(R.color.black));
                create_btn.getDrawable().setTint(getColor(R.color.white));

                home_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
                image_btns.getBackground().setTint(getResources().getColor(R.color.transparent));
                images_daily_routing.getBackground().setTint(getResources().getColor(R.color.transparent));
                search_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
                create_btn.getBackground().setTintList(null);
                create_btn.getDrawable().setTint(getColor(R.color.white));
//                premium_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                profile_btn.getBackground().setTint(getResources().getColor(R.color.transparent));

                if (active != createFragment) {
                    if (!createClicked) {
                        fragmentManager.beginTransaction().add(R.id.main_framelayout, createFragment).show(createFragment).hide(active).commit();
                        createClicked = true;
                    } else {
                        fragmentManager.beginTransaction().show(createFragment).hide(active).commit();
                    }
                }
                showFragmentOnFrame(createFragment);
                break;
//            case R.id.premium_btn:
//                isHome = false;
//                home_btn.setImageDrawable(getDrawable(R.drawable.nav_home));
//                image_btns.setImageDrawable(getDrawable(R.drawable.nav_home));
//                search_btn.setImageDrawable(getDrawable(R.drawable.nav_discover));
//                create_btn.setImageDrawable(getDrawable(R.drawable.nav_create));
//                premium_btn.setImageDrawable(getDrawable(R.drawable.nav_premium_active));
//                profile_btn.setImageDrawable(getDrawable(R.drawable.nav_profile));
//
//                home_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                image_btns.getBackground().setTint(getResources().getColor(R.color.transparent));
//                search_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                create_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                premium_btn.getBackground().setTintList(null);
//                profile_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//
//                if (active != premiumFragment) {
//                    if (!premiumClicked) {
//                        fragmentManager.beginTransaction().add(R.id.main_framelayout, premiumFragment).show(premiumFragment).hide(active).commit();
//                        premiumClicked = true;
//                    } else {
//                        fragmentManager.beginTransaction().show(premiumFragment).hide(active).commit();
//                    }
//                }
//                showFragmentOnFrame(premiumFragment);
//                break;
//            case R.id.profile_btn:
//                isHome = false;
//                home_btn.setImageDrawable(getDrawable(R.drawable.nav_home));
//                image_btns.setImageDrawable(getDrawable(R.drawable.ic_magic));
//                search_btn.setImageDrawable(getDrawable(R.drawable.nav_discover));
//                create_btn.setImageDrawable(getDrawable(R.drawable.nav_create));
////                premium_btn.setImageDrawable(getDrawable(R.drawable.nav_premium));
////                profile_btn.setImageDrawable(getDrawable(R.drawable.nav_profile_active));
//
//                home_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                image_btns.getBackground().setTint(getResources().getColor(R.color.transparent));
//                search_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                create_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
////                premium_btn.getBackground().setTint(getResources().getColor(R.color.transparent));
//                profile_btn.getBackground().setTintList(null);
//
//                if (active != profileFragment) {
//                    if (!profileClicked) {
//                        fragmentManager.beginTransaction().add(R.id.main_framelayout, profileFragment).show(profileFragment).hide(active).commit();
//                        profileClicked = true;
//                    } else {
//                        fragmentManager.beginTransaction().show(profileFragment).hide(active).commit();
//                    }
//                }
//                showFragmentOnFrame(profileFragment);
//                break;
        }
    }

    private void showFragmentOnFrame(Fragment fragment) {
        fragmentManager.beginTransaction().hide(active).show(fragment).commit();
        active = fragment;
    }
}
package com.sendpost.dreamsoft.NavFragment;

import static com.sendpost.dreamsoft.Classes.Constants.SUCCESS;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
;
import com.google.android.material.tabs.TabLayout;

import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.adapter.MyPagerWalleteAdapter;
import com.sendpost.dreamsoft.databinding.WalleteviewpagerBinding;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;

public class WalletePager extends AppCompatActivity {
    WalleteviewpagerBinding binding;
    UserViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WalleteviewpagerBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("POINT"));
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("PIN"));

        getpoint(Functions.getUID(getApplicationContext()));

    }

    private void getpoint(String uid) {

            viewModel.getpinpointhistory(Functions.getUID(this)).observe(this, userResponse -> {

                if (userResponse != null){
                    if (userResponse.code == SUCCESS){
                        binding.tabLayout.removeAllTabs();
                        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("POINT ("+ userResponse.available_points+")"));
                        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("PIN ("+ userResponse.available_pins+")"));
                        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                        final MyPagerWalleteAdapter adapter = new MyPagerWalleteAdapter(this,getSupportFragmentManager(), binding.tabLayout.getTabCount());
                        binding.viewPager.setAdapter(adapter);
                        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
                        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                binding.viewPager.setCurrentItem(tab.getPosition());
                            }
                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {
                            }
                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {
                            }
                        });
                    }else {
                        Functions.showToast(getApplicationContext(),userResponse.message);
                    }
                }
            });

    }
}


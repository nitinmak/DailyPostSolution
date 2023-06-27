package com.sendpost.dreamsoft.NavFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sendpost.dreamsoft.BusinessCard.BusinessCardActivity;
import com.sendpost.dreamsoft.Fragments.SearchFragment;
import com.sendpost.dreamsoft.adapter.SectionAdapter;
import com.sendpost.dreamsoft.BusinessCard.BusinessCardActivity_Digital;
import com.sendpost.dreamsoft.ImageEditor.EditImageActivity;
import com.sendpost.dreamsoft.databinding.FragmentCreateBinding;
import com.sendpost.dreamsoft.model.SectionModel;
import com.sendpost.dreamsoft.PosterPreviewActivity;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;

import java.util.List;

public class CreateFragment extends Fragment {


    public CreateFragment() {}

    Context context;
    FragmentCreateBinding binding;
    HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
                transaction.addToBackStack(null);
                transaction.replace(android.R.id.content, new SearchFragment()).commit();
            }
        });
        binding.customPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, EditImageActivity.class).putExtra("type", "CustomPhoto"));
            }
        });

        binding.customVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, EditImageActivity.class).putExtra("type", "CustomVideo"));
            }
        });

        binding.photoToVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, EditImageActivity.class).putExtra("type", "PhotoVideo"));
            }
        });

        binding.bussinessMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BusinessCardActivity.class));
            }
        });

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getGreetingData().observe(getViewLifecycleOwner(), new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {
                if (homeResponse != null) {
                    if (homeResponse.greeting_section.size() > 0) {
                        Log.d("ddvsvsdv",homeResponse.greeting_section.toString());
                        setSectionAdapter(homeResponse.greeting_section);
                    }
                }
            }
        });

    }

    private void setSectionAdapter(List<SectionModel> greeting_section) {
        binding.shimmerLay.setVisibility(View.GONE);
        binding.shimmerLay.stopShimmer();
        binding.rvSection.setAdapter(new SectionAdapter(context, greeting_section, (view, list, main_position, child_position) -> {
            PosterPreviewActivity.postsModel = list.get(child_position);
            startActivity(new Intent(context, PosterPreviewActivity.class).putExtra("type", "GreetingPosts"));
        }, false,true));
    }

}





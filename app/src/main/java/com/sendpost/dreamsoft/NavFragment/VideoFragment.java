package com.sendpost.dreamsoft.NavFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sendpost.dreamsoft.Fragments.SearchFragment;
import com.sendpost.dreamsoft.adapter.FestivalCategoryAdapter;
import com.sendpost.dreamsoft.adapter.UpcomingEventAdapter;
import com.sendpost.dreamsoft.databinding.FragmentVideoBinding;
import com.sendpost.dreamsoft.dialog.LanguageDialogFragment;
import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;

import java.util.List;

public class VideoFragment extends Fragment {

    public VideoFragment() {}
    Context context;
    View view;
    HomeViewModel homeViewModel;
    FragmentVideoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVideoBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        this.view  = view;

//        binding.languageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new LanguageDialogFragment().show(getFragmentManager(),"");
//            }
//        });

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
                transaction.addToBackStack(null);
                transaction.replace(android.R.id.content, new SearchFragment()).commit();
            }
        });

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getVideoCategory().observe(getViewLifecycleOwner(), new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {
                if (homeResponse != null){
//                    if (homeResponse.upcoming_event.size() > 0){
//                        setUpcomingCategoryAdapter(homeResponse.upcoming_event);
//                    }
//                    if (homeResponse.business_category.size() > 0){
//                        setCategoryAdapter(homeResponse.business_category);
//                    }
                    if (homeResponse.custom_category.size() > 0){
                        setCustomeCategoryAdapter(homeResponse.custom_category);
                    }
                }
            }
        });
    }

//    private void setUpcomingCategoryAdapter(List<CategoryModel> upcoming_event) {
//        binding.upcomingFestivalShimmerLay.stopShimmer();
//        binding.upcomingFestivalShimmerLay.setVisibility(View.GONE);
//        binding.rvUpcomingFestival.setAdapter(new UpcomingEventAdapter(context, upcoming_event,true));
//    }
//
//    private void setCategoryAdapter(List<CategoryModel> business_category) {
//        binding.categoryShimmerLay.stopShimmer();
//        binding.categoryShimmerLay.setVisibility(View.GONE);
//        binding.categoryRecycler.setAdapter(new FestivalCategoryAdapter(context,business_category,true));
//    }

    private void setCustomeCategoryAdapter(List<CategoryModel> custom_category) {
        binding.generalCategoryShimmerLay.stopShimmer();
        binding.generalCategoryShimmerLay.setVisibility(View.GONE);
        binding.rvCustomCategory.setAdapter(new FestivalCategoryAdapter(context,custom_category,true));
    }
}
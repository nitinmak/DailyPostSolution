package com.sendpost.dreamsoft.NavFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;

import com.sendpost.dreamsoft.adapter.BussinessCategoryAdapter;
import com.sendpost.dreamsoft.databinding.ImageFragmentBinding;
import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;

public class ImageFragment extends Fragment {

    public ImageFragment() {}
    Context context;
    View view;
    HomeViewModel homeViewModel;
    ImageFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ImageFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        this.view  = view;

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getImages().observe(getViewLifecycleOwner(), new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {
                if (homeResponse != null){
                    if (homeResponse.business_category.size() > 0){
                        setBussinessCategoryAdapter(homeResponse.business_category);
                    }
                }
            }
        });
    }

    private void setBussinessCategoryAdapter(List<CategoryModel> business_category) {
        binding.businessCategoryShimmerLay.stopShimmer();
        binding.businessCategoryShimmerLay.setVisibility(View.GONE);
        binding.rvBussinessCategory.setAdapter(new BussinessCategoryAdapter(context, business_category));

    }
}

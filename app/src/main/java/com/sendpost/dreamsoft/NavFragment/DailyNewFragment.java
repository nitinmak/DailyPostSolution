package com.sendpost.dreamsoft.NavFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sendpost.dreamsoft.adapter.UpcomingImagesAdapter;
import com.sendpost.dreamsoft.databinding.DailyFragmentBinding;
import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;

import java.util.List;

public class DailyNewFragment extends Fragment {


    public DailyNewFragment() {}
    Context context;
    View view;
    HomeViewModel homeViewModel;
    DailyFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DailyFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        this.view  = view;

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getDailyData().observe(getViewLifecycleOwner(), homeResponse -> {

            if (homeResponse != null){

                if (homeResponse.dailyroutin1.size() > 0){
                    setamazingadapter(homeResponse.dailyroutin1,homeResponse.getDailyc1());
                }

                if (homeResponse.dailyroutin2.size() > 0){
                    setledthotsadapter(homeResponse.dailyroutin2,homeResponse.getDailyc2());
                }

                if (homeResponse.dailyroutin3.size() > 0){
                    setdaily3sadapter(homeResponse.dailyroutin3,homeResponse.dailyc3);
                }

                if (homeResponse.msgtosoc.size() > 0){
                    setmsgtsocadapter(homeResponse.msgtosoc,homeResponse.dailycmsgsoc);
                }

                if (homeResponse.goddeva.size() > 0){
                    setgoddevaadapter(homeResponse.goddeva,homeResponse.dailycgoddeva);
                }

//                    if (homeResponse.goddevi.size() > 0) {
//                        setgoddeviadapter(homeResponse.goddevi,homeResponse.dailycgoddevi);
//                    }
//
//                    if (homeResponse.numerology.size() > 0){
//                        setnumrologyadapter(homeResponse.numerology,homeResponse.dailycnumro);
//                    }
//                    if (homeResponse.rashi.size() > 0){
//                        setrashiadapter(homeResponse.rashi,homeResponse.dailycrashi);
//                    }

            }
        });
    }

    private void setamazingadapter(List<CategoryModel> amazing, String dailyc1) {
        binding.dailyShimmerLay.stopShimmer();
        binding.dailyShimmerLay.setVisibility(View.GONE);
        binding.daily1.setText(" ("+dailyc1+")");
        binding.rvdailyCategory.setAdapter(new UpcomingImagesAdapter(context, amazing,false));
    }

    private void setledthotsadapter(List<CategoryModel> ledth, String dailyc2) {
        binding.motiShimmerLay.stopShimmer();
        binding.motiShimmerLay.setVisibility(View.GONE);
        binding.daily2.setText(" ("+dailyc2+")");
        binding.rvledmotivationalCategory.setAdapter(new UpcomingImagesAdapter(context, ledth,false));
    }

    private void setdaily3sadapter(List<CategoryModel> day3, String dailyc3) {
        binding.daily3ShimmerLay.stopShimmer();
        binding.daily3ShimmerLay.setVisibility(View.GONE);
        binding.daily3.setText(" ("+dailyc3+")");
        binding.rvdaylyr3Category.setAdapter(new UpcomingImagesAdapter(context, day3,false));
    }

    private void setmsgtsocadapter(List<CategoryModel> msgtsoc, String dailycmsgsoc) {
        binding.msgtosocShimmerLay.stopShimmer();
        binding.msgtosocShimmerLay.setVisibility(View.GONE);
        binding.dailycmsgsoc.setText(" ("+dailycmsgsoc+")");
        binding.rvmsgtosocCategory.setAdapter(new UpcomingImagesAdapter(context, msgtsoc,false));
    }

    private void setnumrologyadapter(List<CategoryModel> numro, String dailycnumro) {
//        binding.numShimmerLay.stopShimmer();
//        binding.numShimmerLay.setVisibility(View.GONE);
//        binding.dailycnumrolo.setText(" ("+dailycnumro+")");
//        binding.rvnumrologyCategory.setAdapter(new UpcomingImagesAdapter(context, numro,false));
    }

    private void setrashiadapter(List<CategoryModel> rashi, String dailycrashi) {
//        binding.rashiShimmerLay.stopShimmer();
//        binding.rashiShimmerLay.setVisibility(View.GONE);
//        binding.dailycrashi.setText(" ("+dailycrashi+")");
//        binding.rvrashiCategory.setAdapter(new UpcomingImagesAdapter(context, rashi,false));
    }

    private void setgoddevaadapter(List<CategoryModel> goddeva, String dailycgoddeva) {
        binding.goddevaShimmerLay.stopShimmer();
        binding.goddevaShimmerLay.setVisibility(View.GONE);
        binding.dailycgoddeva.setText(" ("+dailycgoddeva+")");
        binding.rvgoddevaCategory.setAdapter(new UpcomingImagesAdapter(context, goddeva,false));
    }

    private void setgoddeviadapter(List<CategoryModel> devi, String dailycgoddevi) {
//        binding.goddeviShimmerLay.stopShimmer();
//        binding.goddeviShimmerLay.setVisibility(View.GONE);
//        binding.dailycgoddevi.setText(" ("+dailycgoddevi+")");
//        binding.rvgoddeviCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }
}

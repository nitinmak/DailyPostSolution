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

        homeViewModel.getDailynewData().observe(getViewLifecycleOwner(), homeResponse -> {

            if (homeResponse != null){

                if (homeResponse.dailnewyroutin1.size() > 0){
                    setamazingadapter(homeResponse.dailnewyroutin1,homeResponse.getDailynew1());
                }

                if (homeResponse.dailnewyroutin2.size() > 0){
                    setledthotsadapter(homeResponse.dailnewyroutin2,homeResponse.getDailynew2());
                }

                if (homeResponse.dailnewyroutin3.size() > 0){
                    setdaily3sadapter(homeResponse.dailnewyroutin3,homeResponse.dailynew3);
                }

                if (homeResponse.dailnewyroutin4.size() > 0){
                    setmsgtsocadapter(homeResponse.dailnewyroutin4,homeResponse.dailynew4);
                }

                if (homeResponse.dailnewyroutin5.size() > 0){
                    setgoddevaadapter(homeResponse.dailnewyroutin5,homeResponse.dailynew5);
                }

                if (homeResponse.dailnewyroutin6.size() > 0) {
                    setgoddeviadapter(homeResponse.dailnewyroutin6,homeResponse.dailynew6);
                }

                if (homeResponse.dailnewyroutin7.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin7,homeResponse.dailynew7);
                }
                if (homeResponse.dailnewyroutin8.size() > 0){
                    setrashiadapter(homeResponse.dailnewyroutin8,homeResponse.dailynew8);
                }
                if (homeResponse.dailnewyroutin9.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin9,homeResponse.dailynew9);
                }
                if (homeResponse.dailnewyroutin10.size() > 0){
                    setrashiadapter(homeResponse.dailnewyroutin10,homeResponse.dailynew10);
                }
                if (homeResponse.dailnewyroutin11.size() > 0){
                    setamazingadapter(homeResponse.dailnewyroutin11,homeResponse.dailynew11);
                }

                if (homeResponse.dailnewyroutin12.size() > 0){
                    setledthotsadapter(homeResponse.dailnewyroutin12,homeResponse.dailynew12);
                }

                if (homeResponse.dailnewyroutin13.size() > 0){
                    setdaily3sadapter(homeResponse.dailnewyroutin13,homeResponse.dailynew13);
                }

                if (homeResponse.dailnewyroutin14.size() > 0){
                    setmsgtsocadapter(homeResponse.dailnewyroutin14,homeResponse.dailynew14);
                }

                if (homeResponse.dailnewyroutin15.size() > 0){
                    setgoddevaadapter(homeResponse.dailnewyroutin15,homeResponse.dailynew15);
                }

                if (homeResponse.dailnewyroutin16.size() > 0) {
                    setgoddeviadapter(homeResponse.dailnewyroutin16,homeResponse.dailynew16);
                }

                if (homeResponse.dailnewyroutin17.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin17,homeResponse.dailynew17);
                }
                if (homeResponse.dailnewyroutin18.size() > 0){
                    setrashiadapter(homeResponse.dailnewyroutin18,homeResponse.dailynew18);
                }
                if (homeResponse.dailnewyroutin19.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin19,homeResponse.dailynew19);
                }
                if (homeResponse.dailnewyroutin20.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin20,homeResponse.dailynew20);
                }
                if (homeResponse.dailnewyroutin21.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin21,homeResponse.dailynew21);
                }

                if (homeResponse.dailnewyroutin22.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin22,homeResponse.dailynew22);
                }

                if (homeResponse.dailnewyroutin23.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin23,homeResponse.dailynew23);
                }

                if (homeResponse.dailnewyroutin24.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin24,homeResponse.dailynew24);
                }

                if (homeResponse.dailnewyroutin25.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin25,homeResponse.dailynew25);
                }

                if (homeResponse.dailnewyroutin26.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin26,homeResponse.dailynew26);
                }

                if (homeResponse.dailnewyroutin27.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin27,homeResponse.dailynew27);
                }

                if (homeResponse.dailnewyroutin28.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin28,homeResponse.dailynew28);
                }

                if (homeResponse.dailnewyroutin29.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin29,homeResponse.dailynew29);
                }

                if (homeResponse.dailnewyroutin30.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin30,homeResponse.dailynew30);
                }

                if (homeResponse.dailnewyroutin31.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin31,homeResponse.dailynew31);
                }

                if (homeResponse.dailnewyroutin32.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin32,homeResponse.dailynew32);
                }

                if (homeResponse.dailnewyroutin33.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin33,homeResponse.dailynew33);
                }

                if (homeResponse.dailnewyroutin34.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin34,homeResponse.dailynew34);
                }

                if (homeResponse.dailnewyroutin35.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin35,homeResponse.dailynew35);
                }

                if (homeResponse.dailnewyroutin36.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin36,homeResponse.dailynew36);
                }

                if (homeResponse.dailnewyroutin37.size() > 0){
                    setnumrologyadapter(homeResponse.dailnewyroutin37,homeResponse.dailynew37);
                }
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

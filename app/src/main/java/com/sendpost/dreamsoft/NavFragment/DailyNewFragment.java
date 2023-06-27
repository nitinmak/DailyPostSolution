package com.sendpost.dreamsoft.NavFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.sendpost.dreamsoft.Fragments.SearchFragment;
import com.sendpost.dreamsoft.adapter.UpcomingImagesAdapter;
import com.sendpost.dreamsoft.databinding.DailynewfragmentBinding;
import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;

import java.util.List;

public class DailyNewFragment extends Fragment {

    public DailyNewFragment() {}
    Context context;
    View view;
    HomeViewModel homeViewModel;
    DailynewfragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DailynewfragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        this.view  = view;

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

        homeViewModel.getDailynewData().observe(getViewLifecycleOwner(), homeResponse -> {

            if (homeResponse != null){

                if (homeResponse.dailnewyroutin1.size() > 0){
                    setdaylyn1(homeResponse.dailnewyroutin1,homeResponse.getDailynew1());
                }

                if (homeResponse.dailnewyroutin2.size() > 0){
                    setdaylyn2(homeResponse.dailnewyroutin2,homeResponse.getDailynew2());
                }

                if (homeResponse.dailnewyroutin3.size() > 0){
                    setdaylyn3(homeResponse.dailnewyroutin3,homeResponse.dailynew3);
                }

                if (homeResponse.dailnewyroutin4.size() > 0){
                    setdaylyn4(homeResponse.dailnewyroutin4,homeResponse.dailynew4);
                }

                if (homeResponse.dailnewyroutin5.size() > 0){
                    setdaylyn5(homeResponse.dailnewyroutin5,homeResponse.dailynew5);
                }

                if (homeResponse.dailnewyroutin6.size() > 0) {
                    setdaylyn6(homeResponse.dailnewyroutin6,homeResponse.dailynew6);
                }

                if (homeResponse.dailnewyroutin7.size() > 0){
                    setdaylyn7(homeResponse.dailnewyroutin7,homeResponse.dailynew7);
                }
                if (homeResponse.dailnewyroutin8.size() > 0){
                    setdaylyn8(homeResponse.dailnewyroutin8,homeResponse.dailynew8);
                }
                if (homeResponse.dailnewyroutin9.size() > 0){
                    setdaylyn9(homeResponse.dailnewyroutin9,homeResponse.dailynew9);
                }
                if (homeResponse.dailnewyroutin10.size() > 0){
                    setdaylyn10(homeResponse.dailnewyroutin10,homeResponse.dailynew10);
                }
                if (homeResponse.dailnewyroutin11.size() > 0){
                    setdaylyn11(homeResponse.dailnewyroutin11,homeResponse.dailynew11);
                }

                if (homeResponse.dailnewyroutin12.size() > 0){
                    setdaylyn12(homeResponse.dailnewyroutin12,homeResponse.dailynew12);
                }

                if (homeResponse.dailnewyroutin13.size() > 0){
                    setdaylyn13(homeResponse.dailnewyroutin13,homeResponse.dailynew13);
                }

                if (homeResponse.dailnewyroutin14.size() > 0){
                    setdaylyn14(homeResponse.dailnewyroutin14,homeResponse.dailynew14);
                }

                if (homeResponse.dailnewyroutin15.size() > 0){
                    setdaylyn15(homeResponse.dailnewyroutin15,homeResponse.dailynew15);
                }

                if (homeResponse.dailnewyroutin16.size() > 0) {
                    setdaylyn16(homeResponse.dailnewyroutin16,homeResponse.dailynew16);
                }

                if (homeResponse.dailnewyroutin17.size() > 0){
                    setdaylyn17(homeResponse.dailnewyroutin17,homeResponse.dailynew17);
                }
                if (homeResponse.dailnewyroutin18.size() > 0){
                    setdaylyn18(homeResponse.dailnewyroutin18,homeResponse.dailynew18);
                }
                if (homeResponse.dailnewyroutin19.size() > 0){
                    setdaylyn19(homeResponse.dailnewyroutin19,homeResponse.dailynew19);
                }
                if (homeResponse.dailnewyroutin20.size() > 0){
                    setdaylyn20(homeResponse.dailnewyroutin20,homeResponse.dailynew20);
                }
                if (homeResponse.dailnewyroutin21.size() > 0){
                    setdaylyn21(homeResponse.dailnewyroutin21,homeResponse.dailynew21);
                }

                if (homeResponse.dailnewyroutin22.size() > 0){
                    setdaylyn22(homeResponse.dailnewyroutin22,homeResponse.dailynew22);
                }

                if (homeResponse.dailnewyroutin23.size() > 0){
                    setdaylyn23(homeResponse.dailnewyroutin23,homeResponse.dailynew23);
                }

                if (homeResponse.dailnewyroutin24.size() > 0){
                    setdaylyn24(homeResponse.dailnewyroutin24,homeResponse.dailynew24);
                }

                if (homeResponse.dailnewyroutin25.size() > 0){
                    setdaylyn25(homeResponse.dailnewyroutin25,homeResponse.dailynew25);
                }

                if (homeResponse.dailnewyroutin26.size() > 0){
                    setdaylyn26(homeResponse.dailnewyroutin26,homeResponse.dailynew26);
                }

                if (homeResponse.dailnewyroutin27.size() > 0){
                    setdaylyn27(homeResponse.dailnewyroutin27,homeResponse.dailynew27);
                }

                if (homeResponse.dailnewyroutin28.size() > 0){
                    setdaylyn28(homeResponse.dailnewyroutin28,homeResponse.dailynew28);
                }

                if (homeResponse.dailnewyroutin29.size() > 0){
                    setdaylyn29(homeResponse.dailnewyroutin29,homeResponse.dailynew29);
                }

                if (homeResponse.dailnewyroutin30.size() > 0){
                    setdaylyn30(homeResponse.dailnewyroutin30,homeResponse.dailynew30);
                }

                if (homeResponse.dailnewyroutin31.size() > 0){
                    setdaylyn31(homeResponse.dailnewyroutin31,homeResponse.dailynew31);
                }

                if (homeResponse.dailnewyroutin32.size() > 0){
                    setdaylyn32(homeResponse.dailnewyroutin32,homeResponse.dailynew32);
                }

                if (homeResponse.dailnewyroutin33.size() > 0){
                    setdaylyn33(homeResponse.dailnewyroutin33,homeResponse.dailynew33);
                }

                if (homeResponse.dailnewyroutin34.size() > 0){
                    setdaylyn34(homeResponse.dailnewyroutin34,homeResponse.dailynew34);
                }

                if (homeResponse.dailnewyroutin35.size() > 0){
                    setdaylyn35(homeResponse.dailnewyroutin35,homeResponse.dailynew35);
                }

                if (homeResponse.dailnewyroutin36.size() > 0){
                    setdaylyn36(homeResponse.dailnewyroutin36,homeResponse.dailynew36);
                }

                if (homeResponse.dailnewyroutin37.size() > 0){
                    setdaylyn37(homeResponse.dailnewyroutin37,homeResponse.dailynew37);
                }
            }
        });
    }

    private void setdaylyn1(List<CategoryModel> devi, String dailycgoddevi) {
        binding.agriShimmerLay.stopShimmer();
        binding.agriShimmerLay.setVisibility(View.GONE);
        binding.daynew1.setText(" ("+dailycgoddevi+")");
        binding.agriculturenew.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn2(List<CategoryModel> devi, String dailycgoddevi) {
        binding.animalShimmerLay.stopShimmer();
        binding.animalShimmerLay.setVisibility(View.GONE);
        binding.daynew2.setText(" ("+dailycgoddevi+")");
        binding.rvanimalfoodCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn3(List<CategoryModel> devi, String dailycgoddevi) {
        binding.automativeShimmerLay.stopShimmer();
        binding.automativeShimmerLay.setVisibility(View.GONE);
        binding.dailynew3.setText(" ("+dailycgoddevi+")");
        binding.automativeCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn4(List<CategoryModel> devi, String dailycgoddevi) {
        binding.beautysalonShimmerLay.stopShimmer();
        binding.beautysalonShimmerLay.setVisibility(View.GONE);
        binding.dailynew4.setText(" ("+dailycgoddevi+")");
        binding.rvbeautysalonCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn5(List<CategoryModel> devi, String dailycgoddevi) {
        binding.buildingShimmerLay.stopShimmer();
        binding.buildingShimmerLay.setVisibility(View.GONE);
        binding.dailynew5.setText(" ("+dailycgoddevi+")");
        binding.rvbuildingCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn6(List<CategoryModel> devi, String dailycgoddevi) {
        binding.clothingtextShimmerLay.stopShimmer();
        binding.clothingtextShimmerLay.setVisibility(View.GONE);
        binding.dailynew6.setText(" ("+dailycgoddevi+")");
        binding.rvclothingtextCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn7(List<CategoryModel> devi, String dailycgoddevi) {
        binding.cunstructioncontracShimmerLay.stopShimmer();
        binding.cunstructioncontracShimmerLay.setVisibility(View.GONE);
        binding.dailynew7.setText(" ("+dailycgoddevi+")");
        binding.rvcunstructioncontracCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn8(List<CategoryModel> devi, String dailycgoddevi) {
        binding.customerservicecenShimmerLay.stopShimmer();
        binding.customerservicecenShimmerLay.setVisibility(View.GONE);
        binding.dailynew8.setText(" ("+dailycgoddevi+")");
        binding.rvcustomerservicecenCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn9(List<CategoryModel> devi, String dailycgoddevi) {
        binding.educationShimmerLay.stopShimmer();
        binding.educationShimmerLay.setVisibility(View.GONE);
        binding.dailynew9.setText(" ("+dailycgoddevi+")");
        binding.rveducationCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn10(List<CategoryModel> devi, String dailycgoddevi) {
        binding.electricalShimmerLay.stopShimmer();
        binding.electricalShimmerLay.setVisibility(View.GONE);
        binding.dailynew10.setText(" ("+dailycgoddevi+")");
        binding.rvelectricalCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn11(List<CategoryModel> devi, String dailycgoddevi) {
        binding.entertainmentShimmerLay.stopShimmer();
        binding.entertainmentShimmerLay.setVisibility(View.GONE);
        binding.dailynew11.setText(" ("+dailycgoddevi+")");
        binding.rventertainmentCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }
    private void setdaylyn12(List<CategoryModel> devi, String dailycgoddevi) {
        binding.fashionlifeShimmerLay.stopShimmer();
        binding.fashionlifeShimmerLay.setVisibility(View.GONE);
        binding.dailynew12.setText(" ("+dailycgoddevi+")");
        binding.rvfashionlifeCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn13(List<CategoryModel> devi, String dailycgoddevi) {
        binding.cookingmakingShimmerLay.stopShimmer();
        binding.cookingmakingShimmerLay.setVisibility(View.GONE);
        binding.dailynew13.setText(" ("+dailycgoddevi+")");
        binding.rvcookingmakingCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }
    private void setdaylyn14(List<CategoryModel> devi, String dailycgoddevi) {
        binding.preecoockedShimmerLay.stopShimmer();
        binding.preecoockedShimmerLay.setVisibility(View.GONE);
        binding.dailynew14.setText(" ("+dailycgoddevi+")");
        binding.rvpreecoockedCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn15(List<CategoryModel> devi, String dailycgoddevi) {
        binding.furnitureShimmerLay.stopShimmer();
        binding.furnitureShimmerLay.setVisibility(View.GONE);
        binding.dailynew15.setText(" ("+dailycgoddevi+")");
        binding.rvfurnitureCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }
    private void setdaylyn16(List<CategoryModel> devi, String dailycgoddevi) {
        binding.handloomShimmerLay.stopShimmer();
        binding.handloomShimmerLay.setVisibility(View.GONE);
        binding.dailynew16.setText(" ("+dailycgoddevi+")");
        binding.rvhandloomCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }
    private void setdaylyn17(List<CategoryModel> devi, String dailycgoddevi) {
        binding.hardwareShimmerLay.stopShimmer();
        binding.hardwareShimmerLay.setVisibility(View.GONE);
        binding.dailynew17.setText(" ("+dailycgoddevi+")");
        binding.rvhardware.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn18(List<CategoryModel> devi, String dailycgoddevi) {
        binding.homegardenShimmerLay.stopShimmer();
        binding.homegardenShimmerLay.setVisibility(View.GONE);
        binding.dailynew18.setText(" ("+dailycgoddevi+")");
        binding.rvhomegardenCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn19(List<CategoryModel> devi, String dailycgoddevi) {
        binding.hospitalclicnickShimmerLay.stopShimmer();
        binding.hospitalclicnickShimmerLay.setVisibility(View.GONE);
        binding.dailynew19.setText(" ("+dailycgoddevi+")");
        binding.rvhospitalclicnickCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn20(List<CategoryModel> devi, String dailycgoddevi) {
        binding.industrialequipmentShimmerLay.stopShimmer();
        binding.industrialequipmentShimmerLay.setVisibility(View.GONE);
        binding.dailynew20.setText(" ("+dailycgoddevi+")");
        binding.rvindustrialequipmentCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn21(List<CategoryModel> devi, String dailycgoddevi) {
        binding.insuranceShimmerLay.stopShimmer();
        binding.insuranceShimmerLay.setVisibility(View.GONE);
        binding.dailynew21.setText(" ("+dailycgoddevi+")");
        binding.rvinsuranceCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn22(List<CategoryModel> devi, String dailycgoddevi) {
        binding.interiorShimmerLay.stopShimmer();
        binding.interiorShimmerLay.setVisibility(View.GONE);
        binding.dailynew22.setText(" ("+dailycgoddevi+")");
        binding.rvinteriorCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn23(List<CategoryModel> devi, String dailycgoddevi) {
        binding.investmentShimmerLay.stopShimmer();
        binding.investmentShimmerLay.setVisibility(View.GONE);
        binding.dailynew23.setText(" ("+dailycgoddevi+")");
        binding.rvinvestmentCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn24(List<CategoryModel> devi, String dailycgoddevi) {
        binding.ithardwareShimmerLay.stopShimmer();
        binding.ithardwareShimmerLay.setVisibility(View.GONE);
        binding.dailynew24.setText(" ("+dailycgoddevi+")");
        binding.rvithardwareCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn25(List<CategoryModel> devi, String dailycgoddevi) {
        binding.jewllaryShimmerLay.stopShimmer();
        binding.jewllaryShimmerLay.setVisibility(View.GONE);
        binding.dailynew25.setText(" ("+dailycgoddevi+")");
        binding.rvjewllaryCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn26(List<CategoryModel> devi, String dailycgoddevi) {
        binding.laboretroyShimmerLay.stopShimmer();
        binding.laboretroyShimmerLay.setVisibility(View.GONE);
        binding.dailynew26.setText(" ("+dailycgoddevi+")");
        binding.rvlaboretroyCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn27(List<CategoryModel> devi, String dailycgoddevi) {
        binding.loanShimmerLay.stopShimmer();
        binding.loanShimmerLay.setVisibility(View.GONE);
        binding.dailynew27.setText(" ("+dailycgoddevi+")");
        binding.rvloanCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn28(List<CategoryModel> devi, String dailycgoddevi) {
        binding.mobilestoreShimmerLay.stopShimmer();
        binding.mobilestoreShimmerLay.setVisibility(View.GONE);
        binding.dailynew28.setText(" ("+dailycgoddevi+")");
        binding.rvmobilestoreCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn29(List<CategoryModel> devi, String dailycgoddevi) {
        binding.profassionalShimmerLay.stopShimmer();
        binding.profassionalShimmerLay.setVisibility(View.GONE);
        binding.dailynew29.setText(" ("+dailycgoddevi+")");
        binding.rvprofassionalCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn30(List<CategoryModel> devi, String dailycgoddevi) {
        binding.realestateShimmerLay.stopShimmer();
        binding.realestateShimmerLay.setVisibility(View.GONE);
        binding.dailynew30.setText(" ("+dailycgoddevi+")");
        binding.rvrealestateCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn31(List<CategoryModel> devi, String dailycgoddevi) {
        binding.securitysurvilanceShimmerLay.stopShimmer();
        binding.securitysurvilanceShimmerLay.setVisibility(View.GONE);
        binding.dailynew31.setText(" ("+dailycgoddevi+")");
        binding.rvsecuritysurvilanceCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn32(List<CategoryModel> devi, String dailycgoddevi) {
        binding.spiritualShimmerLay.stopShimmer();
        binding.spiritualShimmerLay.setVisibility(View.GONE);
        binding.dailynew32.setText(" ("+dailycgoddevi+")");
        binding.rvspiritualCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn33(List<CategoryModel> devi, String dailycgoddevi) {
        binding.toursandtravelsSimmer.stopShimmer();
        binding.toursandtravelsSimmer.setVisibility(View.GONE);
        binding.dailynew33.setText(" ("+dailycgoddevi+")");
        binding.rvtoursandtravels.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn34(List<CategoryModel> devi, String dailycgoddevi) {
        binding.ttinternationalShimmerLay.stopShimmer();
        binding.ttinternationalShimmerLay.setVisibility(View.GONE);
        binding.dailynew34.setText(" ("+dailycgoddevi+")");
        binding.rvttinternationalCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn35(List<CategoryModel> devi, String dailycgoddevi) {
        binding.transportationShimmerLay.stopShimmer();
        binding.transportationShimmerLay.setVisibility(View.GONE);
        binding.dailynew35.setText(" ("+dailycgoddevi+")");
        binding.rvtransportationCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn36(List<CategoryModel> devi, String dailycgoddevi) {
        binding.visaimigrationShimmerLay.stopShimmer();
        binding.visaimigrationShimmerLay.setVisibility(View.GONE);
        binding.dailynew36.setText(" ("+dailycgoddevi+")");
        binding.rvvisaimigrationCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }

    private void setdaylyn37(List<CategoryModel> devi, String dailycgoddevi) {
        binding.healthgymwellShimmerLay.stopShimmer();
        binding.healthgymwellShimmerLay.setVisibility(View.GONE);
        binding.dailynew37.setText(" ("+dailycgoddevi+")");
        binding.rvhealthgymwellCategory.setAdapter(new UpcomingImagesAdapter(context, devi,false));
    }
}

package com.sendpost.dreamsoft.NavFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.android.material.navigation.NavigationView;
import com.sendpost.dreamsoft.Account.EditProfileFragment;
import com.sendpost.dreamsoft.BusinessCard.BusinessCardActivity;
import com.sendpost.dreamsoft.Fragments.AddBussinessFragment;
import com.sendpost.dreamsoft.Fragments.ReferFragment;
import com.sendpost.dreamsoft.HomeActivity;
import com.sendpost.dreamsoft.PosterActivity;
import com.sendpost.dreamsoft.WebviewA;
import com.sendpost.dreamsoft.adapter.OccasionAdapter;
import com.sendpost.dreamsoft.adapter.RecentPostsAdapter;
import com.sendpost.dreamsoft.adapter.SlidingAdapter;
import com.sendpost.dreamsoft.adapter.UpcomingEventAdapter;
import com.sendpost.dreamsoft.BuildConfig;
import com.sendpost.dreamsoft.BusinessCard.BusinessCardActivity_Digital;
import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.adapter.UpcomingGodsAdapter;
import com.sendpost.dreamsoft.adapter.UpcomingRelationAdapter;
import com.sendpost.dreamsoft.binding.BindingAdaptet;
import com.sendpost.dreamsoft.databinding.FragmentHomeBinding;
import com.sendpost.dreamsoft.dialog.LanguageDialogFragment;
import com.sendpost.dreamsoft.Fragments.ContactFragment;
import com.sendpost.dreamsoft.Fragments.SearchFragment;
import com.sendpost.dreamsoft.Interface.AdapterClickListener;
import com.sendpost.dreamsoft.MainActivity;
import com.sendpost.dreamsoft.model.CategoryModel;
import com.sendpost.dreamsoft.model.OfferDialogModel;
import com.sendpost.dreamsoft.model.PostsModel;
import com.sendpost.dreamsoft.MyPostsActivity;
import com.sendpost.dreamsoft.PosterPreviewActivity;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.model.SliderModel;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    Context context;
    StaggeredGridLayoutManager layoutManager;
    FragmentHomeBinding binding;
    int pageCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Functions.setLocale(Functions.getSharedPreference(getContext()).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), getActivity(), MainActivity.class,false);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        Log.d("language",Functions.getSharedPreference(getContext()).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE));
        return binding.getRoot();
    }

    boolean loading = true;
    View view;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        this.view = view;

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
                transaction.addToBackStack(null);
                transaction.replace(android.R.id.content, new SearchFragment()).commit();
            }
        });

        binding.languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguageDialog();
            }
        });

        binding.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLay.openDrawer(Gravity.LEFT);
            }
        });

//        binding.viewAllFestivalCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, CategoriesActivity.class).putExtra("type","festival"));
//            }
//        });

//        binding.viewAllBusinessCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, CategoriesActivity.class).putExtra("type","business"));
//            }
//        });

        getHomeData();
        iniNavigation(view);
    }

    private void showLanguageDialog() {
        new LanguageDialogFragment().show(getFragmentManager(),"");
    }

    private void iniNavigation(View view) {
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.navigation);
        View headerview = navigationView.getHeaderView(0);
        LinearLayout pinwallet = (LinearLayout) headerview.findViewById(R.id.my_wallate_btn);
        LinearLayout mypost_btn = (LinearLayout) headerview.findViewById(R.id.my_post_btn);
        LinearLayout edit_btn = (LinearLayout) headerview.findViewById(R.id.edit_btn);
        LinearLayout bussiness_lay = (LinearLayout) headerview.findViewById(R.id.bussiness_lay);
        LinearLayout privacy_policy_lay = (LinearLayout) headerview.findViewById(R.id.privacy_policy_lay);
        LinearLayout terms_lay = (LinearLayout) headerview.findViewById(R.id.terms_lay);
        LinearLayout share_btn = (LinearLayout) headerview.findViewById(R.id.share_btn);
        LinearLayout rate_btn = (LinearLayout) headerview.findViewById(R.id.rate_btn);
        LinearLayout logout_btn = (LinearLayout) headerview.findViewById(R.id.logOut_btn);
        LinearLayout contact_btn = (LinearLayout) headerview.findViewById(R.id.contact_btn);
        LinearLayout businesscard_btn = (LinearLayout) headerview.findViewById(R.id.businesscard_btn);
        LinearLayout businesscard_visiting_btn = (LinearLayout) headerview.findViewById(R.id.businesscard_visiting_btn);
        ToggleButton toggleButton = (ToggleButton) headerview.findViewById(R.id.toggleButton);
        TextView version_tv = (TextView) headerview.findViewById(R.id.version_tv);

        if (Functions.getSharedPreference(getActivity()).getBoolean(Variables.NIGHT_MODE,false)){
            toggleButton.setChecked(true);
        }

        toggleButton.setOnCheckedChangeListener((compoundButton, b) -> {
            Functions.getSharedPreference(getActivity()).edit().putBoolean(Variables.NIGHT_MODE,b).apply();
            if (b){
                toggleButton.setChecked(true);
            }else {
                toggleButton.setChecked(false);
            }
            startActivity(new Intent(context,MainActivity.class));
        });

        version_tv.setText("Version " + BuildConfig.VERSION_NAME);

        businesscard_btn.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            startActivity(new Intent(context, BusinessCardActivity_Digital.class));
        });

        businesscard_visiting_btn.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            startActivity(new Intent(context, BusinessCardActivity.class));
        });

        contact_btn.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            showContactFragment();
        });

        pinwallet.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            startActivity(new Intent(getActivity(), WalletePager.class));
        });

        mypost_btn.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            startActivity(new Intent(getActivity(), MyPostsActivity.class));
        });

        edit_btn.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            if (HomeActivity.fragmentManager.isDestroyed()){
                HomeActivity.fragmentManager = getFragmentManager();
            }
            FragmentTransaction transaction = HomeActivity.fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
            transaction.addToBackStack(null);
            transaction.replace(R.id.home_activity, new EditProfileFragment()).commit();
        });

        bussiness_lay.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            startActivity(new Intent(getActivity(), AddBussinessFragment.class));
//          startActivity(new Intent(getActivity(), MyBussinessActivity.class));
        });

        privacy_policy_lay.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            Intent intent = new Intent(getContext(), WebviewA.class);
            intent.putExtra("title", getString(R.string.privacy_policy));
            startActivity(intent);
        });

        terms_lay.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            Intent intent = new Intent(getContext(), WebviewA.class);
            intent.putExtra("title", getString(R.string.terms_service));
            startActivity(intent);
        });

        share_btn.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
//            Functions.shareApp(getContext());
            ReferFragment comment_f = new ReferFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
            transaction.addToBackStack(null);
            transaction.replace(android.R.id.content, comment_f).commit();
        });

        rate_btn.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            Functions.rateApp(getContext());
        });

        logout_btn.setOnClickListener(v -> {
            binding.drawerLay.closeDrawer(Gravity.LEFT);
            Functions.logOut(getActivity());
        });
    }

    private void showContactFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        transaction.addToBackStack(null);
        transaction.replace(android.R.id.content, new ContactFragment()).commit();
    }

    private void showPremiumFragment() {
        PremiumFragment comment_f = new PremiumFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        Bundle args = new Bundle();
        args.putString("from", "preview");
        comment_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(android.R.id.content, comment_f).commit();
    }

    HomeViewModel homeViewModel;

    private void getHomeData() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getData().observe(getViewLifecycleOwner(), new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {
                if (homeResponse != null){
                    if (homeResponse.sliderdata.size() > 0){
                        setSliderAdapter(homeResponse.sliderdata);
                    }
                    if (homeResponse.upcoming_event.size() > 0){
                        setUpcomingCategoryAdapter(homeResponse.upcoming_event,homeResponse.upcoming_eventcount);
                    }
                    if (homeResponse.days.size() > 0){
                        setUpcomingdaysAdapter(homeResponse.days,homeResponse.dayscount);
                    }
                    if (homeResponse.motivational.size() > 0){
                        setmotivational(homeResponse.motivational,homeResponse.motivationalcount);
                    }
                    if (homeResponse.leaderthought.size() > 0){
                        setleaderthought(homeResponse.leaderthought,homeResponse.leaderthoughtcount);
                    }
                    if (homeResponse.gods_day.size() > 0){
                        setGodsCategoryAdapter(homeResponse.gods_day,homeResponse.gods_daycount);
                    }
                    if (homeResponse.relation.size() > 0){
                        setRelationCategoryAdapter(homeResponse.relation,homeResponse.relationcount);
                    }
                    if (homeResponse.occasion.size() > 0){
                        setoccasionCategoryAdapter(homeResponse.occasion,homeResponse.occasioncount);
                    }

                    if (homeResponse.section.size() > 0){
//                        setSectionAdapter(homeResponse.section);
                    }
                    if (homeResponse.festival_category.size() > 0){
                        setFestivalCategoryAdapter(homeResponse.festival_category,homeResponse.festival_categorycount);
                    }
                    if (homeResponse.business_category.size() > 0){
//                        setBussinessCategoryAdapter(homeResponse.business_category);
                    }
                    if (homeResponse.recent.size() > 0){
                        setPostsAdapter(homeResponse.recent,homeResponse.recentcount);
                    }
                    if (homeResponse.offerdialog != null){
                        showOfferDialog(homeResponse.offerdialog);
                    }
                }
            }
        });
    }

    private void showOfferDialog(OfferDialogModel offerdialog) {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setAttributes(getLayoutParams(dialog));
        dialog.setContentView(R.layout.offerdialog);
        ImageView imageView = dialog.findViewById(R.id.dialog_image);
        ImageButton close_btn = dialog.findViewById(R.id.close_btn);
        BindingAdaptet.setImageUrl(imageView,offerdialog.item_url);
        dialog.setCancelable(false);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offerdialog.action.equals(Constants.URL)){
                    Intent intent = new Intent(context, WebviewA.class);
                    intent.putExtra("url", offerdialog.action_item);
                    intent.putExtra("title", getString(R.string.app_name));
                    startActivity(intent);
                }else if (offerdialog.action.equals(Constants.SUBSCRIPTION)){
                    showPremiumFragment();
                }else if (offerdialog.action.equals(Constants.CATEGORY)){
                    context.startActivity(new Intent(context, PosterActivity.class).putExtra("title",getString(R.string.app_name)).putExtra("type","category").putExtra("item_id",offerdialog.action_item));
                }
            }
        });
        dialog.show();
    }

    public void setSliderAdapter(List<SliderModel> sliderdata) {
        binding.sliderShimmerLay.stopShimmer();
        binding.sliderShimmerLay.setVisibility(View.GONE);
        binding.viewPager.setAdapter(new SlidingAdapter(getActivity(), sliderdata, new AdapterClickListener() {
            @Override
            public void onItemClick(View view, int pos, Object object) {
                SliderModel sliderdata = (SliderModel) object;
                if (sliderdata.action.equals(Constants.CATEGORY)){
                    Intent intent = new Intent(context, WebviewA.class);
                    intent.putExtra("url", sliderdata.action_item);
                    intent.putExtra("title", getString(R.string.app_name));
                    startActivity(intent);
                }else if (sliderdata.action.equals(Constants.SUBSCRIPTION)){
                    showPremiumFragment();
                }else if (sliderdata.action.equals(Constants.URL)){
                    context.startActivity(new Intent(context, PosterActivity.class).putExtra("title",getString(R.string.app_name)).putExtra("type","category").putExtra("item_id",sliderdata.action_item));
                }
            }
        }));
        binding.viewPager.startAutoScroll();
    }

    private void setmotivational(List<CategoryModel> custom_category, int motivationalcount) {
        binding.motivationShimmerLay.stopShimmer();
        binding.motivationShimmerLay.setVisibility(View.GONE);
        binding.motivationtxt.setText(getResources().getString(R.string.Motivational)+" ("+motivationalcount+")");
        binding.rvmotivation.setAdapter(new UpcomingRelationAdapter(context, custom_category,false));
    }

    @SuppressLint("SetTextI18n")
    private void setleaderthought(List<CategoryModel> custom_category, int leaderthoughtcount) {
        binding.leaderthoughtShimmerLay.stopShimmer();
        binding.leaderthoughtShimmerLay.setVisibility(View.GONE);
        binding.leadertxt.setText(getResources().getString(R.string.leader_thought)+" ("+leaderthoughtcount+")");
        binding.rvleaderthought.setAdapter(new UpcomingRelationAdapter(context, custom_category,false));
    }

    @SuppressLint("SetTextI18n")
    private void setGodsCategoryAdapter(List<CategoryModel> gods_day, int gods_daycount) {
        binding.godsShimmerLay.stopShimmer();
        binding.godsShimmerLay.setVisibility(View.GONE);
        binding.weekdaytxt.setText(getResources().getString(R.string.upcoming_days)+" ("+gods_daycount+")");
        binding.rvUpcominggods.setAdapter(new UpcomingGodsAdapter(context, gods_day, false));
    }

    @SuppressLint("SetTextI18n")
    private void setRelationCategoryAdapter(List<CategoryModel> gods_day, int relationcount) {
        binding.relationShimmerLay.stopShimmer();
        binding.relationShimmerLay.setVisibility(View.GONE);
        binding.relationtxt.setText(getResources().getString(R.string.upcoming_relation)+" ("+relationcount+")");
        binding.rvUpcomingrelation.setAdapter(new UpcomingRelationAdapter(context, gods_day, false));
    }

    @SuppressLint("SetTextI18n")
    private void setoccasionCategoryAdapter(List<CategoryModel> occasion, int occasioncount) {
        binding.occasionShimmerLay.stopShimmer();
        binding.occasionShimmerLay.setVisibility(View.GONE);
        binding.ocationtxt.setText(getResources().getString(R.string.occasion)+" ("+occasioncount+")");
        binding.rvoccasion.setAdapter(new OccasionAdapter(context, occasion, false));
    }

    private void setFestivalCategoryAdapter(List<CategoryModel> festival_category, int festival_categorycount) {
//        binding.festivalCategoryShimmerLay.stopShimmer();
//        binding.festivalCategoryShimmerLay.setVisibility(View.GONE);
//        binding.rvCategory.setAdapter(new FestivalCategoryAdapter(context, festival_category, false));
    }

//    private void setBussinessCategoryAdapter(List<CategoryModel> business_category) {
//        binding.rvBussinessCategory.setAdapter(new BussinessCategoryAdapter(context, business_category));
//    }

    @SuppressLint("SetTextI18n")
    private void setUpcomingCategoryAdapter(List<CategoryModel> upcoming_event, int upcoming_eventcount) {
        binding.upcomingFestivalShimmerLay.stopShimmer();
        binding.upcomingFestivalShimmerLay.setVisibility(View.GONE);
        binding.upcomingtxt.setText(getResources().getString(R.string.upcoming_festivals)+" ("+upcoming_eventcount+")");
        binding.rvUpcomingFestival.setAdapter(new UpcomingEventAdapter(context, upcoming_event, false));
    }

    @SuppressLint("SetTextI18n")
    private void setUpcomingdaysAdapter(List<CategoryModel> days, int dayscount) {
        binding.daysShimmerLay.stopShimmer();
        binding.daysShimmerLay.setVisibility(View.GONE);
        binding.upcomingdaytxt.setText(getResources().getString(R.string.upcoming_h_days)+" ("+dayscount+")");
        binding.rvUpcomingDays.setAdapter(new UpcomingEventAdapter(context, days,false));
    }

//    private void setSectionAdapter(List<SectionModel> section) {
//        binding.rvSection.setAdapter(new SectionAdapter(context, section, new SectionAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, List<PostsModel> list, int main_position, int child_position) {
//                PosterPreviewActivity.postsModel = list.get(child_position);
//                startActivity(new Intent(context, PosterPreviewActivity.class).putExtra("type", "Posts"));
//            }
//        }, true, false));
//    }

    private void setPostsAdapter(List<PostsModel> recent, int recentcount) {
        binding.newestShimmerLay.stopShimmer();
        binding.newestShimmerLay.setVisibility(View.GONE);
        binding.rvNewest.setAdapter(new RecentPostsAdapter(context, recent, new RecentPostsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, PostsModel postsModels, int main_position) {
                PosterPreviewActivity.postsModel = postsModels;
                startActivity(new Intent(context, PosterPreviewActivity.class).putExtra("type", "Posts"));
            }
        }));
    }

    private WindowManager.LayoutParams getLayoutParams(@NonNull Dialog dialog) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (dialog.getWindow() != null) {
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
        }
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        return layoutParams;
    }
}
















